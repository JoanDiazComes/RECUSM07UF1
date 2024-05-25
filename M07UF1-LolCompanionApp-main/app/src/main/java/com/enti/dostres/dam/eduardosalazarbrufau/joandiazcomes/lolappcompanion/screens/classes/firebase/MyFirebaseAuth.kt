package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.firebase

import android.app.Application
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models.DbUser
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuth(val appContext: Application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: DbUser? = null

    fun isLoginActive() = getUser() != null

    fun getUser() = currentUser

    fun setCurentUser(newUser: DbUser){
        currentUser = newUser
    }

    fun getAuthDbUser(): DbUser? {
        if (currentUser != null) {
            return currentUser
        }

        firebaseAuth.currentUser?.let { user ->
            val dbUser = DbUser(
                id = user.uid,
                email = user.email,
                username = user.displayName,
                photoPath = user.photoUrl.toString()
            )
            return dbUser
        }

        return null
    }
}