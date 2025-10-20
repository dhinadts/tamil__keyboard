package com.example.tamil_keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputConnection
import android.util.Log


class TamilInputMethodService : InputMethodService() {

     private lateinit var keyboardView: TamilKeyboardView
    private var lastKey: String? = null
    private var isTamilMode = true

    // Double-press mapping for vowels
    private val vowelDoubleMap = mapOf(
        "அ" to "ஆ",
        "இ" to "ஈ",
        "உ" to "ஊ",
        "எ" to "ஏ",
        "ஐ" to "ஐ",
        "ஒ" to "ஓ",
        "ஔ" to "ஔ"
    )

    // Full 18 consonants × 12 vowels (216 combos)
     // Tamil UyirMei (Mei + Uyir) combinations for all major consonants
    private val tamilCombinations = mapOf(
        // க்
        Pair("க்", "அ") to "க", Pair("க்", "ஆ") to "கா", Pair("க்", "இ") to "கி", Pair("க்", "ஈ") to "கீ",
        Pair("க்", "உ") to "கு", Pair("க்", "ஊ") to "கூ", Pair("க்", "எ") to "கெ", Pair("க்", "ஏ") to "கே",
        Pair("க்", "ஐ") to "கை", Pair("க்", "ஒ") to "கொ", Pair("க்", "ஓ") to "கோ", Pair("க்", "ஔ") to "கௌ",

        // ங்
        Pair("ங்", "அ") to "ங", Pair("ங்", "ஆ") to "ஙா", Pair("ங்", "இ") to "ஙி", Pair("ங்", "ஈ") to "ஙீ",
        Pair("ங்", "உ") to "ஙு", Pair("ங்", "ஊ") to "ஙூ", Pair("ங்", "எ") to "ஙெ", Pair("ங்", "ஏ") to "ஙே",
        Pair("ங்", "ஐ") to "ஙை", Pair("ங்", "ஒ") to "ஙொ", Pair("ங்", "ஓ") to "ஙோ", Pair("ங்", "ஔ") to "ஙௌ",

        // ச்
        Pair("ச்", "அ") to "ச", Pair("ச்", "ஆ") to "சா", Pair("ச்", "இ") to "சி", Pair("ச்", "ஈ") to "சீ",
        Pair("ச்", "உ") to "சு", Pair("ச்", "ஊ") to "சூ", Pair("ச்", "எ") to "செ", Pair("ச்", "ஏ") to "சே",
        Pair("ச்", "ஐ") to "சை", Pair("ச்", "ஒ") to "சொ", Pair("ச்", "ஓ") to "சோ", Pair("ச்", "ஔ") to "சௌ",

        // ஞ்
        Pair("ஞ்", "அ") to "ஞ", Pair("ஞ்", "ஆ") to "ஞா", Pair("ஞ்", "இ") to "ஞி", Pair("ஞ்", "ஈ") to "ஞீ",
        Pair("ஞ்", "உ") to "ஞு", Pair("ஞ்", "ஊ") to "ஞூ", Pair("ஞ்", "எ") to "ஞெ", Pair("ஞ்", "ஏ") to "ஞே",
        Pair("ஞ்", "ஐ") to "ஞை", Pair("ஞ்", "ஒ") to "ஞொ", Pair("ஞ்", "ஓ") to "ஞோ", Pair("ஞ்", "ஔ") to "ஞௌ",

        // ட்
        Pair("ட்", "அ") to "ட", Pair("ட்", "ஆ") to "டா", Pair("ட்", "இ") to "டி", Pair("ட்", "ஈ") to "டீ",
        Pair("ட்", "உ") to "டு", Pair("ட்", "ஊ") to "டூ", Pair("ட்", "எ") to "டெ", Pair("ட்", "ஏ") to "டே",
        Pair("ட்", "ஐ") to "டை", Pair("ட்", "ஒ") to "டொ", Pair("ட்", "ஓ") to "டோ", Pair("ட்", "ஔ") to "டௌ",

        // ண்
        Pair("ண்", "அ") to "ண", Pair("ண்", "ஆ") to "ணா", Pair("ண்", "இ") to "ணி", Pair("ண்", "ஈ") to "ணீ",
        Pair("ண்", "உ") to "ணு", Pair("ண்", "ஊ") to "ணூ", Pair("ண்", "எ") to "ணெ", Pair("ண்", "ஏ") to "ணே",
        Pair("ண்", "ஐ") to "ணை", Pair("ண்", "ஒ") to "ணொ", Pair("ண்", "ஓ") to "ணோ", Pair("ண்", "ஔ") to "ணௌ",

        // த்
        Pair("த்", "அ") to "த", Pair("த்", "ஆ") to "தா", Pair("த்", "இ") to "தி", Pair("த்", "ஈ") to "தீ",
        Pair("த்", "உ") to "து", Pair("த்", "ஊ") to "தூ", Pair("த்", "எ") to "தெ", Pair("த்", "ஏ") to "தே",
        Pair("த்", "ஐ") to "தை", Pair("த்", "ஒ") to "தொ", Pair("த்", "ஓ") to "தோ", Pair("த்", "ஔ") to "தௌ",

        // ந்
        Pair("ந்", "அ") to "ந", Pair("ந்", "ஆ") to "நா", Pair("ந்", "இ") to "நி", Pair("ந்", "ஈ") to "நீ",
        Pair("ந்", "உ") to "நு", Pair("ந்", "ஊ") to "நூ", Pair("ந்", "எ") to "நெ", Pair("ந்", "ஏ") to "நே",
        Pair("ந்", "ஐ") to "நை", Pair("ந்", "ஒ") to "நொ", Pair("ந்", "ஓ") to "நோ", Pair("ந்", "ஔ") to "நௌ",

        // ப்
        Pair("ப்", "அ") to "ப", Pair("ப்", "ஆ") to "பா", Pair("ப்", "இ") to "பி", Pair("ப்", "ஈ") to "பீ",
        Pair("ப்", "உ") to "பு", Pair("ப்", "ஊ") to "பூ", Pair("ப்", "எ") to "பெ", Pair("ப்", "ஏ") to "பே",
        Pair("ப்", "ஐ") to "பை", Pair("ப்", "ஒ") to "பொ", Pair("ப்", "ஓ") to "போ", Pair("ப்", "ஔ") to "பௌ",

        // ம்
        Pair("ம்", "அ") to "ம", Pair("ம்", "ஆ") to "மா", Pair("ம்", "இ") to "மி", Pair("ம்", "ஈ") to "மீ",
        Pair("ம்", "உ") to "மு", Pair("ம்", "ஊ") to "மூ", Pair("ம்", "எ") to "மெ", Pair("ம்", "ஏ") to "மே",
        Pair("ம்", "ஐ") to "மை", Pair("ம்", "ஒ") to "மொ", Pair("ம்", "ஓ") to "மோ", Pair("ம்", "ஔ") to "மௌ",

        // ய்
        Pair("ய்", "அ") to "ய", Pair("ய்", "ஆ") to "யா", Pair("ய்", "இ") to "யி", Pair("ய்", "ஈ") to "யீ",
        Pair("ய்", "உ") to "யு", Pair("ய்", "ஊ") to "யூ", Pair("ய்", "எ") to "யெ", Pair("ய்", "ஏ") to "யே",
        Pair("ய்", "ஐ") to "யை", Pair("ய்", "ஒ") to "யொ", Pair("ய்", "ஓ") to "யோ", Pair("ய்", "ஔ") to "யௌ",

        // ர்
        Pair("ர்", "அ") to "ர", Pair("ர்", "ஆ") to "ரா", Pair("ர்", "இ") to "ரி", Pair("ர்", "ஈ") to "ரீ",
        Pair("ர்", "உ") to "ரு", Pair("ர்", "ஊ") to "ரூ", Pair("ர்", "எ") to "ரெ", Pair("ர்", "ஏ") to "ரே",
        Pair("ர்", "ஐ") to "ரை", Pair("ர்", "ஒ") to "ரொ", Pair("ர்", "ஓ") to "ரோ", Pair("ர்", "ஔ") to "ரௌ",

        // ல்
        Pair("ல்", "அ") to "ல", Pair("ல்", "ஆ") to "லா", Pair("ல்", "இ") to "லி", Pair("ல்", "ஈ") to "லீ",
        Pair("ல்", "உ") to "லு", Pair("ல்", "ஊ") to "லூ", Pair("ல்", "எ") to "லெ", Pair("ல்", "ஏ") to "லே",
        Pair("ல்", "ஐ") to "லை", Pair("ல்", "ஒ") to "லொ", Pair("ல்", "ஓ") to "லோ", Pair("ல்", "ஔ") to "லௌ",

        // வ்
        Pair("வ்", "அ") to "வ", Pair("வ்", "ஆ") to "வா", Pair("வ்", "இ") to "வி", Pair("வ்", "ஈ") to "வீ",
        Pair("வ்", "உ") to "வு", Pair("வ்", "ஊ") to "வூ", Pair("வ்", "எ") to "வெ", Pair("வ்", "ஏ") to "வே",
        Pair("வ்", "ஐ") to "வை", Pair("வ்", "ஒ") to "வொ", Pair("வ்", "ஓ") to "வோ", Pair("வ்", "ஔ") to "வௌ",

        // ழ்
        Pair("ழ்", "அ") to "ழ", Pair("ழ்", "ஆ") to "ழா", Pair("ழ்", "இ") to "ழி", Pair("ழ்", "ஈ") to "ழீ",
        Pair("ழ்", "உ") to "ழு", Pair("ழ்", "ஊ") to "ழூ", Pair("ழ்", "எ") to "ழெ", Pair("ழ்", "ஏ") to "ழே",
        Pair("ழ்", "ஐ") to "ழை", Pair("ழ்", "ஒ") to "ழொ", Pair("ழ்", "ஓ") to "ழோ", Pair("ழ்", "ஔ") to "ழௌ",

        // ள்
        Pair("ள்", "அ") to "ள", Pair("ள்", "ஆ") to "ளா", Pair("ள்", "இ") to "ளி", Pair("ள்", "ஈ") to "ளீ",
        Pair("ள்", "உ") to "ளு", Pair("ள்", "ஊ") to "ளூ", Pair("ள்", "எ") to "ளெ", Pair("ள்", "ஏ") to "ளே",
        Pair("ள்", "ஐ") to "ளை", Pair("ள்", "ஒ") to "ளொ", Pair("ள்", "ஓ") to "ளோ", Pair("ள்", "ஔ") to "ளௌ",

        // ற்
        Pair("ற்", "அ") to "ற", Pair("ற்", "ஆ") to "றா", Pair("ற்", "இ") to "றி", Pair("ற்", "ஈ") to "றீ",
        Pair("ற்", "உ") to "று", Pair("ற்", "ஊ") to "றூ", Pair("ற்", "எ") to "றெ", Pair("ற்", "ஏ") to "றே",
        Pair("ற்", "ஐ") to "றை", Pair("ற்", "ஒ") to "றொ", Pair("ற்", "ஓ") to "றோ", Pair("ற்", "ஔ") to "றௌ",

        // ன்
        Pair("ன்", "அ") to "ன", Pair("ன்", "ஆ") to "னா", Pair("ன்", "இ") to "னி", Pair("ன்", "ஈ") to "நீ",
        Pair("ன்", "உ") to "நு", Pair("ன்", "ஊ") to "நூ", Pair("ன்", "எ") to "நெ", Pair("ன்", "ஏ") to "நே",
        Pair("ன்", "ஐ") to "நை", Pair("ன்", "ஒ") to "நொ", Pair("ன்", "ஓ") to "நோ", Pair("ன்", "ஔ") to "நௌ"
    )
 override fun onCreateInputView(): View {
        keyboardView = TamilKeyboardView(this)

        keyboardView.setOnKeyAction { key ->
            handleKeyPress(key)
        }

        keyboardView.setOnBackspaceAction {
            handleBackspace()
        }

        keyboardView.setOnSpaceAction {
            handleSpace()
        }

        keyboardView.setOnEnterAction {
            handleEnter()
        }

        keyboardView.setOnLanguageSwitch { tamilMode ->
            isTamilMode = tamilMode
            lastKey = null // Reset composition when switching languages
        }

        keyboardView.setOnCapsAction {
            // Caps action handled internally by TamilKeyboardView
        }

        return keyboardView
    }

