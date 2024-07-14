package me.jitesh.android.news.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(private val viewBindingClass: Class<T>) : Fragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = constructBinding()
        return binding.root
    }

    open fun constructBinding(): T {
        return viewBindingClass.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as T
    }
}