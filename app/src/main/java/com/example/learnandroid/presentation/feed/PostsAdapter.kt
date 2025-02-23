package com.example.learnandroid.presentation.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnandroid.R
import com.example.learnandroid.databinding.PostItemBinding
import com.example.learnandroid.presentation.model.Post
import com.example.learnandroid.utils.toDate

class PostsAdapter : ListAdapter<Post, PostsAdapter.PostsViewHolder>(PostDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = getItem(position)
        return holder.onBind(post)
    }

    inner class PostsViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(post.owner.profile)
                    .error(R.drawable.error)
                    .into(imgAuthorIcon)

                tvAuthor.text = post.owner.firstName.plus(" ").plus(post.owner.lastName)
                tvDate.text = post.owner.postDate.toDate()
                tvDescription.text = post.title

                if (post.comments == 1) {
                    tvComments.text = post.comments.toString().plus(" Comment")
                } else {
                    tvComments.text = post.comments.toString().plus(" Comments")
                }

                if (post.likes == 1) {
                    tvLikes.text = post.likes.toString().plus(" Like")
                } else {
                    tvLikes.text = post.likes.toString().plus(" Likes")
                }

                val imageViews = listOf(imgPost1, imgPost2, imgPost3)
                imageViews.forEach { it.visibility = View.GONE }

                if (!post.images.isNullOrEmpty()) {
                    imageViews.forEachIndexed { index, imageView ->
                        if (index < post.images.count()) {
                            imageView.visibility = View.VISIBLE
                            Glide.with(itemView.context)
                                .load(post.images[index])
                                .error(R.drawable.error)
                                .into(imageView)
                        }
                    }
                }
            }
        }
    }
}

class PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}