package com.seboba.sports.model

data class UITeam(
    val id: Int,
    val name: String,
    val iconURL: String?,
    val bannerURL: String?,
    val fanArtURL: String?,
    val isFavorite: Boolean
)
