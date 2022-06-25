package com.example.questionnaire.models

import com.google.gson.annotations.SerializedName

data class Question (
    @SerializedName("id")
    val id: Int,
    @SerializedName("questionType")
    val questionType: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("answers")
    val answers: ArrayList<Answer>,
    @SerializedName("isRequired")
    val isRequired: Boolean,
    @SerializedName("correctAnswerId")
    val correctAnswerId: Int?,
)