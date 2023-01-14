package com.example.shortstories

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shortstories.databinding.FragmentStoryDetailsBinding


class StoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoryDetailsBinding.inflate(layoutInflater)
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = requireArguments().getString("title")
        val subtitle = requireArguments().getString("subtitle")
        val description = requireArguments().getString("description")

        binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, title)
            intent.putExtra(Intent.EXTRA_TEXT, description)
            startActivity(Intent.createChooser(intent, "Share with"))
        }

        binding.toolbarText.text = title

        binding.tvDesc.text = description
        binding.tvDesc.movementMethod = ScrollingMovementMethod()
    }
}