package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.data.database.Module
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemListBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class ModulesListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory(
            (activity?.application as ModuleApp).appRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        binding.composeListaModulos.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    ListaModulos(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    )
                }
            }
        }

        return binding.root
    }

    @Composable
    private fun ListaModulos(modifier: Modifier = Modifier) {
        val listaModulos = viewModel.allModules.observeAsState(emptyList())
        LazyColumn(modifier) {
            items(listaModulos.value.size) {
                RegistroModulo(modifier = Modifier.fillMaxSize(),listaModulos.value[it])
            }
        }
    }

    @Composable
    private fun RegistroModulo(modifier: Modifier = Modifier, module: Module) {
        Row(modifier){
            Text(
                text = module.id.toString(),
                modifier = Modifier.weight(1.0f),
            )
            Text(
                text = module.name,
                modifier = Modifier.weight(4.0f),
            )
            Text(
                text = module.credits.toString(),
                modifier = Modifier.weight(1.0f),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}