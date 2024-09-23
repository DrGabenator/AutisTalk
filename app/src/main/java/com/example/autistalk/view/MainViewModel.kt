package com.example.autistalk.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.autistalk.data.AppDatabase
import com.example.autistalk.data.Card
import com.example.autistalk.data.CardRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var cardRepository: CardRepository

    init {
        val database = AppDatabase.getInstance(application)
        val cardDao = database.cardDao()
        cardRepository = CardRepository(cardDao)
    }

    fun createOrUpdateCard(card: Card) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                cardRepository.createOrUpdateCard(card)
            } catch (_: Exception) {
            }
        }
    }

    fun getCardByCardId(cardId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val card = cardRepository.getCardByCardId(cardId)
                _cards.postValue(listOf(card))
            } catch (_: Exception) {
            }
        }
    }

    fun getAllCards() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cards = cardRepository.getAllCards()
                _cards.postValue(cards ?: listOf())

            } catch (_: Exception) {
            }
        }
    }

    fun registerUser(name: String, email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user?.updateProfile(profileUpdates)
                    } else {
                        // Falha no registro
                    }
                }
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login bem-sucedido
                        callback(true)
                    } else {
                        // Falha no login
                        callback(false)
                    }
                }
        } else {
            callback(false)  // Se os campos estiverem vazios
        }
    }

    // Função para redefinir senha com callback
    fun resetPassword(email: String, callback: (Boolean) -> Unit) {
        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // E-mail de redefinição enviado
                        callback(true)
                    } else {
                        // Falha ao enviar o e-mail de redefinição
                        callback(false)
                    }
                }
        } else {
            callback(false)  // Se o e-mail estiver vazio
        }
    }

}