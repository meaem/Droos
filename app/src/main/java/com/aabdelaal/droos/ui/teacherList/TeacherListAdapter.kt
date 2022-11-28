package com.aabdelaal.droos.ui.teacherList

import android.widget.ImageView
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.ui.base.BaseRecyclerViewAdapter
import com.aabdelaal.droos.ui.base.DataBindingViewHolder


//Use data binding to show the reminder on the item
class TeacherListAdapter(
    callBack: (selectedTeacher: TeacherInfo) -> Unit,
    private val ecitCallback: (selectedTeacher: TeacherInfo) -> Unit
) : BaseRecyclerViewAdapter<TeacherInfo>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.teacher_list_item

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<TeacherInfo>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.itemView.findViewById<ImageView>(R.id.editIV).setOnClickListener {
            ecitCallback.invoke(getItem(position))
        }
    }
}