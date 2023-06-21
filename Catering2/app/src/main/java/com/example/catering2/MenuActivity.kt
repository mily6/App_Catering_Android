package com.example.catering2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val myPizza = Intent(this, PizzaActivity::class.java)
        val myRagu = Intent(this, RaguActivity::class.java)
        val myBurger = Intent(this, BurgerActivity::class.java)

        val ragu = findViewById<Button>(R.id.button2)
        val piz = findViewById<Button>(R.id.pizza1)
        val burg = findViewById<Button>(R.id.button3)


        piz.setOnClickListener {
            startActivity(myPizza)
        }
        ragu.setOnClickListener {
            startActivity(myRagu)
        }
        burg.setOnClickListener {
            startActivity(myBurger)
        }
    }
}