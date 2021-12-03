package com.wheel.fortune.ui.fortune.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wheel.fortune.R

import com.wheel.fortune.ui.fortune.models.Words

class WordsAdapter(val word : String?,val wordMap: HashMap<Int,String>?) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {
    //private lateinit var binding : LayoutWordsBinding

    private val diffUtil = object  : DiffUtil.ItemCallback<Words>(){

        override fun areItemsTheSame(oldItem: Words, newItem: Words): Boolean =oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Words, newItem: Words): Boolean = oldItem==newItem

    }

    val wordList = AsyncListDiffer(this,diffUtil)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val currentWord : TextView = itemView.findViewById(R.id.currentword)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):WordsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_words,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:WordsAdapter.ViewHolder, position: Int) {
        val data =wordList.currentList[position]
        Log.d("xWord",data.toString())


        holder.apply {
            currentWord.text = data.word
            if (wordMap != null) {
                Log.d("xWord","wordMap"+wordMap.toString())
                for((k,v) in wordMap ){
                    if(k == position){
                        if(v.equals(currentWord.text.toString(),true)){
                            Log.d("xWord","From Value"+v)
                            holder.currentWord.visibility = View.VISIBLE
                        }else{
                            holder.currentWord.visibility = View.INVISIBLE
                        }
                    }

                }
            }

        }


    }

    override fun getItemCount(): Int = wordList.currentList.size

}