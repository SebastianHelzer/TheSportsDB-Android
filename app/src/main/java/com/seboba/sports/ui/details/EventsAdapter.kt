package com.seboba.sports.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seboba.sports.R
import com.seboba.sports.model.UIEvent

class EventsAdapter(private val list: List<UIEvent>, private val selectedTeamID: Int) : RecyclerView.Adapter<EventsAdapter.TeamViewHolder>() {
    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon1: ImageView = itemView.findViewById(R.id.icon_team_1)
        val icon2: ImageView = itemView.findViewById(R.id.icon_team_2)
        val score1: TextView = itemView.findViewById(R.id.score_team_1)
        val score2: TextView = itemView.findViewById(R.id.score_team_2)
        val winLoss: TextView = itemView.findViewById(R.id.win_loss)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_event, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val event = list[position]

        holder.score1.text = event.intHomeScore.toString()
        holder.score2.text = event.intAwayScore.toString()
        holder.winLoss.text = getWinLossChar(event, selectedTeamID)

        setImageSafe(holder.icon1, event.idHomeTeam, holder.itemView)
        setImageSafe(holder.icon2, event.idAwayTeam, holder.itemView)
    }

    private fun getWinLossChar(event: UIEvent, currentTeamID: Int): String{
        val home = event.idHomeTeam == currentTeamID
        val char = getWinLossChar(if(home) event.intHomeScore else event.intAwayScore, if(!home) event.intHomeScore else event.intAwayScore)
        return char.toString()
    }

    private fun getWinLossChar(friendScore: Int?, enemyScore: Int?): Char {
        return if(friendScore == null || enemyScore == null) '?'
        else if(friendScore > enemyScore) 'W'
        else if(friendScore < enemyScore) 'L'
        else 'D'
    }

    private fun setImageSafe(image: ImageView, teamID: Int?, itemView: View){
        setImageSafe(image, teamImages[teamID], itemView)
    }

    private fun setImageSafe(image: ImageView, url: String?, itemView: View){
        if(url == null) {
            Glide
                .with(itemView)
                .load(R.drawable.ic_launcher_background)
                .into(image)
        } else {
            Glide
                .with(itemView)
                .load("${url}/preview")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(image)
        }
    }

    private var teamImages: Map<Int, String> = emptyMap()

    fun setTeamIDAndImages(it: List<TeamIDAndImage>?) {
        it?.let { teamImages = it.toMap() }
        notifyDataSetChanged()
    }

}