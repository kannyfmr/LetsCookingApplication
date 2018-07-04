package com.nasipullen.android.letscooking

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    lateinit var mLoginBtn : Button
    lateinit var mCreateUser : TextView
    lateinit var mForgetPass : TextView
    lateinit var mLoginEmail : EditText
    lateinit var mLoginPass : EditText

    //FIREBASE AUTH
    lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mCreateUser = findViewById(R.id.regTxt)
        mLoginBtn = findViewById(R.id.loginBtn)
        mForgetPass = findViewById(R.id.forgotPass)
        mLoginEmail = findViewById(R.id.user_email)
        mLoginPass = findViewById(R.id.user_password)
        //firebase
        mAuth = FirebaseAuth.getInstance()

        mCreateUser.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}