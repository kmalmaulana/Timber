package com.example.timebarteryeah.presenters

import android.content.Context
import android.util.Patterns
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.SignUpContract
import com.example.timebarteryeah.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import javax.inject.Inject

class SignUpPresenter @Inject constructor(private val view: SignUpContract.View,
                                          private val auth: FirebaseAuth,
                                          private val firestore: FirebaseFirestore)
    : SignUpContract.Presenter {

    override fun setSignUp(context: Context, name: String?, email: String?, password: String?) {

        val user = User.Builder()
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setTimeBalance(5)
                .setTimeSpent(0)
                .setPoint(0)
                .build()

        if (isVerify(context, user)) {

            doAsync {
                onComplete {

                    view.onShowLoading()
                    auth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                            .addOnSuccessListener {

                                val currentUser = auth.currentUser
                                val mapUser = HashMap<String, Any?>()
                                mapUser["name"] = user.name
                                mapUser["email"] = user.email
                                mapUser["time_balance"] = user.timeBalance
                                mapUser["time_spent"] = user.timeSpent
                                mapUser["point"] = user.point

                                firestore.collection("users").document(currentUser!!.uid).set(mapUser)
                                        .addOnSuccessListener {
                                            currentUser.sendEmailVerification()

                                            view.onSuccessSignUp()
                                            view.onHideLoading()
                                        }
                                        .addOnFailureListener {
                                            view.onException(it.message)
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

    private fun isVerify(context: Context, user: User?): Boolean {
        if (user?.name!!.length >= 4)
            if (Patterns.EMAIL_ADDRESS.matcher(user.email).matches())
                if (user.password!!.length >= 8)
                    return true
                else
                    view.onException(context.getString(R.string.text_error_message_password))
            else
                view.onException(context.getString(R.string.text_error_message_email))
        else
            view.onException(context.getString(R.string.text_error_message_name))

        return false
    }

}