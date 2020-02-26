package com.seboba.sports.model

data class UIEvent(
    val idEvent : Int?,
    val strEvent : String?,
    val strSport : String?,
    val strLeague : String?,
    val strDescriptionEN : String?,
    val strHomeTeam : String?,
    val strAwayTeam : String?,
    val intHomeScore : Int?,
    val intAwayScore : Int?,
    val dateEvent : String?,
    val dateEventLocal : String?,
    val strDate : String?,
    val strTime : String?,
    val idHomeTeam : Int?,
    val idAwayTeam : Int?,
    val strCountry : String?,
    val strPoster : String?,
    val strFanart : String?,
    val strThumb : String?,
    val strBanner : String?
)