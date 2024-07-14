package me.jitesh.android.news.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.jitesh.android.news.databinding.ArticleItemBinding
import me.jitesh.android.news.model.Article

class ArticlesListAdapter(private val context: Context) :
    ListAdapter<Article, ArticlesListAdapter.ArticleHolder>(ArticleDiffer) {

    private val picasso by lazy { Picasso.get() }
    val layoutManager = WrapContentLinearLayoutManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(
            ArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArticleHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                title.text = article.title
                desc.text = article.description
                date.text = article.publishedAt
                article.urlToImage?.let { picasso.load(it).into(thumbnail) }
            }
        }
    }

    object ArticleDiffer : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    class WrapContentLinearLayoutManager(context: Context) :
        LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
        override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
            try {
                super.onLayoutChildren(recycler, state)
            } catch (_: IndexOutOfBoundsException) {
            }
        }
    }

}