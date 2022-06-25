package com.example.questionnaire.modules.questionnaireModule.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.questionnaire.base.BindingFragment
import com.example.questionnaire.base.Response
import com.example.questionnaire.databinding.FragmentQuestionnaireBinding
import com.example.questionnaire.models.Question
import com.example.questionnaire.modules.questionnaireModule.viewModels.QuestionnaireViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionnaireFragment : BindingFragment<FragmentQuestionnaireBinding>() {
    companion object {
        val TAG: String = QuestionnaireFragment::class.simpleName ?: "QuestionnaireFragment"

        @JvmStatic
        fun newInstance() = QuestionnaireFragment()
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentQuestionnaireBinding::inflate

    private val viewModel: QuestionnaireViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        onObserve()
    }

    private fun initViews() {

    }

    private fun onObserve() {
        viewModel.questionsList.observe(viewLifecycleOwner) { response ->
            displayQuestions(response)
        }
    }

    private fun displayQuestions(response: Response<ArrayList<Question>>) {
        when (response) {
            is Response.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is Response.Success -> {
                Log.d(TAG, response.data.toString())
                binding.progressBar.visibility = View.GONE
                binding.textView.text = response.data.toString()
            }
            is Response.Error -> {
                Log.d(TAG, response.message)
                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

}