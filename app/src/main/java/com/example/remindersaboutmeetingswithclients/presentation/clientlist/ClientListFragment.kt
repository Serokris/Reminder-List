package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.remindersaboutmeetingswithclients.databinding.FragmentClientListBinding
import android.net.ConnectivityManager
import android.os.Build
import androidx.lifecycle.viewModelScope
import com.example.data.mappers.toClientResponse
import com.example.remindersaboutmeetingswithclients.presentation.base.BaseBindingFragment
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientListFragment :
    BaseBindingFragment<FragmentClientListBinding>(FragmentClientListBinding::inflate),
    ClientListAdapter.ClientClickListener {

    private val viewModel: ClientListViewModel by viewModels()
    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavController()
        initViews()
    }

    private fun initNavController() {
        navController = findNavController()
    }

    private fun initViews() {
        val adapter = CompositeDelegateAdapter(ClientListAdapter(this))
        binding.clientRecyclerView.adapter = adapter

        if (isInternetConnected()) {
            viewModel.viewModelScope.launch {
                val clientList = viewModel.getClientList(15)
                adapter.swapData(clientList)
            }
        } else {
            binding.noInternetImage.visibility = View.VISIBLE
        }
    }

    override fun onClientClick(client: com.example.domain.models.Client) {
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