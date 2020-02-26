package com.seboba.sports.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.seboba.sports.R
import com.seboba.sports.model.UIEvent
import com.seboba.sports.model.UITeam
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.team_details_header.*


class EventListFragment : DialogFragment() {

    companion object {
        fun newInstance(teamID: Int) = EventListFragment().apply {
            arguments = bundleOf(ID_KEY to teamID)
        }

        const val ID_KEY = "TEAM_ID"
    }

    private lateinit var viewModel: EventListViewModel

    private var selectedTeamID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            android.R.style.Theme_Material_Light_NoActionBar
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.events_fragment, container, false)
    }

    private val observer: Observer<List<UIEvent>> = Observer {
        Toast.makeText(requireContext(), "We loaded: ${it?.size} events", Toast.LENGTH_SHORT).show()
        recyclerView.adapter = EventsAdapter(it ?: emptyList(), selectedTeamID)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private val teamIDAndImageObserver: Observer<List<TeamIDAndImage>> = Observer {
        Toast.makeText(requireContext(), "We loaded: ${it?.size} team ids and images", Toast.LENGTH_SHORT).show()
        (recyclerView.adapter as EventsAdapter).setTeamIDAndImages(it)
        Log.d("EventListFrag", "Here are my ids: $it")
    }

    private val teamDataObserver: Observer<UITeam> = Observer { team ->

        Glide.with(this)
            .load(team?.fanArtURL ?: team?.bannerURL ?: team.iconURL)
            .centerCrop()
            .into(background_image)

        Glide.with(this)
            .load(team?.iconURL)
            .into(team_icon)

        team_name.text = team.name

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(EventListViewModel::class.java)
        viewModel.events.observe(viewLifecycleOwner, observer)
        viewModel.teamIDAndImages.observe(viewLifecycleOwner, teamIDAndImageObserver)
        viewModel.teamData.observe(viewLifecycleOwner, teamDataObserver)
        selectedTeamID = arguments?.getInt(ID_KEY) ?: 0
        viewModel.setTeamID(arguments?.getInt(ID_KEY) ?: 0)
    }

}

