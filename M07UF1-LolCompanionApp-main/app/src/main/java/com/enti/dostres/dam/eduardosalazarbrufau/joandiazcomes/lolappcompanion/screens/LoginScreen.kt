package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.R
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.firebase.FB
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models.DbUser
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import java.util.Date

class LoginScreen : Fragment() {

    lateinit var fragmentView: View
    lateinit var auth: FirebaseAuth

    //Referencia al botón del XML
    private val googleAuthButton by lazy { fragmentView.findViewById<SignInButton>(R.id.login_google_button) }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        FB.init(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.login_screen, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //listener a botón de google
        googleAuthButton.setOnClickListener { googleAuth() }


    }


    private fun googleAuth() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if (result.resultCode != Activity.RESULT_OK) {
            FB.crashlytics.logSimpleError("Login Error No User") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            return
        }

        val authUser = FB.auth.getAuthDbUser() ?: kotlin.run {
            FB.crashlytics.logSimpleError("Login Error No User") {
                key("code", result.resultCode)
                key("data", result.toString())
            }

            return
        }

        val id = authUser.id ?: kotlin.run {
            FB.crashlytics.logSimpleError("Login Error No ID") {
                key("code", result.resultCode)
                key("data", result.toString())
            }

            return
        }


        FB.db.find<DbUser>(id, authUser.getTable(),
            onSuccess = { dbUser ->
                dbUser.lastLogin = Date()

                FB.db.save(dbUser,
                    onSuccess = {
                        FB.auth.setCurentUser(dbUser)
                        changeScreenOnLogin()
                    },
                    onFailure = {

                    })
            },
            onFailure = {
                FB.db.save(authUser,
                    onSuccess = { dbUser ->
                        FB.auth.setCurentUser(dbUser)
                        changeScreenOnLogin()
                    },
                    onFailure = {

                    })
            })
    }
    private fun changeScreenOnLogin() {
        //Pantalla que se abre tras hacer login
        val fragment = BuildsScreen()

        val manager = activity?.supportFragmentManager

        val transaction = manager?.beginTransaction()

        transaction?.replace(R.id.mainFragmentContainer, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}