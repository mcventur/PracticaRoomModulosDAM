package com.mpd.pmdm.practicaroommodulos.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mpd.pmdm.practicaroommodulos.data.database.Module

import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemBinding


class ModulesRecyclerViewAdapter():
    ListAdapter<Module, ModulesRecyclerViewAdapter.ViewHolder>(ModuleDiffCallback) {

    var idFieldvisibility = View.VISIBLE

    //Escribimos nuestra implementación de Diffutil.Itemcallback
    companion object{
        private val ModuleDiffCallback = object: DiffUtil.ItemCallback<Module>(){
            override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


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
        val item = getItem(position)
        holder.bind(item)
        holder.binding.moduleId.visibility = idFieldvisibility
    }

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(module: Module){
            binding.moduleId.text = module.id.toString()
            binding.moduleName.text = module.name
            binding.moduleCredits.text = module.credits.toString()
        }
    }

    /*
    Añadimos esta función para actualizar el contenido del RecyclerView
    No es muy eficiente, porque se actualiza la lista completa, en lugar de limitarse a las diferencias

    Con List.Adapter ya no la necesitamos. La comento
     */
/*    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newModulesList: List<Module>){
        modulesList = newModulesList
        notifyDataSetChanged()
    }*/

}