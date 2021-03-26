package com.github.electroway

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity

class StartupPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup_page)
        supportActionBar!!.hide()
        val handler = Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@StartupPage, SignInPage::class.java)
            startActivity(intent)
        }, 1000)
    }
}