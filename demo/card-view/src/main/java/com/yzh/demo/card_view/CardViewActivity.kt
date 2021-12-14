package com.yzh.demo.card_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class CardViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)
        val cardView = findViewById<CardView>(R.id.card_view)
        cardView.radius = 60f
    }
}