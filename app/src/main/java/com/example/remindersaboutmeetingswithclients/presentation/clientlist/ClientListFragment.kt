package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.data.mappers.toClientResponse
import com.example.domain.common.Result
import com.example.remindersaboutmeetingswithclients.databinding.FragmentClientListBinding
import com.example.remindersaboutmeetingswithclients.presentation.base.BaseBindingFragment
import com.example.remindersaboutmeetingswithclients.utils.hideView
import com.example.remindersaboutmeetingswithclients.utils.showView
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

        binding.apply {
            if (thereIsInternetConnection()) {
                viewModel.getClientList(15).onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            loadingClientProgressBar.hideView()
                            adapter.swapData(result.data!!)
                        }
                        is Result.Loading -> {
                            loadingClientProgressBar.showView()
                        }
                        is Result.Error -> {
                            loadingClientProgressBar.hideView()
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }.launchIn(viewModel.viewModelScope)
            } else {
                noInternetImage.showView()
            }
        }
    }

    override fun onClientClick(client: com.example.domain.models.Client) {
        navController?.navigate(
            ClientListFragmentDirections.actionClientListFragmentToCreateReminderFragment(client.toClientResponse())
        )
    }

    private fun thereIsInternetConnection(): Boolean {
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