package com.sky.newsapp.Apiservice.ModelClass

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)