package com.aabdelaal.droos.ui.teacherList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.databinding.FragmentTeacherListBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.base.NavigationCommand
import com.aabdelaal.droos.utils.ManageMode
import com.aabdelaal.droos.utils.setup
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class TeacherListFragment : BaseFragment() {


    private companion object {
        const val TAG = "DroosTeacherListF"
    }

    lateinit var databinding: FragmentTeacherListBinding

    //    override val _viewModel: TeacherListViewModel by viewModel()
    override val _viewModel: TeacherSharedViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databinding = FragmentTeacherListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databinding.lifecycleOwner = this
        databinding.viewModel = _viewModel

        _viewModel.manageForMode.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    ManageMode.ADD -> _viewModel.navigationCommand.value =
                        NavigationCommand.To(TeacherListFragmentDirections.toManageTeacherFragment())

                    ManageMode.UPDATE -> _viewModel.navigationCommand.value =
                        NavigationCommand.To(
                            TeacherListFragmentDirections.toManageTeacherFragment(
                                it
                            )
                        )
                    ManageMode.DISPLAY -> _viewModel.navigationCommand.value =
                        NavigationCommand.To(
                            TeacherListFragmentDirections.toManageTeacherFragment(
                                it
                            )
                        )
                    ManageMode.LIST -> {}
//                    else -> Log.e(TAG, "invalid mode")
                }
            }

        }
        setupRecyclerView()

        databinding.addFab.setOnClickListener {
            _viewModel.navigateToAddFragment()
        }

        databinding.btnDeleteAll.setOnClickListener {
            _viewModel.deleteAll()
        }
    }

    private fun setupRecyclerView() {
        val onTeacherItemClick = fun(t: TeacherInfo) {
            _viewModel.setCurrentTeacher(t)
            _viewModel.navigateToDisplayFragment()
            Log.d(TAG, "onTeacherClick callback")
        }
        val onEditTeacherItemClick = fun(t: TeacherInfo) {
            _viewModel.setCurrentTeacher(t)
            _viewModel.navigateToEditFragment()
            Log.d(TAG, "onEditTeacherItemClick callback")
        }
        val adapter = TeacherListAdapter(onTeacherItemClick, onEditTeacherItemClick)

//        setup the recycler view using the extension function
        databinding.teachersRecyclerView.setup(adapter)
    }

}