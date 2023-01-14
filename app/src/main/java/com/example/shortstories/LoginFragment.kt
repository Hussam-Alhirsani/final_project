package com.example.shortstories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shortstories.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            if (checkInfo()) {
                viewModel.loginUser(
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )
            }
        }
        binding.btnSignup.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment())
                .addToBackStack("SignUpFragment")
                .commit()
        }
        viewModel.loggedIn.observe(viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
        }
    }

    private fun checkInfo() : Boolean {
        if (binding.etEmail.text.toString().trim().isEmpty()) {
            binding.etEmail.error = "Enter your email"
            return false
        } else if (binding.etPassword.text.toString().trim().isEmpty()) {
            binding.etEmail.error = "Enter your password"
            return false
        } else if (!binding.checkbox.isChecked) {
            Toast.makeText(requireContext(), "You need to accept our terms and conditions", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }
}