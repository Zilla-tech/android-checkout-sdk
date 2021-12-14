package com.zilla.commons

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(conserveSpace: Boolean = false) {
    visibility = if (conserveSpace) View.INVISIBLE else View.GONE
}
