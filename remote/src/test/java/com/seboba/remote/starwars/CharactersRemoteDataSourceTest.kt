package com.seboba.remote.starwars

import org.junit.Test

import org.junit.Assert.*

class CharactersRemoteDataSourceTest {

    @Test
    fun getCharacters() {

        val dataSource = CharactersRemoteDataSource()
        dataSource.characters.subscribe{
            println("Response $it")
        }
    }

}