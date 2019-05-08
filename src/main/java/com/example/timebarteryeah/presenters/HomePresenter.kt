package com.example.timebarteryeah.presenters

import android.content.Context
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.HomeContract
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomePresenter @Inject constructor(
    private val view: HomeContract.View,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
)

    : HomeContract.Presenter {

    private val postList = mutableListOf<Post?>()
    private val userList = mutableListOf<User?>()

    override fun getTimeBalanceAndSpent() {

        doAsync {
            onComplete {

                view.onShowLoading()
                firestore.collection("users").document(auth.currentUser!!.uid).get()
                    .addOnSuccessListener {

                        val timeBalance = it.getLong("time_balance")
                        val timeSpent = it.getLong("time_spent")

                        val user = User.Builder()
                            .setTimeBalance(timeBalance)
                            .setTimeSpent(timeSpent)
                            .build()

                        view.onTimeBalanceAndSpent(user)
                        view.onHideLoading()

                    }
                    .addOnFailureListener {
                        view.onException(it.message)
                        view.onHideLoading()
                    }

            }
        }

    }

    override fun getLastPost(context: Context) {

        doAsync {
            onComplete {

                view.onShowLoading()
                firestore.collection("posts").orderBy("date", Query.Direction.DESCENDING)
                    .addSnapshotListener(EventListener<QuerySnapshot> { querySnapshot, e ->

                        if (e != null)
                            return@EventListener

                        if (querySnapshot?.documentChanges!!.isNotEmpty()) {

                            querySnapshot.documentChanges.forEach {

                                val userIdPost = it.document.getString("user_id")
                                val title = it.document.getString("title")
                                val desc = it.document.getString("desc")
                                val category = it.document.get("category") as HashMap<String, Any>
                                val date = it.document.getTimestamp("date")?.toDate()?.time
                                val duration = it.document.getLong("duration")
                                val photoPost = it.document.getString("photo")
                                val documentId = it.document.id
                                val status = it.document.getString("status")
                                val acceptedBy = it.document.getString("accepted_by")
                                val timeDone = it.document.getTimestamp("time_done")?.toDate()?.time

                                firestore.collection("users").document(userIdPost!!).get()
                                    .addOnSuccessListener { documentSnapshot ->

                                        val name = documentSnapshot.getString("name")
                                        val photoUser = documentSnapshot.getString("photo")

                                        val post = Post.Builder()
                                            .setUserId(userIdPost)
                                            .setTitle(title)
                                            .setDesc(desc)
                                            .setCategory(category)
                                            .setDate(date)
                                            .setDuration(duration)
                                            .setPhoto(photoPost)
                                            .setAuth(auth.currentUser?.uid)
                                            .setDocumentId(documentId)
                                            .setStatus(status)
                                            .setAcceptedBy(acceptedBy)
                                            .setTimeDone(timeDone)
                                            .build()

                                        postList.add(post)

                                        val user = User.Builder()
                                            .setName(name)
                                            .setPhoto(photoUser)
                                            .build()

                                        userList.add(user)

                                        view.onAllPost(postList, userList)
                                        view.onHideLoading()

                                    }
                                    .addOnFailureListener { e ->
                                        view.onException(e.toString())
                                        view.onHideLoading()
                                    }

                            }

                        } else {
                            view.onException(context.getString(R.string.text_message_no_post_found))
                            view.onHideLoading()
                        }

                    })

            }

        }

    }

}