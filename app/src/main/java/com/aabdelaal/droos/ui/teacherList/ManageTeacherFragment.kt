package com.aabdelaal.droos.ui.teacherList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.databinding.FragmentManageTeacherBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.dialogs.DeleteConfirmationDialog
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class ManageTeacherFragment : BaseFragment() {

    private companion object {
        const val TAG = "DroosAddTeacherFragment"
    }

    override val _viewModel: TeacherSharedViewModel by activityViewModel()
    lateinit var binding: FragmentManageTeacherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageTeacherBinding.inflate(inflater, container, false)
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
            binding.etPhone.isEnabled = !it
            binding.isActive.isEnabled = !it

        }
        _viewModel.deleteTeacherEvent.observe(viewLifecycleOwner) {
            val ok_action = { _viewModel.deleteTeacher() }
            DeleteConfirmationDialog(ok_action)
                .show(childFragmentManager, DeleteConfirmationDialog.TAG)

        }

//        _viewModel.showLoading.observe()

    }


}