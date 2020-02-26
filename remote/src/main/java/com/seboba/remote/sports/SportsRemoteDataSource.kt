package com.seboba.remote.sports

import com.seboba.remote.HttpInterceptor
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SportsRemoteDataSource: SportsDataSource {

    private val api: SportsDataSource
    private val URL = "https://thesportsdb.com/api/"

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpInterceptor())
            .addInterceptor{
                println("\nThis is a thing: $it ${it.call()} ${it.request()} ${it.connection()}\n")
                it.proceed(it.request())
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(SportsDataSource::class.java)
    }


    override fun searchTeams(term: String): Single<TeamsResponseModel?> {
        return api.searchTeams(term)
    }

    override fun getTeamEvents(teamID: Int): Single<TeamGamesResponseModel> {
        return api.getTeamEvents(teamID)
    }

    override fun getTeamDetails(teamID: Int): Single<TeamsResponseModel> {
        return api.getTeamDetails(teamID)
    }

    override fun getEventDetails(eventID: Int): Single<EventResponseModel> {
        return api.getEventDetails(eventID)
    }

}