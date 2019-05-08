package com.example.timebarteryeah.presenters

import android.content.Context
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.AcceptPostContract
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import javax.inject.Inject

@Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
class AcceptPostPresenter @Inject constructor(
    private val view: AcceptPostContract.View,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AcceptPostContract.Presenter {

    private val postList = mutableListOf<Post>()
    private val userList = mutableListOf<User>()

    override fun getAcceptPosts(context: Context) {

        val userId = auth.currentUser!!.uid

        doAsync {
            onComplete {

                firestore.collection("users").document(userId)
                    .collection("accept_posts")
                    .addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->

                        if (e != null) {
                            return@EventListener
                        }

                        if (snapshot!!.documentChanges.isNotEmpty()) {
                            for (doc in snapshot.documentChanges) {

                                val userIdPost = doc.document.getString("user_id")
                                val title = doc.document.getString("title")
                                val desc = doc.document.getString("desc")
                                val category = doc.document.get("category") as HashMap<String, Any>
                                val date = doc.document.getTimestamp("date")?.toDate()?.time
                                val duration = doc.document.getLong("duration")
                                val photoPost = doc.document.getString("photo")
                                val documentId = doc.document.id
                                val status = doc.document.getString("status")
                                val acceptedBy = doc.document.getString("accepted_by")
                                val timeDone = doc.document.getTimestamp("time_done")?.toDate()?.time

                                firestore.collection("users").document(userIdPost!!).get()
                                    .addOnSuccessListener {

                                        val nameUserPost = it.getString("name")
                                        val photoUserPost = it.getString("photo")

                                        val post = Post.Builder()
                                            .setUserId(userIdPost)
                                            .setTitle(title)
                                            .setDesc(desc)
                                            .setCategory(category)
                                            .setDate(date)
                                            .setDuration(duration)
                                            .setPhoto(photoPost)
                                            .setAuth(userId)
                                            .setDocumentId(documentId)
                                            .setStatus(status)
                                            .setAcceptedBy(acceptedBy)
                                            .setTimeDone(timeDone)
                                            .build()

                                        postList.add(post)

                                        val user = User.Builder()
                                            .setName(nameUserPost)
                                            .setPhoto(photoUserPost)
                                            .build()

                                        userList.add(user)

                                        view.onAcceptPosts(postList, userList)
                                        view.onHideLoading()

                                    }
                                    .addOnFailureListener {
                                        view.onException(it.toString())
                                        view.onHideLoading()
                                    }

                            }

                        } else {
                            view.onMessage(context.getString(R.string.text_message_no_post_found))
                            view.onHideLoading()
                        }
                    })

            }
        }

    }

}