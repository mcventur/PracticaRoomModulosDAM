package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentEditModuleBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory
import kotlinx.coroutines.launch


class EditModuleFragment : Fragment() {
    private var _binding: FragmentEditModuleBinding? = null
    private val binding get() = _binding!!

    private val args: EditModuleFragmentArgs by navArgs()

    private val modulosViewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory()
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
        //Si fuese 0, ya se maneja la lógica en el viewModel
        val cicloId = args.cicloId
        val moduleId = args.moduleId


        registraMandatoryFields()

        //Si venimos del listado de módulos de un ciclo, ocultamos su campo. (Entendemos que el alta es para ese ciclo)
        if (cicloId != 0L) {
            binding.layoutCiclosList.visibility = View.GONE
        }

        //Si estamos editando un módulo existente, cargamos sus datos
        if (moduleId != 0L) {
            cargarDatosModulo(moduleId)
        }

        binding.btnAddModule.setOnClickListener {
            guardaModulo(cicloId, moduleId)
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

    private fun cargarDatosModulo(moduleId: Long) {
        modulosViewModel.getModule(moduleId).observe(viewLifecycleOwner) { modulo ->
            binding.editModuleName.setText(modulo.name)
            //Observar algo dentro de un observador. Funcionará??
            modulosViewModel.getCiclo(modulo.cicloId).observe(viewLifecycleOwner) { ciclo ->
                binding.editModuleCiclo.setText(ciclo.name)
            }
            binding.editModuleCredits.setText(modulo.credits.toString())
            binding.editModuleAbreviatura.setText(modulo.abreviatura)
            binding.editModuleCurso.setText(modulo.curso.toString())
        }
    }

    private fun guardaModulo(cicloId: Long, moduleId: Long) {
        if(datosValidos()){
            if (moduleId != 0L) {//ACTUALIZACIÓN/UPDATE
                modulosViewModel.updateModulo(
                    moduleId,
                    binding.editModuleName.text.toString(),
                    binding.editModuleCredits.text.toString().toByte(),
                    cicloId,
                    binding.editModuleCurso.text.toString().toByte(),
                    binding.editModuleAbreviatura.text.toString()
                )
            } else {//ALTA/INSERT
                //Lo hacemos así para comprobar que se puede recuperar el id insertado
                lifecycleScope.launch {
                    val id = modulosViewModel.insertModule(
                        cicloId,
                        binding.editModuleName.text.toString(),
                        binding.editModuleCredits.text.toString().toByte(),
                        binding.editModuleCurso.text.toString().toByte(),
                        binding.editModuleAbreviatura.text.toString()
                    )
                    Log.d("AddModuleFragment", "Id insertado: $id")
                }

            }
        }
    }

    private fun registraMandatoryFields() {
        mandatoryFields = mapOf(
            binding.editModuleName to binding.layoutModuleName,
            binding.editModuleAbreviatura to binding.layoutModuleAbreviatura,
            binding.editModuleCredits to binding.layoutModuleCredits,
            binding.editModuleCurso to binding.layoutModuleCurso,
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