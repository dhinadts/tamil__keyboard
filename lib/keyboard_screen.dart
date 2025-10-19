import 'package:flutter/material.dart';
import 'tamil_keyboard.dart';

class KeyboardScreen extends StatefulWidget {
  const KeyboardScreen({super.key});

  @override
  State<KeyboardScreen> createState() => _KeyboardScreenState();
}

class _KeyboardScreenState extends State<KeyboardScreen> {
  final TextEditingController _controller = TextEditingController();
  bool _isShift = false;
  bool _isSymbol = false;

  void _onTextInput(String text) {
    final currentText = _controller.text;
    final selection = _controller.selection;

    final newText = currentText.replaceRange(
      selection.start,
      selection.end,
      text,
    );

    _controller.value = _controller.value.copyWith(
      text: newText,
      selection: TextSelection.collapsed(offset: selection.start + text.length),
    );
  }

  void _onBackspace() {
    final currentText = _controller.text;
    final selection = _controller.selection;

    if (selection.start > 0) {
      final newText = currentText.replaceRange(
        selection.start - 1,
        selection.start,
        '',
      );

      _controller.value = _controller.value.copyWith(
        text: newText,
        selection: TextSelection.collapsed(offset: selection.start - 1),
      );
    }
  }

  void _onSpace() {
    _onTextInput(' ');
  }

  void _onEnter() {
    _onTextInput('\n');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Tamil Keyboard Demo'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          Expanded(
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                controller: _controller,
                maxLines: null,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  hintText: 'Type using Tamil keyboard...',
                  labelText: 'Text Input',
                ),
              ),
            ),
          ),
          TamilKeyboard(
            onTextInput: _onTextInput,
            onBackspace: _onBackspace,
            onSpace: _onSpace,
            onEnter: _onEnter,
            onShift: () => setState(() => _isShift = !_isShift),
            onSymbol: () => setState(() => _isSymbol = !_isSymbol),
            isShift: _isShift,
            isSymbol: _isSymbol,
          ),
        ],
      ),
    );
  }
}
