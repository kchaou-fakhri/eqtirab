package com.megahed.eqtarebmenalla.feature_data.presentation.ui.quranListenerReader

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.megahed.eqtarebmenalla.R
import com.megahed.eqtarebmenalla.databinding.FragmentListenerHelperBinding
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Reway
import com.megahed.eqtarebmenalla.feature_data.presentation.viewoModels.HefzVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListenerHelperFragment : Fragment() {

    private lateinit var binding: FragmentListenerHelperBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListenerHelperBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val hefzVM  = HefzVM()

            hefzVM.getAllRewat().observe(viewLifecycleOwner, Observer {

                var arr = arrayListOf<String>()
                 it.data.forEach{
                     if (it.language.equals("ar")){
                         arr.add(it.name)

                     }
                }
                val adapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, arr)

                binding.listOfRewat.setAdapter(adapter)
            })




        return root
    }




}