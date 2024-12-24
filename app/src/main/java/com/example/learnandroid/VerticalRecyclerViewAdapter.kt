package com.example.learnandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.RecyclerviewVerticalBinding


class VerticalRecyclerViewAdapter(
    private var items: List<VerticalRecyclerViewItem>
) : RecyclerView.Adapter<VerticalRecyclerViewAdapter.ViewHolder>() {

    /** Function to be called from MainActivity to update items according to category filter.
     * Updates the items list and informs about change */
    fun updateItems(newItems: List<VerticalRecyclerViewItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvPrice.text = item.price
        holder.binding.imgPhoto.setImageResource(item.photo)
    }

    inner class ViewHolder(val binding: RecyclerviewVerticalBinding) : RecyclerView.ViewHolder(binding.root)
}