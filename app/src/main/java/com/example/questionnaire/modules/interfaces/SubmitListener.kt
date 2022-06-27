package com.example.questionnaire.modules.interfaces

import com.example.questionnaire.models.Answer

interface SubmitListener {
    fun onSubmit(answerList: ArrayList<Answer>)

    fun onNotAllRequiredQuestionsAnswered()
}