package me.jitesh.android.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.jitesh.android.news.BuildConfig
import me.jitesh.android.news.R
import me.jitesh.android.news.appContext
import me.jitesh.android.news.awaitValue
import me.jitesh.android.news.cache.AppliedFilters
import me.jitesh.android.news.cache.ArticleDatabase
import me.jitesh.android.news.model.Country
import me.jitesh.android.news.remote.NewsApiService.Companion.ApiResponse
import me.jitesh.android.news.remote.Remotes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ArticlesViewModel : ViewModel() {

    val country = MutableLiveData<Country>()
    val category = MutableLiveData<String>()
    val articlesList = ArticleDatabase.articleDao.getAll()
    val refreshing = MutableLiveData(false)
    val error = MutableLiveData<String?>(null) // FUTURE: introduce error codes for user
    private val apiCallback = ApiCallback()

    init {
        val cachedCountry = AppliedFilters.getCountry()
            ?.let { cached -> countries.find { it.code == cached } }
            ?: defaultCountry.also { AppliedFilters.setCountry(it.code) }
        country.value = cachedCountry
        val cachedCategory = AppliedFilters.getCategory() ?: categories.first()
            .also { AppliedFilters.setCategory(it) }
        category.value = cachedCategory
        // trigger auto-refresh if cache is empty
        articlesList.awaitValue { if (it.isEmpty()) refresh() }
    }

    @Synchronized
    fun refresh() {
        if (refreshing.value == true) return
        // fetch articles from remote, cache in db and set "refreshing" to false.
        refreshing.value = true
        error.value = null
        Remotes.newsApi.listRepos(
            country.value?.code ?: "",
            category.value ?: ""
        ).enqueue(apiCallback)
    }

    fun setCountry(country: Country) {
        AppliedFilters.setCountry(country.code)
        this.country.value = country
        refresh()
    }

    fun setCategory(category: String) {
        AppliedFilters.setCategory(category)
        this.category.value = category
        refresh()
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
            ArticleDatabase.articleDao.cache(apiResponse.articles.filter {
                it.title != "[Removed]"
            })
        }

        private fun onError(message: String = "Api Error") {
            error.value = message
        }

        override fun onFailure(call: Call<ApiResponse>, throwable: Throwable) {
            refreshing.value = false
            throwable.localizedMessage?.let { onError(it) } ?: onError()
        }
    }

    companion object {
        private const val TAG = "ArticlesViewModel"

        val categories: Array<String> by lazy {
            appContext.resources.getStringArray(R.array.news_categories)
        }

        val countries by lazy {
            val enabledCountries = HashSet<String>()
            enabledCountries.addAll(appContext.resources.getStringArray(R.array.news_countries))
            val countriesSet = HashSet<Country>()
            Locale.getAvailableLocales().filter {
                it.displayCountry.isNotBlank() && enabledCountries.contains(it.country)
            }.forEach {
                countriesSet.add(Country(it.displayCountry, it.country))
            }
            val countriesList = ArrayList(countriesSet)
            countriesList.sort()
            countriesList
        }

        private val defaultCountry by lazy {
            val defaultLocale = Locale.getDefault()
            countries.find { it.code == defaultLocale.country } ?: countries.first()
        }
    }
}