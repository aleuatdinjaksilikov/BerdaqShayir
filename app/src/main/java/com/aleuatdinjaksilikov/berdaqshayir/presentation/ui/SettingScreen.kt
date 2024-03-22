package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.BottomSheetBinding
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentSettingScreenBinding
import com.aleuatdinjaksilikov.berdaqshayir.presentation.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale

class SettingScreen : Fragment(R.layout.fragment_setting_screen) {

    private val binding:FragmentSettingScreenBinding by viewBinding(FragmentSettingScreenBinding::bind)
    private val language = arrayListOf("Tildi saylan","Qaraqalpaq","Uzbek","English")

    private lateinit var dialog:BottomSheetDialog
    private lateinit var adapter : ChangeLanguageAdapter
    private val languageList = arrayListOf("Qaraqalpaq","Uzbek","English")
    private lateinit var LanguageRecycler : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryGreen)

        binding.btnChangeLanguage.setOnClickListener {
            showBottomSheet()
        }

        binding.icBackSetting.setOnClickListener {
            findNavController().popBackStack()
        }

        val sharedPreferences = requireActivity().getSharedPreferences("CHANGE_LANGUAGE",Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        val selectionId = if (sharedPreferences.getString("kaa","kaa") == "kaa"){
            0
        }else if (sharedPreferences.getString("uz","kaa") == "uz"){
            1
        }else{
            2
        }

        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,language)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerChangeLanguage.adapter = adapter
        binding.spinnerChangeLanguage.setSelection(0)
        binding.spinnerChangeLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                if (selectedItem == "Uzbek"){
                    LanguageManager.setLocale(requireActivity(),"uz")
                    editor.putString("lang","uz").apply()
                    restartActivity()
                }else if (selectedItem == "English"){
                    LanguageManager.setLocale(requireActivity(),"en")
                    editor.putString("lang","en").apply()
                    restartActivity()
                }else if (selectedItem == "Qaraqalpaq"){
                    LanguageManager.setLocale(requireActivity(),"kaa")
                    editor.putString("lang","kaa").apply()
                    restartActivity()
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }


    }

    private fun restartActivity(){
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context?.startActivity(intent)
    }

    private fun showBottomSheet(){
        val sharedPreferences = requireActivity().getSharedPreferences("CHANGE_LANGUAGE",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet,null)
        dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        LanguageRecycler = dialogView.findViewById(R.id.rv_change_language)
        adapter  = ChangeLanguageAdapter(languageList)
        adapter.setOnItemClickLanguage {
            Log.d("CLICKLAN",it.toString())
            when (it) {
                0 -> {
                    LanguageManager.setLocale(requireActivity(),"kaa")
                    editor.putString("lang","kaa").apply()
                    restartActivity()
                }
                1 -> {
                    LanguageManager.setLocale(requireActivity(),"uz")
                    editor.putString("lang","uz").apply()
                    restartActivity()
                }
                2 -> {
                    LanguageManager.setLocale(requireActivity(),"en")
                    editor.putString("lang","en").apply()
                    restartActivity()
                }
            }
        }
        LanguageRecycler.adapter = adapter
        dialog.show()

    }
}

object LanguageManager{
    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        context.createConfigurationContext(configuration)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}