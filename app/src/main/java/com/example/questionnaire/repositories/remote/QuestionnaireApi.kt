package com.example.questionnaire.repositories.remote

import com.example.questionnaire.models.Answer
import com.example.questionnaire.models.Question
import retrofit2.http.*

interface QuestionnaireApi {

    @GET("/questions")
    suspend fun getQuestions(): List<Question>

    @Headers("Content-Type: application/json")
    @POST("/answers")
    suspend fun sendAnswers(@Body answers: ArrayList<Answer>)
}