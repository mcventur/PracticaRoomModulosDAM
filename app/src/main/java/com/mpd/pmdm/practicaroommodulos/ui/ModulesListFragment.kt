package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentModulosListBinding
import com.mpd.pmdm.practicaroommodulos.ui.adapters.ModulesListAdapter
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class ModulesListFragment : Fragment() {
    private var _binding: FragmentModulosListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory()
    }

    //Si nos viene el id de ciclo, lo usamos para filtar los módulos mostrados
    private var cicloId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModulosListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarListado()

        binding.fabAddModulo.setOnClickListener {
            //Ojo: Realmente, la navegación actualmente está en EditCicloFragment. Tengo que seguir desde ahí
            val action = EditCicloFragmentDirections
                .actionEditCicloFragmentToEditModuleFragment(cicloId = cicloId?:0, moduleId = 0)
            findNavController().navigate(action)
        }
    }

    private fun configurarListado() {
        binding.list.layoutManager = LinearLayoutManager(context)


        val adapter = ModulesListAdapter{module ->

            //Este fragmento puede estar directamente en el NavHost como destino actual,
            //o bien estar dentro del EditCicloFragment. Tenemos que controlarlo para ver desde
            //qué destino lanzamos la acción deseada
            val action = if(findNavController().currentDestination?.id == R.id.modulesListFragment){
                ModulesListFragmentDirections
                    .actionModulesListFragmentToEditModuleFragment(
                        cicloId = module.cicloId,
                        moduleId = module.id)
            } else{
                EditCicloFragmentDirections
                    .actionEditCicloFragmentToEditModuleFragment(
                        cicloId = module.cicloId,
                        moduleId = module.id)
            }
            findNavController().navigate(action)
        }
        binding.list.adapter = adapter

        //Si no hay ciclo, mostramos todos los módulos. Si lo hay, mostramos sólo los del ciclo
        if (cicloId == null) {
            viewModel.allModules.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            viewModel.getModulesOfCiclo(cicloId ?: 0).observe(viewLifecycleOwner) {
                Log.d("ModulesListFragment", "Modulos del ciclo $cicloId: $it")
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Permite setear el id de ciclo actual, para filtrar los módulos que se muestran en el listado
     * y setea la lista pasada al adapter
     * IMPORTANTE: Si se ejecuta en el onViewCreated del fragmento padre, se ejecuta antes deL onViewCreated de este fragmento
     * así que podemos condicionar la lista que observamos, sin necesidad de repetir esta clase
     */
    fun setCurrentCicloId(cicloId: Long) {
        this.cicloId = cicloId
    }


}