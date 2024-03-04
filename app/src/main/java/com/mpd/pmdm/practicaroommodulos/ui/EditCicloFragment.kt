package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentEditCicloBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory

/**
 * En esta pantalla se pueden añadir nuevos Ciclos
 * o bien editar o eliminar un ciclo existente clickado en el listado
 *
 * Se recibe el id de ciclo como SafeArg
 */
class EditCicloFragment : Fragment() {
    private var _binding: FragmentEditCicloBinding? = null
    private val binding get() = _binding!!

    private val args: EditCicloFragmentArgs by navArgs()

    private val viewModel: ModulosViewModel by activityViewModels { ModulosViewModelFactory() }

    //Será un map que relacione campos obligatorios con su TextInputLayout, para mostrar el mensaje de error si corresponde
    private lateinit var mandatoryFields: Map<TextView, TextInputLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditCicloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idCiclo = args.cicloId




        Log.d("EditCicloFragment","Id de diclo: $idCiclo")

        //Añadimos los campos obligatorios a un map con su layout, para mostrar los posibles errores
        registraMandatoryFields()

        if(idCiclo != 0){
            //Editando un ciclo ya existente. Cargamos sus datos actuales para edición
            mostrarDatosCiclo(idCiclo)
        } else{
            //Si no es una edición, ocultamos el botón borrar y el listado de módulos del ciclo
            ocultarDatosCiclo()
        }

        //Botón guardar
        binding.btnSaveCiclo.setOnClickListener {
            guardaCiclo(idCiclo)
        }
        //Como hemos definido un teclado con BOTÓN Send para el edit de Abreviatura, le asociamos un evento
        //el mismo que al botón guardar. Más info: https://stackoverflow.com/a/59758037/18250355
        binding.editCicloAbreviatura.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEND){
                guardaCiclo(idCiclo)
                true
            }
            false
        }

        //Botón eliminar
        binding.btnDeleteCiclo.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.alertDeleteTitle)
                .setMessage(R.string.alertDeleteCicloMessage)
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.confirm) { _, _ ->
                    viewModel.deleteCiclo(idCiclo.toLong())
                    informarOperacion()
                    findNavController().popBackStack()
                }
                .show()
        }


    }

    private fun ocultarDatosCiclo() {
        binding.btnDeleteCiclo.visibility = View.GONE
        binding.modulosCicloFragment.visibility = View.GONE
    }

    private fun mostrarDatosCiclo(idCiclo: Int) {
        //Mostramos los datos del ciclo en el formulario
        viewModel.getCiclo(idCiclo.toLong()).observe(viewLifecycleOwner) { ciclo ->
            binding.editCicloName.setText(ciclo.name)
            binding.editCicloAbreviatura.setText(ciclo.abreviatura)
            if(activity is MainActivity){
                val actividad = activity as MainActivity
                actividad.setTitulo(getString(R.string.edit_ciclo_title, ciclo.abreviatura))
            }
        }

        val fragmentoListaModulosCiclo = binding.modulosCicloFragment.getFragment<ModulesListFragment>()
        //Esta llamada se ejecuta antes del onViewCreated del fragmento, al hacerla desde nuestro onViewCreated
        fragmentoListaModulosCiclo.setCurrentCicloId(idCiclo.toLong())
    }

    /**
     * Pasa al viewModel la llamada para un insert o update de ciclo
     */
    private fun guardaCiclo(idCiclo: Int) {
        //Si es un alta o una actualización
        val actualizando = idCiclo != 0

        if(datosValidos()){
            //ALTA NUEVA: INSERT
            if(!actualizando){
                viewModel.insertCiclo(
                    binding.editCicloName.text.toString(),
                    binding.editCicloAbreviatura.text.toString()
                )
            } else{ //MODIFICACIÓN: UPDATE
                viewModel.updateCiclo(
                    idCiclo.toLong(),
                    binding.editCicloName.text.toString(),
                    binding.editCicloAbreviatura.text.toString()
                )
            }

            informarOperacion()

            //Si era una edición, volvemos al listado o pantalla anterior
            if(actualizando){
                findNavController().popBackStack()
            }
        }
    }

    /**
     * Limpia los campos
     */
    private fun borrarCampos() {
        binding.editCicloName.text?.clear()
        binding.editCicloAbreviatura.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Registra un map con cada edit y su correspondiente textInputLayout para cada campo obligatorio, a fin de
     * mostrar el error de campo obligatorio fácilmente
     */
    private fun registraMandatoryFields() {
        mandatoryFields = mapOf(
            binding.editCicloName to binding.layoutCicloName,
            binding.editCicloAbreviatura to binding.layoutCicloAbreviatura,
        )
    }

    /**
     * Valida los campos obligatorios y en caso de error, lo muestra
     */
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


    private fun informarOperacion() {
        //Ver https://github.com/material-components/material-components-android/blob/master/docs/components/Snackbar.md#showing-a-snackbar
        Snackbar.make(binding.root, getString(R.string.sucessfull_operation), Snackbar.LENGTH_SHORT)
            .show()
        borrarCampos()
    }
}