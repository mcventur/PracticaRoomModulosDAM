package com.mpd.pmdm.practicaroommodulos.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mpd.pmdm.practicaroommodulos.R
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

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ListaModulos(modifier: Modifier = Modifier) {
        val listaModulos = viewModel.allModules.observeAsState(emptyList())
        val displayId = viewModel.getDisplayIdPreference().collectAsState(initial = true)

        LazyColumn(modifier) {
            stickyHeader {
                CabeceraListaModulos(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(bottom = 10.dp), displayId
                )
            }
            items(listaModulos.value.size) {
                RegistroModulo(modifier = Modifier.fillMaxSize(),listaModulos.value[it], displayId)
            }
        }
    }

    @Composable
    private fun CabeceraListaModulos(modifier: Modifier = Modifier, displayId: State<Boolean>) {
        Surface(modifier){
            Column {
                Row {
                    if(displayId.value){
                        Text(
                            text = getString(R.string.id),
                            modifier = Modifier.weight(1.0f),
                        )
                    }
                    Text(
                        text = getString(R.string.name),
                        modifier = Modifier.weight(3.0f),
                    )
                    Text(
                        text = getString(R.string.credits),
                        modifier = Modifier.weight(1.0f),
                    )
                }
                Divider()

            }
        }
    }

    @Composable
    private fun RegistroModulo(modifier: Modifier = Modifier, module: Module, displayId: State<Boolean>) {
        Row(modifier){
            if(displayId.value){
                Text(
                    text = module.id.toString(),
                    modifier = Modifier.weight(1.0f),
                )
            }
            Text(
                text = module.name,
                modifier = Modifier.weight(3.0f),
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