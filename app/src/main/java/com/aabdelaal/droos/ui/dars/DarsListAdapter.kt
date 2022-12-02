package com.aabdelaal.droos.ui.dars

import android.widget.ImageView
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.Dars
import com.aabdelaal.droos.ui.base.BaseRecyclerViewAdapter
import com.aabdelaal.droos.ui.base.DataBindingViewHolder


//Use data binding to show the reminder on the item
class DarsListAdapter(
    callBack: (selectedDars: Dars) -> Unit,
    private val editCallback: (selectedDars: Dars) -> Unit
) : BaseRecyclerViewAdapter<Dars>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.dars_list_item

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<Dars>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.itemView.findViewById<ImageView>(R.id.editIV).setOnClickListener {
            editCallback.invoke(getItem(position))
        }
    }
}