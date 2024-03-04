package com.mpd.pmdm.practicaroommodulos.ui.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mpd.pmdm.practicaroommodulos.data.database.Module

import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemModuloBinding


class ModulesListAdapter(val onItemClicked: (Module) -> Unit):
    ListAdapter<Module, ModulesListAdapter.ViewHolder>(ModuleDiffCallback) {

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
            FragmentItemModuloBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: FragmentItemModuloBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(module: Module){
            binding.moduleId.text = module.id.toString()
            binding.moduleName.text = module.name
            binding.moduleCredits.text = module.credits.toString()
            binding.moduloEditButton.setOnClickListener {
                Log.d("ModulesListAdapter","Click en modulo $module")
                onItemClicked(module)
            }
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