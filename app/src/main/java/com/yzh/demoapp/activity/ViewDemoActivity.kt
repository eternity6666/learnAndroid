/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yzh.demoapp.R

/**
 * @author baronyang@tencent.com
 * @since 2023/9/26 16:16
 */
class ViewDemoActivity : AppCompatActivity() {

    private val buttonView by lazy {
        findViewById<Button>(R.id.button)
    }
    private val button2View by lazy {
        findViewById<Button>(R.id.button_2)
    }

    private val containerView by lazy {
        findViewById<ViewGroup>(R.id.container)
    }
    private val textView by lazy {
        findViewById<TextView>(R.id.creator_subs_btn_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_demo)
        var x = true
        buttonView.setOnClickListener {
            x = !x
            textView.text = if (x) {
                "关注"
            } else {
                "追"
            }
        }
        var y = true
        button2View.setOnClickListener {
            y = !y
            containerView.isEnabled = y
        }
    }

    companion object {
        private const val TAG: String = "ViewDemoActivity"
    }
}