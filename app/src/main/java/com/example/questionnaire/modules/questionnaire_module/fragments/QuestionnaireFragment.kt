package com.example.questionnaire.modules.questionnaire_module.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.questionnaire.R
import com.example.questionnaire.base.BindingFragment
import com.example.questionnaire.base.Response
import com.example.questionnaire.databinding.FragmentQuestionnaireBinding
import com.example.questionnaire.models.Answer
import com.example.questionnaire.models.Question
import com.example.questionnaire.modules.adapters.QuestionnaireRecyclerViewAdapter
import com.example.questionnaire.modules.interfaces.SubmitListener
import com.example.questionnaire.modules.questionnaire_module.viewModels.QuestionnaireViewModel
import com.example.questionnaire.utils.VerticalSpaceItemDecoration
import com.example.questionnaire.utils.dpToPx
import com.example.questionnaire.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionnaireFragment : BindingFragment<FragmentQuestionnaireBinding>(), SubmitListener {
    companion object {
        val TAG: String = QuestionnaireFragment::class.simpleName ?: "QuestionnaireFragment"

        @JvmStatic
        fun newInstance() = QuestionnaireFragment()
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentQuestionnaireBinding::inflate

    private val viewModel: QuestionnaireViewModel by viewModels()

    private val questionnaireRecyclerViewAdapter: QuestionnaireRecyclerViewAdapter by lazy {
        QuestionnaireRecyclerViewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
    }

    private fun initViews() {
        binding.questionnaireRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = questionnaireRecyclerViewAdapter
            if (binding.questionnaireRecyclerView.itemDecorationCount < 1) {
                this.addItemDecoration(VerticalSpaceItemDecoration(16.dpToPx()))
            }
        }
    }

    private fun onObserve() {
        viewModel.questionsList.observe(viewLifecycleOwner) { response ->
            displayQuestions(response)
        }

        viewModel.sendAnswersState.observe(viewLifecycleOwner) { response ->
            displayAnswersResponse(response)
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
                // set data to recyclerView
                questionnaireRecyclerViewAdapter.setSubmitListener(this)
                questionnaireRecyclerViewAdapter.setData(
                    viewModel.transformArrayListOfQuestionsToDataModelList(response.data),
                    viewModel.getNumOfRequiredQuestions(response.data)
                )
                initViews()
            }
            is Response.Error -> {
                Log.d(TAG, response.message)
                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun displayAnswersResponse(response: Response<Boolean>?) {
        when (response) {
            is Response.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is Response.Success -> {
                binding.progressBar.visibility = View.GONE
                viewModel.resetForm()
            }
            is Response.Error -> {
                Log.d(TAG, response.message)
                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onSubmit(answerList: ArrayList<Answer>) {
        hideKeyboard()
        viewModel.sendAnswers(answerList)
    }

    override fun onNotAllRequiredQuestionsAnswered() {
        hideKeyboard()
        Toast.makeText(requireContext(),
            getString(R.string.answer_all_required),
            Toast.LENGTH_LONG).show()
    }
}