package com.example.questionnaire.modules.questionnaireModule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionnaire.base.Response
import com.example.questionnaire.models.Question
import com.example.questionnaire.repositories.repository.QuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor( private val repository: QuestionnaireRepository) : ViewModel() {
    private val _questionsList: MutableLiveData<Response<ArrayList<Question>>> = MutableLiveData()
    private val _areAllRequiredQuestionsBeenAnswered: MutableLiveData<Boolean> = MutableLiveData()

    val questionsList: LiveData<Response<ArrayList<Question>>> get() = _questionsList
    val areAllRequiredQuestionsBeenAnswered: LiveData<Boolean> get() = _areAllRequiredQuestionsBeenAnswered

    init {
        _areAllRequiredQuestionsBeenAnswered.value = false
        fetchQuestions()
    }

    private fun fetchQuestions() {
        _questionsList.value = Response.Loading()
        viewModelScope.launch {
            try {
                _questionsList.value = Response.Success(ArrayList(repository.getQuestions()))
            } catch (e: HttpException) {
                _questionsList.value = Response.Error(e.localizedMessage ?: "An unexpected error occurred")
            } catch (e: IOException){
                _questionsList.value = Response.Error("Couldn't reach server. Check your internet connection")
            }
        }
    }
}