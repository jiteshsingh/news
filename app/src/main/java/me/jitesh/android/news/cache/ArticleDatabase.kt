package me.jitesh.android.news.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.jitesh.android.news.appContext
import me.jitesh.android.news.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {

        private val instance by lazy {
            Room.databaseBuilder(appContext, ArticleDatabase::class.java, "article")
                .allowMainThreadQueries() // FUTURE: remove this
                .addMigrations()
                .build()
        }

        val articleDao by lazy {
            instance.articleDao()
        }
    }
}