package com.seboba.remote.sports

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsDataSource {

    @GET("v1/json/1/searchteams.php")
    fun searchTeams(@Query("t") term: String): Single<TeamsResponseModel?>

    @GET("v1/json/1/eventslast.php")
    fun getTeamEvents(@Query("id") teamID: Int): Single<TeamGamesResponseModel>

    @GET("v1/json/1/lookupteam.php")
    fun getTeamDetails(@Query("id") teamID: Int): Single<TeamsResponseModel>

    @GET("v1/json/1/lookupevent.php")
    fun getEventDetails(@Query("id") eventID: Int): Single<EventResponseModel>

}

