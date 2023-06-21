package com.example.catering2

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        val zalogowany = Intent(this, MenuAdminActivity::class.java)
        val dbHelper = Database.AdminDatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            Database.AdminDatabaseHelper.COLUMN_LOGIN,
            Database.AdminDatabaseHelper.COLUMN_PASSWORD
        )
        val loginEditText = findViewById<EditText>(R.id.login)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val login = loginEditText.text.toString().trim()
            val password = (passwordEditText.text.toString().trim())
            val cursor = db.query(
                Database.AdminDatabaseHelper.TABLE_ADMIN,
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
                    val baseLogin = cursor.getString(cursor.getColumnIndexOrThrow(Database.AdminDatabaseHelper.COLUMN_LOGIN))
                    val basePassword = cursor.getString(cursor.getColumnIndexOrThrow(Database.AdminDatabaseHelper.COLUMN_PASSWORD))
                    if (login.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "Proszę wprowadzić login i hasło.", Toast.LENGTH_SHORT).show()
                        break
                    } else if (login == baseLogin && password == basePassword) {
                        found = true
                        zalogowany.putExtra("key", baseLogin)
                        startActivity(zalogowany)
                        break
                    }
                } while (cursor.moveToNext())
            }

            if (!found) {
                Toast.makeText(this, "Nieprawidłowe dane logowania.", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }
    }
}