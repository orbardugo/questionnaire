package com.example.questionnaire.utils

import android.content.res.Resources.getSystem


    fun Int.dpToPx(): Int {
        return (this * getSystem().displayMetrics.density).toInt()
    }
