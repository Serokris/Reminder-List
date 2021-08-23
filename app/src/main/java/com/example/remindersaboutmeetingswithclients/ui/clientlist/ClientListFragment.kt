package com.example.remindersaboutmeetingswithclients.ui.clientlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.remindersaboutmeetingswithclients.data.db.entity.Client
import com.example.remindersaboutmeetingswithclients.databinding.FragmentClientListBinding
import com.example.remindersaboutmeetingswithclients.viewmodel.ReminderViewModel
import kotlinx.coroutines.launch
import android.net.ConnectivityManager
import android.os.Build

class ClientListFragment : Fragment(), ClientListAdapter.ClientClickListener {
    private val viewModel: ReminderViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentClientListBinding.inflate(inflater)

        navController = findNavController()
        val adapter = ClientListAdapter(this, requireContext())

        binding.clientRecyclerView.adapter = adapter

        viewModel.viewModelScope.launch {
            if (isInternetConnected()) {
                val clientList = viewModel.getRandomUsers(15).clients
                adapter.submitList(clientList)
            } else {
                binding.noInternetImage.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onClientClick(client: Client) {
        navController.navigate(
            ClientListFragmentDirections.actionClientListFragmentToCreateReminderFragment(client)
        )
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val netWork = connectivityManager.activeNetwork
            netWork != null
        } else {
            val netWorkInfo = connectivityManager.activeNetworkInfo
            netWorkInfo != null && netWorkInfo.isConnected
        }
    }
}