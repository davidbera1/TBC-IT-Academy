package com.example.learnandroid.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.ContentRepository
import com.example.learnandroid.presentation.model.Post
import com.example.learnandroid.presentation.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val contentRepository: ContentRepository) : ViewModel() {

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> = _stories

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        getStories()
        getPosts()
    }

    private fun getStories() {
        viewModelScope.launch {
            _stories.value = contentRepository.getStories()
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            _posts.value = contentRepository.getPosts()
        }
    }
}