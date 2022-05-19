package com.example.naucime

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.model.Lesson
import com.google.firebase.auth.FirebaseAuth

class ProfessorRecyclerAdapter: RecyclerView.Adapter<ProfessorRecyclerAdapter.ViewHolder>() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user = auth.currentUser
    private val professor = DatabaseServiceProvider.db.getProfessor(user?.email.toString())
    private val lessons = DatabaseServiceProvider.db.getLessonsByProfessor(professor)
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfessorRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.professor_card_layout,parent,false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProfessorRecyclerAdapter.ViewHolder, position: Int) {
        holder.tvLessonName.text = lessons[position].name
        holder.tvLessonPrice.text = lessons[position].price.toString()
        holder.tvProfessorContact.text = user?.email

        val lesson = Lesson(holder.tvLessonName.text.toString(),holder.tvLessonPrice.text.toString().toInt(),professor)
        val professor = DatabaseServiceProvider.db.getProfessor(holder.tvProfessorContact.text.toString())

        holder.btDelete.setOnClickListener {
            DatabaseServiceProvider.db.removeLesson(lesson)

            val intent = Intent(context,ProfesorDashboardActivity::class.java)
            context.startActivity(intent)
        }

        holder.tvLessonName.setOnClickListener {
            val intent = Intent(context, SubscribedStudentsActivity::class.java)
            intent.putExtra("professorEmail", professor.email)
            intent.putExtra("lessonName", lesson.name)
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
        var btDelete: ImageButton

        init {
            tvLessonName = itemView.findViewById(R.id.tvLessonName)
            tvLessonPrice = itemView.findViewById(R.id.tvLessonPrice)
            tvProfessorContact = itemView.findViewById(R.id.tvProfessorContact)
            btDelete = itemView.findViewById(R.id.btDelete)
        }

    }
}


