package com.seboba.sports.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seboba.remote.sports.SportsRemoteDataSource
import com.seboba.remote.sports.TeamsModel
import com.seboba.sports.R
import com.seboba.sports.model.UITeam
import com.seboba.sports.ui.details.EventListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private val observer: Observer<List<UITeam>> = Observer { teams ->
            recyclerView.adapter =
                TeamsAdapter(
                    teams ?: emptyList()
                ) { teamID ->
                    EventListFragment.newInstance(teamID).show(childFragmentManager, "Dope details")
                }
            recyclerView.adapter?.notifyDataSetChanged()
        }

    fun respondToSearch(){
        viewModel.searchTeams(editTextTextPersonName.text.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.teams.observe(viewLifecycleOwner, observer)

        button.setOnClickListener { respondToSearch() }

        editTextTextPersonName.setOnEditorActionListener { v, actionId, event ->
            respondToSearch()
            return@setOnEditorActionListener true
        }
    }

}


