package com.example.timebarteryeah.contracts

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.timebarteryeah.views.base.BaseView

interface AddPostContract {

    interface View : BaseView {
        fun onPhotoPreview(bitmap: Bitmap?)
        fun onSuccessPost()
    }

    interface Presenter {
        fun setPhotoPreview(context: Context, uri: Uri?)
        fun setPost(context: Context, title: String?, desc: String?, category: HashMap<String, Any>, duration: Long?)
    }

}