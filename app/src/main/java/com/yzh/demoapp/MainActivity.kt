package com.yzh.demoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.yzh.demoapp.activity.GradientDrawableActivity
import com.yzh.demoapp.adapter.MainPageRecyclerAdapter
import com.yzh.demoapp.data.DataSource

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataList = DataSource.getMainPageList()
        val recyclerView: RecyclerView = findViewById(R.id.item_list)
        recyclerView.adapter = MainPageRecyclerAdapter(dataList, this)
        val intent = Intent()
        intent.setClass(this, GradientDrawableActivity::class.java)
        //   startActivity(intent)
    }

}