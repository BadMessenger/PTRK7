package com.example.ptrk12.repository

import androidx.lifecycle.LiveData
import com.example.ptrk12.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
}