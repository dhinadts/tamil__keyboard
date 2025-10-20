import 'package:flutter/material.dart';
import 'package:vibration/vibration.dart';
import 'package:google_fonts/google_fonts.dart';

void main() {
  runApp(TamilKeyboardApp());
}

class TamilKeyboardApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Tamil Keyboard',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: GoogleFonts.notoSansTamil().fontFamily,
      ),
      home: KeyboardSettingsScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class KeyboardSettingsScreen extends StatefulWidget {
  @override
  _KeyboardSettingsScreenState createState() => _KeyboardSettingsScreenState();
}

class _KeyboardSettingsScreenState extends State<KeyboardSettingsScreen> {
  bool _vibrationEnabled = true;
  bool _soundEnabled = false;
  double _keyboardHeight = 200.0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('தமிழ் விசைப்பலகை அமைப்புகள்'),
        backgroundColor: Colors.blue[700],
      ),
      body: ListView(
        padding: EdgeInsets.all(16),
        children: [
          _buildHeader(),
          SizedBox(height: 20),
          _buildEnableKeyboardCard(),
          SizedBox(height: 20),
          _buildSettingsCard(),
          SizedBox(height: 20),
          _buildKeyboardPreview(),
        ],
      ),
    );
  }

  Widget _buildHeader() {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          children: [
            Icon(Icons.keyboard, size: 64, color: Colors.blue),
            SizedBox(height: 10),
            Text(
              'தமிழ் விசைப்பலகை',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                fontFamily: GoogleFonts.notoSansTamil().fontFamily,
              ),
            ),
            SizedBox(height: 10),
            Text(
              'செல்லுபடியாக்க: அமைப்புகள் > மொழி & உள்ளீடு > விசைப்பலகைகள் > தமிழ் விசைப்பலகை',
              textAlign: TextAlign.center,
              style: TextStyle(fontFamily: 'NotoSansTamil'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildEnableKeyboardCard() {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'விசைப்பலகையை இயக்கு',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            Text('அமைப்புகளில் சென்று தமிழ் விசைப்பலகையை இயக்கவும்'),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: _openKeyboardSettings,
              child: Text('விசைப்பலகை அமைப்புகள்'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSettingsCard() {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'அமைப்புகள்',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            SwitchListTile(
              title: Text('அதிர்வு'),
              subtitle: Text('விசை அழுத்தத்தில் அதிர்வு'),
              value: _vibrationEnabled,
              onChanged: (value) {
                setState(() {
                  _vibrationEnabled = value;
                });
              },
            ),
            SwitchListTile(
              title: Text('ஒலி'),
              subtitle: Text('விசை அழுத்தத்தில் கிளிக் ஒலி'),
              value: _soundEnabled,
              onChanged: (value) {
                setState(() {
                  _soundEnabled = value;
                });
              },
            ),
            SizedBox(height: 10),
            Text('விசைப்பலகை உயரம்'),
            Slider(
              value: _keyboardHeight,
              min: 150.0,
              max: 300.0,
              divisions: 5,
              label: '${_keyboardHeight.round()}',
              onChanged: (value) {
                setState(() {
                  _keyboardHeight = value;
                });
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildKeyboardPreview() {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'முன்தோற்றம்',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            Container(
              height: 120,
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey),
                borderRadius: BorderRadius.circular(8),
              ),
              child: Center(
                child: Text(
                  'தமிழ் விசைப்பலகை முன்தோற்றம்',
                  style: TextStyle(
                      fontFamily: GoogleFonts.notoSansTamil().fontFamily),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  void _openKeyboardSettings() {
    // This would typically open system keyboard settings
    // For now, we'll show a dialog
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('விசைப்பலகை அமைப்புகள்'),
        content: Text(
            'அமைப்புகள் > மொழி & உள்ளீடு > விசைப்பலகைகள் சென்று "தமிழ் விசைப்பலகை" இயக்கவும்'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('சரி'),
          ),
        ],
      ),
    );
  }
}


/* import 'package:flutter/material.dart';

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
 */