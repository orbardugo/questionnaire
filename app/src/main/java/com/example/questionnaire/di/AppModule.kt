package com.example.questionnaire.di


import com.example.questionnaire.constants.Constants
import com.example.questionnaire.coroutines.DefaultDispatcherProvider
import com.example.questionnaire.coroutines.DispatcherProvider
import com.example.questionnaire.repositories.remote.QuestionnaireApi
import com.example.questionnaire.repositories.repository.QuestionnaireRepository
import com.example.questionnaire.repositories.repository.QuestionnaireRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuestionnaireApi(): QuestionnaireApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionnaireApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestionnaireRepository(api: QuestionnaireApi): QuestionnaireRepository {
        return QuestionnaireRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}