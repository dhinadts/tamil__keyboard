package com.example.tamil_keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputConnection

class TamilInputMethodService : InputMethodService() {
    
    private lateinit var keyboardView: TamilKeyboardView

    override fun onCreateInputView(): View {
        keyboardView = TamilKeyboardView(this)
        
        // Use the new callback approach
        keyboardView.setOnKeyAction { key ->
            currentInputConnection?.commitText(key, 1)
        }
        
        keyboardView.setOnBackspaceAction {
            val ic: InputConnection? = currentInputConnection
            ic?.deleteSurroundingText(1, 0)
        }
        
        keyboardView.setOnSpaceAction {
            currentInputConnection?.commitText(" ", 1)
        }
        
        keyboardView.setOnEnterAction {
            currentInputConnection?.commitText("\n", 1)
        }
        
        return keyboardView
    }
}