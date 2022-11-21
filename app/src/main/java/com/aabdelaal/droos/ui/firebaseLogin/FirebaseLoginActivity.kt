package com.aabdelaal.droos.ui.firebaseLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aabdelaal.droos.R
import com.aabdelaal.droos.databinding.ActivityFirebaseLoginBinding
import com.aabdelaal.droos.ui.subjectsList.SubjectListActivity
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirebaseLoginActivity : AppCompatActivity() {
    private val viewModel: AuthenticationViewModel by viewModel()

    companion object {
        const val TAG = "AuthenticationActivity"
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
//        this.onSignInResult(it)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databinding = ActivityFirebaseLoginBinding.inflate(layoutInflater)

        // Observe the authentication state so we can know if the user has logged in successfully.
        // If the user has logged in successfully, bring them back to the settings screen.
        // If the user did not log in successfully, display an error message.
        viewModel.authenticationState.observe(this) { authenticationState ->
            when (authenticationState) {
                AuthenticationViewModel.AuthenticationState.AUTHENTICATED -> {
//                    Log.d(TAG, "Success user name: '${user?.displayName}'")
                    val intent = Intent(this, SubjectListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> Log.e(
                    TAG,
                    "Authentication state that doesn't require any UI change $authenticationState"
                )
            }
        }

        databinding.loginButton.setOnClickListener {
            launchSignInFlow()
        }

        setContentView(databinding.root)
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account. If users
        // choose to register with their email, they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )


        val customLayout = AuthMethodPickerLayout.Builder(R.layout.auth_custom_layout_xml)
            .setGoogleButtonId(R.id.with_google)
            .setEmailButtonId(R.id.by_email)
//            .setTosAndPrivacyPolicyId(R.id.baz)

            .build()

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setAuthMethodPickerLayout(customLayout)
            .setTheme(R.style.Theme_Droos)
            .build()

        signInLauncher.launch(signInIntent)
    }

}