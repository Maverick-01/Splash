package com.maverick.splash

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maverick.splash.adapter.ImageAdapter
import com.maverick.splash.api.ApiInterface
import com.maverick.splash.api.RetrofitHelper
import com.maverick.splash.model.ImageModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var imageList: List<ImageModel>
    private lateinit var adapter: ImageAdapter
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog = ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.setCancelable(false)
        dialog.show()
        imageList = ArrayList()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.setHasFixedSize(true)
        adapter =
            ImageAdapter(this@MainActivity, imageList, object : ImageAdapter.ImageClickListener {
                override fun onImageClickListener(position: Int) {
                    val intent = Intent(this@MainActivity, FullImage::class.java)
                    intent.putExtra("regular", imageList[position].urls.regular)
                    intent.putExtra("description", imageList[position].alt_description)
                    startActivity(intent)
                }

                override fun onUserNameClickListener(position: Int) {
                    val intent = Intent(this@MainActivity, UserCollection::class.java)
                    intent.putExtra("username", imageList[position].user.username)
                    startActivity(intent)
                }

            })
        recyclerView.adapter = adapter

        getData()
    }

    private fun getData() {
        val apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            val result = apiInterface.getImages()
            if (result.body() != null) {
                imageList = result.body()!!
                Log.d("url", result.body().toString())
                Log.d("list", imageList.toString())
                adapter.imageList = imageList //updating adapter list with new data
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    }
}