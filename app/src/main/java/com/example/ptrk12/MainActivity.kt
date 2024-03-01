package com.example.ptrk12


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ActionMenuView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.graphics.component2
import com.example.ptrk12.adapter.OnInteractionListener
import com.example.ptrk12.adapter.PostsAdapter
import com.example.ptrk12.databinding.ActivityMainBinding
import com.example.ptrk12.databinding.CardPostBinding
import com.example.ptrk12.dto.Post
import com.example.ptrk12.util.AndroidUtils
import com.example.ptrk12.vievmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.textView7) {
                requestFocus()
                setText(post.content)
            }
        }

        binding.save.setOnClickListener {
            with(binding.textView7) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
    
    // 2 Вариант
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var w = 1499999
        var likedByMe = false
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { posts ->
            binding.container.removeAllViews()
            posts.map { post ->
                CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
                    textView5.text = post.author
                    textView6.text = post.published
                    textView7.text = post.content
                    imageButton19.setImageResource(
                        if (post.likedByMe) {
                            likedByMe = true
                            R.drawable.like0n
                        } else {
                            likedByMe = false
                            R.drawable.like0ff
                        }
                    )
                    imageButton19.setOnClickListener {
                        if (likedByMe) {
                            w--
                        } else {
                            w++
                        }
                        likedByMe = !likedByMe
                        imageButton19.setImageResource(
                            if (likedByMe) {
                                R.drawable.like0n
                            } else {
                                R.drawable.like0ff
                            }
                        )
                        val e = w.toString()
                        val f = e.substring(0,4)
                        val t1 = "${f[0]}.${f[1]}"
                        val t2 = "${f[0]}${f[1]}"
                        val t3 = "${f[0]}${f[1]}${f[2]}"
                        val t4 = "${f[0]}.${f[1]}"

                        if (w<1000){
                            textView8.text = w.toString()
                        }
                        if(w>999){
                            textView8.text = "${t1}k"
                        }
                        if (w>9999){
                            textView8.text = "${t2}k"
                        }
                        if (w>99999){
                            textView8.text = "${t3}k"
                        }
                        if (w>999999){
                            textView8.text = "${t4}м"
                        }

                    }
                }.root
            }*/

    /* //Самый старый вариант!!!
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                textView5.text = post.author
                textView6.text = post.published
                textView7.text = post.content
                imageButton19.setImageResource(

                    if (post.likedByMe){
                        likedByMe = true
                        R.drawable.like0n
                    } else {
                        likedByMe = false
                        R.drawable.like0ff
                    }
                )
                binding.imageButton19.setOnClickListener {
                    if (likedByMe) {
                        w--
                    } else {
                        w++
                    }
                    likedByMe = !likedByMe
                    imageButton19.setImageResource(
                        if (likedByMe) {
                            R.drawable.like0n
                        } else {
                            R.drawable.like0ff
                        }
                    )
                    textView8.text = w.toString()
                }
                var q = 0
                binding.imageButton2.setOnClickListener {
                    q++
                    textView9.text = q.toString()
                }
            }

        }

        binding.imageButton19.setOnClickListener {
            viewModel.like()
        }*/


}









