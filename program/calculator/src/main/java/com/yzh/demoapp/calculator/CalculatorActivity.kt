package com.yzh.demoapp.calculator

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.yzh.demoapp.calculator.fragment.CalculatorFragment

class CalculatorActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        val fragment = CalculatorFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}