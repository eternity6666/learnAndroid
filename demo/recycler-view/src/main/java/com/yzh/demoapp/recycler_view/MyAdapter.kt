package com.yzh.demoapp.recycler_view

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yzh.demoapp.data.item.DataItem

class MyAdapter(
    private val dataSet: ArrayList<DataItem>,
    private val context: Context
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: ItemView) : RecyclerView.ViewHolder(view) {

        private val item: ItemView = view

        fun bind(data: DataItem) {
            item.setTitle(data.title)
            item.setDescriptionText(data.subTitle)
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
