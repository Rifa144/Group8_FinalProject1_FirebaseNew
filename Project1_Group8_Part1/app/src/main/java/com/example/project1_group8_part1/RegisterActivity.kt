package com.example.project1_group8_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Button
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var registerbtn: Button
    private lateinit var loginbtn: Button
    private var registerEmail:EditText? = null
    private var registerPassword:EditText? = null
    var registerConfirmPassword:EditText? = null
    var email = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
    var emailPattern = Pattern.compile(email)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        registerbtn = findViewById(R.id.registerBtn)
        loginbtn = findViewById(R.id.loginBtn)
        registerEmail = findViewById(R.id.registxtemail)
        registerConfirmPassword = findViewById(R.id.registxtcpassword)
        registerPassword = findViewById(R.id.registxtpassword)
        var email = registerEmail!!.text.toString().trim()
        var password = registerPassword!!.text.toString().trim()
        loginbtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        registerbtn.setOnClickListener {
            Validate()

        }
    }
    private fun Validate(){
        if (TextUtils.isEmpty(registerEmail!!.text.toString().trim())) {
            registerEmail!!.setText(registerEmail!!.text.toString().trim())
            registerEmail!!.error = "Email is required"
            return
        }
        if (!emailPattern.matcher(registerEmail!!.text.toString().trim()).matches()) {
            registerEmail!!.setText(registerEmail!!.text.toString().trim())
            registerEmail!!.error = "Email is not valid"
            return
        }
        if (TextUtils.isEmpty(registerPassword!!.text.toString().trim())) {
            registerPassword!!.setText(registerPassword!!.text.toString().trim())
            registerPassword!!.error = "Password is required"
            return
        }
        if (TextUtils.isEmpty(registerConfirmPassword?.getText().toString().trim())) {
            registerConfirmPassword?.setText(registerConfirmPassword?.getText().toString().trim())
            registerConfirmPassword!!.error="Confirm password is required"
            return
        }
        if (registerPassword?.getText().toString().trim() != registerConfirmPassword?.getText().toString().trim()) {
            registerConfirmPassword?.setText(registerConfirmPassword?.getText().toString().trim())
            registerConfirmPassword?.error="Password is not matching"
            return
        }
        RegisterUser(registerEmail!!.text.toString(),registerPassword!!.text.toString())
    }
    private fun RegisterUser(email: String, password:String){
        mAuth?.createUserWithEmailAndPassword(email,password)
            ?.addOnCompleteListener(this@RegisterActivity) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    // authentication done
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    // auth failed
                    Toast.makeText(this, task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }?.addOnFailureListener {
                Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}