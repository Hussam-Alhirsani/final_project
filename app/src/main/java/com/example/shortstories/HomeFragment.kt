package com.example.shortstories

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shortstories.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireActivity().setActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        adapter = CustomAdapter(parentFragmentManager)

        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(binding.toolbar)

        setupDrawer()
        drawerClicks()
        setupStories()
        binding.btnAddStory.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, AddStoryFragment())
                .addToBackStack("AddStoryFragment")
                .commit()
        }
        viewModel.storyListLiveData.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            val headerView = binding.navView.getHeaderView(0)
            val name = headerView.findViewById<TextView>(R.id.tvUsername)
            val email = headerView.findViewById<TextView>(R.id.tvEmail)
            name.text = user.firstName + " " + user.lastName
            email.text = user.email

        }
        viewModel.loggedOut.observe(viewLifecycleOwner) {
            if (it) {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            }
        }
    }

    private fun setupStories() {
        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecyclerView.adapter = adapter
    }

    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(requireActivity(),binding.drawer, binding.toolbar,R.string.open,R.string.close)
        binding.drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        binding.navView.setNavigationItemSelectedListener(this)
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_drawer, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                binding.drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> false
        }
    }

    private fun drawerClicks() {
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    binding.drawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Do you want to log out?")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.logout()
                        }
                        .setNegativeButton("No", null)
                        .create().show()
                    true
                }
                else -> true
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }
}