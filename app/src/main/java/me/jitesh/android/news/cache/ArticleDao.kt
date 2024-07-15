package me.jitesh.android.news.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import me.jitesh.android.news.model.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): LiveData<List<Article>>

    // FUTURE: cache at max 100 latest articles only
    @Insert
    fun add(articles: List<Article>)

    @Delete
    fun delete(article: Article)

    @Query("DELETE FROM article")
    fun deleteAll()

    @Transaction
    fun cache(articles: List<Article>) {
        deleteAll()
        add(articles)
    }
}