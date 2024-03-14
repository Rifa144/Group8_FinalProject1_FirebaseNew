package com.example.project1_group8_part1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1_group8_part1.models.Candidate
import com.example.project1_group8_part1.models.CandidateAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CandidateActivity : AppCompatActivity() {
    private var logoutButton: ImageView? = null
    private var btnGoHome:ImageView? =null
    private var adapter: CandidateAdapter? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate)
        logoutButton = findViewById(R.id.btnLogout)
        btnGoHome = findViewById(R.id.btnGoHome)
        logoutButton?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        btnGoHome?.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        val query = FirebaseDatabase.getInstance().reference.child("Candidates")
        val options = FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate::class.java).build()
        adapter = CandidateAdapter(options)

        val rView : RecyclerView = findViewById(R.id.rView)
        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = adapter
    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}