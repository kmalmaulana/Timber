package com.example.timebarteryeah.presenters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.MyProfileContract
import com.example.timebarteryeah.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.jetbrains.anko.onComplete
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import javax.inject.Inject

class MyProfilePresenter @Inject constructor(private val view: MyProfileContract.View,
                                             private val auth: FirebaseAuth,
                                             private val firestore: FirebaseFirestore,
                                             private val storage: StorageReference)
    : MyProfileContract.Presenter, AnkoLogger {

    override fun getProfileView() {

        val currentUser = auth.currentUser

        doAsync {
            onComplete {

                view.onShowLoading()
                firestore.collection("users").document(currentUser!!.uid).get()
                        .addOnSuccessListener {
                            val name = it.getString("name")
                            val email = it.getString("email")
                            val photo = it.getString("photo")
                            val point = it.getLong("point")

                            var level: Long = 1

                            when {
                                point!! in 0..50 -> level = 1
                                point in 50..200 -> level = 2
                                point in 200..500 -> level = 3
                                point in 500..1000 -> level = 4
                                point in 1000..1500 -> level = 5
                                point in 1500..2000 -> level = 6
                                point in 2000..3000 -> level = 7
                                point in 3000..5000 -> level = 8
                                point in 5000..10000 -> level = 9
                                point > 10000 -> level = 10
                            }

                            val user = User.Builder()
                                    .setName(name)
                                    .setEmail(email)
                                    .setPhoto(photo)
                                    .setPoint(point)
                                    .setLevel(level)
                                    .build()

                            view.onSuccessProfileView(user)
                            view.onHideLoading()
                        }
                        .addOnFailureListener {
                            view.onException(it.message)
                            view.onHideLoading()
                        }

            }
        }

    }

    override fun setPhoto(context: Context, uri: Uri?) {

        try {

            doAsync {
                onComplete {

                    val photoStream = context.contentResolver.openInputStream(uri!!)
                    val photoBitmap = BitmapFactory.decodeStream(photoStream)
                    val photoScaled = Bitmap.createScaledBitmap(photoBitmap, 200, 200, true)

                    val baos = ByteArrayOutputStream()
                    photoScaled.compress(Bitmap.CompressFormat.JPEG, 50, baos)

                    val photoCompressed = baos.toByteArray()
                    val currentUser = auth.currentUser

                    view.onShowLoading()
                    val imageRef = storage.child("users/profiles").child(currentUser!!.uid + ".jpg")
                    imageRef.putBytes(photoCompressed)
                            .addOnCompleteListener {

                                imageRef.downloadUrl
                                        .addOnSuccessListener { url ->

                                            val mapUser = HashMap<String, Any>()
                                            mapUser["photo"] = url.toString()

                                            firestore.collection("users").document(currentUser.uid).set(mapUser, SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        getProfileView()

                                                        view.onMessage(context.getString(R.string.text_message_photo_changed))
                                                        view.onHideLoading()
                                                    }
                                                    .addOnFailureListener {
                                                        view.onException(it.message)
                                                        view.onHideLoading()
                                                    }

                                        }

                            }
                            .addOnFailureListener {
                                view.onException(it.message)
                                view.onHideLoading()
                            }

                }
            }

        } catch (e: FileNotFoundException) {
            error(e.message)
        } catch (e: NullPointerException) {
            error(e.message)
        }

    }

    override fun doSignOut() {
        doAsync {
            onComplete {
                auth.signOut()
                view.onSignOut()
            }
        }
    }

}