package com.princeabdurrahman.instagram

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_signin_link.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            // method baru untuk akun
            createAccount()
        }
    }

    private fun createAccount() {
        val fullname = fullname_register.text.toString()
        val username = username_register.text.toString()
        val email = email_register.text.toString()
        val password = password_register.text.toString()

        when{
            TextUtils.isEmpty(fullname) -> Toast.makeText(this, "Full name is required",
            Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(username) -> Toast.makeText(this, "User name is required",
                Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required",
                Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required",
                Toast.LENGTH_SHORT).show()

            else -> {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Register")
                progressDialog.setMessage("Please Wait....")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            seveUserInfo(fullname, username, email, progressDialog)
                        }else{
                            val message = task.exception.toString()
                            Toast.makeText(this,"error: $message", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun seveUserInfo(
        fullname: String,
        username: String,
        email: String,
        progressDialog: ProgressDialog
    ) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullname.toLowerCase()
        userMap["username"] = username.toLowerCase()
        userMap["email"] = email
        userMap["bio"] = "Hey Iam student at IDN Boarding School"
        //create default image profile
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/socialmedia-ad4c4.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=c74009c1-c3cb-4469-b2c1-2784b99a6586"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful ){
                    progressDialog.dismiss()
                    Toast.makeText(this, "Account sudah dibuat", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }

    }
}