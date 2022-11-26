package com.aabdelaal.droos.ui.teacherList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aabdelaal.droos.databinding.FragmentManageTeacherBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTeacherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTeacherFragment : BaseFragment() {

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

//        _viewModel.showLoading.observe()

    }


}