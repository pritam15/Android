package com.example.retrofitproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitproject.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    // Global Variables
  lateinit var  binding : ActivityMainBinding
    val baseUrl : String = "https://jsonplaceholder.typicode.com/"
     var PostsList = ArrayList<Posts>()
    lateinit var adapter : RecylerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ShowData()
    }

    fun ShowData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI : RetrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val call : Call<List<Posts>> = retrofitAPI.getAllPosts()

        call.enqueue(object : Callback<List<Posts>>{
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
              PostsList = response.body() as ArrayList<Posts>
                adapter = RecylerViewAdapter(PostsList)
                binding.recylerView.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.recylerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.localizedMessage,Toast.LENGTH_LONG).show()
            }
        })


    }
}