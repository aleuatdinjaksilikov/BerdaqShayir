package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentPoetInfoBinding
import com.aleuatdinjaksilikov.berdaqshayir.presentation.ui.songs.SongInfoScreenArgs
import com.aleuatdinjaksilikov.berdaqshayir.presentation.vm.PoetInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PoetInfoFragment : Fragment(R.layout.fragment_poet_info) {

    private val binding : FragmentPoetInfoBinding by viewBinding(FragmentPoetInfoBinding::bind)
    private lateinit var viewModel:PoetInfoViewModel
    private val args : PoetInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[PoetInfoViewModel::class.java]


        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryGreen)

        lifecycleScope.launch {
            viewModel.getPoetInfo(args.id)
        }

        binding.icBackPoetInfo.setOnClickListener {
            findNavController().popBackStack()
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.getPoetInfo.onEach {
            binding.textInfo.text = Html.fromHtml(it.title, Html.FROM_HTML_MODE_COMPACT)
            Log.d("TITLE", it.title)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.messageFlow.onEach {
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            Log.d("TITLE_MESSAGE",it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlow.onEach {
            Log.d("TITLE_ERROR", it.toString())
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}