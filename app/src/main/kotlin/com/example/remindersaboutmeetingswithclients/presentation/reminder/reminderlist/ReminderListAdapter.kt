package com.example.remindersaboutmeetingswithclients.presentation.reminder.reminderlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.ReminderItem
import com.example.remindersaboutmeetingswithclients.databinding.ReminderListItemBinding

class ReminderListAdapter :
    ListAdapter<ReminderItem, ReminderListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ReminderListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ViewHolder(
        private val binding: ReminderListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(reminderItem: ReminderItem) {
            binding.apply {
                reminderTitle.text = reminderItem.title
                reminderDateTime.text = "${reminderItem.date}  ${reminderItem.time}"
                clientFullName.text =
                    "${reminderItem.client.fullName.firstName} ${reminderItem.client.fullName.lastName}"
                clientEmail.text = reminderItem.client.email
                Glide.with(root).load(reminderItem.client.picture.large).into(clientImage)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ReminderItem>() {
        override fun areItemsTheSame(oldItem: ReminderItem, newItem: ReminderItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ReminderItem, newItem: ReminderItem) =
            oldItem == newItem
    }
}