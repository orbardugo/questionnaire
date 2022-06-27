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
    val answers: List<Answer>,
    @SerializedName("isRequired")
    val isRequired: Boolean,
    @SerializedName("correctAnswerId")
    val correctAnswerId: Int?,
) {
    fun getAnswerById(answerId: Int): Answer? {
        for(answer in answers){
            if (answer.id == answerId){
                answer.questionId = id
                return answer
            }
        }
        return null
    }

    fun getOtherAnswerId(): Answer? {
        for(answer in answers){
            if (answer.answerText.isEmpty()){
                answer.questionId = id
                return answer
            }
        }
        return null
    }

}