package com.mpd.pmdm.practicaroommodulos.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentSettingsBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val modulosViewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory((activity?.application as ModuleApp).appRepository)
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

        lifecycleScope.launch {
            modulosViewModel.displayIdPreference.collect {
                binding.displayIdSwitch.isChecked = it
            }

            modulosViewModel.sortField.collect{
                when(it){
                    "id" -> binding.rbtSortId.isChecked = true
                    "name" -> binding.rbtSortName.isChecked = true
                    else -> binding.rbtSortCredits.isChecked = true
                }
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val sortField = when(checkedId){
                R.id.rbtSortId -> "id"
                R.id.rbtSortName -> "name"
                else -> "credits"
            }
            modulosViewModel.setSortField(sortField)
        }

        binding.displayIdSwitch.setOnClickListener {
            modulosViewModel.setDisplayIdPreference(binding.displayIdSwitch.isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}