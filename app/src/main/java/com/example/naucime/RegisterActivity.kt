//"Designed by Vectorium / Freepik"
package com.example.naucime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.model.Professor
import com.example.naucime.model.Student
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val btnRegister:Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {

            val editName:EditText = findViewById(R.id.editName)
            val editSurname:EditText = findViewById(R.id.editSurname)
            val editEmail:EditText = findViewById(R.id.editEmail)
            val editPassword:EditText = findViewById(R.id.editPassword)
            val editCPassword:EditText = findViewById(R.id.editCPassword)

            if(editName.text.trim().isNotEmpty() && editSurname.text.trim().isNotEmpty() && editEmail.text.trim().isNotEmpty()
                && editPassword.text.trim().isNotEmpty() && editCPassword.text.trim().isNotEmpty()) {
//              register to firebase
                registerUser()
            } else {
                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()
            }
        }

        val tvLogin:TextView = findViewById(R.id.tvLogin)
        tvLogin.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val imgBtnLogout: ImageButton = findViewById(R.id.imgBtnLogout)
        imgBtnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun registerUser() {

        val editName:EditText = findViewById(R.id.editName)
        val editSurname:EditText = findViewById(R.id.editSurname)
        val editEmail:EditText = findViewById(R.id.editEmail)
        val editPassword:EditText = findViewById(R.id.editPassword)

        auth.createUserWithEmailAndPassword(editEmail.text.trim().toString(), editPassword.text.trim().toString())
            .addOnCompleteListener(this) {

            task -> if(task.isSuccessful) {

                val userType: String = intent.getStringExtra("userType").toString()

                if(userType == "student") {
//              add new student user to memory
                    val student = Student(editName.text.trim().toString(), editSurname.text.trim().toString(), editEmail.text.trim().toString())
                    DatabaseServiceProvider.db.addStudent(student)
                } else {
//              add new professor user to memory
                    var professor = Professor(editName.text.trim().toString(), editSurname.text.trim().toString(), editEmail.text.trim().toString())
                    DatabaseServiceProvider.db.addProfessor(professor)
                }

                Toast.makeText(this,"Register successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("userType", userType)
                startActivity(intent)

            } else {
                Toast.makeText(this,"Register failed " + task.exception , Toast.LENGTH_LONG).show()
            }
        }
    }
}