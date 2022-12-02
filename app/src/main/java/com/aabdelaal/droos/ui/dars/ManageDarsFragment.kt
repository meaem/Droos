package com.aabdelaal.droos.ui.dars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.databinding.FragmentManageDarsBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.dialogs.DateTimeSelectDialog
import com.aabdelaal.droos.ui.dialogs.DeleteConfirmationDialog
import com.aabdelaal.droos.ui.subjectsList.SelectSubjectDialogFragment
import com.aabdelaal.droos.ui.teacherList.SelectTeacherDialogFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.util.*

class ManageDarsFragment : BaseFragment() {
    private companion object {
        const val TAG = "DroosManageDarsFragment"
    }

    override val _viewModel: DarsSharedViewModel by activityViewModel()

    lateinit var binding: FragmentManageDarsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageDarsBinding.inflate(inflater, container, false)

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
        binding.subjectNameTV.setOnClickListener {
            _viewModel.navigateToSelectFragment()
        }

        binding.dateTV.setOnClickListener {
            _viewModel.selectDate()
        }

        _viewModel.displayOnly.observe(viewLifecycleOwner) {
            binding.etDuration.isEnabled = !it
            binding.dateTV.isEnabled = !it

        }

        _viewModel.deleteDarsEvent.observe(viewLifecycleOwner) {
            val ok_action = { _viewModel.deleteDars() }
            DeleteConfirmationDialog(ok_action)
                .show(childFragmentManager, DeleteConfirmationDialog.TAG)

        }

        _viewModel.subjectList.observe(viewLifecycleOwner) {
            _viewModel.dialogIsReady = true
        }
        val setSubject = fun(t: Subject?) {
            _viewModel.currentDars.value?.copy(subject = t)
                ?.let { _viewModel.setCurrentDars(it) } //.teacher = t
        }
        _viewModel.selectSubjectEvent.observe(viewLifecycleOwner) {
            if (it && _viewModel.dialogIsReady) {
//                _viewModel.navigationCommand.value =
//                NavigationCommand.To(ManageDarsFra gmentDirections.toTeacherListFragment(ManageMode.SELECT))
                _viewModel.subjectList.value?.toTypedArray()?.let { arrayOfSubjects ->
                    val selectedIndex =
                        arrayOfSubjects.indexOf(_viewModel.currentDars.value?.subject)
                    SelectSubjectDialogFragment(setSubject, arrayOfSubjects, selectedIndex).show(
                        childFragmentManager,
                        SelectTeacherDialogFragment.TAG
                    )
                }
            }
        }

        val setDateTime = fun(hourOfDay: Int, minute: Int, year: Int, month: Int, dayOfMonth: Int) {
            val c = Calendar.getInstance()
            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
            c.set(Calendar.MINUTE, minute)
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            Log.d(TAG, "seleted time: ${c.time}")
            _viewModel.currentDars.value?.copy(date = c.time)
                ?.let { _viewModel.setCurrentDars(it) } //.teacher = t
        }
        _viewModel.selectDateTimeEvent.observe(viewLifecycleOwner) {
            if (it) {
//
                DateTimeSelectDialog(setDateTime).show(
                    childFragmentManager,
                    SelectTeacherDialogFragment.TAG
                )

            }
        }
    }
}