package com.github.electroway
import android.content.Intent
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignInPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)

        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        forgotPassword.setOnClickListener {
            val intent = Intent(this@SignInPage, ForgotPasswordPage::class.java)
            startActivity(intent)
        }

        val createAccountButton = findViewById<Button>(R.id.createAccountButton)
        createAccountButton.setOnClickListener {
            val intent = Intent(this@SignInPage, CreateAccountPage::class.java)
            startActivity(intent)
        }
    }
}