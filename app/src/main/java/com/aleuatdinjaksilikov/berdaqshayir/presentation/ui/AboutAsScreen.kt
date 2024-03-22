package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.MediaController
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.databinding.FragmentAboutAsScreenBinding


class AboutAsScreen : Fragment(R.layout.fragment_about_as_screen) {

    private val binding: FragmentAboutAsScreenBinding by viewBinding(FragmentAboutAsScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryGreen)
        binding.scroolView.smoothScrollTo(0,0)

        val mediaControllerV1 = MediaController(requireContext())
        mediaControllerV1.setAnchorView(binding.video1)

        val mediaControllerV2 = MediaController(requireContext())
        mediaControllerV1.setAnchorView(binding.video2)

        val uri_v1 = Uri.parse("android.resource://${activity?.packageName}/${R.raw.v1}")
        val uri_v2 = Uri.parse("android.resource://${activity?.packageName}/${R.raw.v2_2}")

        binding.video1.setMediaController(mediaControllerV1)
        binding.video1.setVideoURI(uri_v1)

        binding.video2.setMediaController(mediaControllerV2)
        binding.video2.setVideoURI(uri_v2)

        binding.icBackAboutAs.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}