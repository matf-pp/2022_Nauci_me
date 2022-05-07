package com.example.naucime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.model.Lesson
import com.example.naucime.model.Professor
import com.google.firebase.auth.FirebaseAuth

class AddLessonActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lesson)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        var pEmail: String? = ""
        if(user != null) {
            pEmail = user.email
        } else {
            Toast.makeText(this,"Current user not found", Toast.LENGTH_LONG).show()
        }

        val btnAddNewLesson: Button = findViewById(R.id.btAddNewLesson)
        btnAddNewLesson.setOnClickListener{

            var les: EditText = findViewById(R.id.etLessonName)
            var lname:String = les.text.trim().toString()

            var lesPrice: EditText = findViewById(R.id.etLessonPrice)
            var lprice:String = lesPrice.text.trim().toString()

            val professor:Professor = DatabaseServiceProvider.db.getProfessor(pEmail.toString())
            DatabaseServiceProvider.db.addLesson(Lesson(lname, lprice.toInt(), professor))

            val intent = Intent(this, ProfesorDashboardActivity::class.java)
            startActivity(intent)
        }
    }
}