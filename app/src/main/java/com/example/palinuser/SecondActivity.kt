package com.example.palinuser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var tvName: TextView
    private lateinit var tvSelectedUser: TextView
    private lateinit var btnChooseUser: Button

    companion object {
        var selectedUser: String = ""
    }

    override fun onResume() {
        super.onResume()
        tvSelectedUser.text = selectedUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvWelcome = findViewById(R.id.Welcome)
        tvName = findViewById(R.id.tvName)
        tvSelectedUser = findViewById(R.id.tvSelectedUser)
        btnChooseUser = findViewById(R.id.btnChooseUser)

        val name = intent.getStringExtra("name")
        tvName.text = name

        btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}