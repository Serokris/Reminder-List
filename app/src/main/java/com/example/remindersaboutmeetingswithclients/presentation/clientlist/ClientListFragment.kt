package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.example.remindersaboutmeetingswithclients.databinding.FragmentClientListBinding
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.data.mappers.toClientResponse
import com.example.remindersaboutmeetingswithclients.data.source.remote.RandomUserApiService
import com.example.remindersaboutmeetingswithclients.presentation.base.BaseBindingFragment
import com.example.remindersaboutmeetingswithclients.utils.constants.NetworkConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class ClientListFragment :
    BaseBindingFragment<FragmentClientListBinding>(FragmentClientListBinding::inflate),
    ClientListAdapter.ClientClickListener {

    private val viewModel: ClientListViewModel by viewModels()
    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        val adapter = ClientListAdapter(this, requireContext())

        binding.clientRecyclerView.adapter = adapter

        if (isInternetConnected()) {
            viewModel.viewModelScope.launch {
                val clientList = viewModel.getClientList(15)
                adapter.submitList(clientList)
            }
        } else {
            binding.noInternetImage.visibility = View.VISIBLE
        }
    }

    override fun onClientClick(client: Client) {
        navController?.navigate(
            ClientListFragmentDirections.actionClientListFragmentToCreateReminderFragment(client.toClientResponse())
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