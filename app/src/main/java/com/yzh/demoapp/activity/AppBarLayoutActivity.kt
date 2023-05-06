/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.appbar.AppBarLayout
import com.yzh.annotation.YActivity
import com.yzh.demoapp.R
import com.yzh.demoapp.util.dp100
import com.yzh.demoapp.util.dp50
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@YActivity(
    title = "AppBarLayout滚动到目标高度", description = "通过编码的方式控制AppBarLayout滚动到目标高度"
)
class AppBarLayoutActivity : ComponentActivity() {

    private val coordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.coordinator_container)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[AppBarLayoutViewModel::class.java]
    }
    private val appBarLayout by lazy {
        findViewById<AppBarLayout>(R.id.app_bar_layout).apply {
            addOnOffsetChangedListener { _, verticalOffset ->
                viewModel.dispatch(AppBarLayoutViewModel.Action.AddLog("$verticalOffset"))
            }
        }
    }
    private val listScrollView by lazy {
        findViewById<NestedScrollView>(R.id.list_scroll_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_bar_layout)
        initView()
    }

    private fun initView() {
        findViewById<TextView>(R.id.text_view).also { textView ->
            textView.setOnClickListener {
                val intent = Intent()
                intent.setClass(this, AppListActivity::class.java)
                startActivityForResult(intent, KEY_REQUEST_CODE_ACTIVITY)
            }
        }
        findViewById<ComposeView>(R.id.log_view).also {
            it.setContent {
                val data = viewModel.logData.collectAsState(initial = emptyList())
                LazyVerticalGrid(columns = GridCells.Adaptive(40.dp), content = {
                    items(data.value) { text ->
                        Text(text = text)
                    }
                })
            }
        }
        findViewById<LinearLayout>(R.id.list_layout).apply {
            repeat(100) {
                val textView = TextView(context)
                val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp100)
                textView.text = "$it"
                textView.gravity = Gravity.CENTER
                textView.background = ColorDrawable(
                    ResourcesCompat.getColor(
                        resources,
                        if (it % 2 == 0) R.color.purple_200 else R.color.teal_200,
                        null
                    )
                )
                addView(textView, layoutParams)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KEY_REQUEST_CODE_ACTIVITY) {
            hideAppBarPartArea()
        }
    }

    private fun hideAppBarPartArea() {
        val appBarLayoutParams = appBarLayout.layoutParams as? CoordinatorLayout.LayoutParams
        (appBarLayoutParams?.behavior as? AppBarLayout.Behavior)?.let { behavior ->
            val distance = behavior.topAndBottomOffset + dp50
            var last = 0
            ValueAnimator.ofInt(0, distance).apply {
                duration = 300
                addUpdateListener {
                    val currentValue = it.animatedValue as Int
                    val dy = currentValue - last
                    last = currentValue
                    behavior.onNestedPreScroll(
                        coordinatorLayout, appBarLayout, listScrollView, 0, dy, intArrayOf(0, 0),
                        ViewCompat.TYPE_NON_TOUCH
                    )
                }
                Log.i(TAG, "ValueAnimator start")
                start()
            }
        }
    }

    class AppBarLayoutViewModel : ViewModel() {
        private val _logData = MutableStateFlow(emptyList<String>())
        val logData: Flow<List<String>> = _logData

        fun dispatch(action: Action) {
            when (action) {
                is Action.AddLog -> {
                    viewModelScope.launch {
                        _logData.emit(_logData.value + action.log)
                    }
                }
            }
        }

        sealed class Action {
            class AddLog(val log: String) : Action()
        }
    }

    companion object {
        private const val TAG: String = "AppBarLayoutActivity"
        private const val KEY_REQUEST_CODE_ACTIVITY = 200003
    }
}