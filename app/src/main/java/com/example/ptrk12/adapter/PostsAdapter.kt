package com.example.ptrk12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ptrk12.R
import com.example.ptrk12.databinding.CardPostBinding
import com.example.ptrk12.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onCancel(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        var w = 23
        var likedByMe = false
        binding.apply {
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
            /*imageButton19.setOnClickListener {
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
                val e = w.toString()
                val f = e.substring(0, 4)
                val t1 = "${f[0]}.${f[1]}"
                val t2 = "${f[0]}${f[1]}"
                val t3 = "${f[0]}${f[1]}${f[2]}"
                val t4 = "${f[0]}.${f[1]}"

                if (w < 1000) {
                    textView8.text = w.toString()
                }
                if (w > 999) {
                    textView8.text = "${t1}k"
                }
                if (w > 9999) {
                    textView8.text = "${t2}k"
                }
                if (w > 99999) {
                    textView8.text = "${t3}k"
                }
                if (w > 999999) {
                    textView8.text = "${t4}м"
                }
            }*/

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            /*R.id.cancel_save -> {
                                onInteractionListener.onCancel(post)
                                true
                            }*/
                            else -> false
                        }
                    }
                }.show()
            }

            imageButton19.setOnClickListener {
                onInteractionListener.onLike(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}