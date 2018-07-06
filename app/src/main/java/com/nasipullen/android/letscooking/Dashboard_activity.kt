package com.nasipullen.android.letscooking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Dashboard_activity : AppCompatActivity() {

    lateinit var tvEmail : TextView
    lateinit var tvUsername : TextView
    lateinit var tvVerify : TextView
    lateinit var logoutBtn : Button
    lateinit var mAuth : FirebaseAuth

    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_activity)

        tvEmail = findViewById(R.id.tv_email)
        tvUsername = findViewById(R.id.tv_username)
        tvVerify = findViewById(R.id.tv_email_verifiied)
        logoutBtn = findViewById(R.id.logoutBtn)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        logoutBtn.setOnClickListener { userLogout() }

    }

    private fun userLogout() {
        mAuth.signOut()
        Toast.makeText(this, "Successfully Logout", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@Dashboard_activity, Home::class.java ))
    }

    override fun onStart(){
        super.onStart()

        val mUser = mAuth.currentUser
        val mUserReference = mDatabase.child(mUser!!.uid)

        tvEmail.text = mUser.email
        tvVerify.text = mUser.isEmailVerified.toString()
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvUsername.text = snapshot.child("name").value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

}
