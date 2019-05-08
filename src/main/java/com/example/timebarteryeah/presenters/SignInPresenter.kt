package com.example.timebarteryeah.presenters

import android.content.Context
import android.util.Patterns
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.SignInContract
import com.example.timebarteryeah.models.User
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import javax.inject.Inject

class SignInPresenter @Inject constructor(private val view: SignInContract.View, 
                                          private val auth: FirebaseAuth)
    : SignInContract.Presenter {

    override fun setSignIn(context: Context, email: String?, password: String?) {
        
        val user = User.Builder()
                .setEmail(email)
                .setPassword(password)
                .build()
        
        if(isVerify(context, user)) {

            doAsync {
                onComplete {

                    view.onShowLoading()
                    auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                            .addOnSuccessListener {

                                val currentUser = auth.currentUser

                                if(currentUser!!.isEmailVerified) {
                                    view.onSuccessSignIn()
                                    view.onHideLoading()
                                } else {
                                    view.onException(context.getString(R.string.text_error_message_verify_account))
                                    view.onHideLoading()
                                }

                            }
                            .addOnFailureListener {
                                view.onException(it.message)
                                view.onHideLoading()
                            }

                }

            }
            
        }
        
    }

    override fun setRememberMe() {
        if(auth.currentUser != null)
            view.onRememberMe()
    }
    
    private fun isVerify(context: Context, user: User?): Boolean {
        if(Patterns.EMAIL_ADDRESS.matcher(user?.email).matches())
            if(user?.password!!.isNotEmpty())
                return true
            else
                view.onException(context.getString(R.string.text_error_message_password_empty))
        else
            view.onException(context.getString(R.string.text_error_message_email))

        
        return false
    }
    
}