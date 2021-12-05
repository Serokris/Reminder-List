package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.Client
import com.example.remindersaboutmeetingswithclients.databinding.ClientListItemBinding

class ClientListAdapter(
    private val listener: ClientClickListener,
    private val clients: List<Client>
) : RecyclerView.Adapter<ClientListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientListAdapter.ViewHolder {
        return ViewHolder(
            ClientListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientListAdapter.ViewHolder, position: Int) {
        val currentItem = clients[position]
        holder.bind(currentItem)
    }

    inner class ViewHolder(
        private val binding: ClientListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var _client: Client? = null

        init {
            binding.root.setOnClickListener {
                _client?.let { client ->
                    listener.onClientClick(client)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(client: Client) {
            _client = client
            binding.apply {
                clientFullName.text =
                    "${client.fullName.firstName} ${client.fullName.lastName}"
                clientEmail.text = client.email
                Glide.with(root).load(client.picture.large).into(clientImage)
            }
        }
    }

    override fun getItemCount(): Int = clients.size

    interface ClientClickListener {
        fun onClientClick(client: Client)
    }
}