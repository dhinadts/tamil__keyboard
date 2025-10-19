import 'package:flutter/material.dart';

class TamilKeyboard extends StatelessWidget {
  final ValueChanged<String> onTextInput;
  final VoidCallback onBackspace;
  final VoidCallback onSpace;
  final VoidCallback onEnter;
  final VoidCallback onShift;
  final VoidCallback onSymbol;
  final bool isShift;
  final bool isSymbol;

  const TamilKeyboard({
    super.key,
    required this.onTextInput,
    required this.onBackspace,
    required this.onSpace,
    required this.onEnter,
    required this.onShift,
    required this.onSymbol,
    required this.isShift,
    required this.isSymbol,
  });

  // Tamil keyboard layouts
  static const List<List<String>> tamilLetters = [
    ['அ', 'ஆ', 'இ', 'ஈ', 'உ', 'ஊ', 'எ', 'ஏ', 'ஐ', 'ஒ'],
    ['ஓ', 'ஔ', 'க', 'ங', 'ச', 'ஞ', 'ட', 'ண', 'த', 'ந'],
    ['ப', 'ம', 'ய', 'ர', 'ல', 'வ', 'ழ', 'ள', 'ற', 'ன'],
  ];

  static const List<List<String>> tamilVowels = [
    ['ா', 'ி', 'ீ', 'ு', 'ூ', 'ெ', 'ே', 'ை', 'ொ', 'ோ'],
    ['ௌ', '்', 'ஂ', 'ஃ', 'ௗ', 'ஶ', 'ஷ', 'ஸ', 'ஹ', 'க்ஷ'],
  ];

  static const List<List<String>> symbols = [
    ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'],
    ['@', '#', '\$', '%', '&', '*', '-', '+', '(', ')'],
    ['!', '"', "'", ':', ';', '/', '?', ',', '.', '='],
  ];

  Widget _buildKey(String text, {double width = 1.0, bool isSpecial = false}) {
    return Expanded(
      flex: (width * 10).round(),
      child: Padding(
        padding: const EdgeInsets.all(2.0),
        child: Material(
          color: isSpecial ? Colors.grey[300] : Colors.white,
          child: InkWell(
            onTap: () => onTextInput(text),
            child: Container(
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey[400]!),
                borderRadius: BorderRadius.circular(4.0),
              ),
              child: Center(
                child: Text(
                  text,
                  style: TextStyle(
                    fontSize: isSpecial ? 16 : 18,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildSpecialKey(
    IconData icon,
    VoidCallback onTap, {
    double width = 1.0,
  }) {
    return Expanded(
      flex: (width * 10).round(),
      child: Padding(
        padding: const EdgeInsets.all(2.0),
        child: Material(
          color: Colors.grey[300],
          child: InkWell(
            onTap: onTap,
            child: Container(
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey[400]!),
                borderRadius: BorderRadius.circular(4.0),
              ),
              child: Center(child: Icon(icon, size: 20)),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildRow(List<Widget> children) {
    return Row(children: children);
  }

  @override
  Widget build(BuildContext context) {
    final currentLayout = isSymbol
        ? symbols
        : (isShift ? tamilVowels : tamilLetters);

    return Container(
      color: Colors.grey[200],
      padding: const EdgeInsets.all(4.0),
      child: Column(
        children: [
          // First row
          _buildRow([
            _buildKey(currentLayout[0][0]),
            _buildKey(currentLayout[0][1]),
            _buildKey(currentLayout[0][2]),
            _buildKey(currentLayout[0][3]),
            _buildKey(currentLayout[0][4]),
            _buildKey(currentLayout[0][5]),
            _buildKey(currentLayout[0][6]),
            _buildKey(currentLayout[0][7]),
            _buildKey(currentLayout[0][8]),
            _buildKey(currentLayout[0][9]),
          ]),

          // Second row
          _buildRow([
            _buildKey(currentLayout[1][0]),
            _buildKey(currentLayout[1][1]),
            _buildKey(currentLayout[1][2]),
            _buildKey(currentLayout[1][3]),
            _buildKey(currentLayout[1][4]),
            _buildKey(currentLayout[1][5]),
            _buildKey(currentLayout[1][6]),
            _buildKey(currentLayout[1][7]),
            _buildKey(currentLayout[1][8]),
            _buildKey(currentLayout[1][9]),
          ]),

          // Third row
          _buildRow([
            _buildSpecialKey(
              isShift ? Icons.keyboard_arrow_up : Icons.keyboard_arrow_down,
              onShift,
              width: 1.2,
            ),
            _buildKey(currentLayout[2][0]),
            _buildKey(currentLayout[2][1]),
            _buildKey(currentLayout[2][2]),
            _buildKey(currentLayout[2][3]),
            _buildKey(currentLayout[2][4]),
            _buildKey(currentLayout[2][5]),
            _buildKey(currentLayout[2][6]),
            _buildKey(currentLayout[2][7]),
            _buildKey(currentLayout[2][8]),
            _buildKey(currentLayout[2][9]),
            _buildSpecialKey(Icons.backspace, onBackspace, width: 1.2),
          ]),

          // Fourth row (special keys)
          _buildRow([
            _buildSpecialKey(Icons.settings, onSymbol, width: 1.5),
            _buildSpecialKey(Icons.language, () {}, width: 1.5),
            _buildSpecialKey(Icons.space_bar, onSpace, width: 4.0),
            _buildSpecialKey(Icons.emoji_emotions, () {}, width: 1.5),
            _buildSpecialKey(Icons.keyboard_return, onEnter, width: 1.5),
          ]),
        ],
      ),
    );
  }
}
