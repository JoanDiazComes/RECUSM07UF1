package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.firebase.FB
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.firebase.MyFirebase
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models.Comentary
import com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models.DBTestBuild

class MyApp: Application() {
    companion object{
        private lateinit var Instance: MyApp

        fun get() = Instance
    }
    override fun onCreate() {
        super.onCreate()
        MyFirebase.init(this)
        Instance = this
        //enviar evento de que la app se ha abierto
        FB.init(this)
        FB.analytics.LogOpenApp()

        FB.crashlytics.logSimpleError("On open App Error") {
            key("Name", "Errores")
            key("Es un error", true)
        }

        val build = DBTestBuild()
        build.comentaries.add(Comentary("1", "Comentario Uno"))
        build.comentaries.add(Comentary("2", "Comentario Dos"))

        FB.db.save(build, onSuccess = {

        }, onFailure = {

        })
    }
    fun claseKeyboard(focusedView: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}