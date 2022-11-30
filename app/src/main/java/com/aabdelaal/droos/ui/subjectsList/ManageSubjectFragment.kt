package com.aabdelaal.droos.ui.subjectsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.databinding.FragmentManageSubjectBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class ManageSubjectFragment : BaseFragment() {

    private companion object {
        const val TAG = "DroosManageSubjectFragment"
    }

    override val _viewModel: SubjectSharedViewModel by activityViewModel()
    lateinit var binding: FragmentManageSubjectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageSubjectBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel

        binding.btnSave.setOnClickListener {
            _viewModel.doAction()
        }

        binding.btnCancel.setOnClickListener {
            _viewModel.cancelAdd()
        }

        _viewModel.displayOnly.observe(viewLifecycleOwner) {
            binding.etName.isEnabled = !it
            binding.techerNameTV.isEnabled = !it
//            binding.isActive.isEnabled = !it

        }

//        _viewModel.showLoading.observe()

    }

}