package com.example.tamil_keyboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TamilKeyboardSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<Button>(R.id.btn_enable_keyboard).setOnClickListener {
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_choose_keyboard).setOnClickListener {
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS)
            startActivity(intent)
        }
    }
}