package com.aabdelaal.droos.ui.subjectsList

import android.widget.ImageView
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.ui.base.BaseRecyclerViewAdapter
import com.aabdelaal.droos.ui.base.DataBindingViewHolder


//Use data binding to show the reminder on the item
class SubjectListAdapter(
    callBack: (selectedSubject: Subject) -> Unit,
    private val editCallback: (selectedSubject: Subject) -> Unit
) : BaseRecyclerViewAdapter<Subject>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.subject_list_item

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<Subject>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.itemView.findViewById<ImageView>(R.id.editIV).setOnClickListener {
            editCallback.invoke(getItem(position))
        }
    }
}