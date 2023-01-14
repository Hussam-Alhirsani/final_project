package com.example.shortstories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shortstories.databinding.FragmentAddStoryBinding
import com.google.firebase.auth.FirebaseAuth

class AddStoryFragment : Fragment() {

    private lateinit var binding: FragmentAddStoryBinding
    private lateinit var viewModel: AddStoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddStoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddStoryViewModel::class.java)

        setupListeners()

        viewModel.storyAdded.observe(viewLifecycleOwner) { storyAdded ->
            if (storyAdded) {
                Toast.makeText(requireContext(), "Story Added", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupListeners() {

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.trim().toString()
            val subtitle = binding.etSubTitle.text.trim().toString()
            val description = binding.etDescription.text.trim().toString()

            when {
                title.isEmpty() -> {
                    binding.etTitle.error = getString(R.string.enter_title)
                }
                subtitle.isEmpty() -> {
                    binding.etSubTitle.error = getString(R.string.enter_sub_title)
                }
                description.isEmpty() -> {
                    binding.etDescription.error = getString(R.string.enter_description)
                }
                else -> {

                    val data = mutableMapOf<String, String>()
                    data["title"] = title
                    data["subtitle"] = subtitle
                    data["description"] = description
                    data["email"] = FirebaseAuth.getInstance().currentUser?.email ?: ""

                    binding.btnAdd.isEnabled = false
                    viewModel.addStoryToDatabase(data)
                }
            }
        }
    }
}