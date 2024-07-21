package me.jitesh.android.news.cache

import android.content.Context
import me.jitesh.android.news.appContext

object AppliedFilters {
    private const val TAG = "Filters"
    private val sharedPrefs = appContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)

    private const val SPK_COUNTRY = "country"
    private const val SPK_CATEGORY = "category"

    fun getCountry(): String? {
        return sharedPrefs.getString(SPK_COUNTRY, null)
    }

    fun setCountry(country: String?) {
        sharedPrefs.edit().putString(SPK_COUNTRY, country).apply()
    }

    fun getCategory(): String? {
        return sharedPrefs.getString(SPK_CATEGORY, null)
    }

    fun setCategory(category: String?) {
        sharedPrefs.edit().putString(SPK_CATEGORY, category).apply()
    }
}