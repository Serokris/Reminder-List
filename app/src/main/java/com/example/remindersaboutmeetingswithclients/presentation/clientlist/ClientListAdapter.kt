package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.example.remindersaboutmeetingswithclients.databinding.ClientListItemBinding

class ClientListAdapter(private val listener: ClientClickListener, private val context: Context) :
    ListAdapter<Client, ClientListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ClientListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(
        private val binding: ClientListItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(client: Client) {
            binding.apply {
                clientFullName.text = "${client.fullName.firstName} ${client.fullName.lastName}"
                clientEmail.text = client.email
                Glide.with(context).load(client.picture.large).into(clientImage)

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val currentClient = getItem(position)
                        listener.onClientClick(currentClient)
                    }
                }
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Client>() {
        override fun areContentsTheSame(oldItem: Client, newItem: Client) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Client, newItem: Client) = oldItem == newItem
    }

    interface ClientClickListener {
        fun onClientClick(client: Client)
    }
}