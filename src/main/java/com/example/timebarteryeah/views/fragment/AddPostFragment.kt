package com.example.timebarteryeah.views.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.AddPostContract
import com.example.timebarteryeah.models.constant.Constant
import com.example.timebarteryeah.presenters.AddPostPresenter
import com.example.timebarteryeah.utils.PermissionUtil
import com.example.timebarteryeah.views.activity.MainActivity
import com.example.timebarteryeah.views.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_post_add.*
import kotlinx.android.synthetic.main.fragment_post_add.view.*
import javax.inject.Inject

class AddPostFragment : BaseFragment(), AddPostContract.View {

    private lateinit var ui: View
    private lateinit var mapCategory: HashMap<String, Any>
    private var duration: Long = 0

    @Inject lateinit var presenter: AddPostPresenter

    override fun getContentView(): Int = R.layout.fragment_post_add

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui = view
        mapCategory = HashMap()

        ui.btnCatCourse.setOnClickListener {
            if(mapCategory["A"] == null) {
                mapCategory["A"] = getString(R.string.text_btn_course)
                ui.btnCatCourse.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                mapCategory.remove("A")
                ui.btnCatCourse.setBackgroundColor(resources.getColor(R.color.colorPrimaryBackground))
            }
        }
        ui.btnCatHobby.setOnClickListener {
            if(mapCategory["B"] == null) {
                mapCategory["B"] = getString(R.string.text_btn_hobby)
                ui.btnCatHobby.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                mapCategory.remove("B")
                ui.btnCatHobby.setBackgroundColor(resources.getColor(R.color.colorPrimaryBackground))
            }
        }
        ui.btnCatConsult.setOnClickListener {
            if(mapCategory["C"] == null) {
                mapCategory["C"] = getString(R.string.text_btn_consultation)
                ui.btnCatConsult.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                mapCategory.remove("C")
                ui.btnCatConsult.setBackgroundColor(resources.getColor(R.color.colorPrimaryBackground))
            }
        }
        ui.btnCatGeneral.setOnClickListener {
            if(mapCategory["D"] == null) {
                mapCategory["D"] = getString(R.string.text_btn_general)
                ui.btnCatGeneral.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                mapCategory.remove("D")
                ui.btnCatGeneral.setBackgroundColor(resources.getColor(R.color.colorPrimaryBackground))
            }
        }
        ui.btnIncrement.setOnClickListener { btnDurationIncrement() }
        ui.btnDecrement.setOnClickListener { btnDurationDecrement() }
        ui.btnAddPhoto.setOnClickListener { isGalleryPermissionGranted() }

        ui.btnPost.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()

            presenter.setPost(
                activity!!,
                title,
                desc,
                mapCategory,
                duration
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.setPhotoPreview(activity!!, data?.data)
    }

    override fun getProgressBar(): View {
        val ui = activity as MainActivity
        return ui.rlProgressBar
    }

    override fun onPhotoPreview(bitmap: Bitmap?) {
        ui.ivPrevPhoto.setImageBitmap(bitmap)
    }

    override fun onSuccessPost() {

    }

    private fun isGalleryPermissionGranted() {
        if(PermissionUtil.isExternalStorageGranted(activity!!))
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Constant.Tag.EXTERNAL_STORAGE_PERMISSION)
        else
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.Tag.EXTERNAL_STORAGE_PERMISSION)
    }

    private fun btnDurationIncrement() {
        if(duration == 24L)
            return

        duration += 1
        displayDuration(duration)
    }

    private fun btnDurationDecrement() {
        if(duration == 0L)
            return

        duration -= 1
        displayDuration(duration)
    }

    private fun displayDuration(duration: Long) {
        ui.tvDate.text = duration.toString()
    }

}