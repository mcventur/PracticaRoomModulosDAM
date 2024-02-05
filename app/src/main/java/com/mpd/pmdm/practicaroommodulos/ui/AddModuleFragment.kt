package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mpd.pmdm.practicaroommodulos.ModulesApp
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentAddModuleBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModuleViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModuleViewModelFactory


class AddModuleFragment : Fragment() {
    private var _binding: FragmentAddModuleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ModuleViewModel by activityViewModels {
        ModuleViewModelFactory(
            (activity?.application as ModulesApp).appRepository
        )
    }

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
            if(datosIntroducidos()){
                try {
                    viewModel.insert(
                        binding.editModuleName.text.toString(),
                        binding.editModuleCredits.text.toString().toByte()
                    )
                }catch (e: Exception){
                    Log.e("AddModuleFragment", e.message?:"")
                }

                limpiarLoEscrito()
                mostrarToast(getString(R.string.saved_module_message))
            }
        }

        binding.btnClearList.setOnClickListener {
            //Creamos un cuadro de diálogo para confirmar la operación
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_message))
                .setNegativeButton(getString(R.string.cancel)){
                    dialog,_ -> dialog.cancel()
                }
                .setPositiveButton(getString(R.string.confirm)){
                    _,_ -> viewModel.clearAll()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun datosIntroducidos(): Boolean {
        if(binding.editModuleName.text?.isEmpty() ?: true){
            binding.editModuleName.error = "Obligatorio"
            return false
        }
        if(binding.editModuleCredits.text?.isEmpty() ?: true){
            binding.editModuleCredits.error = "Obligatorio"
            return false
        }

        return true
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(activity, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarLoEscrito() {
        binding.editModuleName.text?.clear()
        binding.editModuleCredits.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}