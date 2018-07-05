package com.nasipullen.android.letscooking

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    lateinit var mLoginBtn : Button
    lateinit var mCreateUser : TextView
    lateinit var mForgetPass : TextView
    lateinit var mLoginEmail : EditText
    lateinit var mLoginPass : EditText
    lateinit var mProgressbar : ProgressDialog

    //FIREBASE AUTH
    lateinit var mAuth : FirebaseAuth
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mCreateUser = findViewById(R.id.regTxt)
        mLoginBtn = findViewById(R.id.loginBtn)
        mForgetPass = findViewById(R.id.forgotPass)
        mLoginEmail = findViewById(R.id.user_email)
        mLoginPass = findViewById(R.id.user_password)
        mProgressbar = ProgressDialog(this)
        //firebase
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        mCreateUser.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        mLoginBtn.setOnClickListener {
            var email = user_email.text.toString().trim()
            var password = user_password.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                mLoginEmail.error = "please enter your email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                mLoginPass.error = "please enter your password"
                return@setOnClickListener
            }
            loginUser(email, password)

        }
    }

    private fun loginUser(email: String, password: String) {
        mProgressbar.setMessage("Login In on Proccess")
        mProgressbar.show()
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                      if (task.isSuccessful){
                        mProgressbar.dismiss()
                          val startIntent = Intent(applicationContext, DashboardActivity::class.java)
                          startActivity(startIntent)
                          finish()
                      } else {
                          Toast.makeText(this, "Email dan Password Salah", Toast.LENGTH_LONG).show()
                      }
                    mProgressbar.dismiss()
                }

    }
}