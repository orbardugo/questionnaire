package com.example.questionnaire.models

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("answerText")
    var answerText: String,

    var questionId: Int = -1
)