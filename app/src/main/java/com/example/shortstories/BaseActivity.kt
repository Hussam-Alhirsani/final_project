package com.example.shortstories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shortstories.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private var binding: ActivityBaseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SplashFragment())
            .commit()
    }
}