package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.PostDto
import com.example.learnandroid.data.model.StoryDto
import retrofit2.http.GET

interface ContentApiService {
    @GET("00a18030-a8c7-47c4-b0c5-8bff92a29ebf")
    suspend fun getStories() : List<StoryDto>

    @GET("1ba8b612-8391-41e5-8560-98e4a48decc7")
    suspend fun getPosts() : List<PostDto>
}