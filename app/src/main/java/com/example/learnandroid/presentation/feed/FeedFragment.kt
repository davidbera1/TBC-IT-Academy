package com.example.learnandroid.presentation.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.databinding.FragmentFeedBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate) {

    private val viewModel: FeedViewModel by viewModels()
    private val storiesAdapter = StoriesAdapter()
    private val postsAdapter = PostsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerViews()
        observeStories()
        observePosts()
    }

    private fun observeStories() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stories.collect { stories ->
                storiesAdapter.submitList(stories)
            }
        }
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collect { posts ->
                postsAdapter.submitList(posts)
            }
        }
    }

    private fun setUpRecyclerViews() {
        binding.rvStories.adapter = storiesAdapter
        binding.rvPosts.adapter = postsAdapter
    }
}