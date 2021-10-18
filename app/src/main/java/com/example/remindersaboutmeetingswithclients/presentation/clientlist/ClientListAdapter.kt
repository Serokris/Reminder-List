package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.domain.models.Client
import com.example.remindersaboutmeetingswithclients.databinding.ClientListItemBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class ClientListAdapter(private val listener: ClientClickListener) :
    ViewBindingDelegateAdapter<Client, ClientListItemBinding>(ClientListItemBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun ClientListItemBinding.onBind(item: Client) {
        clientFullName.text = "${item.fullName.firstName} ${item.fullName.lastName}"
        clientEmail.text = item.email
        Glide.with(root).load(item.picture.large).into(clientImage)

        root.setOnClickListener {
            listener.onClientClick(item)
        }
    }

    override fun isForViewType(item: Any): Boolean = item is Client

    override fun Client.getItemId(): Any = email

    interface ClientClickListener {
        fun onClientClick(client: Client)
    }
}