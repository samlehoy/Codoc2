package com.example.codoc.activity.dokter

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.codoc.DatabaseHelper
import com.example.codoc.R
import com.google.android.material.textfield.TextInputLayout

class LoginDokterActivity : AppCompatActivity() {

    //to retrieve data on ProfileActivity
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_dokter)

        supportActionBar?.hide()

        //button
        val btnMasuk: Button = findViewById(R.id.buttonMasuk)
        val btnDaftar: TextView = findViewById(R.id.textViewDaftar)

        //instance
        val txtEmailLayout: TextInputLayout = findViewById(R.id.inputEmail)
        val txtPasswordLayout: TextInputLayout = findViewById(R.id.inputPassword)

        //to retrieve data on ProfileActivity
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        //event button Masuk/login
        btnMasuk.setOnClickListener {

            //instance
            val dbHelper = DatabaseHelper(this)

            // Access the underlying EditText from TextInputLayout
            val email = txtEmailLayout.editText?.text.toString().trim()
            val password = txtPasswordLayout.editText?.text.toString().trim()

            //check login
            val result: Boolean = dbHelper.checkLoginDokter(email, password)
            if (result) {
                // Store the user's email in shared preferences upon successful login
                val editor = sharedPreferences.edit()
                editor.putString("user_email", email)
                editor.apply()

                val intentLogin = Intent(this@LoginDokterActivity, HomeDokterActivity::class.java)
                startActivity(intentLogin)
            } else {
                Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show()
                txtEmailLayout.hint = "email"
                txtPasswordLayout.hint = "password"
            }
        }
        //event "Daftar"
        btnDaftar.setOnClickListener {
            val intentRegisterDokterActivity = Intent(this, RegisterDokterActivity::class.java)
            startActivity(intentRegisterDokterActivity)
        }
    }
}