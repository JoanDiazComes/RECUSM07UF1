package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
class ChampionsScreen:  Fragment() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    private lateinit var selectImageButton: Button
    private lateinit var uploadImage: Button
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var selectedImage: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageReference = FirebaseStorage.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.champions_screen, container, false)

        selectImageButton = view.findViewById(R.id.select_image_button)
        uploadImage = view.findViewById(R.id.upload_image_button)
        selectedImage = view.findViewById(R.id.selected_image)  // Cambié el nombre a selectedImage para que coincida con el ID del XML

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        selectImageButton.setOnClickListener {
            selectImage()
        }
        uploadImage.setOnClickListener {
            uploadImage()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonChangeToBuildsScreen = view.findViewById<Button>(R.id.navigation_builds_button)
        val buttonChangeToChampionsScreen = view.findViewById<Button>(R.id.navigation_create_build_button)
        val buttonChangeToConfigScreen = view.findViewById<Button>(R.id.navigation_settings_button)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            selectedImage.setImageURI(selectedImageUri)  // Usa la variable selectedImage aquí
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

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun uploadImage() {
        if (selectedImageUri != null) {
            val imageName = UUID.randomUUID().toString()
            val imageRef = storageReference.child("images/$imageName")

            imageRef.putFile(selectedImageUri!!)

        }
    }
}
