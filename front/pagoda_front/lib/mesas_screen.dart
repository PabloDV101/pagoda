import 'package:flutter/material.dart';

class MesasScreen extends StatefulWidget {
  const MesasScreen({super.key});

  @override
  State<MesasScreen> createState() => _MesasScreenState();
}

class _MesasScreenState extends State<MesasScreen> {
  // Lista de mesas
  final List<Map<String, String>> mesas = [
    {"nombre": "Mesa 1", "estado": "Libre"},
    {"nombre": "Mesa 2", "estado": "Libre"},
    {"nombre": "Mesa 3", "estado": "Libre"},
    {"nombre": "Mesa 4", "estado": "Libre"},
  ];

  // Obtener color según estado
  Color _colorPorEstado(String estado) {
    switch (estado) {
      case "Libre":
        return Colors.green;
      case "Ocupado":
        return Colors.red;
      case "Pedir Cuenta":
        return Colors.yellow;
      case "Limpiando":
        return Colors.orange;
      default:
        return Colors.grey;
    }
  }

  //cambiar estado de una mesa
  void _cambiarEstado(int index) {
    showDialog(
      context: context,
      builder: (context) {
        return SimpleDialog(
          title: Text("Cambiar estado de ${mesas[index]["nombre"]}"),
          children: [
            SimpleDialogOption(
              onPressed: () {
                setState(() => mesas[index]["estado"] = "Libre");
                Navigator.pop(context);
              },
              child: const Text("Libre (Verde)"),
            ),
            SimpleDialogOption(
              onPressed: () {
                setState(() => mesas[index]["estado"] = "Ocupado");
                Navigator.pop(context);
              },
              child: const Text("Ocupado (Rojo)"),
            ),
            SimpleDialogOption(
              onPressed: () {
                setState(() => mesas[index]["estado"] = "Pedir Cuenta");
                Navigator.pop(context);
              },
              child: const Text("Pedir Cuenta (Amarillo)"),
            ),
            SimpleDialogOption(
              onPressed: () {
                setState(() => mesas[index]["estado"] = "Limpiando");
                Navigator.pop(context);
              },
              child: const Text("Limpiando (Naranja)"),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Estado de Mesas"),
        backgroundColor: Colors.black,
      ),
      body: GridView.builder(
        padding: const EdgeInsets.all(16),
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 2, // 2 columnas
          crossAxisSpacing: 12,
          mainAxisSpacing: 12,
        ),
        itemCount: mesas.length,
        itemBuilder: (context, index) {
          final mesa = mesas[index];
          return GestureDetector(
            onTap: () => _cambiarEstado(index),
            child: Card(
              color: _colorPorEstado(mesa["estado"]!),
              child: Center(
                child: Text(
                  "${mesa["nombre"]}\n${mesa["estado"]}",
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
