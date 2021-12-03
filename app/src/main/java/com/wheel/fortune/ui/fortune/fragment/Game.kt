package com.wheel.fortune.ui.fortune.fragment

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget
import com.bluehomestudio.luckywheel.WheelItem
import com.wheel.fortune.R
import com.wheel.fortune.databinding.FragmentGameBinding
import com.wheel.fortune.ui.fortune.adapters.LifeAdapter
import com.wheel.fortune.ui.fortune.adapters.WordsAdapter
import com.wheel.fortune.ui.fortune.models.LifeModel
import com.wheel.fortune.ui.fortune.models.Words
import com.wheel.fortune.utils.AppUtils
import com.wheel.fortune.utils.PrefernceHelper


class Game : Fragment() {

    private lateinit var binding : FragmentGameBinding
    private lateinit var wheelItems : ArrayList<WheelItem>

    private var lifeLeft : Int = 5
    private var points : Long = 0

    private lateinit var lifeAdapter : LifeAdapter
    private lateinit var lifeList :ArrayList<LifeModel>
    private lateinit var wordAdapter : WordsAdapter

    private lateinit var wordToGuess : String
    private lateinit var charsInWord : ArrayList<Words>
    private lateinit var wordMap : HashMap<Int, String>

    private var isSpinng = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateWheelItems()
        wordMap = HashMap()
        wordToGuess = GameArgs.fromBundle(requireArguments()).word
        charsInWord = ArrayList()
        Log.d("xword ", wordToGuess.toString())

        for(i in 0 until wordToGuess.length){

            val word = Words(i, wordToGuess[i].toString())
            if(!word.equals(" ")){
                charsInWord.add(word)
            }


        }

        Log.d("xword ", charsInWord.toString())




        lifeAdapter = LifeAdapter()
        lifeList = arrayListOf<LifeModel>(
                LifeModel(1, R.drawable.ic_heart),
                LifeModel(1, R.drawable.ic_heart),
                LifeModel(1, R.drawable.ic_heart),
                LifeModel(1, R.drawable.ic_heart),
                LifeModel(1, R.drawable.ic_heart))
        lifeAdapter.lifeList.submitList(lifeList)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(layoutInflater)
        binding.wordToGuess = wordToGuess
        binding.hintToBeShown = GameArgs.fromBundle(requireArguments()).hint
        binding.executePendingBindings()
        wordAdapter = WordsAdapter(null, null)
        wordAdapter.wordList.submitList(charsInWord)
        setUpLifeRecyclerView()
        setupFortuneWheel()
        setUpWordRecyclerView(wordAdapter)

