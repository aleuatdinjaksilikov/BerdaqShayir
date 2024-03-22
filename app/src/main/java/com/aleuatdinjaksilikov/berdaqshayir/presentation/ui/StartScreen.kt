package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui


import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentStartScreenBinding

class StartScreen : Fragment(R.layout.fragment_start_screen) {

    private val binding : FragmentStartScreenBinding by viewBinding(FragmentStartScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("CHANGE_LANGUAGE", Context.MODE_PRIVATE)
        Log.d("LANG",sharedPreferences.getString("lang","").toString())
        if(sharedPreferences.getString("lang","").toString()=="kaa"){
            LanguageManager.setLocale(requireActivity(),"kaa")
            binding.tvAppName.text = "Berdaq shayir \nQosiqlar toplami"
            binding.buttonStart.text = "Baslaw"
        }else if (sharedPreferences.getString("lang","").toString()=="uz"){
            LanguageManager.setLocale(requireActivity(),"uz")
            binding.tvAppName.text = "Berdaq shayir \nShe’rlar to’plami"
            binding.buttonStart.text = "Boshlash"
        }else if (sharedPreferences.getString("lang","").toString()=="en"){
            LanguageManager.setLocale(requireActivity(),"en")
            binding.tvAppName.text = "Musical composition by \nthe poet Berdakh"
            binding.buttonStart.text = "Lets go …"
        }

        binding.buttonStart.setOnClickListener {
            findNavController().navigate(StartScreenDirections.actionStartScreenToMainFragment())
        }

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryWhite)
    }
}