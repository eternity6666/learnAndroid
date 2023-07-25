package com.yzh.demoapp.recycler_view

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yzh.demoapp.data.source.FirstDataSource

class RecyclerViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = MyAdapter(FirstDataSource.dataList, this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(
            MyDecoration()
        )
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            recyclerView.layoutManager = GridLayoutManager(this, 5, RecyclerView.HORIZONTAL, false)
        }
        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            recyclerView.layoutManager = StaggeredGridLayoutManager(4, RecyclerView.VERTICAL)
        }
    }

    class MyDecoration() : RecyclerView.ItemDecoration() {


        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = 10
            outRect.left = 10
        }

    }
}