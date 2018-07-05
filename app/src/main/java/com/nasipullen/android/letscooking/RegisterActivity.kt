package com.nasipullen.android.letscooking

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var mRegisterBtn : Button
    lateinit var mRegEmail : EditText
    lateinit var mRegPass : EditText
    lateinit var mUserName : EditText
    lateinit var mLoginBtn : TextView
    lateinit var mProgressBar: ProgressDialog
    //firebase
    lateinit var mAuth : FirebaseAuth
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //inisialasi id
        mRegisterBtn = findViewById(R.id.registerBtn)
        mRegEmail = findViewById(R.id.user_email)
        mRegPass = findViewById(R.id.user_password)
        mUserName = findViewById(R.id.user_name)
        mLoginBtn = findViewById(R.id.loginTxt)
        mProgressBar = ProgressDialog(this)

        //firebaseauth
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        mLoginBtn.setOnClickListener {
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }
        mRegisterBtn.setOnClickListener {

           // mProgressBar.setMessage("Please wait")
           // mProgressBar.show()

            val name = mUserName.text.toString().trim()
            val email = mRegEmail.text.toString().trim()
            val password = mRegPass.text.toString().trim()

            if (TextUtils.isEmpty(name)){
                mUserName.error = "Enter Your Name"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)){
                mRegEmail.error = "Enter Your Email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                mRegPass.error = "Enter Your Password"
                return@setOnClickListener
            }
            createUser(name, email, password)
        }
    }

    private fun createUser(name : String, email : String, password : String) {

        mProgressBar.setMessage("Registering User....")
        mProgressBar.show()
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful) {
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val uid = currentUser!!.uid
                        verifyEmail()

                        val userMap = HashMap<String, String>()
                        userMap["name"] = name

                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                        mDatabase.setValue(userMap).addOnCompleteListener(OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this@RegisterActivity, Dashboard_activity::class.java))
                                Toast.makeText(this, "Registering Successfully", Toast.LENGTH_LONG).show()
                                mProgressBar.dismiss()

                            }
                        })
                    } else{
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
                        mProgressBar.dismiss()
                    }
        }
    }
    private fun verifyEmail(){
        val mUser = mAuth.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Email verification sent" + mUser, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to Send Verification", Toast.LENGTH_LONG).show()
            }
        }
    }
}
