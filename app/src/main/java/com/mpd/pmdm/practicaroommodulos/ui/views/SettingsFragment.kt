package com.mpd.pmdm.practicaroommodulos.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentSettingsBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.SortFields

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val modulosViewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Al cargar el fragment en pantalla, se actualiza el estado de cada vista conforme
        //a lo guardado en el datastore
        observarPreferencias()

        actualizarPreferenciasEventos()
    }

    private fun actualizarPreferenciasEventos() {
        //Escribimos en el datastore si hay cambios en alguna de las preferencias
        binding.displayIdSwitch.setOnCheckedChangeListener { _, isChecked ->
            modulosViewModel.setDisplayIdPref(isChecked)
        }
        binding.sortFieldGroup.setOnCheckedChangeListener { _, checkedId ->
            val sortField = when (checkedId) {
                binding.rbtSortId.id -> SortFields.ID
                binding.rbtSortName.id -> SortFields.NAME
                else -> SortFields.CREDITS
            }
            modulosViewModel.setSortField(sortField)

        }
        binding.sortAscSwitch.setOnCheckedChangeListener { _, isChecked ->
            modulosViewModel.setSortAsc(isChecked)
        }
    }

    private fun observarPreferencias() {
        modulosViewModel.preferencias.observe(viewLifecycleOwner) { userPreferences ->
            binding.displayIdSwitch.isChecked = userPreferences.displayId

            when (userPreferences.sortField) {
                SortFields.NAME.toString() -> binding.rbtSortName.isChecked = true
                SortFields.CREDITS.toString() -> binding.rbtSortCredits.isChecked = true
                else -> binding.rbtSortId.isChecked = true
            }

            binding.sortAscSwitch.isChecked = userPreferences.sortAsc
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}