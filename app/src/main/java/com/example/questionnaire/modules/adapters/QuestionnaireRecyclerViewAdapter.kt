package com.example.questionnaire.modules.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.questionnaire.R
import com.example.questionnaire.models.Answer
import com.example.questionnaire.modules.interfaces.QuestionAnsweredListener
import com.example.questionnaire.modules.interfaces.SubmitListener

class QuestionnaireRecyclerViewAdapter() : RecyclerView.Adapter<QuestionViewHolder>(),
    QuestionAnsweredListener {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MULTI_CHOICE = 1
        private const val TYPE_OPEN = 2
        private const val TYPE_SUBMIT = 3
    }

    private val adapterData = mutableListOf<DataModel>()
    private val answersData: HashMap<Int, Answer> = hashMapOf()
    private var numOfRequiredQuestions: Int = -1
    private var requiredQuestionAnsweredCounter: Int = 0
    private lateinit var submitListener: SubmitListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuestionViewHolder {

        val layout = when (viewType) {
            TYPE_HEADER -> R.layout.title_card_item
            TYPE_MULTI_CHOICE -> R.layout.multi_choice_question_card_item
            TYPE_OPEN -> R.layout.open_question_card_item
            TYPE_SUBMIT -> R.layout.submit_item
            else -> throw IllegalArgumentException("Invalid type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return QuestionViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: QuestionViewHolder,
        position: Int,
    ) {
        holder.bind(adapterData[position], this)
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is DataModel.Header -> TYPE_HEADER
            is DataModel.MultiChoiceQuestion -> TYPE_MULTI_CHOICE
            is DataModel.OpenQuestion -> TYPE_OPEN
            else -> TYPE_SUBMIT
        }
    }

    fun setData(data: List<DataModel>, numOfRequiredQuestions: Int) {
        adapterData.apply {
            clear()
            addAll(data)
        }
        adapterData.add(0, DataModel.Header())
        adapterData.add(DataModel.Submit())
        requiredQuestionAnsweredCounter = 0
        this.numOfRequiredQuestions = numOfRequiredQuestions
        answersData.clear()
    }

    fun setSubmitListener(listener: SubmitListener) {
        submitListener = listener
    }

    override fun questionAnswered(
        answer: Answer,
        isQuestionRequired: Boolean,
        questionPosition: Int,
    ) {
        if (isQuestionRequired && !answersData.containsKey(questionPosition)) {
            requiredQuestionAnsweredCounter++
        }
        answersData[questionPosition] = answer
    }

    override fun removeAnswer(isQuestionRequired: Boolean, questionPosition: Int) {
        if (answersData.containsKey(questionPosition)) {
            if (isQuestionRequired) {
                requiredQuestionAnsweredCounter--
            }
            answersData.remove(questionPosition)
        }
    }

    override fun onSubmit() {
        if (numOfRequiredQuestions == requiredQuestionAnsweredCounter) {
            submitListener.onSubmit(ArrayList(answersData.values))
        } else {
            submitListener.onNotAllRequiredQuestionsAnswered()
        }
    }
}