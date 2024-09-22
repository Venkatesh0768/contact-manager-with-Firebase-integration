package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivitySignupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Activity_Signup : AppCompatActivity() {

    lateinit var database : DatabaseReference
    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnsignup.setOnClickListener {
            val name = binding.EtName.text.toString()
            val email = binding.EtEmail.text.toString()
            val password = binding.EtPassword.text.toString()
            val username = binding.EtUsername.text.toString()

            val user = User(name , email, password, username)
            database = FirebaseDatabase.getInstance().getReference("users")
            database.child(username).setValue(user).addOnSuccessListener {
                Toast.makeText(this , "you data successfully stored" , Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this , "you data storing is failed " , Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvsignin.setOnClickListener{
            val intent = Intent(this , activity_signin::class.java)
            startActivity(intent)
        }



    }
}