package me.jitesh.android.news.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.google.android.material.color.DynamicColors

abstract class BaseActivity<T : ViewBinding>(private val viewBindingClass: Class<T>) :
    AppCompatActivity() {
    companion object {
        private const val TAG = "BaseActivity"
    }

    protected lateinit var binding: T
    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivityIfAvailable(this)
        windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        enableEdgeToEdge()
        binding = constructBinding()
        setContentView(binding.root)
        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val systemBars = insets.getInsets(WindowInsets.Type.systemBars())
            view.updatePadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            // sometimes overview screen removes fullscreen flags (possibly due to a system bug);
            //   overview screen doesn't pause activity, but changes window insets;
            //   so we trigger fullscreen on inset changes, just to ensure the flags remain intact.
            insets
        }
        onBackInvokedDispatcher.registerOnBackInvokedCallback(1_000) {
            onBackInvoked()
            onBackPressedDispatcher.onBackPressed()
        }
    }

    protected open fun onBackInvoked() {}

    open fun constructBinding(): T {
        return viewBindingClass.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as T
    }
}