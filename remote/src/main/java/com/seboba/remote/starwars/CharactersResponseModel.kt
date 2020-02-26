package com.seboba.remote.starwars

data class CharactersResponseModel(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<CharacterModel>? = null
)