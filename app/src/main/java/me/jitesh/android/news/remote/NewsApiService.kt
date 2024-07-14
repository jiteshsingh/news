package me.jitesh.android.news.remote

import me.jitesh.android.news.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"

        data class ApiResponse(val status: String, val articles: List<Article>)
    }

    // FUTURE: write an okhttp interceptor to automatically inject apiKey
    // FUTURE: add option for choosing multiple countries and categories as a pref
    @GET("top-headlines?sortBy=publishedAt")
    fun listRepos(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String
    ): Call<ApiResponse>
}