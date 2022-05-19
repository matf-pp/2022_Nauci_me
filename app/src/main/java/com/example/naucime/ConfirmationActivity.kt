package com.example.naucime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        var tvConfiramtion: TextView = findViewById(R.id.tvConfiramtion)
        var uspeh:Boolean = intent.getBooleanExtra("uspeh", false)

        if(uspeh) {
            tvConfiramtion.text = "Uspešno ste se prijavili na čas"
        } else {
            tvConfiramtion.text = "Ne možete da se prijavite na svoj čas"
        }

        val imgBtnLogout: ImageButton = findViewById(R.id.imgBtnLogout)
        imgBtnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}