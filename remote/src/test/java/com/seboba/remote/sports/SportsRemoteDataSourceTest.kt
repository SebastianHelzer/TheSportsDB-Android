package com.seboba.remote.sports

import org.junit.Test

import org.junit.Assert.*

class SportsRemoteDataSourceTest {

    @Test
    fun searchTeams() {

        val api = SportsRemoteDataSource()
        api.searchTeams("Dortmund").subscribe {
            println("Response: $it")
        }

    }

    @Test
    fun getTeamEvents() {
        val api = SportsRemoteDataSource()
        api.getTeamEvents(133602).subscribe {
            println("Response: $it")
        }
    }

    @Test
    fun getTeamDetails() {
        val api = SportsRemoteDataSource()
        api.getTeamDetails(133602).subscribe ({
            println("Response: $it")
        }, {

        })
    }

    @Test
    fun getEventDetails() {
        val api = SportsRemoteDataSource()
        api.getEventDetails(672116).subscribe {
            println("Response: $it")
        }
    }

}