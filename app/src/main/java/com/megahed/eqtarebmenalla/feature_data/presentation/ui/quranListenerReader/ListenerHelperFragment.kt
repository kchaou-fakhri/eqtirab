package com.megahed.eqtarebmenalla.feature_data.presentation.ui.quranListenerReader

import android.app.Dialog
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.megahed.eqtarebmenalla.R
import com.megahed.eqtarebmenalla.common.Constants
import com.megahed.eqtarebmenalla.databinding.FragmentListenerHelperBinding
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Ayah
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Eya
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Reway
import com.megahed.eqtarebmenalla.feature_data.presentation.viewoModels.HefzVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListenerHelperFragment : Fragment() {

    private lateinit var binding: FragmentListenerHelperBinding
    private lateinit var hefzVM: HefzVM
    private lateinit var dialogBox: Dialog
    lateinit var  player : ExoPlayer
    var arrEytMP3 = arrayListOf<Eya>()
    lateinit var ayetAdapter : AyetAdapter

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
                        binding.nbEyaEnd.isEnabled = false
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


        binding.stop.setOnClickListener {
            binding.start.visibility = View.VISIBLE
            binding.stop.visibility = View.GONE

            player.stop()
        }




        binding.start.setOnClickListener {
            var rewayIdSelected = ""
            tempRewat.forEach {
                if (it.name.equals(binding.listOfRewat.text.toString())){
                    rewayIdSelected = it.identifier

                }
            }

            hefzVM.getSuraMp3(tempSuraId ,rewayIdSelected).observe(viewLifecycleOwner, Observer {

                this.dialogBox = Dialog(requireContext(),android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
                this.dialogBox.setContentView(R.layout.ayet_alert)
                val window: Window = dialogBox.getWindow()!!
                val wlp = window.attributes
                wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
                window.attributes = wlp
                val alert : ConstraintLayout = this.dialogBox.findViewById(R.id.alert_constraint)
                alert.setBackgroundColor(Color.parseColor("#ffffff"))
                val btnStop: TextView = this.dialogBox.findViewById(R.id.stop)

                 ayetAdapter = AyetAdapter(this.requireContext(), arrEytMP3)
                val rcAyet: RecyclerView = this.dialogBox.findViewById(R.id.re_ayet)
                rcAyet.layoutManager = LinearLayoutManager(this.requireContext())
                rcAyet.adapter = ayetAdapter


                dialogBox.show()
                binding.start.visibility = View.GONE
                binding.stop.visibility = View.VISIBLE
                btnStop.setOnClickListener {
                    dialogBox.dismiss()
                    player.stop()
                }

//                rcAyet.adapter = ayetAdapter
              ///  for(i in 0..binding.suraRepeat.text.toString().toString().toInt()-1){
                    soundRepeat(it.data.ayahs,
                        binding.nbAya.text.toString().toInt()-1,
                        binding.nbEyaEnd.text.toString().toInt()-1,
                        binding.nbAyaRepeat.text.toString().toInt(),
                        binding.suraRepeat.text.toString().toString().toInt()-1,)
            //    }

            })
        }


        return root
    }


    fun soundRepeat(sura: List<Ayah>, nbAya: Int, abAyaEnd: Int, nbRepeat: Int, suraRepeat: Int){
        player = ExoPlayer.Builder(requireContext()).build()

        arrEytMP3.clear()
        // Build the media items.
        var audioItem: MediaItem
for (r in 0..suraRepeat){


        for(i in nbAya .. abAyaEnd){
            for (j in 0 until nbRepeat){
                if(sura[i].audioSecondary.size !=0){
                    audioItem = MediaItem.fromUri(sura[i].audioSecondary.get(0))

                }else{
                    audioItem = MediaItem.fromUri(sura[i].audio)

                }


                player.addMediaItem(audioItem)
            }
        }
}

        for(i in nbAya .. abAyaEnd){
            arrEytMP3.add(Eya(sura.get(i).text, sura.get(i).numberInSurah))

        }

        player.prepare();
        player.play();
        ayetAdapter.notifyDataSetChanged()


    }





}