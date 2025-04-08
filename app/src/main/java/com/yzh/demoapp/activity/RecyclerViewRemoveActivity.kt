/*
 * CopyRight (C) 2024 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.OnHierarchyChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yzh.annotation.YActivity
import com.yzh.demoapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author baronyang@tencent.com
 * @since 2024/2/27 16:16
 */
@YActivity(
    title = "RecyclerView remove view 时机"
)
class RecyclerViewRemoveActivity : AppCompatActivity() {

    private val itemList = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_remove_view)
        val adapter = RecyclerViewRemoveAdapter(itemList)
        val view = findViewById<RecyclerView>(R.id.recycler_view)?.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            it.setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View?, child: View?) {
                }

                override fun onChildViewRemoved(parent: View?, child: View?) {
                    Log.i(TAG, "view=${(child as? TextView)?.text ?: child} be removed")
                }
            })
        }
        itemList.addAll(listOf(0, 1, 2))
        adapter.notifyDataSetChanged()
        view?.scrollToPosition(1)
        lifecycleScope.launch {
            launch {
                delay(2000)
                Log.e(TAG, "onLoadFinish")
                itemList.clear()
                itemList.addAll(listOf(-2, -1, 0, 1, 2, 3))
                adapter.notifyDataSetChanged()
            }
        }
    }

    class RecyclerViewRemoveAdapter(
        private val itemList: List<Int>,
    ) : RecyclerView.Adapter<RecyclerViewRemoveAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                TextView(parent.context).also {
                    it.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                }
            )
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun getItemViewType(position: Int): Int {
            return 0
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val value = itemList.getOrElse(position) { -1 }
            holder.view.let {
                it.text = "$value"
                it.background = ColorDrawable(
                    ResourcesCompat.getColor(
                        it.resources,
                        when (value) {
                            -1 -> R.color.red
                            else -> when (value % 3) {
                                1 -> R.color.teal_200
                                2 -> R.color.green
                                else -> R.color.purple_200
                            }
                        },
                        null
                    )
                )
            }
        }

        class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {

        }
    }

    companion object {
        private const val TAG = "RecyclerViewRemoveActivity"
    }
}