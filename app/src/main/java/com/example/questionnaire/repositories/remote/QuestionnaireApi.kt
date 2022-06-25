package com.example.questionnaire.repositories.remote

import com.example.questionnaire.models.Answer
import com.example.questionnaire.repositories.dto.QuestionsDto
import retrofit2.http.*

interface QuestionnaireApi {

    @GET("/questions")
    suspend fun getQuestions(): QuestionsDto

    @Headers("Content-Type: application/json")
    @POST("/answers")
    suspend fun sendAnswers(@Body answers: ArrayList<Answer>)
}