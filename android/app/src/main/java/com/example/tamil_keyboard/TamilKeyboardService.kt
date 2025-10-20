package com.example.tamil_keyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputConnection
import android.content.Context
import android.os.Vibrator
import android.content.SharedPreferences

class TamilKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private var keyboardView: KeyboardView? = null
    private var tamilKeyboard: Keyboard? = null
    private var currentKeyboard: Keyboard? = null
    private var capsLock = false
    private var prefs: SharedPreferences? = null

    // Tamil Unicode characters
    companion object {
        val TAMIL_VOWELS = arrayOf(
            "அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஐ", "ஒ", "ஓ", "ஔ"
        )
        
        val TAMIL_CONSONANTS = arrayOf(
            "க", "ங", "ச", "ஞ", "ட", "ண", "த", "ந", "ப", "ம", "ய", "ர", "ல", "வ", "ழ", "ள", "ற", "ன"
        )
        
        val TAMIL_OTHER = arrayOf(
            "ஜ", "ஷ", "ஸ", "ஹ", "க்ஷ", "ஶ்ரீ"
        )
    }

    override fun onCreate() {
        super.onCreate()
        prefs = getSharedPreferences("tamil_keyboard_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        tamilKeyboard = Keyboard(this, R.xml.tamil_keyboard_layout)
        
        currentKeyboard = tamilKeyboard
        keyboardView!!.keyboard = currentKeyboard
        keyboardView!!.setOnKeyboardActionListener(this)
        keyboardView!!.isPreviewEnabled = false
        
        return keyboardView!!
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection: InputConnection? = currentInputConnection
        
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                handleBackspace()
            }
            Keyboard.KEYCODE_SHIFT -> {
                handleShift()
            }
            Keyboard.KEYCODE_DONE -> {
                handleEnter()
            }
            Keyboard.KEYCODE_MODE_CHANGE -> {
                // Switch between keyboards if needed
            }
            else -> {
                val character = primaryCode.toChar()
                handleCharacterInput(character.toString())
            }
        }
    }

    private fun handleCharacterInput(char: String) {
        val inputConnection = currentInputConnection ?: return
        inputConnection.commitText(char, 1)
        
        // Provide feedback
        provideFeedback()
    }

    private fun handleBackspace() {
        val inputConnection = currentInputConnection ?: return
        val selectedText = inputConnection.getSelectedText(0)
        
        if (selectedText != null && selectedText.isNotEmpty()) {
            inputConnection.commitText("", 1)
        } else {
            inputConnection.deleteSurroundingText(1, 0)
        }
        
        provideFeedback()
    }

    private fun handleShift() {
        // Implement shift functionality for Tamil if needed
        capsLock = !capsLock
        keyboardView!!.isShifted = capsLock
        keyboardView!!.invalidateAllKeys()
    }

    private fun handleEnter() {
        val inputConnection = currentInputConnection ?: return
        inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
        inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
    }

    private fun provideFeedback() {
        val vibrationEnabled = prefs?.getBoolean("vibration_enabled", true) ?: true
        val soundEnabled = prefs?.getBoolean("sound_enabled", false) ?: false
        
        if (vibrationEnabled) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20)
        }
        
        if (soundEnabled) {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
        }
    }

    // Required interface methods
    override fun onPress(primaryCode: Int) {
        // Optional: Preview functionality
    }

    override fun onRelease(primaryCode: Int) {
        // Optional: Release actions
    }

    override fun onText(text: CharSequence?) {
        val inputConnection = currentInputConnection ?: return
        inputConnection.commitText(text, 1)
        provideFeedback()
    }

    override fun swipeDown() {
        // Handle swipe down
    }

    override fun swipeLeft() {
        // Handle swipe left
    }

    override fun swipeRight() {
        // Handle swipe right
    }

    override fun swipeUp() {
        // Handle swipe up
    }
}