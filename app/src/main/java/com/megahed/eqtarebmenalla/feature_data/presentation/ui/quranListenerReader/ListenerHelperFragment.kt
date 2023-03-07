package com.megahed.eqtarebmenalla.feature_data.presentation.ui.quranListenerReader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.megahed.eqtarebmenalla.common.Constants
import com.megahed.eqtarebmenalla.databinding.FragmentListenerHelperBinding
import com.megahed.eqtarebmenalla.feature_data.presentation.viewoModels.HefzVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListenerHelperFragment : Fragment() {

    private lateinit var binding: FragmentListenerHelperBinding
    private lateinit var hefzVM: HefzVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListenerHelperBinding.inflate(inflater, container, false)
        val root: View = binding.root

          hefzVM  = HefzVM()
        var arrSura = arrayListOf<String>()
        var arrEya = arrayListOf<Int>()
        var arrEyaEnd = arrayListOf<Int>()

        val adapterSura = ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, arrSura)

        var adapterEya =  ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, arrEya)
        binding.nbAya.setAdapter(adapterEya)

        var adapterEyaEnd =  ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, arrEyaEnd)
        binding.nbEyaEnd.setAdapter(adapterEyaEnd)



        // Display list of rewat
        hefzVM.getAllRewat().observe(viewLifecycleOwner, Observer {

                var arrRewat = arrayListOf<String>()
                 it.data.forEach{
                     if (it.language.equals("ar")){
                         arrRewat.add(it.name)

                     }
                }
                val adapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, arrRewat)

                binding.listOfRewat.setAdapter(adapter)
            binding.listOfRewat.setOnItemClickListener(
                OnItemClickListener { adapterView, view, position, id ->
                    for (list in arrRewat) {
                        if (list.toString().equals(adapterView.getItemAtPosition(position))) {
                            binding.soraStartSpinner.isEnabled = true


                        }
                    }
                })

        })



        // get list  of suar and convert to list of string
            Constants.SORA_OF_QURAN_WITH_NB_EYA.forEach {
                arrSura.add(it.key)
            }

        // display list of suar



        binding.listSouraName.setAdapter(adapterSura)

        binding.listSouraName.setOnItemClickListener(
            OnItemClickListener { adapterView, view, position, id ->
                for (list in arrSura) {
                    if (list.toString().equals(adapterView.getItemAtPosition(position))) {
                        arrEya.clear()
                        for (i in 1..Constants.SORA_OF_QURAN_WITH_NB_EYA.get(list.toString())!!){
                            arrEya.add(i)

                        }
                        binding.soraStartEditText.isEnabled = true
                        adapterEya.notifyDataSetChanged()

                    }
                }
            })



        binding.nbAya.setOnItemClickListener(
            OnItemClickListener { adapterView, view, position, id ->
                for (list in arrEya) {
                    if (list == adapterView.getItemAtPosition(position).toString().toInt()) {
                        arrEyaEnd.clear()
                        Toast.makeText(requireContext(), list.toString(), Toast.LENGTH_LONG).show()
            var max = Constants.SORA_OF_QURAN_WITH_NB_EYA.get(binding.listSouraName.text.toString())
                        for (i in list+1 ..max!!){
                            arrEyaEnd.add(i)


                        }
                        binding.soraStartEndText.isEnabled = true

                        adapterEyaEnd.notifyDataSetChanged()

                    }
                }
            })






        return root
    }




}