package com.example.questionnaire.repositories.repository

import com.example.questionnaire.coroutines.DispatcherProvider
import com.example.questionnaire.models.Answer
import com.example.questionnaire.models.Question
import com.example.questionnaire.repositories.remote.QuestionnaireApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuestionnaireRepositoryImpl @Inject constructor(
    private val api: QuestionnaireApi,
    private val dispatcherProvider: DispatcherProvider,
) : QuestionnaireRepository {

    @Throws(Throwable::class)
    override suspend fun getQuestions(): List<Question> {
        return withContext(dispatcherProvider.io()) { api.getQuestions() }
    }

    @Throws(Throwable::class)
    override suspend fun sendAnswers(answers: ArrayList<Answer>) {
        return withContext(dispatcherProvider.io()) { api.sendAnswers(answers) }
    }
}