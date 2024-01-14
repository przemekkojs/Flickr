package com.example.flicker.model

// Response from Flickr
data class Response(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<Photo>
)