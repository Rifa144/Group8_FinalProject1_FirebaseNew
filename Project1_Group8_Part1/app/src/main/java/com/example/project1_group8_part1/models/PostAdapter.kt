package com.example.project1_group8_part1.models

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1_group8_part1.CandidateActivity
import com.example.project1_group8_part1.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class PostAdapter(option: FirebaseRecyclerOptions<Post>) :
    FirebaseRecyclerAdapter<Post, PostAdapter.MyViewHolder>(option) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PostAdapter.MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Post) {
        val storageRef: StorageReference =
            FirebaseStorage.getInstance().getReferenceFromUrl((model.postImage))
        Glide.with(holder.postImage.context).load(storageRef).into(holder.postImage)

        holder.candidateId.text = model.candidateId.toString()
        holder.connectionName.text = model.connectionName
        holder.postContent.text = model.postContent
        holder.postDate.text = model.postDate


        holder.knowMore.setOnClickListener {
            val i = Intent(holder.knowMore.context, CandidateActivity::class.java)
            it.context.startActivity(i);

        }

    }




        class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.post_row, parent, false)) {
//

            var candidateId: TextView = itemView.findViewById(R.id.candidateId)
            var connectionName: TextView = itemView.findViewById(R.id.connectionName)
            var postContent: TextView = itemView.findViewById(R.id.postContent)
            var postDate: TextView = itemView.findViewById(R.id.postDate)
            var postImage: ImageView = itemView.findViewById(R.id.postImage)
            var knowMore: Button = itemView.findViewById(R.id.knowMore)
        }
    }

