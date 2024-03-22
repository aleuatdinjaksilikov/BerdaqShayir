package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding : FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("CHANGE_LANGUAGE",Context.MODE_PRIVATE)
        val lang = when(sharedPreferences.getString("lang","kaa").toString()){
            "uz"->{
                2
            }
            "en"->{
                1
            }
            else-> {
                3
            }
        }

        binding.btnPoetInfo.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToPoetInfoFragment(lang))
        }

        binding.btnSongsList.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSongsListScreen(sharedPreferences.getString("lang","kaa").toString()))
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingScreen())
        }

        binding.btnAboutAs.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAboutAsScreen())
        }
    }
}