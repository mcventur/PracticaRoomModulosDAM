package com.mpd.pmdm.practicaroommodulos.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mpd.pmdm.practicaroommodulos.data.database.Ciclo
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemCicloBinding

/**
 * @param onItemClicked: Una función de Ciclo: La acción a desencadenar al hacer click en el botón editar de cada Ciclo
 * Podéis ver el ejemplo hecho en BusSchedule:
 * https://github.com/mcventur/BusSchedulerMarce/blob/fc0cef266d03a57d9cd86d548205d5fbd975e08a/app/src/main/java/com/example/busschedule/adapters/BusStopAdapter.kt#L30
 */
class CiclosListAdapter(val onItemClicked: (Ciclo) -> Unit
): ListAdapter<Ciclo, CiclosListAdapter.ViewHolder>(ModuleDiffCallback) {

    //Escribimos nuestra implementación de Diffutil.Itemcallback
    companion object{
        private val ModuleDiffCallback = object: DiffUtil.ItemCallback<Ciclo>(){
            override fun areContentsTheSame(oldItem: Ciclo, newItem: Ciclo): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Ciclo, newItem: Ciclo): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return  ViewHolder(
            FragmentItemCicloBinding.inflate(
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

    inner class ViewHolder(val binding: FragmentItemCicloBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ciclo: Ciclo){
            binding.cicloId.text = ciclo.id.toString()
            binding.cicloAbreviatura.text = ciclo.abreviatura
            binding.cicloNombre.text = ciclo.name
            //Defino el onClickListener, que es una función de Ciclo que no devuelve nada
            //Ya podemos indicarle el ciclo
            binding.cicloEditButton.setOnClickListener {
                onItemClicked(ciclo)
            }
        }
    }

}