package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens

import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.R
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.databinding.MainActivityBinding
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models.LolUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ConfigScreen : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var  databaseReference: DatabaseReference
    private lateinit var  storageReference : StorageReference
    private lateinit var  imageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonChangeToBuildsScreen = view?.findViewById<Button>(R.id.navigation_builds_button)
        val buttonChangeToChampionsScreen = view?.findViewById<Button>(R.id.navigation_create_build_button)
        val buttonChangeToConfigScreen = view?.findViewById<Button>(R.id.navigation_settings_button)
        val buttonSaveProfile = view?.findViewById<Button>(R.id.Save)
        val uid = auth.currentUser?.uid
        buttonSaveProfile?.setOnClickListener{
            val username = view?.findViewById<EditText>(R.id.Username)?.text
            val elo = view?.findViewById<EditText>(R.id.ELO)?.text
            val level = view?.findViewById<EditText>(R.id.Level)?.text
            val user = LolUser(username.toString(), elo.toString(), level.toString())

            if(uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener{
                    if(it.isSuccessful){
                        uploadProfilePic()
                    }
                    else{
                        Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        buttonChangeToBuildsScreen?.setOnClickListener {
            changeToBuildsScreen()
        }
        buttonChangeToChampionsScreen?.setOnClickListener {
            changeToChampionsScreen()
        }
        buttonChangeToConfigScreen?.setOnClickListener {
            changeToConfigScreen()
        }
    }

    private fun uploadProfilePic(){
        imageUri = Uri.parse("android.resource://${R.drawable.nautilus_0}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(context, "Profile successfully updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(context, "Profile failed to update", Toast.LENGTH_SHORT).show()
        }
    }
    private fun changeToBuildsScreen() {
        val buildsScreen = BuildsScreen()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainer, buildsScreen)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun changeToChampionsScreen() {
        val championsScreen = ChampionsScreen()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainer, championsScreen)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun changeToConfigScreen() {
        val configScreen = ConfigScreen()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainer, configScreen)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
