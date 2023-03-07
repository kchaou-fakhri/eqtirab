package com.megahed.eqtarebmenalla.feature_data.presentation.ui.quranListenerReader

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arges.sepan.argmusicplayer.Models.ArgAudio
import com.arges.sepan.argmusicplayer.Models.ArgAudioList
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.megahed.eqtarebmenalla.common.Constants
import com.megahed.eqtarebmenalla.databinding.FragmentListenerHelperBinding
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Reway
import com.megahed.eqtarebmenalla.feature_data.presentation.viewoModels.HefzVM


class ListenerHelperFragment : Fragment() {

    private lateinit var binding: FragmentListenerHelperBinding
    private lateinit var hefzVM: HefzVM
    lateinit var mediaPlayer: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListenerHelperBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        hefzVM  = HefzVM()
        var arrSura = arrayListOf<String>()
        var arrEya = arrayListOf<Int>()
        var arrEyaEnd = arrayListOf<Int>()
        var tempRewat = arrayListOf<Reway>()
        var tempSuraId = 1

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

            tempRewat.addAll(it.data)
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
                        tempSuraId = position+1
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
                        var max = Constants.SORA_OF_QURAN_WITH_NB_EYA.get(binding.listSouraName.text.toString())
                        for (i in list ..max!!){
                            arrEyaEnd.add(i)


                        }
                        binding.soraStartEndText.isEnabled = true

                        adapterEyaEnd.notifyDataSetChanged()

                    }
                }
            })

        binding.nbEyaEnd.setOnItemClickListener(
            OnItemClickListener { adapterView, view, position, id ->
                for (list in arrEyaEnd) {
                    if (list == adapterView.getItemAtPosition(position).toString().toInt()) {

                        binding.start.isEnabled = true


                    }
                }
            })




        binding.start.setOnClickListener {
            var rewayIdSelected = ""
            tempRewat.forEach {
                if (it.name.equals(binding.listOfRewat.text.toString())){
                    rewayIdSelected = it.identifier

                }
            }
            val player = ExoPlayer.Builder(requireContext()).build()


            // Build the media items.
            var audioItem: MediaItem

            hefzVM.getSuraMp3(tempSuraId ,rewayIdSelected).observe(viewLifecycleOwner, Observer {

                for(i in binding.nbAya.text.toString().toInt()-1 ..binding.nbEyaEnd.text.toString().toInt()-1){
                    for (j in 0 until binding.nbAyaRepeat.text.toString().toInt()){
//
                        audioItem = MediaItem.fromUri(it.data.ayahs.get(i).audioSecondary.get(0))

                        player.addMediaItem(audioItem)
                    }
                }
                player.prepare();
                player.play();


            })
        }


        return root
    }




}