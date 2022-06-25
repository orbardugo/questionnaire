package com.example.questionnaire.modules.questionnaireModule.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.questionnaire.R
import com.example.questionnaire.base.BindingFragment
import com.example.questionnaire.databinding.FragmentQuestionnaireBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionnaireFragment : BindingFragment<FragmentQuestionnaireBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() = QuestionnaireFragment()
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentQuestionnaireBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questionnaire, container, false)
    }


}