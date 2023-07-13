/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.yzh.demoapp.R

/**
 * @author baronyang@tencent.com
 * @since 2023/7/12 16:16
 */
class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val adapter = MyAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        findViewById<View>(R.id.button1).apply {
            setOnClickListener {
                adapter.replaceData(listOf(0, 1))
                viewPager.adapter = adapter
            }
        }
        findViewById<View>(R.id.button2).apply {
            setOnClickListener {
                adapter.replaceData(listOf(3))
                viewPager.adapter = adapter
            }
        }
    }

    class RedFragment() : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_calculator, container, false)?.also {
                it.setBackgroundColor(ResourcesCompat.getColor(it.resources, R.color.red, null))
            }
        }
    }

    class GreenFragment() : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_calculator, container, false)?.also {
                it.setBackgroundColor(ResourcesCompat.getColor(it.resources, R.color.green, null))
            }
        }
    }

    class PurpleFragment() : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_calculator, container, false)?.also {
                it.setBackgroundColor(ResourcesCompat.getColor(it.resources, R.color.purple_200, null))
            }
        }
    }

    class BlackFragment() : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_calculator, container, false)?.also {
                it.setBackgroundColor(ResourcesCompat.getColor(it.resources, R.color.black, null))
            }
        }
    }

    class MyAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        val data = MutableList(2) { it }

        fun replaceData(dataList: List<Int>) {
            data.clear()
            data.addAll(dataList)
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Fragment {
            return if (position < count) {
                if (data[position] == 0) {
                    RedFragment()
                } else if (data[position] == 3) {
                    PurpleFragment()
                } else {
                    GreenFragment()
                }
            } else BlackFragment()
        }
    }

    companion object {
        private const val TAG: String = "ViewPagerActivity"
    }
}