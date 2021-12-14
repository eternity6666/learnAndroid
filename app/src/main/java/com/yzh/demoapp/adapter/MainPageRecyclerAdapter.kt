package com.yzh.demoapp.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yzh.demoapp.data.MainPageItemData
import com.yzh.demoapp.view.ItemView

class MainPageRecyclerAdapter(
    private val dataSet: ArrayList<MainPageItemData>,
    private val context: Context
) : RecyclerView.Adapter<MainPageRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: ItemView) : RecyclerView.ViewHolder(view) {

        private val item: ItemView = view

        fun bind(data: MainPageItemData) {
            item.setOnClickListener {
                val intent = Intent()
                intent.setClass(it.context, data.clazz.java)
                it.context.startActivity(intent)
            }
            item.setTitle(data.title)
            item.setDescriptionText(data.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemView(parent.context))
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

}
