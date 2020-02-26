package com.seboba.remote.starwars

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CharactersRemoteDataSource :
    CharactersDataSource {
    private val api: CharactersDataSource
    private val URL = "http://swapi.co/api/"
    override val characters: Observable<CharactersResponseModel?>
        get() = api.characters

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        api = retrofit.create(CharactersDataSource::class.java)
    }
}