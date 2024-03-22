package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui.songs

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentSongInfoScreenBinding
import com.aleuatdinjaksilikov.berdaqshayir.presentation.vm.SongsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class SongInfoScreen : Fragment(R.layout.fragment_song_info_screen) {

    private val binding : FragmentSongInfoScreenBinding by viewBinding(FragmentSongInfoScreenBinding::bind)
    private lateinit var viewModel:SongsViewModel
    private val args : SongInfoScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[SongsViewModel::class.java]

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryGreen)

        binding.tvSongName.text = args.name

        binding.icBackSongInfo.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            viewModel.getSongById(args.id)
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.getSongById.onEach {
            binding.textInfo.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.messageSong.onEach {
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorSong.onEach {
            Log.d("SONG_INFO_ERROR",it.toString())
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}