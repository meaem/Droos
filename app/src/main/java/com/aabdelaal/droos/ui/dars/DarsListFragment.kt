package com.aabdelaal.droos.ui.dars

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aabdelaal.droos.data.model.Dars
import com.aabdelaal.droos.databinding.FragmentDarsListBinding
import com.aabdelaal.droos.ui.base.BaseFragment
import com.aabdelaal.droos.ui.base.NavigationCommand
import com.aabdelaal.droos.ui.dialogs.DeleteConfirmationDialog
import com.aabdelaal.droos.utils.ManageMode
import com.aabdelaal.droos.utils.setup
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DarsListFragment() : BaseFragment() {


    private companion object {
        const val TAG = "DroosDarsListF"
    }


    lateinit var databinding: FragmentDarsListBinding

    //    override val _viewModel: TeacherListViewModel by viewModel()
    override val _viewModel: DarsSharedViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databinding = FragmentDarsListBinding.inflate(inflater, container, false)
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
                        NavigationCommand.To(DarsListFragmentDirections.toManageDarsFragment())

                    ManageMode.UPDATE -> _viewModel.navigationCommand.value =
                        NavigationCommand.To(
                            DarsListFragmentDirections.toManageDarsFragment(
                                it
                            )
                        )
                    ManageMode.DISPLAY -> _viewModel.navigationCommand.value =
                        NavigationCommand.To(
                            DarsListFragmentDirections.toManageDarsFragment(
                                it
                            )
                        )
                    ManageMode.LIST -> {}
                    ManageMode.SELECT -> {}
//                    else -> Log.e(TAG, "invalid mode")
                }
            }

        }
        setupRecyclerView()

        databinding.addFab.setOnClickListener {
            _viewModel.navigateToAddFragment()
        }

        databinding.btnDeleteAll.setOnClickListener {
            val ok_Action = { _viewModel.deleteAll() }

            DeleteConfirmationDialog(ok_Action).show(
                childFragmentManager,
                DeleteConfirmationDialog.TAG
            )


        }
    }

    private fun setupRecyclerView() {
        val onDarsItemClick = fun(t: Dars) {
            _viewModel.setCurrentDars(t)
            _viewModel.navigateToDisplayFragment()
            Log.d(TAG, "onDarsItemClick callback")
        }
        val onEditDarsItemClick = fun(t: Dars) {
            _viewModel.setCurrentDars(t)
            _viewModel.navigateToEditFragment()
            Log.d(TAG, "onEditDarsItemClick callback")
        }
        val adapter = DarsListAdapter(onDarsItemClick, onEditDarsItemClick)

//        setup the recycler view using the extension function
        databinding.subjectsRecyclerView.setup(adapter)
    }

}