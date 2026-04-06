import 'package:flutter/material.dart';
import 'login_screen.dart';   // Importamos la pantalla de login

void main() {
  runApp(const LaPagodaApp());
}

class LaPagodaApp extends StatelessWidget {
  const LaPagodaApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'La Pagoda POS',
      theme: ThemeData.dark().copyWith(
        primaryColor: Colors.amber,
        scaffoldBackgroundColor: Colors.black,
      ),
      home: const LoginScreen(), // Pantalla inicial
    );
  }
}