    private fun handleKeyPress(key: String) {
        val ic = currentInputConnection ?: return

        if (isTamilMode) {
            handleTamilComposition(key, ic)
        } else {
            handleEnglishInput(key, ic)
        }
    }

    private fun handleTamilComposition(key: String, ic: InputConnection) {
        Log.d("TamilKeyboard", "Last key: $lastKey, Current key: $key")
        
        val combo = tamilCombinations[Pair(lastKey, key)]
        if (combo != null) {
            // Delete the previous consonant and replace with combined character
            ic.deleteSurroundingText(1, 0)
            ic.commitText(combo, 1)
            lastKey = combo
            Log.d("TamilKeyboard", "Combined: $combo")
        } else {
            // Check if this key can be combined with next key
            if (key.endsWith("்") || tamilCombinations.keys.any { it.first == key }) {
                // This is a consonant that can be combined
                ic.commitText(key, 1)
                lastKey = key
                Log.d("TamilKeyboard", "Set last key: $key")
            } else {
                // This is a vowel or other character that doesn't combine
                ic.commitText(key, 1)
                lastKey = null
                Log.d("TamilKeyboard", "Committed standalone: $key")
            }
        }
    }

    private fun handleEnglishInput(key: String, ic: InputConnection) {
        ic.commitText(key, 1)
        lastKey = null // Reset Tamil composition
    }

    private fun handleBackspace() {
        val ic = currentInputConnection ?: return
        ic.deleteSurroundingText(1, 0)
        lastKey = null // Reset composition on backspace
    }

    private fun handleSpace() {
        val ic = currentInputConnection ?: return
        ic.commitText(" ", 1)
        lastKey = null // Reset composition on space
    }

    private fun handleEnter() {
        val ic = currentInputConnection ?: return
        ic.commitText("\n", 1)
        lastKey = null // Reset composition on enter
    }

    override fun onStartInputView(info: android.view.inputmethod.EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        lastKey = null // Reset composition when starting new input
    }
}