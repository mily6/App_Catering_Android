package com.example.catering2


import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val myIntent = Intent(this, LoginActivity::class.java)
        val verificationIntent = Intent(this,VerificationActivity::class.java)
        val regloginedit = findViewById<EditText>(R.id.reglogin)
        val regmailedit = findViewById<EditText>(R.id.regmail)
        val regpasswordedit = findViewById<EditText>(R.id.regpassword)
        val repeatpasswordedit = findViewById<EditText>(R.id.repeatpassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginLink = findViewById<Button>(R.id.loginLink)

        registerButton.setOnClickListener {
            val reglogin = regloginedit.text.toString().trim()
            val regmail = regmailedit.text.toString().trim()
            val regpassword = regpasswordedit.text.toString().trim()
            val repeatpassword = repeatpasswordedit.text.toString().trim()

            if (reglogin.isEmpty() || regpassword.isEmpty() || repeatpassword.isEmpty() || regmail.isEmpty()) {
                Toast.makeText(this, "Proszę uzupełnić wszystkie pola.", Toast.LENGTH_SHORT).show()

            } else if (regpassword != repeatpassword) {
                Toast.makeText(this, "Hasła nie zgadzają się.", Toast.LENGTH_SHORT).show()

            } else {
                verificationIntent.putExtra("name", reglogin)
                verificationIntent.putExtra("password", regpassword)
                verificationIntent.putExtra("mail", regmail)
                startActivity(verificationIntent)

            }

        }
        loginLink.setOnClickListener {
            startActivity(myIntent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}