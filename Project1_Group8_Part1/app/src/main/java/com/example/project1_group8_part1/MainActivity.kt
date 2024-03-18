package com.example.project1_group8_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1_group8_part1.models.Post
import com.example.project1_group8_part1.models.PostAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private var logoutButton: ImageView? = null
    private var btnGoHome: ImageView? =null
    private  var adapter : PostAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoutButton = findViewById(R.id.btnLogout)
        btnGoHome = findViewById(R.id.btnGoHome)
        logoutButton?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        btnGoHome?.setOnClickListener {
            startActivity(Intent(this,CandidateActivity::class.java))
        }

        val query = FirebaseDatabase.getInstance().reference.child("post");
        val option = FirebaseRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java).build()
        adapter = PostAdapter(option)


        val rView : RecyclerView = findViewById(R.id.pView)
        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}