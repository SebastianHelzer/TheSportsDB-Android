package com.seboba.sports.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.seboba.sports.R
import com.seboba.sports.model.UITeam
import com.seboba.sports.ui.details.EventListFragment
import com.seboba.sports.ui.search.TeamsAdapter
import kotlinx.android.synthetic.main.main_fragment.*


class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = FavoritesViewModel(requireContext().getSharedPreferences("Dope Prefs", Context.MODE_PRIVATE))

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.teams.observe(viewLifecycleOwner, observer)
    }

}


