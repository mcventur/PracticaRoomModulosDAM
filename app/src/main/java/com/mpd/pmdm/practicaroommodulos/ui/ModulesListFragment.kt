package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mpd.pmdm.practicaroommodulos.ModulesApp
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemListBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModuleViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModuleViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class ModulesListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
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
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            val listaModulos = viewModel.allModules.observeAsState(emptyList())
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(listaModulos.value.size){
                    RegistroModulo(listaModulos.value[it])
                }
            }
        }
    }

    @Composable
    private fun RegistroModulo(module: Module) {
        Row{
            Text(text = module.id.toString(), Modifier.weight(1.0f))
            Text(text = module.name, Modifier.weight(3.0f))
            Text(text = module.credits.toString(), Modifier.weight(1.0f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}