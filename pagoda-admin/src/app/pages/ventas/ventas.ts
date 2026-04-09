import { Component, signal, computed, inject, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { PeriodoVentas, ResumenVentasDiario, Venta } from '../../models/index';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { VentasService } from '../../services/ventas';
import { JornadaService } from '../../services/jornada';

@Component({
  selector: 'app-ventas',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './ventas.html',
  styleUrl: './ventas.scss'
})
export class Ventas implements OnInit {
  private ventasService = inject(VentasService);
  private jornadaService = inject(JornadaService);

  periodo = signal<PeriodoVentas>('diario');

  resumen = signal<ResumenVentasDiario>({
    jornada_id: 0,
    total_ventas: 0,
    total_efectivo: 0,
    total_tarjeta_bruto: 0,
    total_tarjeta_neto: 0,
    total_transacciones: 0,
    fondo_caja: 0
  });

  ventas = signal<Venta[]>([]);
  expandidas = signal<Set<number>>(new Set());

  cargando = signal(false);
  error = signal<string | null>(null);

  ngOnInit() {
    this.cargarVentas();
  }

  cargarVentas() {
    this.cargando.set(true);
    const jornada = this.jornadaService.jornadaActual();
    
    if (jornada && jornada.id) {
      this.cargarDatos(jornada.id);
    } else {
      this.cargarJornadaYVentas();
    }
  }

  cargarJornadaYVentas() {
    this.jornadaService.cargar().subscribe({
      next: (jornada) => {
        if (jornada && jornada.id) {
          this.cargarDatos(jornada.id);
        }
      },
      error: () => {
        this.error.set('Error al cargar la jornada');
        this.cargando.set(false);
      }
    });
  }

  cargarDatos(jornadaId: number) {
    this.ventasService.getResumenVentas(jornadaId).subscribe({
      next: (resumen) => {
        if (Array.isArray(resumen) && resumen.length > 0) {
          this.resumen.set(resumen[0]); // Tomar el primer resumen
        }
      },
      error: () => {
        this.error.set('Error al cargar el resumen de ventas');
      }
    });

    this.ventasService.getVentasActivas().subscribe({
      next: (data) => {
        this.ventas.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('Error al cargar las ventas');
        this.cargando.set(false);
      }
    });
  }

  setPeriodo(p: PeriodoVentas) {
    this.periodo.set(p);
  }

  toggleVenta(id: number) {
    const set = new Set(this.expandidas());
    set.has(id) ? set.delete(id) : set.add(id);
    this.expandidas.set(set);
  }

  isExpandida(id: number): boolean {
    return this.expandidas().has(id);
  }

  getTotalNeto(venta: Venta): number {
    return venta.pagos?.reduce((s, p) => s + p.monto_neto, 0) ?? 0;
  }

  esTarjeta(venta: Venta): boolean {
    return venta.pagos?.some(p => p.metodo_pago?.nombre === 'TARJETA') ?? false;
  }

  getComision(venta: Venta): number {
    return venta.pagos?.find(p => p.metodo_pago?.nombre === 'TARJETA')?.comision_porcentaje ?? 0;
  }

  exportarPDF() {
    const doc = new jsPDF();
    const resumen = this.resumen();
    const ventas = this.ventas();
    const periodo = this.periodo();

    // ── Encabezado ──────────────────────────────────────
    doc.setFontSize(20);
    doc.setFont('helvetica', 'bold');
    doc.text('Pagoda — Reporte de Ventas', 14, 20);

    doc.setFontSize(11);
    doc.setFont('helvetica', 'normal');
    doc.setTextColor(100);
    doc.text(`Periodo: ${periodo.charAt(0).toUpperCase() + periodo.slice(1)}`, 14, 28);
    doc.text(`Generado: ${new Date().toLocaleDateString('es-MX')}`, 14, 34);

    // ── Total ────────────────────────────────────────────
    doc.setFontSize(14);
    doc.setFont('helvetica', 'bold');
    doc.setTextColor(0);
    doc.text(`Total de Ventas: $${resumen.total_ventas.toLocaleString('es-MX')}`, 14, 46);

    // ── Métricas ─────────────────────────────────────────
    autoTable(doc, {
      startY: 52,
      head: [['Fondo Inicial', 'Total Efectivo', 'Total Tarjeta Bruto', 'Total Tarjeta Neto']],
      body: [[
        `$${resumen.fondo_caja.toLocaleString('es-MX')}`,
        `$${resumen.total_efectivo.toLocaleString('es-MX')}`,
        `$${resumen.total_tarjeta_bruto.toLocaleString('es-MX')}`,
        `$${resumen.total_tarjeta_neto.toLocaleString('es-MX')}`,
      ]],
      styles: { fontSize: 11 },
      headStyles: { fillColor: [17, 24, 39] },
    });

    // ── Tabla de ventas ───────────────────────────────────
    const filas: any[] = [];

    ventas.forEach(venta => {
      // Fila encabezado de mesa
      filas.push([
        `Mesa ${venta.mesa?.numero} — Pedido ${venta.id}`,
        `$${venta.total_cuenta.toLocaleString('es-MX')}`,
        `$${this.getTotalNeto(venta).toLocaleString('es-MX')}`,
      ]);

      // Items
      venta.items?.forEach(item => {
        filas.push([
          `    ${item.producto?.nombre}`,
          `$${item.precio_unitario.toLocaleString('es-MX')}`,
          '—',
        ]);
      });

      // Pagos
      venta.pagos?.forEach(pago => {
        const metodo = pago.metodo_pago?.nombre ?? '';
        const comision = metodo === 'TARJETA' ? ` (-${pago.comision_porcentaje}%)` : '';
        filas.push([
          `      • ${metodo}${comision}`,
          `$${pago.monto.toLocaleString('es-MX')}`,
          `$${pago.monto_neto.toLocaleString('es-MX')}`,
        ]);
      });
    });

    autoTable(doc, {
      startY: (doc as any).lastAutoTable.finalY + 12,
      head: [['Descripción', 'Total Bruto', 'Total Neto']],
      body: filas,
      styles: { fontSize: 11 },
      headStyles: { fillColor: [17, 24, 39] },
      columnStyles: {
        1: { halign: 'right' },
        2: { halign: 'right' },
      },
      didParseCell: (data) => {
        // Negritas en filas de mesa
        if (data.row.index >= 0 && data.section === 'body') {
          const texto = String(data.cell.raw ?? '');
          if (texto.startsWith('Mesa')) {
            data.cell.styles.fontStyle = 'bold';
          }
        }
      }
    });

    // ── Pie de página ─────────────────────────────────────
    const pageCount = doc.getNumberOfPages();
    for (let i = 1; i <= pageCount; i++) {
      doc.setPage(i);
      doc.setFontSize(9);
      doc.setTextColor(150);
      doc.text(
        `Página ${i} de ${pageCount}`,
        doc.internal.pageSize.width / 2,
        doc.internal.pageSize.height - 10,
        { align: 'center' }
      );
    }

    doc.save(`reporte-ventas-${periodo}-${new Date().toISOString().slice(0, 10)}.pdf`);
  }
}