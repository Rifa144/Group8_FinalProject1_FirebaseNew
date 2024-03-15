package com.example.project1_group8_part1

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

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



        val txtName: TextView = findViewById(R.id.txtName)
        val txtTitle: TextView = findViewById(R.id.txtTitle)
        val txtDescription: TextView = findViewById(R.id.txtDescription)
        val txtEmail: TextView = findViewById(R.id.txtEmail)
        val txtPhone: TextView = findViewById(R.id.txtPhone)
        val txtJob: TextView = findViewById(R.id.txtJob)
        val txtEducation: TextView = findViewById(R.id.txtEducation)
        val detailsImageView: ImageView = findViewById(R.id.detailsImageView)


        txtName.text = name
        txtTitle.text = title
        txtDescription.text = description
        txtEmail.text = email
        txtPhone.text = phone
        txtJob.text = currentJob
        txtEducation.text = highestEducation
        Glide.with(this).load(photo).into(detailsImageView)


    }
}
