package com.example.questionnaire.repositories.repository

import com.example.questionnaire.models.Answer
import com.example.questionnaire.models.Question
import com.example.questionnaire.repositories.remote.QuestionnaireApi
import javax.inject.Inject

class QuestionnaireRepositoryImpl @Inject constructor(private val api: QuestionnaireApi): QuestionnaireRepository {

    override suspend fun getQuestions(): List<Question> {
        return api.getQuestions().questions
    }

    override suspend fun sendAnswers(answers: ArrayList<Answer>) {
        return api.sendAnswers(answers)
    }
}