package com.wheel.fortune.ui.fortune.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wheel.fortune.R
import com.wheel.fortune.databinding.FragmentGameResultBinding
import com.wheel.fortune.utils.PrefernceHelper


class GameResult : Fragment() {

    private lateinit var bindings : FragmentGameResultBinding
    private lateinit var gameResult : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = GameResultArgs.fromBundle(requireArguments())
        gameResult = args.result?:"lost"


        Log.d("xGameResult", gameResult.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentGameResultBinding.inflate(layoutInflater)
        bindings.game = gameResult
        var roundNumber = PrefernceHelper.getRound(requireContext())
        if(gameResult.equals("Game Won")){
            if(roundNumber <3){
                bindings.res = "Next Round"
                bindings.playagain.text = "Next Round"
                roundNumber+=1
                PrefernceHelper.setRound(requireContext(),roundNumber)

            }else{
                bindings.res = "Game won"
                bindings.playagain.text = "Play again"
            }

        }else{
            bindings.res = "Game lost"
        }
        bindings.executePendingBindings()

        bindings.playagain.setOnClickListener {
            findNavController().popBackStack(R.id.game,true)
        }

        return bindings.root
    }


}