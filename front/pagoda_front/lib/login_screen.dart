import 'package:flutter/material.dart';
import 'mesas_screen.dart'; // Importamos pantalla de mesas

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  String pin = "";

  void _addDigit(String digit) {
    if (pin.length < 4) {
      setState(() {
        pin += digit;
      });
    }

    // Cuando ya se ingresaron los 4 dígitos, validamos
    if (pin.length == 4) {
      if (pin == "1234") {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const MesasScreen()),
        );
      } else {
        // Mostrar mensaje de PIN incorrecto
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("PIN incorrecto")),
        );
        setState(() {
          pin = "";
        });
      }
    }
  }


  void _deleteDigit() {
    if (pin.isNotEmpty) {
      setState(() {
        pin = pin.substring(0, pin.length - 1);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text("LA PAGODA", style: TextStyle(fontSize: 28, color: Colors.amber)),
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: List.generate(4, (index) {
                return Container(
                  margin: const EdgeInsets.all(8),
                  width: 20,
                  height: 20,
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    color: index < pin.length ? Colors.amber : Colors.grey,
                  ),
                );
              }),
            ),
            const SizedBox(height: 20),
            const Text("Ingrese su PIN", style: TextStyle(color: Colors.white)),
            const SizedBox(height: 20),
            _buildKeypad(),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _deleteDigit,
              style: ElevatedButton.styleFrom(backgroundColor: Colors.amber),
              child: const Text("BORRAR"),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildKeypad() {
    final digits = [
      ["1", "2", "3"],
      ["4", "5", "6"],
      ["7", "8", "9"],
      ["", "0", ""],
    ];

    return Column(
      children: digits.map((row) {
        return Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: row.map((digit) {
            if (digit.isEmpty) return const SizedBox(width: 60);
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: ElevatedButton(
                onPressed: () => _addDigit(digit),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.grey[800],
                  minimumSize: const Size(60, 60),
                ),
                child: Text(digit, style: const TextStyle(fontSize: 20)),
              ),
            );
          }).toList(),
        );
      }).toList(),
    );
  }
}
