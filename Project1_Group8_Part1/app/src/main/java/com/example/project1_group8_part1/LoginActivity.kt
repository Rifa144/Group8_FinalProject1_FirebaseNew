package com.example.project1_group8_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.*
import android.widget.Button
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var registerbtn: Button
    private lateinit var loginbtn: Button
    private var registerEmail:EditText? = null
    private var registerPassword:EditText? = null
    var email = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
    var emailPattern = Pattern.compile(email)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        registerbtn = findViewById(R.id.registerBtn)
        loginbtn = findViewById(R.id.loginBtn)
        registerEmail = findViewById(R.id.registxtemail)
        registerPassword = findViewById(R.id.registxtpassword)
        var email = registerEmail!!.text.toString().trim()
        var password = registerPassword!!.text.toString().trim()
        registerbtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginbtn.setOnClickListener {
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
        LoginUser(registerEmail!!.text.toString(),registerPassword!!.text.toString())
    }
    private fun LoginUser(email: String, password:String){
        mAuth?.signInWithEmailAndPassword(email,password)
            ?.addOnCompleteListener(this@LoginActivity) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    // authentication done
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                } else {
                    // auth failed
                    Toast.makeText(this@LoginActivity, task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }?.addOnFailureListener {
                Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}