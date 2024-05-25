package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.firebase

import android.app.Application
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class MyFirebaseAnalytics(private val appContext: Application) {

    private val analytics = FirebaseAnalytics.getInstance(appContext)

    fun LogOpenApp()
    {
        val bundle = Bundle()
        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
    }
}