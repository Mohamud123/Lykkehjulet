package com.wheel.fortune.utils

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wheel.fortune.R
import com.wheel.fortune.databinding.LayoutSpinResultBinding


class AppUtils() {

    companion object{

        fun showResultDialog(context: Context, res: Int):AlertDialog{

            val binding  = LayoutSpinResultBinding.inflate(LayoutInflater.from(context))
            val dialog = MaterialAlertDialogBuilder(context)

            dialog.setView(binding.root)
            dialog.setCancelable(true)

            val box =dialog.show()

            showresult(context, res, binding.resultimg, binding.resulttext, box, binding)


            return box
        }

        private fun showresult(context: Context, res: Int, resultimg: ImageView, resulttext: TextView, dialog: AlertDialog, binding: LayoutSpinResultBinding) {


            when(res){
                1 -> {
                    resultimg.setImageResource(R.drawable.bankrpt)
                    resulttext.text = context.getString(R.string.bankrputmsg)
                }
                2 -> {
                    resultimg.setImageResource(R.drawable.close)
                    resulttext.text = context.getString(R.string.lostturn)
                }
                3 -> {
                    dialog.setCancelable(false)
                    binding.reslayout.visibility = View.GONE
                    binding.enteraword.visibility = View.VISIBLE
                    binding.saveBtn.setOnClickListener {

                        val enteredWord = binding.userInput.text.toString()
                        if(!enteredWord.isDigitsOnly()){
                            if (TextUtils.isEmpty(enteredWord)) {
                                showToastMsg(context, "Please enter a letter")
                            } else {
                                Log.d("xWord",enteredWord)
                                PrefernceHelper.setWord(context,enteredWord)
                                Log.d("xWord",PrefernceHelper.getWord(context).toString())
                                dialog.dismiss()
                            }
                        }else{
                            showToastMsg(context,"Please enter alphabets only")
                        }

                    }

                }
                4 -> {
                    resultimg.setImageResource(R.drawable.ic_heart)
                    resulttext.text = context.getString(R.string.addextralife)
                }
                5->{
                    dialog.setCancelable(false)
                    binding.reslayout.visibility = View.GONE
                    binding.enteraword.visibility = View.VISIBLE
                    val vowels : String  = "AEIOU"
                    binding.userInput.addTextChangedListener(object :TextWatcher{
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            if(!s.toString().isDigitsOnly()){
                                if(vowels.contains(s.toString(),true)){
                                    Log.d("xWord","isVowel")

                                    PrefernceHelper.setWord(context,s.toString())
                                }else{
                                    showToastMsg(context, "Please enter a vowel (AEIOU)")
                                }
                            }else{
                                showToastMsg(context,"Please enter alphabets only")
                            }

                        }

                        override fun afterTextChanged(s: Editable?) {
                        }

                    })
                    binding.saveBtn.setOnClickListener {

                        val enteredWord = binding.userInput.text.toString()

                        if (TextUtils.isEmpty(enteredWord)) {
                            showToastMsg(context, "Please enter a vowel (AEIOU)")
                        } else {
                            if(vowels.contains(enteredWord,ignoreCase = true)){
                                PrefernceHelper.setWord(context,enteredWord)
                                dialog.dismiss()
                            }else{
                                showToastMsg(context, "Please enter a vowel (AEIOU)")
                            }
                        }
                    }
                }

            }

        }
        fun showToastMsg(context: Context, msg: String){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }


}