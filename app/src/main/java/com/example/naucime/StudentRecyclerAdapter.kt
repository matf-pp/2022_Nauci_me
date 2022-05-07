package com.example.naucime

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.model.Lesson
import com.example.naucime.model.Student
import com.google.firebase.auth.FirebaseAuth

class StudentRecyclerAdapter: RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user = auth.currentUser
    private val lessons = DatabaseServiceProvider.db.getLessons()
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.student_card_layout,parent,false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: StudentRecyclerAdapter.ViewHolder, position: Int) {
        holder.tvLessonName.text = lessons[position].name
        holder.tvLessonPrice.text = lessons[position].price.toString()
        holder.tvProfessorContact.text = lessons[position].professor.email
        holder.btSubscribe.setOnClickListener {
            val professor = DatabaseServiceProvider.db.getProfessor(holder.tvProfessorContact.text.toString())
            val lesson = Lesson(holder.tvLessonName.text.toString(),holder.tvLessonPrice.text.toString().toInt(),professor)

//            todo: implament getstudent by email, create student, subscribe student to lesson

            val intent = Intent(context,StudentDashboardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return lessons.size

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvLessonName: TextView
        var tvLessonPrice: TextView
        var tvProfessorContact: TextView
        var btSubscribe: Button

        init {
            tvLessonName = itemView.findViewById(R.id.tvLessonName)
            tvLessonPrice = itemView.findViewById(R.id.tvLessonPrice)
            tvProfessorContact = itemView.findViewById(R.id.tvProfessorContact)
            btSubscribe = itemView.findViewById(R.id.btSubscribe)
        }

    }
}


