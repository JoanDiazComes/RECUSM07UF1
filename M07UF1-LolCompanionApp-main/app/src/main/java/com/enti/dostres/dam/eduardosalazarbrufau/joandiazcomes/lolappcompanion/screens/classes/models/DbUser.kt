package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models

import java.util.Date

data class DbUser(
    override var id: String? = null,
    var email: String? = null,
    var username: String? =null,
    var photoPath: String? =null,
    var lastLogin: Date? = Date(),
    val admin: Boolean = false

) : DbBaseData {
    override fun getTable() = "Users"
}