package com.example.questionnaire.modules.questionnaireModule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionnaire.base.Response
import com.example.questionnaire.repositories.dto.QuestionsDto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuestionnaireViewModel : ViewModel() {
    private val _questionsList: MutableLiveData<Response<QuestionsDto>> = MutableLiveData()
    private val _areAllRequiredQuestionsBeenAnswered: MutableLiveData<Boolean> = MutableLiveData()

    val questionsList: LiveData<Response<QuestionsDto>> get() = _questionsList
    val areAllRequiredQuestionsBeenAnswered: LiveData<Boolean> get() = _areAllRequiredQuestionsBeenAnswered

    init {
        _areAllRequiredQuestionsBeenAnswered.value = false
        fetchQuestions()
    }

    private fun fetchQuestions() {
        _questionsList.value = Response.Loading()
        viewModelScope.launch {
            try {

            } catch (e: HttpException) {

            } catch (e: IOException){

            }
        }
    }
}