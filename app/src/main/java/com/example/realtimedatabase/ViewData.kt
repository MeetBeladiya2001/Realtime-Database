package com.example.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.realtimedatabase.databinding.ActivityViewDataBinding
import com.example.realtimedatabase.databinding.AdapterViewBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ViewData : AppCompatActivity() {
    private lateinit var binding: ActivityViewDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: AdapterView
    private var sounds = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterView(this)
        binding.recView.adapter = adapter
        binding.recView.layoutManager = LinearLayoutManager(this)

        database = Firebase.database.reference
        val userReference = database.child("users")

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (data in snapshot.children){
                        val username = data.child("username").getValue(String::class.java) ?: ""
                        val fullname = data.child("fullname").getValue(String::class.java) ?: ""
                        val email = data.child("email").getValue(String::class.java) ?: ""
                        val mobile = data.child("mobile").getValue(Long::class.java) ?: 0
                        val password = data.child("password").getValue(String::class.java) ?: ""
                        val item = UserData(fullname,username,email,mobile,password)
                        item.let {
                            sounds.addAll(listOf(it))
                            adapter.setData(sounds)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Data1", "Error: $error")
            }
        })
    }
}