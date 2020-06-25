package com.ahmedali.a7danatproject.haddana.ShowDetails

import android.R.id.toggle
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.ahmedali.a7danatproject.R
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show_details.*


class ShowDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        // Write a message to the database

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("adminposts")

        val prefs = getSharedPreferences("login", Context.MODE_PRIVATE)
        val name = prefs.getString("id", "No name defined") //"No name defined" is the default value.



        val myRef1= myRef.child(name.toString()).child(intent.getStringExtra("post_id"))

        // Read from the database
        myRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.e("datalai",dataSnapshot.toString())
                val model:admin_post_mode = dataSnapshot.getValue(admin_post_mode::class.java)!!

                textView6.setText(model!!.details.toString())
                Log.e("model", model!!.details.toString())
                Glide.with(applicationContext).load(model?.img1).into(imageView6)
                if(model.enable.equals("true")) {
                    toggleButton.isChecked = false
                }
                else
                {
                    toggleButton.isChecked = true

                }

                toggleButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    if (!isChecked) {
                        val myRef1= myRef.child(name.toString()).child(intent.getStringExtra("post_id")).child("enable")
                        myRef1.setValue("true")

                        // The toggle is enabled
                    } else {
                        val myRef1= myRef.child(name.toString()).child(intent.getStringExtra("post_id")).child("enable")

                        myRef1.setValue("false")
                        // The toggle is disabled
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}