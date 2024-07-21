package me.jitesh.android.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.awaitValue(function: (T) -> Unit) {
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            function.invoke(value)
        }
    }
    observeForever(observer)
}