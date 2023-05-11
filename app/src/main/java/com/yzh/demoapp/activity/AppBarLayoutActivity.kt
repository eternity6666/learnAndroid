/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.addListener
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
    private val contentLayout by lazy {
        findViewById<FrameLayout>(R.id.content_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_bar_layout)
        initView()
    }

    private fun initView() {
        findViewById<TextView>(R.id.hide).also {
            it.setOnClickListener {
                hideAppBarPartArea()
            }
        }
        findViewById<TextView>(R.id.show).also {
            it.setOnClickListener {
                showAppBar()
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
        val behavior = (appBarLayoutParams?.behavior as? AppBarLayout.Behavior) ?: return
        val distance = behavior.topAndBottomOffset + dp50
        val animator = buildAnimator(
            distance, behavior, coordinatorLayout, appBarLayout, contentLayout
        )
        AnimatorSet().apply {
            play(animator)
            addListener(
                onEnd = {
                    Handler(Looper.getMainLooper()).postDelayed({
                        showAppBar()
                    }, 3000)
                }
            )
            start()
        }
    }

    private fun showAppBar() {
        val appBarLayoutParams = appBarLayout.layoutParams as? CoordinatorLayout.LayoutParams
        val behavior = (appBarLayoutParams?.behavior as? AppBarLayout.Behavior) ?: return
        val distance = behavior.topAndBottomOffset
        val animator = buildAnimator(
            distance, behavior, coordinatorLayout, appBarLayout, contentLayout
        )
        val color = Color(0xFFFFFFFF)
        AnimatorSet().apply {
            play(animator)
            start()
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

        private fun buildAnimator(
            distance: Int,
            behavior: AppBarLayout.Behavior,
            coordinatorLayout: CoordinatorLayout,
            appBarLayout: AppBarLayout,
            contentLayout: View,
        ): ValueAnimator {
            var last = 0
            return ValueAnimator.ofInt(0, distance).apply {
                duration = 300
                addUpdateListener {
                    val currentValue = it.animatedValue as Int
                    val dy = currentValue - last
                    last = currentValue
                    behavior.onNestedPreScroll(
                        coordinatorLayout, appBarLayout, contentLayout,
                        0, dy, intArrayOf(0, 0), ViewCompat.TYPE_NON_TOUCH
                    )
                }
                Log.i(TAG, "ValueAnimator start")
            }
        }
    }
}