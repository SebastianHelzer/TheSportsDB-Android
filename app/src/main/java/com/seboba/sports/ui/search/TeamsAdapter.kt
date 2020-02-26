package com.seboba.sports.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seboba.sports.R
import com.seboba.sports.model.UITeam

class TeamsAdapter(private val list: List<UITeam>, private val itemClicked: (teamID: Int) -> Unit) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {
    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.team_icon)
        val name = itemView.findViewById<TextView>(R.id.team_name)
        val text1 = itemView.findViewById<TextView>(R.id.team_text)
        val text2 = itemView.findViewById<TextView>(R.id.team_text1)
        val favoriteTeam = itemView.findViewById<ImageButton>(R.id.favorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = list[position]
        holder.name.text = team.name
        holder.text1.text = team.score
        holder.text2.text = team.sport

        holder.itemView.setOnClickListener { itemClicked.invoke(team.id) }

        if (team.iconURL == null) {
            Glide
                .with(holder.itemView)
                .load(R.drawable.ic_launcher_background)
                .into(holder.icon)
        } else {
            Glide
                .with(holder.itemView)
                .load("${team.iconURL}/preview")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.icon);
        }

        val prefs = holder.itemView.context.getSharedPreferences("Dope Prefs", Context.MODE_PRIVATE)

        holder.favoriteTeam.setOnClickListener {
            if(prefs.getInt("${team.id}", 0) == team.id) {
                prefs.edit()
                    .putInt("${team.id}", 0)
                    .apply()
            } else {
                prefs.edit()
                    .putInt("${team.id}", team.id)
                    .apply()
            }

            notifyItemChanged(position)
        }

        if(prefs.getInt("${team.id}", 0) == team.id) {
            holder.favoriteTeam.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            holder.favoriteTeam.setImageResource(R.drawable.ic_favorite)
        }
    }

    fun favoriteClicked(){



    }
}