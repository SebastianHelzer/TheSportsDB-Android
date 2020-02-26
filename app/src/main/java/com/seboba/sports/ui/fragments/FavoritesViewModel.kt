package com.seboba.sports.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seboba.remote.sports.SportsRemoteDataSource
import com.seboba.remote.sports.TeamsModel
import com.seboba.sports.model.UITeam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FavoritesViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val dataSource = SportsRemoteDataSource()
    private val teamsResponse: MutableLiveData<List<UITeam>> = MutableLiveData()

    val teams: LiveData<List<UITeam>> = teamsResponse

    override fun onCleared() {
        println("on cleared")
        compositeDisposable.clear()
        super.onCleared()
    }

    fun searchTeams(term: String) {
        loadTeams(term)
    }

    init {
        loadTeams("Dortmund")
    }

    private fun loadTeams(term: String) {
        compositeDisposable.add(
        dataSource.searchTeams(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.teams.toUITeam() }
            .subscribe ({
                teamsResponse.value = it
            }, {
                Log.e("MainViewModel", it.message)
            })
        )
    }

}