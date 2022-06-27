package com.example.questionnaire.modules.interfaces

import com.example.questionnaire.models.Answer

interface QuestionAnsweredListener {
    fun questionAnswered(answer: Answer, isQuestionRequired: Boolean, questionPosition: Int)

    fun removeAnswer(isQuestionRequired: Boolean, questionPosition: Int)

    fun onSubmit()
}