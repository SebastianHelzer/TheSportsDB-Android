package com.seboba.remote.starwars

import io.reactivex.Observable
import retrofit2.http.GET

interface CharactersDataSource {
    @get:GET("people/")
    val characters: Observable<CharactersResponseModel?>
}