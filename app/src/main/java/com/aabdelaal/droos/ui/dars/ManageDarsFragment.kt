package com.aabdelaal.droos.ui.dars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.databinding.FragmentManageDarsBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.dialogs.DateTimeSelectDialog
import com.aabdelaal.droos.ui.dialogs.DeleteConfirmationDialog
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
        binding.dateTV.setOnClickListener {
            _viewModel.navigateToSelectFragment()
        }

        _viewModel.displayOnly.observe(viewLifecycleOwner) {
            binding.etName.isEnabled = !it
            binding.dateTV.isEnabled = !it

        }

        _viewModel.deleteDarsEvent.observe(viewLifecycleOwner) {
            val ok_action = { _viewModel.deleteDars() }
            DeleteConfirmationDialog(ok_action)
                .show(childFragmentManager, DeleteConfirmationDialog.TAG)

        }
        val setTime = fun(hourOfDay: Int, minute: Int) {
            val c = Calendar.getInstance()
            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
            c.set(Calendar.MINUTE, minute)
            Log.d(TAG, "seleted time: ${c.time}")
            _viewModel.currentDars.value?.copy(date = c.time)
                ?.let { _viewModel.setCurrentDars(it) } //.teacher = t
        }
        _viewModel.subjectList.observe(viewLifecycleOwner) {
            _viewModel.dialogIsReady = true
        }
        _viewModel.selectSubjectEvent.observe(viewLifecycleOwner) {
            if (it && _viewModel.dialogIsReady) {
//                _viewModel.navigationCommand.value =
//                NavigationCommand.To(ManageDarsFra gmentDirections.toTeacherListFragment(ManageMode.SELECT))
                _viewModel.subjectList.value?.toTypedArray()?.let { it1 ->
                    DateTimeSelectDialog(setTime).show(
                        childFragmentManager,
                        SelectTeacherDialogFragment.TAG
                    )
                }
            }
        }
    }
}