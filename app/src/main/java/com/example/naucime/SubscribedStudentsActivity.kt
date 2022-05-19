package com.example.naucime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.model.Student
import java.lang.StringBuilder

class SubscribedStudentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribed_students)

        val pEmail: String = intent.getStringExtra("professorEmail").toString()
        val lName: String = intent.getStringExtra("lessonName").toString()

        val tvSubscribedStudents: TextView = findViewById(R.id.tvSubscribedStudents)

        val subscribedStudents: MutableList<Student> = DatabaseServiceProvider.db.getSubscribedStudents(pEmail, lName)

        var builder: StringBuilder = StringBuilder()
        for (student in subscribedStudents) {
            builder.append(student).append("\n")
        }

        if(builder.isEmpty()){
            tvSubscribedStudents.text = "Nema prijavljenih studenata"
        } else {
            tvSubscribedStudents.text = builder
        }

        val imgBtnLogout: ImageButton = findViewById(R.id.imgBtnLogout)
        imgBtnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}