package com.aabdelaal.droos.ui.subjectsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.databinding.FragmentManageSubjectBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.dialogs.DeleteConfirmationDialog
import com.aabdelaal.droos.ui.teacherList.SelectTeacherDialogFragment
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
        binding.techerNameTV.setOnClickListener {
            _viewModel.navigateToSelectFragment()
        }

        _viewModel.displayOnly.observe(viewLifecycleOwner) {
            binding.etName.isEnabled = !it
            binding.techerNameTV.isEnabled = !it

        }

        _viewModel.deleteSubjectEvent.observe(viewLifecycleOwner) {
            val ok_action = { _viewModel.deleteSubject() }
            DeleteConfirmationDialog(ok_action)
                .show(childFragmentManager, DeleteConfirmationDialog.TAG)

        }
        val setTeacher = fun(t: TeacherInfo) {
            _viewModel.currentSubject.value?.copy(teacher = t)
                ?.let { _viewModel.setCurrentSubject(it) } //.teacher = t
        }
        _viewModel.teacherList.observe(viewLifecycleOwner) {
            _viewModel.dialogIsReady = true
        }
        _viewModel.selectTeacherEvent.observe(viewLifecycleOwner) {
            if (it && _viewModel.dialogIsReady) {
//                _viewModel.navigationCommand.value =
//                NavigationCommand.To(ManageSubjectFragmentDirections.toTeacherListFragment(ManageMode.SELECT))
                _viewModel.teacherList.value?.toTypedArray()?.let { it1 ->
                    SelectTeacherDialogFragment(setTeacher, it1).show(
                        childFragmentManager,
                        SelectTeacherDialogFragment.TAG
                    )
                }


            }

        }
    }

}