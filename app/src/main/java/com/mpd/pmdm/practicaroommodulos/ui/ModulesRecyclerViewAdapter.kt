package com.mpd.pmdm.practicaroommodulos.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mpd.pmdm.practicaroommodulos.data.database.Module

import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemBinding


class ModulesRecyclerViewAdapter(
    private var modulesList: List<Module> = emptyList()
) : RecyclerView.Adapter<ModulesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modulesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = modulesList.size

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(module: Module){
            binding.moduleId.text = module.id.toString()
            binding.moduleName.text = module.name
            binding.moduleCredits.text = module.credits.toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newModulesList: List<Module>){
        modulesList = newModulesList
        notifyDataSetChanged()
    }

}