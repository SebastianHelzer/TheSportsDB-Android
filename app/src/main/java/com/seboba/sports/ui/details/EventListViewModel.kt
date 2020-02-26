package com.seboba.sports.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seboba.remote.sports.Results
import com.seboba.remote.sports.SportsRemoteDataSource
import com.seboba.sports.model.UIEvent
import com.seboba.sports.model.UITeam
import com.seboba.sports.model.toUITeam
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class EventListViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val dataSource = SportsRemoteDataSource()

    private val eventResponse: MutableLiveData<List<UIEvent>> = MutableLiveData()
    private val eventTeamIDs: MutableLiveData<List<TeamIDAndImage>> = MutableLiveData()
    private val teamDataResponse: MutableLiveData<UITeam> = MutableLiveData()

    val events: LiveData<List<UIEvent>> = eventResponse
    val teamIDAndImages: LiveData<List<TeamIDAndImage>> = eventTeamIDs
    val teamData: LiveData<UITeam> = teamDataResponse

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun setTeamID(teamID: Int) {
        events(teamID)
    }

    private fun events(teamID: Int) {
        compositeDisposable.add(
        dataSource.getTeamEvents(teamID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { responseModel ->
                val ids = responseModel?.results?.map { listOf(it.idAwayTeam, it.idHomeTeam) }?.flatten()
                ids?.let { loadTeamImagesURLSByIds(it) }
            }
            .subscribe ({
                it?.let { eventResponse.value = it.results?.toUIEvents() ?: emptyList() }
            }, {
                Log.e("What happened?", it?.localizedMessage.toString())
            })
        )

        compositeDisposable.add(
        dataSource.getTeamDetails(teamID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                teamDataResponse.value = it.teams?.first()?.toUITeam()
            },{
                Log.e("What happened?","Error: ${it.message}")
            })
        )
    }

    private fun loadTeamImagesURLSByIds(ids: List<Int>) {
        val obs: List<Single<TeamIDAndImage>> = ids.toSet().map { id ->
            dataSource.getTeamDetails(id)
                .map { responseModel -> responseModel.teams?.first().let { TeamIDAndImage(it?.idTeam ?: 0, it?.strTeamBadge ?: "") } }
        }

        compositeDisposable.add(
            Single.zip<TeamIDAndImage, List<TeamIDAndImage>>(obs) { args ->
                args.filterIsInstance<TeamIDAndImage>()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                eventTeamIDs.value = it
            }, {
                Log.e("What happened?", "Error: ${it.message}")
            })
        )
    }

    private fun List<Results>.toUIEvents(): List<UIEvent> {
        return map { it.toUIEvent() }
    }

    private fun Results.toUIEvent(): UIEvent {
        return UIEvent(
            idEvent, strEvent, strSport, strLeague,
            strDescriptionEN, strHomeTeam, strAwayTeam, intHomeScore,
            intAwayScore, dateEvent, dateEventLocal, strDate,
            strTime, idHomeTeam, idAwayTeam,
            strCountry, strPoster, strFanart, strThumb, strBanner
        )
    }

}

