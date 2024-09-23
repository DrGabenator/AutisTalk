package com.example.autistalk.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.autistalk.MainActivity
import com.example.autistalk.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        val emailLogin = findViewById<EditText>(R.id.emailLogin)
        val passwordLogin = findViewById<EditText>(R.id.passwordLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)
        val txtForgotPassword = findViewById<TextView>(R.id.txtForgotPassword)

        // Ação de login
        btnLogin.setOnClickListener {
            val email = emailLogin.text.toString()
            val password = passwordLogin.text.toString()

            // Passando um callback como lambda para verificar o sucesso do login
            viewModel.loginUser(email, password) { isSuccess ->
                if (isSuccess) {
                    // Login bem-sucedido, redireciona para outra Activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Fecha a Activity de Login para evitar voltar
                } else {
                    // Exibe uma mensagem de erro se falhar
                    Toast.makeText(this, "Falha no login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Ação para ir para a tela de registro
        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Ação de redefinir senha
        txtForgotPassword.setOnClickListener {
            val email = emailLogin.text.toString()

            // Passando um callback como lambda para verificar o sucesso do envio do e-mail de redefinição
            viewModel.resetPassword(email) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, "E-mail de redefinição enviado.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Falha ao enviar o e-mail de redefinição.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}