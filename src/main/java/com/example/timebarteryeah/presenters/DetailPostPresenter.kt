package com.example.timebarteryeah.presenters

import android.content.Context
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.DetailPostContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DetailPostPresenter @Inject constructor(private val view: DetailPostContract.View,
                                              private val auth: FirebaseAuth,
                                              private val firestore: FirebaseFirestore)
    : DetailPostContract.Presenter {

    override fun setToListHelp(context: Context, documentId: String?) {

        doAsync {
            onComplete {

                view.onShowLoading()
                val postsRef = firestore.collection("posts").document(documentId!!)
                postsRef.get()
                        .addOnSuccessListener {

                            val userIdPost = it.getString("user_id")

                            val mapAcceptPost = HashMap<String, Any?>()
                            mapAcceptPost["user_id"] = userIdPost
                            mapAcceptPost["title"] = it.getString("title")
                            mapAcceptPost["desc"] = it.getString("desc")
                            mapAcceptPost["category"] = it.get("category") as HashMap<String, Any>
                            mapAcceptPost["date"] = it.getTimestamp("date")
                            mapAcceptPost["duration"] = it.getLong("duration")
                            mapAcceptPost["photo"] = it.getString("photo")
                            mapAcceptPost["status"] = it.getString("status")
                            mapAcceptPost["accepted_by"] = it.getString("accepted_by")
                            mapAcceptPost["time_done"] = it.getLong("time_done")

                            val userAccept = firestore.collection("users").document(auth.currentUser!!.uid)
                            userAccept.get()
                                    .addOnSuccessListener {
                                        val userAcceptId = auth.currentUser!!.uid
                                        val userAcceptName = it.getString("name")

                                        userAccept.collection("accept_posts").document(documentId).set(mapAcceptPost)
                                                .addOnSuccessListener {

                                                    val mapUserPost = HashMap<String, Any?>()
                                                    mapUserPost["user_accept_id"] = userAcceptId
                                                    mapUserPost["accepted_by"] = userAcceptName

                                                    firestore.collection("users").document(userIdPost!!)
                                                            .collection("my_posts").document(documentId).set(mapUserPost, SetOptions.merge())
                                                            .addOnSuccessListener {
                                                                postsRef.delete()
                                                                view.onMessage(context.getString(R.string.text_message_accepted_post))

                                                                view.onToListHelp()
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

    override fun setDonePost(context: Context, documentId: String) {

        doAsync {
            onComplete {

                val userAuth = auth.currentUser?.uid

                view.onShowLoading()
                val userPostRef = firestore.collection("users").document(userAuth!!)
                userPostRef.get()
                        .addOnSuccessListener {
                            val timeSpent = it.getLong("time_spent")

                            userPostRef.collection("my_posts").document(documentId).get()
                                    .addOnSuccessListener {
                                        val userAcceptId = it.getString("user_accept_id")
                                        val duration = it.getLong("duration")
                                        
                                        val userAcceptRef = firestore.collection("users").document(userAcceptId!!)
                                        userAcceptRef.get()
                                                .addOnSuccessListener { 
                                                    val timeBalance = it.getLong("time_balance")
                                                    val point = it.getLong("point")
                                                    
                                                    val mapUserPost = HashMap<String, Any?>()
                                                    mapUserPost["time_spent"] = timeSpent!! + duration!!
                                                    
                                                    userPostRef.set(mapUserPost, SetOptions.merge())
                                                            .addOnSuccessListener { 
                                                                
                                                                val mapUserAccept = HashMap<String, Any?>()
                                                                mapUserAccept["time_balance"] = timeBalance!! + duration
                                                                mapUserAccept["point"] = point!! + (duration * 50)
                                                                
                                                                userAcceptRef.set(mapUserAccept, SetOptions.merge())
                                                                        .addOnSuccessListener { 
                                                                            
                                                                            val mapUserPost2 = HashMap<String, Any?>()
                                                                            mapUserPost2["status"] = "Done"
                                                                            mapUserPost2["time_done"] = FieldValue.serverTimestamp()
                                                                            
                                                                            userPostRef.collection("my_posts").document(documentId).set(mapUserPost2, SetOptions.merge())
                                                                                    .addOnSuccessListener { 
                                                                                        
                                                                                        val mapUserAccept2 = HashMap<String, Any?>()
                                                                                        mapUserAccept2["status"] = "Done"
                                                                                        mapUserAccept2["time_done"] = FieldValue.serverTimestamp()
                                                                                        
                                                                                        userAcceptRef.collection("accept_posts").document(documentId).set(mapUserAccept2, SetOptions.merge())
                                                                                                .addOnSuccessListener {
                                                                                                    view.onMessage(context.getString(R.string.text_message_help_is_done))
                                                                                                    
                                                                                                    view.onDonePost()
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
                        .addOnFailureListener {
                            view.onException(it.message)
                            view.onHideLoading()
                        }

            }
        }


    }

}