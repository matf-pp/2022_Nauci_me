package com.example.naucime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naucime.db.DatabaseServiceProvider
import com.google.firebase.auth.FirebaseAuth

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user = auth.currentUser
    private val professor = DatabaseServiceProvider.db.getProfessor(user?.email.toString())
    private val lessons = DatabaseServiceProvider.db.getLessonsByProfessor(professor)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.tvLessonName.text = lessons[position].name
        holder.tvLessonPrice.text = lessons[position].price.toString()
        holder.tvProfessorContact.text = user?.email
    }

    override fun getItemCount(): Int {
        return lessons.size

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvLessonName: TextView
        var tvLessonPrice: TextView
        var tvProfessorContact: TextView
        var btDelete: Button

        init {
            tvLessonName = itemView.findViewById(R.id.tvLessonName)
            tvLessonPrice = itemView.findViewById(R.id.tvLessonPrice)
            tvProfessorContact = itemView.findViewById(R.id.tvProfessorContact)
            btDelete = itemView.findViewById(R.id.btDelete)
        }

    }
}


