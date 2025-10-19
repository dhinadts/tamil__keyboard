import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Tamil Keyboard Demo',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const KeyboardDemoScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class KeyboardDemoScreen extends StatefulWidget {
  const KeyboardDemoScreen({super.key});

  @override
  State<KeyboardDemoScreen> createState() => _KeyboardDemoScreenState();
}

class _KeyboardDemoScreenState extends State<KeyboardDemoScreen> {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Tamil Keyboard Demo'),
        actions: [
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: () {
              // Open keyboard settings
            },
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Demo:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Expanded(
              child: TextField(
                controller: _controller,
                maxLines: null,
                expands: true,
                textAlignVertical: TextAlignVertical.top,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  hintText:
                      'Switch to Tamil Keyboard in system settings to type in Tamil...\n\nInstructions:\n1. Go to Settings → System → Languages & input\n2. Tap "Virtual keyboard"\n3. Tap "Manage keyboards"\n4. Enable "Tamil Keyboard"\n5. In any app, tap text field and select Tamil Keyboard from input method',
                ),
              ),
            ),
            const SizedBox(height: 20),
            const Text(
              'Note: This is a demo screen. To use the Tamil keyboard in other apps:\n• Enable it in system settings\n• Select it from the input method menu when typing',
              style: TextStyle(color: Colors.grey, fontSize: 12),
            ),
          ],
        ),
      ),
    );
  }
}
