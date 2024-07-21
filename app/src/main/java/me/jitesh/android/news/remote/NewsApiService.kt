package me.jitesh.android.news.remote

import me.jitesh.android.news.BuildConfig
import me.jitesh.android.news.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"

        data class ApiResponse(val status: String, val articles: List<Article>)
    }

    @GET("top-headlines?sortBy=publishedAt")
    fun listRepos(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY,
    ): Call<ApiResponse>
}