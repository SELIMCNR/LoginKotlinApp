package com.example.ozetuygulama

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.ozetuygulama.databinding.ActivitySignBinding
import android.widget.Toast

class SignActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignBinding
    private lateinit var databaseHelper: DatabaseHelper
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.imageButtonVsble.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.BtnSignUp.setOnClickListener {
            signUp()
        }
        binding.textLogin.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.imageButtonVsble.setImageResource(R.drawable.invisible)
        } else {
            binding.editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.imageButtonVsble.setImageResource(R.drawable.visible)
        }
        isPasswordVisible = !isPasswordVisible
    }

    private fun signUp() {
        val username = binding.editTextUsername.text.toString()
        val email = binding.editTextEmail.text.toString()
        val number = binding.editTextNumber.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (username.isNotEmpty() && email.isNotEmpty() && number.isNotEmpty() && password.isNotEmpty()) {
            val user = User(0, username, email, number, password)
            val result = databaseHelper.addUser(user)
            if (result > -1) {
                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Login_Activity::class.java)
                startActivity(intent)
                binding.editTextUsername.text.clear()
                binding.editTextEmail.text.clear()
                binding.editTextNumber.text.clear()
                binding.editTextPassword.text.clear()
                binding.editTextUsername.requestFocus()
                binding.editTextUsername.setSelection(binding.editTextUsername.text.length)


            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