        return binding.root
    }

    private fun setUpWordRecyclerView(newWordAdapter: WordsAdapter) {
        binding.wordRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = newWordAdapter
        }

        if(wordMap.size == wordToGuess.length){
            sendToGameResult("Game Won")
        }

    }

    private fun setupFortuneWheel() {
        binding.wheel.addWheelItems(wheelItems)

        binding.spinwheel.setOnClickListener {
            spinTheWheel()



        }
    }

    private fun spinTheWheel() {
        if(!isSpinng){
            isSpinng =true
            val target = (1..4).random()
            binding.wheel.rotateWheelTo(target)
            binding.wheel.setLuckyWheelReachTheTarget(
                    OnLuckyWheelReachTheTarget {
                        showHideWheelandSpinButton(0)
                        showOptionsLayout(1)
                        decideResult(target)
                    })
        }else{
            AppUtils.showToastMsg(requireContext(), "Please wait while wheel is spinning")
        }
    }

    private fun setUpLifeRecyclerView() {
        binding.lifeRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = lifeAdapter
        }
    }

    private fun decideResult(target: Int) {
        isSpinng = false
        when(target){

            1 -> {
                val zero: Long = 0
                if (points != zero) {
                    points = 0
                    binding.points.text = points.toString()
                    showResultDialog(1)
                } else {
                    removeLife()
                }

            }
            2 -> {

                removeLife()


            }
            3 -> {

                showHideWheelandSpinButton(0)
                val word = showResultDialog(3)
                word.setOnDismissListener {
                    dismissListener(3, null)
                }

            }
            4 -> {

                if (lifeLeft != 5) {
                    lifeLeft += 1
                    Log.d("xLife", "add life target :-$target")
                    lifeList.add(lifeLeft - 1, LifeModel(lifeLeft, R.drawable.ic_heart))
                    lifeAdapter.lifeList.submitList(lifeList)
                    binding.lifeRecyclerview.adapter = lifeAdapter
                    showResultDialog(4)
                } else {
                    AppUtils.showToastMsg(requireContext(), "You already have maximum life")
                }

            }

            5 -> {

                showHideWheelandSpinButton(0)
                val word = showResultDialog(3)
                word.setOnDismissListener {
                    dismissListener(5, null)

                }

            }
            6 -> {
                showHideWheelandSpinButton(0)
                val word = showResultDialog(3)
                word.setOnDismissListener {
                    dismissListener(6, null)

                }

            }
            7 -> {

                showHideWheelandSpinButton(0)
                val word = showResultDialog(3)
                word.setOnDismissListener {
                    dismissListener(7, null)

                }

            }

        }

    }

    private fun dismissListener(res: Int, enteredWord: String?) {

        val typedWord = enteredWord?:PrefernceHelper.getWord(requireContext()).toString()
        val pointsToIncrease = pointToIncrease(res)
        Log.d("xWord", typedWord.toString())
        if(wordToGuess.contains(typedWord, true)){
            var count = 0
            if(!wordMap.containsValue(typedWord)){
                for(i in 0 until wordToGuess.length){
                    if(wordToGuess[i].toString().equals(typedWord, true)){
                        //val pos = wordToGuess.indexOf(typedWord,0,true)
                        wordMap[i] = typedWord
                        count+=1
                    }

                }
                if(res==1){
                    points -= pointsToIncrease!!
                }else{
                    points += (count * pointsToIncrease!!).toLong()
                }

                val wordsAdapter = WordsAdapter(typedWord, wordMap)
                wordsAdapter.wordList.submitList(charsInWord)
                setUpWordRecyclerView(wordsAdapter)
                binding.points.text = points.toString()
            }

        }else{
            if(res==1){
                points -= pointsToIncrease!!
            }
            binding.points.text = points.toString()
            AppUtils.showToastMsg(requireContext(), "Letter not found!! You lost a life")
            removeLife()

        }
    }

    private fun pointToIncrease(result: Int): Int? {

        when(result){
            1 -> {
                return 100
            }
            2 -> {
                return 50
            }
            3 -> {
                return 1000
            }
            5 -> {
                return 100
            }
            6 -> {
                return 250
            }
            7 -> {
                return 500
            }else->{
               return null
            }

        }

    }

    private fun showOptionsLayout(res: Int) {
        binding.apply {
            if(res==0){
                binding.mainlayout.visibility = View.GONE
            }else{
                mainlayout.visibility = View.VISIBLE
                guess.setOnClickListener {
                    if(!isSpinng){
                        showHideGuessEditText(1)
                        showOptionsLayout(0)
                    }
                    else{
                        AppUtils.showToastMsg(requireContext(), "Please wait while wheel is spinning")
                    }


                }
                spin.setOnClickListener {
                    spinTheWheel()
                }
                vowel.setOnClickListener {
                    if(!isSpinng){
                        val currentPoints=points.text.toString().toInt()
                        if(currentPoints==0 || currentPoints - 150 <=0){
                            AppUtils.showToastMsg(requireContext(), "You dont have enough points to use a vowel")
                        }else{
                            val dialog = showResultDialog(5)
                            dialog.setOnDismissListener {
                                dismissListener(1, null)

                            }
                        }

                    }
                    else{
                        AppUtils.showToastMsg(requireContext(), "Please wait while wheel is spinning")
                    }

                }

            }

        }

    }

    private fun removeLife() {
        if(lifeLeft!=1){

            lifeList.removeAt(lifeLeft - 1)
            lifeLeft-=1
            lifeAdapter.lifeList.submitList(lifeList)
            binding.lifeRecyclerview.adapter = lifeAdapter
            showResultDialog(2)
        }else{
            sendToGameResult("Game lost")
        }
    }


    private fun sendToGameResult(result: String) {
        val action = GameDirections.actionGameToGameResult()
        action.result = result

        findNavController().navigate(action)
    }

    private fun generateWheelItems() {
        wheelItems = ArrayList()
        wheelItems.add(
                WheelItem(
                        Color.parseColor("#0053F2"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.bankrpt
                ), "bankrupt"
                )
        )
        wheelItems.add(
                WheelItem(
                        Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.close
                ), "miss turn"
                )
        )

        wheelItems.add(
                WheelItem(
                        Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.add
                ), "1000+"
                )
        )
        wheelItems.add(
                WheelItem(
                        Color.parseColor("#F00E6F"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.extralife
                ), "extra turn"
                )
        )

        wheelItems.add(
                WheelItem(
                        Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.add
                ), "100+"
                )
        )

        wheelItems.add(
                WheelItem(
                        Color.parseColor("#F22400"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.add
                ), "500+"
                )
        )

        wheelItems.add(
                WheelItem(
                        Color.parseColor("#F2BD30"), BitmapFactory.decodeResource(
                        resources,
                        R.drawable.add
                ), "250+"
                )
        )


    }

    private fun showResultDialog(result: Int):AlertDialog{
        val res = AppUtils.showResultDialog(requireContext(), result)
        return res
    }

    private fun showHideWheelandSpinButton(res: Int){

            binding.apply {
                if(res==0) {

                    spinwheel.visibility = View.INVISIBLE

                }else{

                    spinwheel.visibility = View.VISIBLE
                }
            }



    }
    private fun showHideGuessEditText(res: Int){

        binding.apply {
            if(res==0){
                closeKeyboard()
                guessword.visibility = View.GONE
                showbtns.visibility = View.GONE
                guessword.text.clear()
                
            }else{
                guessword.visibility = View.VISIBLE
                showbtns.visibility = View.VISIBLE


                cancel.setOnClickListener {

                    showOptionsLayout(1)
                    showHideGuessEditText(0)
                }
                submit.setOnClickListener { 
                    val enteredWord = guessword.text.toString()
                    if(!enteredWord.isDigitsOnly()){
                        if(TextUtils.isEmpty(enteredWord)){
                            AppUtils.showToastMsg(requireContext(), "Please enter atleast a single word")
                        }else{
                            checkIfWordIsPresent(enteredWord)
                            guessword.text.clear()


                            showOptionsLayout(1)
                            showHideGuessEditText(0)
                        }
                    }else{
                        AppUtils.showToastMsg(requireContext(), "Please enter a alphabet only")
                    }

                }
             

            }
        }

    }

    private fun checkIfWordIsPresent(enteredWord: String) {
        dismissListener(2, enteredWord)

    }

    override fun onResume() {

        super.onResume()

    }


    private fun closeKeyboard() {
        val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        manager?.hideSoftInputFromWindow(binding.guessword.windowToken, 0)

    }
}