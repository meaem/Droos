package com.aabdelaal.droos.utils

import androidx.annotation.StringRes
import com.aabdelaal.droos.R

enum class ManageMode(@StringRes val modeResID: Int) {
    ADD(R.string.add),
    UPDATE(R.string.update),
    LIST(R.string.list),
    DISPLAY(R.string.display),
    SELECT(R.string.select),
}