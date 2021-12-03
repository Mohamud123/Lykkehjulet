package com.wheel.fortune.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.wheel.fortune.R

@BindingAdapter("gameresult")
fun resultImg(view:ImageView, result:String){
    Log.d("xResult",result)
    if(result == "Game Won"){
        view.setImageResource(R.drawable.youwon)
    }else{
        view.setImageResource(R.drawable.youlost)
    }

}