package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentAddModuleBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddModuleFragment : Fragment() {
    private var _binding: FragmentAddModuleBinding? = null
    private val binding get() = _binding!!

    private val modulosViewModel: ModulosViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddModule.setOnClickListener {
            //TODO: Validación y control de errores
            lifecycleScope.launch {
                val id = modulosViewModel.insert(
                    binding.editModuleName.text.toString(),
                    binding.editModuleCredits.text.toString().toByte()
                )
                Log.d("AddModuleFragment", "Id insertado: $id")
                Toast.makeText(requireContext(), "El id insertado es $id", Toast.LENGTH_LONG).show()
            }

        }

        binding.btnClearList.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.alertClearTitle)
                .setMessage(R.string.alertClearMessage)
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.confirm) { _, _ ->
                    modulosViewModel.clearAll()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}