package com.mpd.pmdm.practicaroommodulos.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentSettingsBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory
import kotlinx.coroutines.launch

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
        modulosViewModel.preferencias.observe(viewLifecycleOwner){userPreferences ->
            binding.displayIdSwitch.isChecked = userPreferences.displayId
        }

        //Escribimos en el datastore si hay cambios en alguna de las preferencias
        binding.displayIdSwitch.setOnCheckedChangeListener { _, isChecked ->
            modulosViewModel.setDisplayIdPref(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}