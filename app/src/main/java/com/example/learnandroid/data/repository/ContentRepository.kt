package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toPost
import com.example.learnandroid.data.mapper.toStory
import com.example.learnandroid.data.remote.ContentApiService
import com.example.learnandroid.presentation.model.Post
import com.example.learnandroid.presentation.model.Story
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(private val contentApiService: ContentApiService) {

    suspend fun getStories(): List<Story> {
        return contentApiService.getStories().map { it.toStory() }
    }

    suspend fun getPosts(): List<Post> {
        return contentApiService.getPosts().map { it.toPost() }
    }
}