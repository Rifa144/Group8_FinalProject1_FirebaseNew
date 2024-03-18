package com.example.project1_group8_part1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val name = intent.getStringExtra("name")
        val currentJob = intent.getStringExtra("current_job")
        val description = intent.getStringExtra("description")
        val highestEducation = intent.getStringExtra("highestEducation")
        val email = intent.getStringExtra("email")
        val photo = intent.getStringExtra("photo")
        val phone = intent.getStringExtra("phone")
        val title = intent.getStringExtra("title")
        val candidateId = intent.getStringExtra("candidateId")


        val txtName: TextView = findViewById(R.id.txtName)
        val txtTitle: TextView = findViewById(R.id.txtTitle)
        val txtDescription: TextView = findViewById(R.id.txtDescription)
        val txtEmail: TextView = findViewById(R.id.txtEmail)
        val txtPhone: TextView = findViewById(R.id.txtPhone)
        val txtJob: TextView = findViewById(R.id.txtJob)
        val txtEducation: TextView = findViewById(R.id.txtEducation)
        val detailsImageView: ImageView = findViewById(R.id.detailsImageView)
        val btnConnect: Button = findViewById(R.id.btnConnect)



        txtName.text = name
        txtTitle.text = title
        txtDescription.text = description
        txtEmail.text = email
        txtPhone.text = phone
        txtJob.text = currentJob
        txtEducation.text = highestEducation
        Glide.with(this).load(photo).into(detailsImageView)

        checkConnectionStatus(btnConnect)

        btnConnect.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null && candidateId != null) {
                val connections = FirebaseDatabase.getInstance().reference.child("connections")

                connections.orderByChild("connected_candidate_id").equalTo(userId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var isConnected = false
                            var connectionKey: String? = null

                            for (snapshot in dataSnapshot.children) {
                                val connectedCandidateId =
                                    snapshot.child("connected_candidate_id").getValue(String::class.java)
                                val connectedCandidate = snapshot.child("candidate_id").getValue(String::class.java)

                                if (connectedCandidateId == userId && connectedCandidate == candidateId) {
                                    isConnected = true
                                    connectionKey = snapshot.key
                                    break
                                }
                            }
                            if (isConnected) {
                                connections.child(connectionKey!!).removeValue()
                                btnConnect.text = "CONNECT"
                                Toast.makeText(applicationContext, "Connection removed", Toast.LENGTH_SHORT).show()
                            } else {
                                val newConnection = connections.push()
                                newConnection.child("candidate_id").setValue(candidateId)
                                newConnection.child("connected_candidate_id").setValue(userId)
                                btnConnect.text = "CONNECTED"
                                Toast.makeText( applicationContext,"Connected!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(applicationContext, "Database errors", Toast.LENGTH_SHORT).show()
                        }
                    })

            } else {
                Toast.makeText(this, "Unable to connect", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkConnectionStatus(btnConnect: Button) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val candidateId = intent.getStringExtra("candidateId")
        if (userId != null && candidateId != null) {
            val connections = FirebaseDatabase.getInstance().reference.child("connections")

            connections.orderByChild("connected_candidate_id").equalTo(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var isConnected = false

                        for (snapshot in dataSnapshot.children) {
                            val connectedCandidateId = snapshot.child("connected_candidate_id").getValue(String::class.java)
                            val connectedCandidate = snapshot.child("candidate_id").getValue(String::class.java)
                            if (connectedCandidateId == userId && connectedCandidate == candidateId) {
                                isConnected = true
                                break
                            }
                        }
                        if (isConnected) {
                            btnConnect.text = "CONNECTED"
                        } else {
                            btnConnect.text = "CONNECT"
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(applicationContext, "Database errors", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}