package com.example.tamil_keyboard

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow

class TamilKeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    // Use a simpler callback approach instead of interface
    private var onKeyAction: ((String) -> Unit)? = null
    private var onBackspaceAction: (() -> Unit)? = null
    private var onSpaceAction: (() -> Unit)? = null
    private var onEnterAction: (() -> Unit)? = null

    // Tamil keyboard layouts
    private val tamilLetters = arrayOf(
        arrayOf("அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஐ", "ஒ"),
        arrayOf("ஓ", "ஔ", "க", "ங", "ச", "ஞ", "ட", "ண", "த", "ந"),
        arrayOf("ப", "ம", "ய", "ர", "ல", "வ", "ழ", "ள", "ற", "ன")
    )

    private val tamilVowels = arrayOf(
        arrayOf("ா", "ி", "ீ", "ு", "ூ", "ெ", "ே", "ை", "ொ", "ோ"),
        arrayOf("ௌ", "்", "ஂ", "ஃ", "ௗ", "ஶ", "ஷ", "ஸ", "ஹ", "க்ஷ")
    )

    private val symbols = arrayOf(
        arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        arrayOf("@", "#", "$", "%", "&", "*", "-", "+", "(", ")"),
        arrayOf("!", "\"", "'", ":", ";", "/", "?", ",", ".", "=")
    )

    private var isShift = false
    private var isSymbol = false

    init {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(Color.DKGRAY)
        setupKeyboard()
    }

    // Simplified setter methods
    fun setOnKeyAction(action: (String) -> Unit) {
        this.onKeyAction = action
    }

    fun setOnBackspaceAction(action: () -> Unit) {
        this.onBackspaceAction = action
    }

    fun setOnSpaceAction(action: () -> Unit) {
        this.onSpaceAction = action
    }

    fun setOnEnterAction(action: () -> Unit) {
        this.onEnterAction = action
    }

    private fun setupKeyboard() {
        removeAllViews()
        
        val tableLayout = TableLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            isStretchAllColumns = true
        }
        
        val currentLayout = if (isSymbol) symbols else if (isShift) tamilVowels else tamilLetters

        // Create rows for the keyboard
        for (row in currentLayout.indices) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for (key in currentLayout[row]) {
                val keyButton = createKeyButton(key)
                tableRow.addView(keyButton)
            }
            tableLayout.addView(tableRow)
        }
        addView(tableLayout)

        // Add special keys row
        val specialRow = TableRow(context)
        specialRow.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        
        // Shift key
        val shiftButton = createSpecialButton("⇧") {
            isShift = !isShift
            setupKeyboard()
        }
        specialRow.addView(shiftButton)

        // Symbol key
        val symbolButton = createSpecialButton("?123") {
            isSymbol = !isSymbol
            setupKeyboard()
        }
        specialRow.addView(symbolButton)

        // Space key
        val spaceButton = createSpecialButton("SPACE", 3) {
            onSpaceAction?.invoke()
        }
        specialRow.addView(spaceButton)

        // Enter key
        val enterButton = createSpecialButton("ENTER") {
            onEnterAction?.invoke()
        }
        specialRow.addView(enterButton)

        // Backspace key
        val backspaceButton = createSpecialButton("⌫") {
            onBackspaceAction?.invoke()
        }
        specialRow.addView(backspaceButton)

        addView(specialRow)
    }

    private fun createKeyButton(text: String): Button {
        return Button(context).apply {
            this.text = text
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                setMargins(2, 2, 2, 2)
            }
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            textSize = 16f
            setOnClickListener {
                onKeyAction?.invoke(text)
            }
        }
    }

    private fun createSpecialButton(text: String, weight: Int = 1, onClick: () -> Unit): Button {
        return Button(context).apply {
            this.text = text
            layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight.toFloat()
            ).apply {
                setMargins(2, 2, 2, 2)
            }
            setBackgroundColor(Color.LTGRAY)
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            textSize = 14f
            setOnClickListener { onClick() }
        }
    }
}