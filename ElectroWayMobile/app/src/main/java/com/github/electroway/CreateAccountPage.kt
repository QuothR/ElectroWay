package com.github.electroway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CreateAccountPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account_page)

        val cancelAccountCreationButton = findViewById<Button>(R.id.cancelAccountCreationButton)
        cancelAccountCreationButton.setOnClickListener {
            val intent = Intent(this@CreateAccountPage, SignInPage::class.java)
            startActivity(intent)
        }
    }
}