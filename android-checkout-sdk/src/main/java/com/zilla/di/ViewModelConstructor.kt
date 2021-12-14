package com.zilla.di

import androidx.lifecycle.ViewModel

interface ViewModelConstructor {
    fun create(): ViewModel
}
