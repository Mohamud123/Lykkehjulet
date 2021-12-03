package com.wheel.fortune.ui.fortune.models

import android.view.View
import android.widget.TextView

data class Words(
        val id : Int,
        val word : String,


){
   /* fun showHideWord(view : TextView,wordTyped: String?){

        if(wordTyped!=null&& wordsMap!=null) {
            for ((k, v) in wordsMap) {
                if (v.equals(this.word, true)) {

                    view.visibility = View.VISIBLE
                } else {
                    view.visibility = View.INVISIBLE
                }

            }
        }
    }*/
}
