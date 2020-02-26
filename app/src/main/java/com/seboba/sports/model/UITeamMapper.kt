package com.seboba.sports.model

import com.seboba.remote.sports.TeamsModel

fun List<TeamsModel>.toUITeam(): List<UITeam>? = map { it.toUITeam() }

fun TeamsModel.toUITeam(): UITeam {
    return UITeam(
        id = idTeam,
        name = strTeam ?: "Unknown",
        iconURL = strTeamBadge,
        bannerURL = strTeamBanner,
        fanArtURL = strTeamFanart1,
        isFavorite = false,
        sport = strSport ?: "Unknown",
        league = strDivision ?: strLeague ?: "Unknown"
    )
}
