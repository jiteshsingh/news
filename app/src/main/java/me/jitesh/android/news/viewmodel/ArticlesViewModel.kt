package me.jitesh.android.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import me.jitesh.android.news.BuildConfig
import me.jitesh.android.news.cache.ArticleDatabase
import me.jitesh.android.news.model.Article
import me.jitesh.android.news.remote.NewsApiService.Companion.ApiResponse
import me.jitesh.android.news.remote.Remotes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel : ViewModel() {
    companion object {
        private const val TAG = "ArticlesViewModel"
    }

    val articlesList = ArticleDatabase.articleDao.getAll()
    val refreshing = MutableLiveData(false)
    val error = MutableLiveData<String?>(null)
    private val apiCallback = ApiCallback()

    init {
        articlesList.observeForever(object : Observer<List<Article>> {
            override fun onChanged(value: List<Article>) {
                articlesList.removeObserver(this)
                if (value.isEmpty()) refresh()
            }
        })
    }

    fun refresh() {
        refreshing.value = true
        // get from remote, store in db and set "refreshing" to false
        Remotes.newsApi.listRepos(BuildConfig.NEWS_API_KEY, "us", "technology")
            .enqueue(apiCallback)
    }

    private inner class ApiCallback : Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            refreshing.value = false
            if (!response.isSuccessful) {
                onError(); return
            }
            val apiResponse = response.body()
            if (apiResponse == null || apiResponse.status != "ok") {
                onError(); return
            }
            ArticleDatabase.articleDao.cache(apiResponse.articles)
        }

        private fun onError(message: String = "Api Error") {
            error.value = message
        }

        override fun onFailure(call: Call<ApiResponse>, throwable: Throwable) {
            refreshing.value = false
            throwable.localizedMessage?.let { onError(it) } ?: onError()
        }
    }
}