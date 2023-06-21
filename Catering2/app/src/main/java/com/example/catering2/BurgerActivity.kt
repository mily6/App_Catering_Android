package com.example.catering2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class BurgerActivity : AppCompatActivity() {
    private lateinit var dbHelper: Database.PizzaDatabaseHelper
    private lateinit var selectedPizza: String
    private var selectedPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burger)

        dbHelper = Database.PizzaDatabaseHelper(this)

        val items = listOf(
            "Burger: Klasyczny - 25zł",
            "Burger: BekonBurger - 27zł",
            "Burger: ChilliBurger - 30zł",
            "Burger: CheeseBurger - 32zł",
            "Burger: WegeBurger - 33zł"
        )

        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete)

        val adapter = ArrayAdapter(this, R.layout.list_item, items)

        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i).toString()
                val namePrice = itemSelected.split("-")
                val pizzaName = namePrice[0].trim()
                val pizzaPrice = namePrice[1].trim().replace("zł", "").trim().toInt()

                selectedPizza = pizzaName
                selectedPrice = pizzaPrice

                Toast.makeText(this, "Wybrano: $itemSelected", Toast.LENGTH_SHORT).show()
            }

        val save = findViewById<Button>(R.id.button2)
        save.setOnClickListener {
            val editTextAddress = findViewById<EditText>(R.id.adres)
            val address = editTextAddress.text.toString()

            val editNumber = findViewById<EditText>(R.id.numer)
            val number = editNumber.text.toString()

            if (::selectedPizza.isInitialized && selectedPrice > 0) {
                dbHelper.addPizza(selectedPizza, selectedPrice, address, number)
                Toast.makeText(this, "Zapisano do bazy: $selectedPizza - $selectedPrice zł, Adres: $address, Numer: $number", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wybierz burgera z listy.", Toast.LENGTH_SHORT).show()
            }
            val summaryIntent = Intent(this, PodsumowanieActivity::class.java)
            startActivity(summaryIntent)
        }
    }
}