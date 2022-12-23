package com.example.clashofbattle.playerEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clashofbattle.databinding.ViewCapabilityBinding
import com.example.clashofbattle.models.Capability
import com.example.clashofbattle.utils.getColor
import com.example.clashofbattle.utils.getNameId

class ViewCapabilitiesAdapter(val clickListener: (Int) -> Unit) : ListAdapter<Capability, CapabilityViewHolder>(CapabilityViewHolder.CapabilityDiffCallback()) {

    override fun onBindViewHolder(holder: CapabilityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item, position)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapabilityViewHolder {
        return CapabilityViewHolder.from(parent)
    }

}

class CapabilityViewHolder private constructor(val binding: ViewCapabilityBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: (Int) -> Unit, item: Capability, index: Int) {
        binding.capabilityNumber.text = "Compétence ${index + 1}"
        binding.capabilityName.setTextColor(item.getColor(binding.capabilityName.context))
        binding.capabilityName.setText(item.getNameId())
        binding.btnEditCapability.setOnClickListener { clickListener(index) }
    }



    companion object {
        fun from(parent: ViewGroup): CapabilityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewCapabilityBinding.inflate(layoutInflater, parent, false)
            return CapabilityViewHolder(binding)
        }
    }

    class CapabilityDiffCallback : DiffUtil.ItemCallback<Capability>() {
        override fun areItemsTheSame(oldItem: Capability, newItem: Capability): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Capability, newItem: Capability): Boolean {
            return oldItem == newItem
        }
    }

}