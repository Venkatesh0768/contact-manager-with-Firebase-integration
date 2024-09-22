package com.example.contactmanager

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.contactmanager.contactdetails
import com.example.contactmanager.databinding.ActivityHomeBinding
import com.example.contactmanager.userdata
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Home_activity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var databaseReference: DatabaseReference

    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("contacts")


        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)

        val btnonok = dialog.findViewById<Button>(R.id.btnok)
        val btnoncancel = dialog.findViewById<Button>(R.id.btncancel)

        btnonok.setOnClickListener {
            dialog.dismiss()
        }

        btnoncancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnsubmit.setOnClickListener {


            val name = binding.Etname.text.toString()
            val email = binding.Etemail.text.toString()
            val number = binding.Etnumber.text.toString()

            val details = userdata(name, email, number)
            databaseReference.child(name).setValue(details).addOnSuccessListener {
                binding.Etname.text?.clear()
                binding.Etemail.text?.clear()
                binding.Etnumber.text?.clear()
                dialog.show()
                Toast.makeText(this, "Details added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to add details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnViewData.setOnClickListener {
            // Fetch data from Firebase
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val stringBuilder = StringBuilder()

                        // Loop through all contacts and display them
                        for (contactSnapshot in dataSnapshot.children) {
                            val contact = contactSnapshot.getValue(userdata::class.java)
                            if (contact != null) {
                                stringBuilder.append("Name: ${contact.name}\n")
                                stringBuilder.append("Email: ${contact.email}\n")
                                stringBuilder.append("Number: ${contact.number}\n\n")
                            }
                        }

                        // Display the contacts in a dialog or a Toast (for quick display)
                        val alertDialog = AlertDialog.Builder(this@Home_activity)
                            .setTitle("Contact Details")
                            .setMessage(stringBuilder.toString())
                            .setPositiveButton("OK", null)
                            .create()
                        alertDialog.show()

                    } else {
                        Toast.makeText(this@Home_activity, "No contact data found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@Home_activity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
