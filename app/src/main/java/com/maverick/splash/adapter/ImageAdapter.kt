package com.maverick.splash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maverick.splash.R
import com.maverick.splash.model.ImageModel

class ImageAdapter(
    private val context: Context, var imageList: List<ImageModel>,
    private val imageClickListener: ImageClickListener
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    interface ImageClickListener {
        fun onImageClickListener(position: Int)
        fun onUserNameClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(imageList[position].urls.thumb).into(holder.imageView)
        holder.username.text = imageList[position].user.username
        holder.description.text = imageList[position].alt_description
        holder.bind(position)
    }

    override fun getItemCount() = imageList.size

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val username: TextView = itemView.findViewById(R.id.username)
        val description: TextView = itemView.findViewById(R.id.description)
        fun bind(position: Int) {
            imageView.setOnClickListener {
                imageClickListener.onImageClickListener(position)
            }
            username.setOnClickListener {
                imageClickListener.onUserNameClickListener(position)
            }
        }
    }
}