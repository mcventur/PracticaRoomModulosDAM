package com.mpd.pmdm.practicaroommodulos.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mpd.pmdm.practicaroommodulos.data.database.Module

import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemBinding


class ModulesRecyclerViewAdapter():
    ListAdapter<Module,ModulesRecyclerViewAdapter.ModuleViewHolder>(DiffUtilItemCallback) {

    companion object{
        val DiffUtilItemCallback = object: DiffUtil.ItemCallback<Module>(){
            override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean {
                return oldItem == newItem
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {

        return ModuleViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ModuleViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(module: Module){
            binding.moduleId.text = module.id.toString()
            binding.moduleName.text = module.name
            binding.moduleCredits.text = module.credits.toString()
        }
    }

/*  No se usa esto ya con un ListAdapter
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newModulesList: List<Module>){
        modulesList = newModulesList
        notifyDataSetChanged()
    }*/

}