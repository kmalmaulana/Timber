package com.example.timebarteryeah.views.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.MyProfileContract
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.models.constant.Constant
import com.example.timebarteryeah.presenters.MyProfilePresenter
import com.example.timebarteryeah.utils.PermissionUtil
import com.example.timebarteryeah.views.activity.MainActivity
import com.example.timebarteryeah.views.activity.SignInActivity
import com.example.timebarteryeah.views.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile_my.view.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class MyProfileFragment : BaseFragment(), MyProfileContract.View {

    private lateinit var ui: View

    @Inject lateinit var presenter: MyProfilePresenter

    override fun getContentView(): Int = R.layout.fragment_profile_my

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui = view

        presenter.getProfileView()

        ui.btnSignOut.setOnClickListener { presenter.doSignOut() }
        ui.cvPhoto.setOnClickListener { isGalleryPermissionGranted() }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.setPhoto(activity!!, data?.data)
    }

    override fun getProgressBar(): View {
        val ui = activity as MainActivity
        return ui.rlProgressBar
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessProfileView(user: User?) {
        ui.tvName.text = user?.name
        ui.tvEmail.text = user?.email
        Picasso.get()
            .load(user?.photo)
            .placeholder(activity?.resources!!.getDrawable(R.drawable.logo_no_photo))
            .error(activity?.resources!!.getDrawable(R.drawable.logo_no_photo))
            .into(ui.ivPhoto)
        ui.tvPoint.text = "Point: ${user?.point}"
        ui.tvLevel.text = "Level: ${user?.level}"
    }

    override fun onSignOut() {
        AlertDialog.Builder(activity)
            .setTitle(getString(R.string.text_title_alert_sign_out))
            .setMessage(getString(R.string.text_message_alert_sign_out))
            .setPositiveButton(getString(R.string.text_btn_alert_confirm)) {_, _ ->

                startActivity(activity?.intentFor<SignInActivity>()
                    ?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                activity?.finish()

                val prefs = activity?.getSharedPreferences(Constant.Tag.REMEMBER_ME, Context.MODE_PRIVATE)
                prefs?.edit()
                    ?.clear()
                    ?.apply()

            }
            .setNegativeButton(getString(R.string.text_btn_alert_cancel)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()
    }

    private fun isGalleryPermissionGranted() {
        if(PermissionUtil.isExternalStorageGranted(activity!!))
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Constant.Tag.EXTERNAL_STORAGE_PERMISSION)
        else
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.Tag.EXTERNAL_STORAGE_PERMISSION)
    }

}