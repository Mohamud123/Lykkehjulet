package com.wheel.fortune.ui.fortune.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wheel.fortune.databinding.LayoutItemsBinding
import com.wheel.fortune.ui.fortune.models.Categories

class CategoryAdapter(private val onSelectCategory: OnSelectCategory ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val diffUtil = object  :DiffUtil.ItemCallback<Categories>(){
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }

    }

    val categoryList = AsyncListDiffer(this,diffUtil)
    private lateinit var binding :LayoutItemsBinding
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        binding = LayoutItemsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val data = categoryList.currentList[position]

        holder.itemView.apply {
            binding.model = data
            binding.executePendingBindings()

            setOnClickListener {
                onSelectCategory.onItemClick(data)
            }
        }

    }

    override fun getItemCount(): Int = categoryList.currentList.size

    interface OnSelectCategory{
        fun onItemClick(category : Categories)
    }
}