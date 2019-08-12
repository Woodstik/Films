package com.example.films.data.models

import java.util.*

data class Post(
    val id: String,
    val title: String,
    val createdDate: Date,
    val source: String,
    val thumbnail: String,
    val points: Long,
    val comments: List<Comment>
)
