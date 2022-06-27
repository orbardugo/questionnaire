package com.example.questionnaire.modules.questionnaire_module.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionnaire.base.Response
import com.example.questionnaire.models.Answer
import com.example.questionnaire.models.Question
import com.example.questionnaire.modules.adapters.DataModel
import com.example.questionnaire.repositories.repository.QuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor( private val repository: QuestionnaireRepository) : ViewModel() {
    private val _questionsList: MutableLiveData<Response<ArrayList<Question>>> = MutableLiveData()
    private val _sendAnswersState: MutableLiveData<Response<Boolean>> = MutableLiveData()

    val questionsList: LiveData<Response<ArrayList<Question>>> get() = _questionsList
    val sendAnswersState: LiveData<Response<Boolean>> get() = _sendAnswersState

    init {
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

    fun sendAnswers(answers: ArrayList<Answer>) {
        _sendAnswersState.value = Response.Loading()
        viewModelScope.launch {
            try {
                repository.sendAnswers(answers)
                _sendAnswersState.value =  Response.Success(true)
            } catch (e: HttpException) {
                _sendAnswersState.value = Response.Error( e.localizedMessage ?: "An unexpected error occurred")
            } catch (e: IOException) {
                _sendAnswersState.value = Response.Error( "Couldn't reach server. Check your internet connection")
            }
        }
    }

    fun resetForm() {
        fetchQuestions()
    }

    fun transformArrayListOfQuestionsToDataModelList(arraylist: ArrayList<Question>) : List<DataModel> {
        return arraylist.map { question ->
            when (question.questionType) {
                1 -> DataModel.MultiChoiceQuestion(question)
                else -> {
                    DataModel.OpenQuestion(question)
                }
            }
        }
    }

    fun getNumOfRequiredQuestions(questions: ArrayList<Question>): Int {
        var counter = 0;
        for (question in questions) {
            if(question.isRequired){
                counter++
            }
        }
        return counter
    }
}