package com.example.tamil_keyboard

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TamilKeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var onKeyAction: ((String) -> Unit)? = null
    private var onBackspaceAction: (() -> Unit)? = null
    private var onSpaceAction: (() -> Unit)? = null
    private var onEnterAction: (() -> Unit)? = null
    private var onLanguageSwitch: ((Boolean) -> Unit)? = null
    private var onCapsAction: (() -> Unit)? = null

    private var isTamilMode = true
    private var isCapsLock = false

    // Tamil characters
    private val tamilVowels = arrayOf("அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஐ", "ஒ", "ஓ", "ஔ")
    private val tamilConsonants = arrayOf("க்", "ங்", "ச்", "ஞ்", "ட்", "ண்", "த்", "ந்", "ப்", "ம்", "ய்", "ர்", "ல்", "வ்", "ழ்", "ள்", "ற்", "ன்")
    private val tamilOther = arrayOf("ஜ", "ஷ", "ஸ", "ஹ", "க்ஷ", "ஶ்ரீ", "ஃ")

    // English QWERTY layout
    private val englishRows = arrayOf(
        arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        arrayOf("z", "x", "c", "v", "b", "n", "m")
    )

    init {
        orientation = LinearLayout.VERTICAL
        setupKeyboard()
    }

    fun setOnKeyAction(listener: (String) -> Unit) {
        onKeyAction = listener
    }

    fun setOnBackspaceAction(listener: () -> Unit) {
        onBackspaceAction = listener
    }

    fun setOnSpaceAction(listener: () -> Unit) {
        onSpaceAction = listener
    }

    fun setOnEnterAction(listener: () -> Unit) {
        onEnterAction = listener
    }

    fun setOnLanguageSwitch(listener: (Boolean) -> Unit) {
        onLanguageSwitch = listener
    }

    fun setOnCapsAction(listener: () -> Unit) {
        onCapsAction = listener
    }

    private fun setupKeyboard() {
        removeAllViews()
        if (isTamilMode) {
            setupTamilKeyboard()
        } else {
            setupEnglishKeyboard()
        }
    }

    private fun setupTamilKeyboard() {
        // Row 1: Vowels
        val row1 = createRow()
        tamilVowels.forEach { char ->
            row1.addView(createKeyButton(char))
        }
        addView(row1)

        // Row 2: Consonants (first half)
        val row2 = createRow()
        tamilConsonants.take(9).forEach { char ->
            row2.addView(createKeyButton(char))
        }
        addView(row2)

        // Row 3: Consonants (second half) and other characters
        val row3 = createRow()
        tamilConsonants.drop(9).forEach { char ->
            row3.addView(createKeyButton(char))
        }
        tamilOther.take(3).forEach { char ->
            row3.addView(createKeyButton(char))
        }
        addView(row3)

        // Row 4: Control keys
        val row4 = createRow()
        
        // Language switch button
        val langButton = createKeyButton("ENG")
        langButton.setBackgroundColor(Color.parseColor("#4CAF50"))
        langButton.setTextColor(Color.WHITE)
        langButton.setOnClickListener {
            switchToEnglish()
        }
        row4.addView(langButton)

        // Space bar
        val spaceButton = createKeyButton("space")
        spaceButton.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f)
        spaceButton.setOnClickListener {
            onSpaceAction?.invoke()
        }
        row4.addView(spaceButton)

        // Backspace
        val backspaceButton = createKeyButton("⌫")
        backspaceButton.setBackgroundColor(Color.parseColor("#F44336"))
        backspaceButton.setTextColor(Color.WHITE)
        backspaceButton.setOnClickListener {
            onBackspaceAction?.invoke()
        }
        row4.addView(backspaceButton)

        // Enter
        val enterButton = createKeyButton("↵")
        enterButton.setBackgroundColor(Color.parseColor("#2196F3"))
        enterButton.setTextColor(Color.WHITE)
        enterButton.setOnClickListener {
            onEnterAction?.invoke()
        }
        row4.addView(enterButton)

        addView(row4)
    }

    private fun setupEnglishKeyboard() {
        // Row 1: QWERTY top row
        val row1 = createRow()
        englishRows[0].forEach { char ->
            val displayChar = if (isCapsLock) char.uppercase() else char
            row1.addView(createKeyButton(displayChar))
        }
        addView(row1)

        // Row 2: QWERTY middle row
        val row2 = createRow()
        englishRows[1].forEach { char ->
            val displayChar = if (isCapsLock) char.uppercase() else char
            row2.addView(createKeyButton(displayChar))
        }
        addView(row2)

        // Row 3: QWERTY bottom row and controls
        val row3 = createRow()
        
        // Caps lock button
        val capsButton = createKeyButton("⇧")
        capsButton.setBackgroundColor(if (isCapsLock) Color.parseColor("#2196F3") else Color.parseColor("#E0E0E0"))
        capsButton.setTextColor(if (isCapsLock) Color.WHITE else Color.BLACK)
        capsButton.setOnClickListener {
            toggleCapsLock()
        }
        row3.addView(capsButton)

        // Bottom row letters
        englishRows[2].forEach { char ->
            val displayChar = if (isCapsLock) char.uppercase() else char
            row3.addView(createKeyButton(displayChar))
        }

        // Comma and period
        row3.addView(createKeyButton(","))
        row3.addView(createKeyButton("."))
        addView(row3)

        // Row 4: Control keys
        val row4 = createRow()
        
        // Language switch button
        val langButton = createKeyButton("தமிழ்")
        langButton.setBackgroundColor(Color.parseColor("#4CAF50"))
        langButton.setTextColor(Color.WHITE)
        langButton.setOnClickListener {
            switchToTamil()
        }
        row4.addView(langButton)

        // Space bar
        val spaceButton = createKeyButton("space")
        spaceButton.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f)
        spaceButton.setOnClickListener {
            onSpaceAction?.invoke()
        }
        row4.addView(spaceButton)

        // Backspace
        val backspaceButton = createKeyButton("⌫")
        backspaceButton.setBackgroundColor(Color.parseColor("#F44336"))
        backspaceButton.setTextColor(Color.WHITE)
        backspaceButton.setOnClickListener {
            onBackspaceAction?.invoke()
        }
        row4.addView(backspaceButton)

        // Enter
        val enterButton = createKeyButton("↵")
        enterButton.setBackgroundColor(Color.parseColor("#2196F3"))
        enterButton.setTextColor(Color.WHITE)
        enterButton.setOnClickListener {
            onEnterAction?.invoke()
        }
        row4.addView(enterButton)

        addView(row4)
    }

    private fun createRow(): LinearLayout {
        return LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.HORIZONTAL
        }
    }

    private fun createKeyButton(text: String): Button {
        return Button(context).apply {
            this.text = text
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(2, 2, 2, 2)
            }
            setBackgroundColor(Color.parseColor("#FFFFFF"))
            setTextColor(Color.BLACK)
            textSize = 14f
            setOnClickListener {
                onKeyAction?.invoke(text)
            }
        }
    }

    private fun switchToEnglish() {
        isTamilMode = false
        isCapsLock = false
        setupKeyboard()
        onLanguageSwitch?.invoke(false)
    }

    private fun switchToTamil() {
        isTamilMode = true
        isCapsLock = false
        setupKeyboard()
        onLanguageSwitch?.invoke(true)
    }

    private fun toggleCapsLock() {
        isCapsLock = !isCapsLock
        if (!isTamilMode) {
            setupKeyboard()
        }
        onCapsAction?.invoke()
    }

    fun setTamilMode(tamilMode: Boolean) {
        isTamilMode = tamilMode
        setupKeyboard()
    }

    fun setCapsLock(capsLock: Boolean) {
        isCapsLock = capsLock
        if (!isTamilMode) {
            setupKeyboard()
        }
    }
}