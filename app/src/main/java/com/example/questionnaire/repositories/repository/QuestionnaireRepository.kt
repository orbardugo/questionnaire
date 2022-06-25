package com.example.questionnaire.repositories.repository

import com.example.questionnaire.models.Answer
import com.example.questionnaire.models.Question

interface QuestionnaireRepository {
    suspend fun getQuestions(): List<Question>

    suspend fun sendAnswers(answers: ArrayList<Answer>)
}