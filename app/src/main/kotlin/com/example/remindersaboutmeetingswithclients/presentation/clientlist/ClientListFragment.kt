package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.data.mappers.toClientResponse
import com.example.remindersaboutmeetingswithclients.databinding.FragmentClientListBinding
import com.example.remindersaboutmeetingswithclients.presentation.base.BaseBindingFragment
import com.example.remindersaboutmeetingswithclients.utils.appComponent
import com.example.remindersaboutmeetingswithclients.utils.hideView
import com.example.remindersaboutmeetingswithclients.utils.showView

class ClientListFragment :
    BaseBindingFragment<FragmentClientListBinding>(FragmentClientListBinding::inflate),
    ClientListAdapter.ClientClickListener {

    private val viewModel: ClientListViewModel by viewModels {
        appComponent.viewModelFactory()
    }
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
    }

    private fun subscribeUi() {
        binding.apply {
            viewModel.dataLoading.observe(viewLifecycleOwner, { isLoading ->
                if (isLoading) loadingProgressBar.showView()
                else loadingProgressBar.hideView()
            })

            viewModel.clientsList.observe(viewLifecycleOwner, { clientList ->
                if (clientList.isNotEmpty()) {
                    val adapter = ClientListAdapter(this@ClientListFragment, clientList)
                    clientRecyclerView.setHasFixedSize(true)
                    clientRecyclerView.adapter = adapter
                }
            })

            viewModel.errorMessage.observe(viewLifecycleOwner, { errorMessage ->
                if (errorMessage.isNotEmpty()) {
                    Toast.makeText(
                        root.context, errorMessage, Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    override fun onClientClick(client: com.example.domain.models.Client) {
        navController.navigate(
            ClientListFragmentDirections.actionClientListFragmentToCreateReminderFragment(client.toClientResponse())
        )
    }
}