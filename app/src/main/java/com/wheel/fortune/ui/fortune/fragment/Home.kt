package com.wheel.fortune.ui.fortune.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wheel.fortune.databinding.FragmentHomeBinding
import com.wheel.fortune.ui.fortune.adapters.CategoryAdapter
import com.wheel.fortune.ui.fortune.models.Categories
import com.wheel.fortune.ui.fortune.models.GuessWord


class Home : Fragment(),CategoryAdapter.OnSelectCategory {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var binding : FragmentHomeBinding
    private lateinit var categoryList : ArrayList<Categories>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter = CategoryAdapter(this)
        categoryList = arrayListOf(
                Categories(1,"Person", listOf<GuessWord>(GuessWord("Obama","Former president of USA"))),
                Categories(2,"Food", listOf<GuessWord>(GuessWord("Spaghetti","Long,thin, solid, cylindrical pasta"),GuessWord("Chocolate","Dessert!!"))),
                Categories(3,"Sport", listOf<GuessWord>(GuessWord("Football","Sport"))),
                Categories(4,"Subject", listOf<GuessWord>(GuessWord("Mathematics","Numbers and algebra"))),
                Categories(5,"Animal", listOf<GuessWord>(GuessWord("Lion","Mufassa"))),

        )
        categoryAdapter.categoryList.submitList(categoryList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(layoutInflater)
        sendToHomeScreen()
        //setUpRecyclerView()


        return binding.root
    }

    private fun setUpRecyclerView() {

        binding.recyclrview.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
        }

    }

    private fun sendToHomeScreen(){
        val action = HomeDirections.actionHome2ToGame()
        val category = (0..categoryList.size-1).random()
        val getCategory = categoryList.get(category)
        Log.d("xWord",categoryList.toString())
        val getWord = let{
            if(getCategory.word.size>1){
                val getWord = (0..getCategory.word.size-1).random()
                action.hint = getCategory.word.get(getWord).hint
                return@let getCategory.word.get(getWord).word
            }else{
                action.hint = getCategory.word.get(0).hint
                return@let getCategory.word.get(0).word
            }
        }

        action.word = getWord
        findNavController().navigate(action)

    }
    override fun onItemClick(category: Categories) {





    }


}