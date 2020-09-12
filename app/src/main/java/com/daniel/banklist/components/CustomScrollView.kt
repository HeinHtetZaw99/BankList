package com.daniel.banklist.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView
import com.daniel.banklist.showLogE

class CustomScrollView : NestedScrollView {
    private var lastScrollUpdate: Long = -1
    private var listener : CustomScrollListener? = null
    private val customTimeRange = 500L
    private var scrollable : Boolean = true

    constructor(context: Context) : super(context)
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)


    fun enableNestedScrolling(){
        isNestedScrollingEnabled = true
    }

    fun disableNestedScrolling(){
        isNestedScrollingEnabled = false
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun setScrollingEnabled(enabled: Boolean) {
        this.scrollable = enabled
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        showLogE("CustomScrollView" ,"onTouchEvent's Scrollable ${super.onTouchEvent(ev) && scrollable}")
        return super.onTouchEvent(ev) && scrollable
    }

    fun addCustomScrollListener(listener : CustomScrollListener){
        this.listener = listener
    }

    private inner class ScrollStateHandler : Runnable {
        override fun run() {
            if (scrollable) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastScrollUpdate > customTimeRange) {
                    lastScrollUpdate = -1
                    listener?.onScrollEnd()
                } else {
                    postDelayed(this, customTimeRange)
                }
            }
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (lastScrollUpdate == -1L) {
            listener?.onScrollStart()
            postDelayed(ScrollStateHandler(), customTimeRange)
        }
        lastScrollUpdate = System.currentTimeMillis()
    }

    interface CustomScrollListener {
        fun onScrollStart()
        fun onScrollEnd()
    }

}