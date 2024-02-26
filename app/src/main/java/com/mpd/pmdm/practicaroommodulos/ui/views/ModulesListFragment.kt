package com.mpd.pmdm.practicaroommodulos.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpd.pmdm.practicaroommodulos.core.ModuleApp
import com.mpd.pmdm.practicaroommodulos.databinding.FragmentItemListBinding
import com.mpd.pmdm.practicaroommodulos.ui.adapters.ModulesRecyclerViewAdapter
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class ModulesListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ModulosViewModel by activityViewModels {
        ModulosViewModelFactory()
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

        binding.list.layoutManager = LinearLayoutManager(context)
        val adapter = ModulesRecyclerViewAdapter()
        binding.list.adapter = adapter

        //Con adaptator de tipo ListAdapter, llamamos a la función submitList
        viewModel.allModulesSorted.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }



        viewModel.preferencias.observe(viewLifecycleOwner){userPreferences ->
            val idViewVisibility = if(userPreferences.displayId) View.VISIBLE else View.GONE
            binding.cabecera.tvIdHeader.visibility = idViewVisibility
            adapter.idFieldvisibility = idViewVisibility
            adapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}