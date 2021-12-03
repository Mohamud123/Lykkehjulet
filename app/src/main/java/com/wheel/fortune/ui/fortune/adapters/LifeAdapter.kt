package com.wheel.fortune.ui.fortune.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wheel.fortune.databinding.LayoutLifeBinding
import com.wheel.fortune.ui.fortune.models.LifeModel

class LifeAdapter : RecyclerView.Adapter<LifeAdapter.ViewHolder>() {

    private val diffUtil = object  : DiffUtil.ItemCallback<LifeModel>(){

        override fun areItemsTheSame(oldItem: LifeModel, newItem:LifeModel): Boolean =oldItem.life == newItem.life

        override fun areContentsTheSame(oldItem: LifeModel, newItem: LifeModel): Boolean = oldItem==newItem

    }

    val lifeList = AsyncListDiffer(this,diffUtil)
    private lateinit var binding : LayoutLifeBinding
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):LifeAdapter.ViewHolder {
        binding = LayoutLifeBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: LifeAdapter.ViewHolder, position: Int) {
        val data =lifeList.currentList[position]



    }

    override fun getItemCount(): Int = lifeList.currentList.size

}