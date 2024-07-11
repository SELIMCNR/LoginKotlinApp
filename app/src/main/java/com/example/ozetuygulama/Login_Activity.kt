package com.example.ozetuygulama

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ozetuygulama.databinding.LoginActivityMainBinding

class Login_Activity : AppCompatActivity() {

    private lateinit var binding: LoginActivityMainBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin  .setOnClickListener {
            val username = binding.editTextUsername .text.toString()
            val password = binding.editTextPassword .text.toString()

            if (username.isNotEmpty()  && password.isNotEmpty()) {


                val databaseHelper = DatabaseHelper(this)
                val user = databaseHelper.getUser(username)
                if (user != null && user.password == password) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
                binding.editTextUsername.text.clear()
                binding.editTextPassword.text.clear()


            }

            else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        binding .BtnLoginFacebook .setOnClickListener {
            Toast.makeText(this, "Login with Facebook clicked", Toast.LENGTH_SHORT).show()
        }

        binding .BtnLoginGoogle .setOnClickListener {
            Toast.makeText(this, "Login with Google clicked", Toast.LENGTH_SHORT).show()
        }

        binding.imageButtonVsble.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.imageButtonVsble.setImageResource(R.drawable.visible)
            } else {
                binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.imageButtonVsble.setImageResource(R.drawable.invisible)
            }
            binding.editTextPassword.setSelection(binding.editTextPassword.text.length)
        }
        binding.textForget.setOnClickListener {
            Toast.makeText(this, "Forget Password clicked", Toast.LENGTH_SHORT).show()
        }

        binding.textSignup .setOnClickListener {
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
        }
    }
}
