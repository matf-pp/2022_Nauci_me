package com.example.naucime

import android.content.Intent
import android.database.DatabaseErrorHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.model.Professor
import com.example.naucime.model.Student
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        val btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener{

            val etUsername: EditText = findViewById(R.id.etUsername)
            val etPassword: EditText = findViewById(R.id.etPassword)

            if(etUsername.text.trim().isNotEmpty() && etPassword.text.trim().isNotEmpty()){
//                process data
                signInUser()

                val user = auth.currentUser
                var pEmail: String? = ""

                if(user != null) {
                    pEmail = user.email
                } else {
                    Toast.makeText(this,"Current user not found", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()

            }
        }

        val tvRegister: TextView = findViewById(R.id.tvRegister)
        tvRegister.setOnClickListener {

            val userType: String = intent.getStringExtra("userType").toString()

            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("userType", userType)
            startActivity(intent)
        }

        val imgBtnLogout: ImageButton = findViewById(R.id.imgBtnLogout)
        imgBtnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun signInUser() {

        val etUsername:EditText = findViewById(R.id.etUsername)
        val etPassword:EditText = findViewById(R.id.etPassword)
        val userType: String = intent.getStringExtra("userType").toString()

        auth.signInWithEmailAndPassword(etUsername.text.trim().toString(),etPassword.text.trim().toString())
            .addOnCompleteListener(this) {
                    task -> if (task.isSuccessful) {
                        var professor = DatabaseServiceProvider.db.getProfessor(etUsername.text.trim().toString())
                        var student = DatabaseServiceProvider.db.getStudent(etUsername.text.trim().toString())

                //postojim kao profesor a ulazim kao student
                        if(professor.email != "" && userType == "student") {
                            var student = Student(professor.name, professor.lastName, etUsername.text.trim().toString());
                            DatabaseServiceProvider.db.addStudent(student)
                        }
                        else if(student.email != "" && userType == "profesor"){
                            var professorNew = Professor(student.name, student.lastName, etUsername.text.trim().toString())
                            DatabaseServiceProvider.db.addProfessor(professorNew)
                        }

                        val intent = Intent(this, if (userType == "student") StudentDashboardActivity::class.java else ProfesorDashboardActivity::class.java)
                        startActivity(intent)


            } else {
                Toast.makeText(this,"Authentication error " + task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }
}