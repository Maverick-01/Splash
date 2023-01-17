package com.maverick.splash

import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FullImage : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        dialog = ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.setCancelable(false)
        dialog.show()
        val fullImage = findViewById<ImageView>(R.id.full_image)
        val description = findViewById<TextView>(R.id.tv_description)
        description.text = intent.getStringExtra("description")
        Glide.with(this).load(intent.getStringExtra("regular"))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(
                        this@FullImage,
                        "Image couldn't load. try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    dialog.dismiss()
                    return false
                }

            }).into(fullImage)

    }
}