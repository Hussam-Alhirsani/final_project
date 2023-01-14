package com.example.shortstories

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shortstories.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({

            if (FirebaseAuth.getInstance().currentUser != null) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            }

        }, 3000)
    }
}