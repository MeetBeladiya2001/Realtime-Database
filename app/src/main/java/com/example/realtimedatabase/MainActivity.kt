package com.example.realtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.realtimedatabase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
        val userReference = database.child("users")

        binding.savedataBTN.setOnClickListener {

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val totalUsers = snapshot.childrenCount.toInt()

                val userId = (totalUsers + 1).toString()
                val fullName = binding.nameTXT.text.toString()
                val username = binding.usernameTXT.text.toString()
                val email = binding.emailTXT.text.toString()
                val mobile2 = binding.numberTXT.text.toString()
                val mobile = mobile2.toLong()
                val password = binding.passTXT.text.toString()

                if (fullName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && mobile2.isNotEmpty()) {

                    var isUsernameExist = false

                    for (sound in snapshot.children) {
                        val username2 = sound.child("username").getValue(String::class.java) ?: ""

                        if (username == username2) {
                            Toast.makeText(this@MainActivity, "Retry With Different Username", Toast.LENGTH_SHORT).show()
                            isUsernameExist = true
                            break
                        }
                    }

                    if (!isUsernameExist) {
                        val user = UserData(fullName, username, email, mobile, password)

                        database.child("users").child(userId).setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Successfully inserted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                            }
                    }

                } else {
                    Toast.makeText(this@MainActivity, "Please Fill All Details", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FirebaseError", "Error getting total number of users", error.toException())
            }

        })


        }

        binding.viewdataBTN.setOnClickListener {
            startActivity(Intent(this,ViewData::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}