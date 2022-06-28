package com.example.questionnaire.modules.adapters

import com.example.questionnaire.models.Question

sealed class DataModel {
    data class Header(val title: String? = null) : DataModel()

    data class MultiChoiceQuestion(val question: Question) : DataModel()

    data class OpenQuestion(val question: Question) : DataModel()

    data class Submit(val title: String? = null) : DataModel()
}
