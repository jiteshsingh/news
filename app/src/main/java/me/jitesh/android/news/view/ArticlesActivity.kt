package me.jitesh.android.news.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import me.jitesh.android.news.databinding.ArticlesActivityBinding
import me.jitesh.android.news.viewmodel.ArticlesViewModel

/**
 * Launcher activity containing list of articles.
 */
class ArticlesActivity :
    BaseActivity<ArticlesActivityBinding>(ArticlesActivityBinding::class.java) {
    companion object {
        private const val TAG = "ArticlesActivity"
    }

    private val articlesViewModel: ArticlesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adaptor = ArticlesListAdapter(this)
        binding.list.layoutManager = adaptor.layoutManager
        binding.list.adapter = adaptor
        articlesViewModel.articlesList.observe(this) {
            // if list is empty then we swap the list view with an empty indicator view
            val empty = it.isEmpty()
            binding.empty.visibility = if (empty) VISIBLE else GONE
            binding.list.visibility = if (empty) GONE else VISIBLE
            adaptor.submitList(it)
        }
        articlesViewModel.refreshing.observe(this) { refreshing ->
            adaptor.layoutManager.setScrollEnabled(!refreshing)
            // we want to achieve shimmer on actual list items that were being displayed, so we
            //   pop-out and pop back in the listview into shimmer view
            if (refreshing) {
                binding.root.removeView(binding.list)
                binding.shimmer.addView(binding.list)
                binding.shimmer.startShimmer()
                binding.shimmer.visibility = VISIBLE
            } else {
                binding.shimmer.stopShimmer()
                binding.shimmer.removeView(binding.list)
                binding.root.addView(binding.list)
                binding.shimmer.visibility = GONE
            }
        }
        articlesViewModel.error.observe(this) {
            val hasError = it != null
            binding.error.visibility = if (hasError) VISIBLE else GONE
            binding.list.visibility = if (hasError) GONE else VISIBLE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && event?.isLongPress == true) {
            articlesViewModel.refresh()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}