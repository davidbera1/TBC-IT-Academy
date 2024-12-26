package com.example.learnandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.RecyclerviewItemBinding

class ItemDiffUtil : DiffUtil.ItemCallback<Items>() {
    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem == newItem
    }

}

class RecyclerViewAdapter : ListAdapter<Items, RecyclerViewAdapter.ItemsViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemsViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Items) {
            binding.image.setImageResource(item.image)
            binding.tvAddressName.text = item.addressName
            binding.tvAddress.text = item.address

            // edit is only clickable when checkbox is checked
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    binding.tvEdit.isClickable = true
                    binding.tvEdit.setOnClickListener {

                        // pass current adapter position to MainActivity to access it from EditAddressFragment
                        (itemView.context as MainActivity).currentPosition = adapterPosition

                        // start EditAddressFragment
                        (itemView.context as MainActivity).makeFragmentVisible(true)
                        (itemView.context as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, EditAddressFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
                else {
                    binding.tvEdit.isClickable = false
                }
            }

            // Long click listener, calls removeItem() function from MainActivity
            binding.root.setOnLongClickListener {
                (itemView.context as MainActivity).removeItem(item.id, adapterPosition)
                true
            }
        }
    }
}
