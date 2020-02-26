package com.seboba.sports.ui.favorites

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seboba.remote.sports.SportsRemoteDataSource
import com.seboba.sports.model.UITeam
import com.seboba.sports.ui.search.toUITeam
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FavoritesViewModel(private val prefs: SharedPreferences) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val dataSource = SportsRemoteDataSource()
    private val teamsResponse: MutableLiveData<List<UITeam>> = MutableLiveData()

    val teams: LiveData<List<UITeam>> = teamsResponse

    override fun onCleared() {
        println("on cleared")
        compositeDisposable.clear()
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
        super.onCleared()
    }

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        val badTeam = teamsResponse.value?.firstOrNull{ it.id == key.toInt() } ?: return@OnSharedPreferenceChangeListener
        teamsResponse.value = teamsResponse.value?.minus(badTeam)
    }

    init {
        loadTeams()
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun loadTeams() {
        compositeDisposable.add(
            Observable.fromIterable(prefs.all.values.filterIsInstance<Int>())
                .filter { it != 0 }
                .flatMapSingle { dataSource.getTeamDetails(it) }
                .map { it.teams.first().toUITeam() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    teamsResponse.value = (teamsResponse.value ?: emptyList()) + it
                }, {
                    Log.e(logTag, "Error: ${it?.message}")
                })
        )
    }

    private val logTag = "Favorite manager"

}