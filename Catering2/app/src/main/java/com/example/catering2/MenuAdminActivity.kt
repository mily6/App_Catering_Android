package com.example.catering2

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.TextView
import android.widget.Toast
import android.widget.EditText
import android.widget.Button

class MenuAdminActivity : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    lateinit var displayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)

        val dbHelper = Database(this)
        db = dbHelper.readableDatabase
        displayTextView = findViewById(R.id.textView5)

        val deleteEditText = findViewById<EditText>(R.id.id)
        val deleteButton = findViewById<Button>(R.id.usun)

        deleteButton.setOnClickListener {
            val accountIdToDelete = deleteEditText.text.toString().toIntOrNull()

            if (accountIdToDelete != null) {
                // Zapytanie usuwające
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf(accountIdToDelete.toString())
                db.delete(LoginSchema.LoginEntry.TABLE_NAME, selection, selectionArgs)

                // Informacja o usunięciu użytkownika
                Toast.makeText(
                    this,
                    "Użytkownik o ID $accountIdToDelete został usunięty",
                    Toast.LENGTH_SHORT
                ).show()

                // Możesz chcieć odświeżyć wyświetlane dane po usunięciu użytkownika
                refreshData()
            } else {
                Toast.makeText(
                    this,
                    "Proszę wprowadzić poprawny numer ID użytkownika",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Możesz chcieć odświeżyć dane od razu po uruchomieniu aktywności
        refreshData()
    }

    // Funkcja do odświeżania danych
    fun refreshData() {
        val cursor = db.query(
            LoginSchema.LoginEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        var accountNumber = 1
        // Wyczyść displayTextView przed dodaniem nowych danych
        displayTextView.text = ""
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val login = getString(getColumnIndexOrThrow(LoginSchema.LoginEntry.COLUMN_NAME_LOGIN))
                val password = getString(getColumnIndexOrThrow(LoginSchema.LoginEntry.COLUMN_NAME_PASSWORD))
                val email = getString(getColumnIndexOrThrow(LoginSchema.LoginEntry.COLUMN_NAME_EMAIL))

                displayTextView.append("\nID: $id\n, Numer konta: $accountNumber\n, login: $login\n, hasło: $password\n, email: $email\n")
                accountNumber++
            }
        }

        cursor.close()
    }
}
