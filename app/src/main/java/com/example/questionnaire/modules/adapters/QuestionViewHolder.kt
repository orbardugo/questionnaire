package com.example.questionnaire.modules.adapters

import android.view.View
import android.widget.RadioButton
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.questionnaire.databinding.MultiChoiceQuestionCardItemBinding
import com.example.questionnaire.databinding.OpenQuestionCardItemBinding
import com.example.questionnaire.databinding.SubmitItemBinding
import com.example.questionnaire.models.Answer
import com.example.questionnaire.modules.interfaces.QuestionAnsweredListener

class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private fun bindHeader(item: DataModel.Header, listener: QuestionAnsweredListener) {
        //Do your view assignment here from the data model
    }

    private fun bindMultiChoiceQuestion(
        item: DataModel.MultiChoiceQuestion,
        listener: QuestionAnsweredListener,
    ) {
        //Do your view assignment here from the data model
        val binding = MultiChoiceQuestionCardItemBinding.bind(itemView)
        binding.questionTitle.text = item.question.title
        if (item.question.isRequired) {
            binding.required.visibility = View.VISIBLE
        }
        for (answer in item.question.answers) {
            if (answer.answerText.isEmpty()) {
                binding.openTextRadioButton.id = answer.id
                binding.openTextRadioButton.visibility = View.VISIBLE
                binding.openText.visibility = View.VISIBLE
            } else {
                val radioButton = RadioButton(binding.root.context)
                radioButton.apply {
                    text = answer.answerText
                    id = answer.id
                }
                binding.answersGroup.addView(radioButton, 0)
            }
        }

        binding.answersGroup.setOnCheckedChangeListener { radioGroup, checkId ->
            run {
                var radioButton: RadioButton = itemView.findViewById(checkId)
                val selectedAnswer: Answer? = item.question.getAnswerById(checkId)
                if(radioButton.text != "other") {
                    binding.openText.text.clear()
                    binding.openText.clearFocus()
                }
                if (selectedAnswer != null) {
                    listener.questionAnswered(selectedAnswer,
                        item.question.isRequired,
                        adapterPosition)
                }
            }
        }
        binding.openText.doOnTextChanged { text, _, _, _ ->
            run {
                val selectedAnswer: Answer? = item.question.getAnswerById(binding.answersGroup.checkedRadioButtonId)
                selectedAnswer?.answerText =
                   text.toString().ifEmpty { "other" }
                if (selectedAnswer != null) {
                    listener.questionAnswered(selectedAnswer,
                        item.question.isRequired,
                        adapterPosition)
                }
            }
        }
    }

    private fun bindOpenQuestion(item: DataModel.OpenQuestion, listener: QuestionAnsweredListener) {
        val binding = OpenQuestionCardItemBinding.bind(itemView)
        binding.questionTitle.text = item.question.title
        if (item.question.isRequired) {
            binding.required.visibility = View.VISIBLE
        }
        binding.answerEditText.doOnTextChanged { text, _, _, _ ->
            run {
                if (text.isNullOrEmpty()) {
                    listener.removeAnswer(item.question.isRequired, adapterPosition)
                } else {
                    val answer = item.question.answers[0]
                    answer.answerText = text.toString()
                    listener.questionAnswered(answer,
                        item.question.isRequired,
                        adapterPosition)
                }
            }
        }
    }

    private fun bindSubmit(item: DataModel.Submit, listener: QuestionAnsweredListener) {
        val binding = SubmitItemBinding.bind(itemView)
        binding.submitButton.setOnClickListener {
            listener.onSubmit()
        }

    }

    fun bind(dataModel: DataModel, listener: QuestionAnsweredListener) {
        when (dataModel) {
            is DataModel.Header -> bindHeader(dataModel, listener)
            is DataModel.MultiChoiceQuestion -> bindMultiChoiceQuestion(dataModel, listener)
            is DataModel.OpenQuestion -> bindOpenQuestion(dataModel, listener)
            is DataModel.Submit -> bindSubmit(dataModel, listener)
        }
    }


}