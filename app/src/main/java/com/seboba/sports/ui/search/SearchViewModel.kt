package com.seboba.sports.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seboba.remote.sports.SportsRemoteDataSource
import com.seboba.sports.model.UITeam
import com.seboba.sports.model.toUITeam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchViewModel : ViewModel() {

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

    private fun loadTeams(term: String) {
        compositeDisposable.add(
        dataSource.searchTeams(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.teams?.toUITeam() ?: emptyList() }
            .subscribe ({
                teamsResponse.value = it
            }, {
                Log.e("MainViewModel", "Error: ${it.message}")
            })
        )
    }

}