package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.PostDto
import com.example.learnandroid.presentation.model.Post

fun PostDto.toPost(): Post {
    return Post(
        id = id,
        images = images,
        title = title,
        comments = comments,
        likes = likes,
        shareContent = shareContent,
        owner = owner.toOwner()
    )
}

private fun PostDto.OwnerDto.toOwner(): Post.Owner {
    return Post.Owner(
        firstName = firstName,
        lastName = lastName,
        profile = profile,
        postDate = postDate
    )
}