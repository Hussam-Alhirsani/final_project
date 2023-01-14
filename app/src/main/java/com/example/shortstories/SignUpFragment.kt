package com.example.shortstories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shortstories.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    
    lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)
        binding.btnSignup.setOnClickListener {
            if (checkInfo()) {
                viewModel.registerUser(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.email.text.toString(),
                    binding.password.text.toString(),
                )
            }
        }

        viewModel.accountCreated.observe(viewLifecycleOwner) { accountCreated ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInfo(): Boolean {
        if (binding.firstName.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_LONG).show()
            return false
        } else if (binding.firstName.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_LONG).show()
            return false
        } else if (binding.lastName.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_LONG).show()
            return false
        } else if (binding.email.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_LONG).show()
            return false
        } else if (binding.password.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_LONG).show()
            return false
        } else if (binding.password.text.toString() != binding.confirmPassword.text.toString()) {
            Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_LONG).show()
            return false
        } else {
            return true
        }
    }
}