package com.aabdelaal.droos.ui.teacherList

import android.view.View
import android.widget.ImageView
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.ui.base.BaseRecyclerViewAdapter
import com.aabdelaal.droos.ui.base.DataBindingViewHolder


//Use data binding to show the reminder on the item
class TeacherListAdapter(
    callBack: (selectedTeacher: TeacherInfo) -> Unit,
    private val ecitCallback: (selectedTeacher: TeacherInfo) -> Unit,
    val dialogMode: Boolean = false
) : BaseRecyclerViewAdapter<TeacherInfo>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.teacher_list_item

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<TeacherInfo>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        if (dialogMode) {
            holder.itemView.findViewById<ImageView>(R.id.editIV).visibility = View.GONE
        } else {
            holder.itemView.findViewById<ImageView>(R.id.editIV).setOnClickListener {
                ecitCallback.invoke(getItem(position))
            }
        }
    }
}