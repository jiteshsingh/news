package me.jitesh.android.news.view

import android.content.Context
import android.widget.ArrayAdapter

open class BaseListAdapter<T> : ArrayAdapter<T> {
    constructor(context: Context, resource: Int, objects: Array<out T>) : super(
        context,
        resource,
        objects
    )

    constructor(context: Context, resource: Int) : super(context, resource)

    companion object {
        private const val TAG = "BaseListAdapter"
    }
}
