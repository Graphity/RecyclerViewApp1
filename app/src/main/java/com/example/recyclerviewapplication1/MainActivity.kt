package com.example.recyclerviewapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapplication1.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var homeRVAdapter : HomeRecyclerViewAdapter

    private val db = FirebaseFirestore.getInstance()

    private val emailsCollection = db.collection("emails")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            getData { dataList ->
                homeRVAdapter = HomeRecyclerViewAdapter(dataList)
                homeRV.adapter = homeRVAdapter
                homeRV.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun getData(callback: (MutableList<Email>) -> Unit) {
        emailsCollection.get()
            .addOnSuccessListener { result ->
                val dataList = ArrayList<Email>()

                for (document in result) {
                    val author = document.getString("author").toString()
                    val subject = document.getString("subject").toString()
                    val content = document.getString("content").toString()
                    val image = document.getString("image").toString()

                    val email = Email(author, subject, content, image)
                    dataList.add(email)
                }
                callback(dataList)
            }
    }


}