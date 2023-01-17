package com.maverick.splash

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maverick.splash.adapter.ImageAdapter
import com.maverick.splash.api.ApiInterface
import com.maverick.splash.api.RetrofitHelper
import com.maverick.splash.model.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserCollection : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var imageList: List<ImageModel>
    private lateinit var adapter: ImageAdapter
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_collection)
        dialog = ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.setCancelable(false)
        dialog.show()
        imageList = ArrayList()
        recyclerView = findViewById(R.id.recycler_view_1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = ImageAdapter(this, imageList, object : ImageAdapter.ImageClickListener {
            override fun onImageClickListener(position: Int) {
                val intent = Intent(this@UserCollection, FullImage::class.java)
                intent.putExtra("regular", imageList[position].urls.regular)
                startActivity(intent)
            }

            override fun onUserNameClickListener(position: Int) {
            }

        })
        recyclerView.adapter = adapter

        getData()
    }

    private fun getData() {
        val apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            val result = apiInterface.getUser(intent.getStringExtra("username").toString())
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