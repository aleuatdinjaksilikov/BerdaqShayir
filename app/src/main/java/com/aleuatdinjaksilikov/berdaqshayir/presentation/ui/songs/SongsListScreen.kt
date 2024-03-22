package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui.songs

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentSongsListScreenBinding
import com.aleuatdinjaksilikov.berdaqshayir.presentation.ui.PoetInfoFragmentArgs
import com.aleuatdinjaksilikov.berdaqshayir.presentation.vm.PoetInfoViewModel
import com.aleuatdinjaksilikov.berdaqshayir.presentation.vm.SongsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class SongsListScreen : Fragment(R.layout.fragment_songs_list_screen) {

    private val binding: FragmentSongsListScreenBinding by viewBinding(
        FragmentSongsListScreenBinding::bind
    )
    private lateinit var viewModel: SongsViewModel
    private val adapter = SongsListScreenAdapter()
    private val args : SongsListScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[SongsViewModel::class.java]

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryGreen)

        lifecycleScope.launch {
            viewModel.getAllSongs(args.lang)
        }

        binding.icBackListScreen.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.songsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.songsList.adapter = adapter

        adapter.setOnItemClickListener {
            findNavController().navigate(
                SongsListScreenDirections.actionSongsListScreenToSongInfoScreen(
                    it.id,
                    it.lan,
                    it.name
                )
            )
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.getAllSongs.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.messageFlow.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlow.onEach {
            Log.d("LIST_ERROR", it.toString())
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}