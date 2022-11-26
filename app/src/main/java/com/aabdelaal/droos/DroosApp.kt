package com.aabdelaal.droos

import android.app.Application
import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.source.DefaultDroosRepository
import com.aabdelaal.droos.data.source.DroosRepository
import com.aabdelaal.droos.data.source.local.DroosLocalDataSource
import com.aabdelaal.droos.data.source.local.LocalDB
import com.aabdelaal.droos.data.source.local.LocalDataSource
import com.aabdelaal.droos.data.source.remote.DroosRemoteDataSource
import com.aabdelaal.droos.data.source.remote.RemoteDataSource
import com.aabdelaal.droos.ui.firebaseLogin.AuthenticationViewModel
import com.aabdelaal.droos.ui.firebaseLogin.FirebaseUserLiveData
//import com.aabdelaal.droos.ui.teacherList.TeacherListViewModel
import com.aabdelaal.droos.ui.teacherList.TeacherSharedViewModel
import com.google.firebase.auth.FirebaseUser
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class DroosApp : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
//            Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                TeacherSharedViewModel(get(), get())
            }

//            viewModel {
//                TeacherListViewModel(get())
//            }


            viewModel {
                AuthenticationViewModel(get(), get())
            }

            single<LiveData<FirebaseUser?>> { FirebaseUserLiveData() }

            //Declare singleton definitions to be later injected using by inject()
//            viewModel {
//                //This view model is declared singleton to be used across multiple fragments
//                SaveReminderViewModel(
//                    get(),
//                    get() as ReminderDataSource
//                )
//            }

            single<DroosRepository> {
                DefaultDroosRepository(
                    get(named(name = "remote")),
                    get(named(name = "local"))
                )
            }
            single<LocalDataSource>(named(name = "local")) { DroosLocalDataSource(get()) }
            single<RemoteDataSource>(named(name = "remote")) { DroosRemoteDataSource() }

            single { LocalDB.createDroosDao(this@DroosApp) }
        }

        startKoin {
            androidContext(this@DroosApp)
            modules(listOf(myModule))
        }
    }
}