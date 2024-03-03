package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentEditModuleBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory
import kotlinx.coroutines.launch


class EditModuleFragment : Fragment() {
    private var _binding: FragmentEditModuleBinding? = null
    private val binding get() = _binding!!

    private val modulosViewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory((activity?.application as ModuleApp).appRepository)
    }

    //Mapa de campos obligatorios con sus layouts
    private lateinit var mandatoryFields: Map<TextView, TextInputLayout>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registraMandatoryFields()

        binding.btnAddModule.setOnClickListener {
            if (datosValidos()) {
                lifecycleScope.launch {
                    val id = modulosViewModel.insert(
                        binding.editModuleName.text.toString(),
                        binding.editModuleCredits.text.toString().toByte()
                    )

                    Log.d("AddModuleFragment", "Id insertado: $id")
                    Toast.makeText(requireContext(), "El id insertado es $id", Toast.LENGTH_LONG)
                        .show()
                }
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

        //Seteamos los datos del selector de Ciclos
        modulosViewModel.allCiclosName.observe(viewLifecycleOwner) {

            val adapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_ciclo, it)
            binding.editModuleCiclo.setAdapter(adapter)
            binding.editModuleCiclo.setOnItemClickListener { _, _, position, id ->
                modulosViewModel.updateSelectedCiclo(adapter.getItem(position))
            }
        }


    }

    private fun registraMandatoryFields() {
        mandatoryFields = mapOf(
            binding.editModuleName to binding.layoutModuleName,
            binding.editModuleCredits to binding.layoutModuleCredits,
            binding.editModuleCiclo to binding.layoutCiclosList,
        )
    }


    private fun datosValidos(): Boolean {
        var okValues = true
        if (this::mandatoryFields.isInitialized) {
            mandatoryFields.forEach { (edit, layout) ->
                if (TextUtils.isEmpty(edit.text)) {
                    layout.error = getString(R.string.error_mandatory)
                    okValues = false
                } else layout.error = null
            }
        }
        return okValues
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}