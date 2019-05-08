package com.example.timebarteryeah.presenters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.AddPostContract
import com.example.timebarteryeah.models.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.jetbrains.anko.onComplete
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*
import javax.inject.Inject

class AddPostPresenter @Inject constructor(
    private val view: AddPostContract.View,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference
) : AddPostContract.Presenter, AnkoLogger {

    private lateinit var photoBitmap: Bitmap

    override fun setPhotoPreview(context: Context, uri: Uri?) {
        try {

            val photoStream = context.contentResolver.openInputStream(uri!!)
            photoBitmap = BitmapFactory.decodeStream(photoStream)

            view.onPhotoPreview(photoBitmap)

        } catch (e: FileNotFoundException) {
            error(e.message)
        } catch (e: NullPointerException) {
            error(e.message)
        }
    }

    override fun setPost(
        context: Context,
        title: String?,
        desc: String?,
        category: HashMap<String, Any>,
        duration: Long?
    ) {
        try {

            val currentUser = auth.currentUser
            val randomName = UUID.randomUUID().toString()

            val post = Post.Builder()
                .setUserId(currentUser?.uid)
                .setTitle(title)
                .setDesc(desc)
                .setCategory(category)
                .setDuration(duration)
                .setAcceptedBy("None")
                .setStatus("Not Done")
                .setTimeDone(null)
                .build()

            if (isVerify(context, post)) {

                doAsync {
                    onComplete {

                        val baos = ByteArrayOutputStream()
                        val photoScaled = Bitmap.createScaledBitmap(photoBitmap, 500, 500, true)
                        photoScaled.compress(Bitmap.CompressFormat.JPEG, 50, baos)

                        val photoCompressed = baos.toByteArray()

                        view.onShowLoading()
                        firestore.collection("users").document(auth.currentUser!!.uid).get()
                            .addOnSuccessListener {
                                val timeBalance = it.getLong("time_balance")

                                if (timeBalance!! > 0L && post.duration!! <= timeBalance) {

                                    val countTimeBalance = timeBalance.minus(post.duration)
                                    val mapUser = HashMap<String, Long?>()
                                    mapUser["time_balance"] = countTimeBalance

                                    firestore.collection("users").document(auth.currentUser!!.uid)
                                        .set(mapUser, SetOptions.merge())
                                        .addOnSuccessListener {

                                            val photoRef = storage.child("users/posts").child("$randomName.jpg")
                                            photoRef.putBytes(photoCompressed)
                                                .addOnSuccessListener {

                                                    photoRef.downloadUrl
                                                        .addOnSuccessListener { url ->

                                                            val mapPost = HashMap<String, Any?>()
                                                            mapPost["user_id"] = post.userId
                                                            mapPost["title"] = post.title
                                                            mapPost["desc"] = post.desc
                                                            mapPost["category"] = post.category
                                                            mapPost["duration"] = post.duration
                                                            mapPost["photo"] = url.toString()
                                                            mapPost["photo_name"] = "$randomName.jpg"
                                                            mapPost["date"] = FieldValue.serverTimestamp()
                                                            mapPost["status"] = post.status
                                                            mapPost["accepted_by"] = post.acceptedBy
                                                            mapPost["time_done"] = post.timeDone

                                                            firestore.collection("posts").add(mapPost)
                                                                .addOnSuccessListener {

                                                                    firestore.collection("users")
                                                                        .document(auth.currentUser!!.uid)
                                                                        .collection("my_posts").document(it.id)
                                                                        .set(mapPost)
                                                                        .addOnSuccessListener {
                                                                            view.onMessage(context.getString(R.string.text_message_post_uploaded))

                                                                            view.onSuccessPost()
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


                                } else {
                                    view.onException(context.getString(R.string.text_error_message_time_balance_not_enough))
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

        } catch (e: FileNotFoundException) {
            view.onException(context.getString(R.string.text_error_message_no_photo_for_post))
        } catch (e: NullPointerException) {
            view.onException(context.getString(R.string.text_error_message_no_photo_for_post))
        } catch (e: UninitializedPropertyAccessException) {
            view.onException(context.getString(R.string.text_error_message_no_photo_for_post))
        }
    }

    private fun isVerify(context: Context, post: Post?): Boolean {
        if (post?.title!!.isNotEmpty())
            if (post.desc!!.isNotEmpty())
                if (post.category!!.isNotEmpty())
                    if (post.duration!! != 0L)
                        return true
                    else
                        view.onException(context.getString(R.string.text_error_message_post_duration))
                else
                    view.onException(context.getString(R.string.text_error_message_post_category))
            else
                view.onException(context.getString(R.string.text_error_message_post_desc))
        else
            view.onException(context.getString(R.string.text_error_message_post_title))

        return false
    }

}