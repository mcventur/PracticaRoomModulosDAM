package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.color.MaterialColors
import com.google.android.material.divider.MaterialDivider
import com.mpd.pmdm.practicaroommodulos.ModulesApp
import com.mpd.pmdm.practicaroommodulos.R
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

    @OptIn(ExperimentalFoundationApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            val listaModulos = viewModel.allModules.observeAsState(emptyList())
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Blue))
                    .padding(10.dp)
            ) {
                stickyHeader {
                    Cabecera()
                }
                items(listaModulos.value.size){
                    RegistroModulo(listaModulos.value, it)
                }
            }
        }
    }

    @Composable
    private fun Cabecera() {
        Surface(Modifier.height(30.dp)){
            Column{
                Row(){
                    Text(text = getString(R.string.id), Modifier.weight(1.0f))
                    Text(text = getString(R.string.name), Modifier.weight(3.0f))
                    Text(text = getString(R.string.credits), Modifier.weight(1.0f))
                }
                Divider()
            }
        }
    }

    @Composable
    private fun RegistroModulo(moduleList: List<Module>, pos: Int) {
        val bg = if(pos % 2 == 0) Color.LightGray else Color.Transparent
        val module = moduleList[pos]
        Row(Modifier.background(bg)){
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


