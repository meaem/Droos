package com.aabdelaal.droos.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aabdelaal.droos.data.model.Dars
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.data.source.local.entities.DarsEntity
import com.aabdelaal.droos.data.source.local.entities.SubjectEntity
import com.aabdelaal.droos.data.source.local.entities.TeacherInfoEntity
import com.aabdelaal.droos.data.source.local.mapping.DroosAndSubject
import com.aabdelaal.droos.data.source.local.mapping.TeacherInfoAndSubject
import com.aabdelaal.droos.data.source.remote.models.RemoteDars
import com.aabdelaal.droos.data.source.remote.models.RemoteSubject
import com.aabdelaal.droos.data.source.remote.models.RemoteTeacherInfo
import com.aabdelaal.droos.ui.base.BaseRecyclerViewAdapter


/**
 * Extension function to setup the RecyclerView
 */
fun <T> RecyclerView.setup(
    adapter: BaseRecyclerViewAdapter<T>
) {
    this.apply {
        layoutManager = LinearLayoutManager(this.context)
        this.adapter = adapter
    }
}

fun Fragment.setTitle(title: String) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}

fun Fragment.setDisplayHomeAsUpEnabled(bool: Boolean) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            bool
        )
    }
}

//animate changing the view visibility
fun View.fadeIn() {
    this.visibility = View.VISIBLE
    this.alpha = 0f
    this.animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@fadeIn.alpha = 1f
        }
    })
}

//animate changing the view visibility
fun View.fadeOut() {
    this.animate().alpha(0f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@fadeOut.alpha = 1f
            this@fadeOut.visibility = View.GONE
        }
    })
}

fun TeacherInfo.asEntity(): TeacherInfoEntity {
    this.let {
        return TeacherInfoEntity(this.name, this.phone, this.active, this.remoteID, this.id)
    }
}

fun TeacherInfo.asRemote(): RemoteTeacherInfo {
    this.let {
        return RemoteTeacherInfo(this.name, this.phone, this.active, this.remoteID, this.id)
    }
}

fun TeacherInfoEntity.asExternalModel(): TeacherInfo {
    this.let {
        return TeacherInfo(this.name, this.phone, this.isActive, this.remoteID, this.id)
    }
}

fun Subject.asEntity(): SubjectEntity {
    return SubjectEntity(this.name, this.isActive, this.remoteID, this.id, this.teacher?.id)

}

fun Subject.asRemote(): RemoteSubject {
    return RemoteSubject(this.name, this.isActive, this.remoteID, this.id, teacher?.remoteID)

}

fun SubjectEntity.asExternalModel(): Subject {

    return Subject(name, currentTeacherInfoEntity?.asExternalModel(), isActive, remoteID, id)

}


fun TeacherInfoAndSubject.asExternalModel(): Subject {
    return Subject(
        this.subject.name, this.teacher?.asExternalModel(), this.subject.isActive,
        this.subject.remoteID, this.subject.id
    )

}


fun Dars.asEntity(): DarsEntity {
    return DarsEntity(date, duration, remoteID, id, subject?.id)

}


fun DroosAndSubject.asExternalModel(): Dars {
    return Dars(subject?.asExternalModel(), dars.date, dars.duration, dars.remoteID, dars.id)

}


fun Dars.asRemote(): RemoteDars {
//    this.let {
    return RemoteDars(date, duration, remoteID, id, subject?.remoteID)
//    }
}