package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentCiclosListBinding
import com.mpd.pmdm.practicaroommodulos.ui.adapters.CiclosListAdapter
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory

class CiclosListFragment : Fragment() {
    private var _binding: FragmentCiclosListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ModulosViewModel by activityViewModels{ ModulosViewModelFactory()}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCiclosListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instanciamos el adaptador, pasándole una implementación para el onClickListener
        //Falta saber qué ciclo se está clickando. Se define en el adapter
        val adapterCiclos = CiclosListAdapter{ ciclo ->
            val action = CiclosListFragmentDirections.actionCiclosFragmentToEditCicloFragment(ciclo.id.toInt())
            findNavController().navigate(action)
        }
        binding.listCiclos.adapter = adapterCiclos

        //Observamos los cambios en la bdd de Ciclos
        viewModel.allCiclos.observe(viewLifecycleOwner){
            adapterCiclos.submitList(it)
        }


        binding.fabAddCiclo.setOnClickListener {
            //Navegamos al editor de Ciclos eon el valor por defecto de ciclo (0)
            val action = CiclosListFragmentDirections.actionCiclosFragmentToEditCicloFragment()
            findNavController().navigate(action)
        }

    }
}