package com.aabdelaal.droos.ui.subjectsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.R
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubjectListFragment() : BaseFragment() {


    override val _viewModel: BaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subject_list, container, false)
    }


}