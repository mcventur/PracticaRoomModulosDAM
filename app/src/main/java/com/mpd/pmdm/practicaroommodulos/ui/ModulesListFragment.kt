package com.mpd.pmdm.practicaroommodulos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpd.pmdm.practicaroommodulos.ModulesApp
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

        binding.list.layoutManager = LinearLayoutManager(context)
        val adapter = ModulesRecyclerViewAdapter()
        binding.list.adapter = adapter

        viewModel.allModules.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}