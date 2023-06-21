package com.example.catering2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class PodsumowanieActivity : AppCompatActivity() {
    private lateinit var dbHelper: Database.PizzaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podsumowanie)

        dbHelper = Database.PizzaDatabaseHelper(this)

        val cursor = dbHelper.getData()

        // Użyjemy StringBuilder, aby stworzyć tekst do wyświetlenia
        val stringBuilder = StringBuilder()
        Log.d("PodsumowanieActivity", "Ilość rekordów: ${cursor.count}")
        while (cursor.moveToNext()) {
            val pizzaName = cursor.getString(cursor.getColumnIndexOrThrow(Database.PizzaDatabaseHelper.COLUMN_NAME))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow(Database.PizzaDatabaseHelper.COLUMN_PRICE))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(Database.PizzaDatabaseHelper.COLUMN_ADDRESS))
            val number = cursor.getString(cursor.getColumnIndexOrThrow(Database.PizzaDatabaseHelper.COLUMN_NUMBER))
            stringBuilder.append("\n$pizzaName\n, Cena: $price zł\n, Adres: $address\n, Termin: $number\n")
        }

        // Zakładamy, że w layoucie dla tej aktywności jest TextView o ID 'textView'
        val textView = findViewById<TextView>(R.id.pods)
        textView.text = stringBuilder.toString()
    }
}

