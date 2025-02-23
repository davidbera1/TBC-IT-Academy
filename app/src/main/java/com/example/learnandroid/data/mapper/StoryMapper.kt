package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.StoryDto
import com.example.learnandroid.presentation.model.Story

fun StoryDto.toStory(): Story {
    return Story(
        id = id,
        cover = cover,
        title = title
    )
}