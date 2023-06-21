package com.example.catering2

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val myPowrot = Intent(this, MainActivity::class.java)
        val powrot = findViewById<Button>(R.id.wroc)

        powrot.setOnClickListener {
            startActivity(myPowrot)
        }

        val myIntent = Intent(this, RegistrationActivity::class.java)
        val zalogowany = Intent(this, MenuActivity::class.java)
        val dbHelper = Database(this)
        val dbHelper2 = Database.PizzaDatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            LoginSchema.LoginEntry.COLUMN_NAME_LOGIN,
            LoginSchema.LoginEntry.COLUMN_NAME_PASSWORD
        )
        val loginEditText = findViewById<EditText>(R.id.login)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerLink)

        loginButton.setOnClickListener {
            val login = loginEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val cursor = db.query(
                LoginSchema.LoginEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            var found = false

            if (cursor.moveToFirst()) {
                do {
                    val baselogin = cursor.getString(cursor.getColumnIndexOrThrow(LoginSchema.LoginEntry.COLUMN_NAME_LOGIN))
                    val basepassword = cursor.getString(cursor.getColumnIndexOrThrow(LoginSchema.LoginEntry.COLUMN_NAME_PASSWORD))
                    if (login.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "Proszę wprowadzić login i hasło.", Toast.LENGTH_SHORT).show()
                        break
                    } else if (login == baselogin && password == basepassword) {
                        found = true
                        zalogowany.putExtra("key", baselogin)
                        startActivity(zalogowany)
                        dbHelper2.clearPizzaTable()
                        break
                    }
                } while (cursor.moveToNext())
            }

            if (!found) {
                Toast.makeText(this, "Nieprawidłowe dane logowania.", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }

        registerButton.setOnClickListener{
            startActivity(myIntent)
        }
    }
}