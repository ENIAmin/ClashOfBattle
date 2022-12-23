package com.example.clashofbattle

import android.graphics.Paint.Cap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clashofbattle.databinding.ViewCapabilityBinding
import com.example.clashofbattle.databinding.ViewPlayerBinding
import com.example.clashofbattle.models.Capability
import com.example.clashofbattle.models.Player
import com.example.clashofbattle.utils.getColor
import com.example.clashofbattle.utils.getNameId
import com.example.clashofbattle.utils.getPlayerJob
import com.example.democlashofbattle.utils.loadImage

class ViewPlayersAdapter(val clickListener: (Long) -> Unit) : ListAdapter<Player, PlayerViewHolder>(PlayerViewHolder.PlayerDiffCallback()) {

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.from(parent)
    }

}

class PlayerViewHolder private constructor(val binding: ViewPlayerBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: (Long) -> Unit, item: Player) {
        binding.playerName.text = item.name
        loadImage(binding.playerImage, item.imageUrl)
        binding.playerClass.setTextColor(getPlayerJob(item).getColor(binding.playerClass.context))
        binding.playerClass.setText(getPlayerJob(item).getNameId())
        binding.root.setOnClickListener { clickListener(item.id) }
    }



    companion object {
        fun from(parent: ViewGroup): PlayerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewPlayerBinding.inflate(layoutInflater, parent, false)
            return PlayerViewHolder(binding)
        }
    }

    class PlayerDiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

}