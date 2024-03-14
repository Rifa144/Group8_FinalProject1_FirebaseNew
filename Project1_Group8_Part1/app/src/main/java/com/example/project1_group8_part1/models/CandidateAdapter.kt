package com.example.project1_group8_part1.models

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1_group8_part1.DetailsActivity
import com.example.project1_group8_part1.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class CandidateAdapter(options: FirebaseRecyclerOptions<Candidate>) : FirebaseRecyclerAdapter<Candidate, CandidateAdapter.MyViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int,
        model: Candidate
    ) {
        Glide.with(holder.image.context).load(model.photo).into(holder.image)
        val p: Candidate = model
        holder.txtName.text = p.name
        holder.txtCurrentJob.text = p.current_job
        holder.listItem.setOnClickListener {
            var intent  = Intent(holder.listItem.context,DetailsActivity::class.java)
            intent.putExtra("name",model.name)
            holder.listItem.context.startActivity(intent)
        }

    }
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.candidate_row, parent, false))
    {
        val txtName: TextView = itemView.findViewById(R.id.candidateName)
        val txtCurrentJob: TextView = itemView.findViewById(R.id.current_job)
        val image: ImageView = itemView.findViewById(R.id.candidateImage)
        val listItem : CardView = itemView.findViewById(R.id.listItem)
    }

}