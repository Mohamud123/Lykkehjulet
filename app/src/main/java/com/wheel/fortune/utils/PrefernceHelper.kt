package com.wheel.fortune.utils

import android.content.Context
import android.content.SharedPreferences
import com.wheel.fortune.utils.Constants.Companion.ROUND
import com.wheel.fortune.utils.Constants.Companion.WORD

class PrefernceHelper {
    companion object{

        private fun preferenceHelper(context: Context): SharedPreferences = context.getSharedPreferences("default",0)


        fun setWord(context: Context, word:String){

            preferenceHelper(context)
                    .edit()
                    .putString(WORD,word)
                    .apply()

        }

        fun getWord(context: Context):String? = preferenceHelper(context).getString(WORD,null)

        fun setRound(context: Context, round:Int){
            preferenceHelper(context)
                    .edit()
                    .putInt(ROUND,round)
                    .apply()
        }

        fun getRound(context: Context):Int = preferenceHelper(context).getInt(ROUND,1)
    }
}