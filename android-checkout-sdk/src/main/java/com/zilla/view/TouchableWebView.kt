package com.zilla.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.webkit.WebView

class TouchableWebView : WebView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onTouchEvent(event: MotionEvent): Boolean {

        // Prevent crash if activity is hardware accelerated
        if (event.findPointerIndex(0) == -1) {
            return super.onTouchEvent(event)
        }

        requestDisallowInterceptTouchEvent(true)

        performClick()
        return super.onTouchEvent(event)
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        val inputConnection = BaseInputConnection(this, false)
        outAttrs?.imeOptions = EditorInfo.IME_ACTION_DONE
//        val inputConnection = super.onCreateInputConnection(outAttrs)
        return inputConnection
    }

}