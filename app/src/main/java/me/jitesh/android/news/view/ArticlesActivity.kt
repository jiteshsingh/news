package me.jitesh.android.news.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import me.jitesh.android.news.databinding.ArticlesActivityBinding
import me.jitesh.android.news.viewmodel.ArticlesViewModel

class ArticlesActivity :
    BaseActivity<ArticlesActivityBinding>(ArticlesActivityBinding::class.java) {
    companion object {
        private const val TAG = "ArticlesActivity"
    }

    private val articlesViewModel: ArticlesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        val adaptor = ArticlesListAdapter(this)
        binding.list.layoutManager = adaptor.layoutManager
        binding.list.adapter = adaptor
        articlesViewModel.articlesList.observe(this) { adaptor.submitList(it) }
        articlesViewModel.refreshing.observe(this) { refreshing ->
            if (refreshing) {
                binding.shimmer.startShimmer()
            } else {
                binding.shimmer.stopShimmer()
            }
            binding.list.visibility = if (refreshing) GONE else VISIBLE
            binding.shimmer.visibility = if (refreshing) VISIBLE else GONE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            articlesViewModel.refresh()
            return true
        }
        return super.onKeyDown(keyCode, event);
    }
}