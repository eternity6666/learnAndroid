package com.yzh.demoapp.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yzh.demoapp.calculator.fragment.CalculatorFragment

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        val fragment = CalculatorFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}