package com.example.contactmanager


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivitySigninBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity_signin : AppCompatActivity() {

    companion object{
        const val KEY1 = "com.example.contactmanager.activity_signin.name"
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnsignin.setOnClickListener {
            val username = binding.EtUsername.text.toString()
            if (username.isNotEmpty()) {
                readData(username)
            } else {
                Toast.makeText(this, "Please Enter the Username", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvsignin.setOnClickListener{
            val intent = Intent(this , Activity_Signup::class.java)
            startActivity(intent)
        }
    }

    private fun readData(username: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.child(username).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value
                val intent = Intent(this, Home_activity::class.java)
                intent.putExtra(KEY1 , name.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
        }
    }
}
