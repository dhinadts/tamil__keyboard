# Tamil Keyboard - அஆஇஈ

## 🎓 Project Overview
A complete Flutter-based Tamil keyboard - அஆஇஈ implementation designed for flutter research and learning. This project demonstrates mobile development, natural language processing, and human-computer interaction principles.

## 📚 Objectives
- Implement complete Tamil Unicode support
- Develop user-friendly input method
- Provide research-ready documentation
- Demonstrate cross-platform development

## 🛠 Technical Stack
- **Framework**: Flutter 3.0+
- **Language**: Dart
- **Architecture**: MVVM Pattern
- **Unicode**: Tamil Block (U+0B80-U+0BFF)
- **Platform**: Android (Input Method Service)

## 🎯 Features
- Complete Tamil character set
- Vowel modifiers and combinations
- Flutter research data
- Unicode information display
- Vibration feedback
- Material Design 3
- **Bilingual Support** - Switch between Tamil and English QWERTY
- **Smart Tamil Typing** - UyirMei character composition (consonant + vowel combinations)
- **CAPS Lock Functionality** with blue visual indicator
- **Full Keyboard Controls** (Space, Backspace, Enter)
- **Professional UI** with color-coded keys

## ✅ What's Working
- **Tamil UyirMei character composition** (consonant + vowel combinations)
- **Bilingual support** - Switch between Tamil and English QWERTY
- **CAPS lock functionality** with blue visual indicator
- **Full keyboard controls** (Space, Backspace, Enter)
- **Professional UI** with color-coded keys

## 🎯 Key Features
- **Smart Tamil typing**: க் + அ = க, ச் + இ = சி, etc.
- **English QWERTY**: Standard keyboard layout for English typing
- **Visual feedback**: Different colors for different key types
- **Language switching**: Easy toggle between Tamil and English

## 📱 Installation
```bash
flutter pub get
flutter run
```

### 🔧 Keyboard Setup
Install the app

Go to Android Settings → System → Languages & input → Virtual keyboards

Enable "Tamil Keyboard"

In any text field, switch to Tamil Keyboard using the keyboard selector

### 🚀 Next Steps
Testing - Try the keyboard in different apps

Customization - Add more Tamil characters or layouts

Settings integration - Connect with your Flutter settings UI

Dictionary - Add word suggestions and auto-complete

Enhanced Tamil Support - Grantha characters and extended combinations

Themes - Customizable keyboard themes

Gesture Typing - Swipe-based input method

### 🏗 Architecture
This project uses a hybrid approach:

Flutter: For settings UI and configuration

Native Android: For high-performance keyboard service

Provider: For state management

Shared Preferences: For persistent settings

### 📝 Usage Examples
Keys Pressed	Output
க் + அ	க
க் + ஆ	கா
ச் + இ	சி
Switch to English → Type "A"	A
CAPS Lock ON → Type "a"	A
Switch back to Tamil → ப் + ஈ	பீ
### 🔄 Project Structure
```
tamil_keyboard/
├── lib/                 # Flutter UI (Settings, Configuration)
├── android/app/src/main/
│   ├── kotlin/         # Native Keyboard Service
│   ├── res/xml/        # Keyboard Layouts
│   └── res/layout/     # Keyboard Views
```

### 📄 License
This project is open for research and educational purposes.