package com.aabdelaal.droos

import android.app.Application
import androidx.lifecycle.LiveData
import com.aabdelaal.droos.ui.firebaseLogin.AuthenticationViewModel
import com.aabdelaal.droos.ui.firebaseLogin.FirebaseUserLiveData
import com.google.firebase.auth.FirebaseUser
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class DroosApp : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * use Koin Library as a service locator
         */
        val myModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
//            viewModel {
//                RemindersListViewModel(
//                    get(),
//                    get() as ReminderDataSource
//                )
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

//            single { RemindersLocalRepository(get()) as ReminderDataSource }
//            single { LocalDB.createRemindersDao(this@MyApp) }
        }

        startKoin {
            androidContext(this@DroosApp)
            modules(listOf(myModule))
        }
    }
}