package com.enti.dostres.dam.eduardosalazarbrufau.joandiazcomes.lolappcompanion.screens.classes.models

data class DbChampions(
    override var id: String? = null,
    var championName: String? = null,
    var championPortraitPath: String? = null
) : DbBaseData{
    override fun getTable() = "Champions"
}