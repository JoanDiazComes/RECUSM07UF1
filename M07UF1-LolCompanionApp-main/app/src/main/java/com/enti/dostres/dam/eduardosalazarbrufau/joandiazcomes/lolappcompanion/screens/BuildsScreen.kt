package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.R

class BuildsScreen: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.builds_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonChangeToBuildsScreen = view?.findViewById<Button>(R.id.navigation_builds_button)
        val buttonChangeToChampionsScreen = view?.findViewById<Button>(R.id.navigation_create_build_button)
        val buttonChangeToConfigScreen = view?.findViewById<Button>(R.id.navigation_settings_button)

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