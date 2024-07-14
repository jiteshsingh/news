package me.jitesh.android.news

import android.content.Context
import android.os.Handler
import android.os.Looper

lateinit var appContext: Context
val mainHandler = Handler(Looper.getMainLooper())