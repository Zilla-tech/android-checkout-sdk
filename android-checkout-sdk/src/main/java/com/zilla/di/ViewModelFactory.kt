package com.zilla.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory {

    fun build(viewModelConstructor: ViewModelConstructor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelConstructor.create() as T
            }
        }
    }

}
