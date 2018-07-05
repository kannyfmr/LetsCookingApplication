package com.nasipullen.android.letscooking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPass : AppCompatActivity() {
    lateinit var resEmail : EditText
    lateinit var resetBtn : Button

    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        resEmail = findViewById(R.id.resetPass)
        resetBtn = findViewById(R.id.resetBtn)
        mAuth = FirebaseAuth.getInstance()

        resetBtn.setOnClickListener { resetPassword() }
    }

    private fun resetPassword() {
        val email = resEmail.text.toString()

        if (!TextUtils.isEmpty(email)){
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "email successfully sent", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(this, "no user found with this email", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Masukkan Email Dengan Benar", Toast.LENGTH_LONG).show()
        }
    }
}
