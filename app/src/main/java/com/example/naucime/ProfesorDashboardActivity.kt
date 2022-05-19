package com.example.naucime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfesorDashboardActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ProfessorRecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor_dashboard)

        layoutManager = LinearLayoutManager(this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        adapter = ProfessorRecyclerAdapter()
        recyclerView.adapter = adapter

        val btNewLesson: Button = findViewById(R.id.btNewLesson)
        btNewLesson.setOnClickListener {

            val intent = Intent(this, AddLessonActivity::class.java)
            startActivity(intent)
        }

        val imgBtnLogout: ImageButton = findViewById(R.id.imgBtnLogout)
        imgBtnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}