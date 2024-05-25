package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.firebase

import com.google.firebase.crashlytics.KeyValueBuilder
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class MyFirebaseCrashlytics {
    private val crashlytics = Firebase.crashlytics

    fun logSimpleError(text: String, addExtraDataFun: (KeyValueBuilder.() -> Unit)? = null)
    {
        logError(Exception(text), addExtraDataFun)
    }

    fun logError(exception: Exception, addExtraDataFun: (KeyValueBuilder.() -> Unit)? = null)
    {
        addExtraDataFun?.let { addExtraDataFun ->
            val builder = KeyValueBuilder(crashlytics)
            builder.addExtraDataFun()
        }
        crashlytics.setCustomKeys {
            key("hola","si")
            key("bool", true)
        }

        crashlytics.recordException(exception)//coje el error y lo envia al server
    }
}