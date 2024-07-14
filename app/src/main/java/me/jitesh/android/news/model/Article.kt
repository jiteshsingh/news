package me.jitesh.android.news.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0, // auto-inc id for local db
    var author: String?,
    var title: String,
    var description: String?,
    var url: String, // article origin url
    var urlToImage: String?, // thumbnail url
    var publishedAt: String, // example; 2024-07-13T15:12:24Z
    var content: String? // full content of the article
)