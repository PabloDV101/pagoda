import { Component, signal, computed, inject, OnInit } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { PeriodoVentas, ResumenVentasDiario, Venta } from '../../models/index';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-ventas',
  standalone: true,
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './ventas.html',
  styleUrl: './ventas.scss'
})
export class Ventas implements OnInit {

  periodo = signal<PeriodoVentas>('diario');

  resumen = signal<ResumenVentasDiario>({
    jornada_id: 1,
    total_ventas: 48337,
    total_efectivo: 24745,
    total_tarjeta_bruto: 22375,
    total_tarjeta_neto: 21592,
    total_transacciones: 12,
    fondo_caja: 2000
  });

  ventas = signal<Venta[]>([
    {
      id: 1, mesa_id: 1, usuario_id: 1, jornada_id: 1,
      num_comensales: 2, tipo_cobro_id: 1, total_cuenta: 320,
      comision_porcentaje: 0, fecha_creacion: '2026-04-03T10:00:00',
      mesa: { id: 1, numero: 1, capacidad: 4, estado_id: 1 },
      items: [
        { id: 1, venta_id: 1, producto_id: 1, numero_comensal: 1, precio_unitario: 145, cantidad: 1, estado_item_id: 2, producto: { id: 1, nombre: 'Pad Thai', precio: 145, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } } },
        { id: 2, venta_id: 1, producto_id: 2, numero_comensal: 1, precio_unitario: 50, cantidad: 1, estado_item_id: 2, producto: { id: 2, nombre: 'Spring Rolls', precio: 50, categoria_id: 2, activo: true, fecha_creacion: '', categoria: { id: 2, nombre: 'Entradas' } } },
        { id: 3, venta_id: 1, producto_id: 3, numero_comensal: 2, precio_unitario: 125, cantidad: 1, estado_item_id: 2, producto: { id: 3, nombre: 'Tom Yum Goong', precio: 125, categoria_id: 3, activo: true, fecha_creacion: '', categoria: { id: 3, nombre: 'Sopas' } } },
      ],
      pagos: [
        { id: 1, venta_id: 1, monto: 320, comision_porcentaje: 0, monto_neto: 320, propina_monto: 0, propina_neto: 0, metodo_pago_id: 1, metodo_pago: { id: 1, nombre: 'EFECTIVO' } }
      ]
    },
    {
      id: 2, mesa_id: 3, usuario_id: 1, jornada_id: 1,
      num_comensales: 2, tipo_cobro_id: 1, total_cuenta: 255,
      comision_porcentaje: 3.5, fecha_creacion: '2026-04-03T11:00:00',
      mesa: { id: 3, numero: 3, capacidad: 4, estado_id: 1 },
      items: [
        { id: 4, venta_id: 2, producto_id: 4, numero_comensal: 1, precio_unitario: 155, cantidad: 1, estado_item_id: 2, producto: { id: 4, nombre: 'Green Curry', precio: 155, categoria_id: 1, activo: true, fecha_creacion: '', categoria: { id: 1, nombre: 'Platos Principales' } } },
        { id: 5, venta_id: 2, producto_id: 5, numero_comensal: 1, precio_unitario: 75, cantidad: 1, estado_item_id: 2, producto: { id: 5, nombre: 'Mango Sticky Rice', precio: 75, categoria_id: 4, activo: true, fecha_creacion: '', categoria: { id: 4, nombre: 'Postres' } } },
        { id: 6, venta_id: 2, producto_id: 6, numero_comensal: 2, precio_unitario: 25, cantidad: 1, estado_item_id: 2, producto: { id: 6, nombre: 'Satay de Pollo', precio: 25, categoria_id: 2, activo: true, fecha_creacion: '', categoria: { id: 2, nombre: 'Entradas' } } },
      ],
      pagos: [
        { id: 2, venta_id: 2, monto: 255, comision_porcentaje: 3.5, monto_neto: 246, propina_monto: 0, propina_neto: 0, metodo_pago_id: 2, metodo_pago: { id: 2, nombre: 'TARJETA' } }
      ]
    },
    {
      id: 3, mesa_id: 2, usuario_id: 1, jornada_id: 1,
      num_comensales: 3, tipo_cobro_id: 1, total_cuenta: 410,
      comision_porcentaje: 0, fecha_creacion: '2026-04-03T12:00:00',
      mesa: { id: 2, numero: 2, capacidad: 6, estado_id: 1 },
      items: [],
      pagos: [
        { id: 3, venta_id: 3, monto: 410, comision_porcentaje: 0, monto_neto: 410, propina_monto: 0, propina_neto: 0, metodo_pago_id: 1, metodo_pago: { id: 1, nombre: 'EFECTIVO' } }
      ]
    },
    {
      id: 4, mesa_id: 4, usuario_id: 1, jornada_id: 1,
      num_comensales: 2, tipo_cobro_id: 1, total_cuenta: 480,
      comision_porcentaje: 3.5, fecha_creacion: '2026-04-03T13:00:00',
      mesa: { id: 4, numero: 4, capacidad: 4, estado_id: 1 },
      items: [],
      pagos: [
        { id: 4, venta_id: 4, monto: 480, comision_porcentaje: 3.5, monto_neto: 463, propina_monto: 0, propina_neto: 0, metodo_pago_id: 2, metodo_pago: { id: 2, nombre: 'TARJETA' } }
      ]
    }
  ]);

  expandidas = signal<Set<number>>(new Set());

  ngOnInit() { }

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