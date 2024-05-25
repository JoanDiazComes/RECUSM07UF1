package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models

data class DBTestBuild(
    override var id: String? = null,
    var buildImagePath: String? = null,
    var comentaries: MutableList<Comentary> = mutableListOf()
): DbBaseData {
    override fun getTable() = "Builds"
}

data class Comentary(
    var id: String? = null,
    var text: String? = null
)
