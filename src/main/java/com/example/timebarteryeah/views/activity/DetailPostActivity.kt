package com.example.timebarteryeah.views.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.DetailPostContract
import com.example.timebarteryeah.extensions.gone
import com.example.timebarteryeah.extensions.visible
import com.example.timebarteryeah.models.constant.Constant
import com.example.timebarteryeah.presenters.DetailPostPresenter
import com.example.timebarteryeah.views.base.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.support_toolbar.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject

@Suppress("CAST_NEVER_SUCCEEDS")
class DetailPostActivity : BaseActivity(), DetailPostContract.View {

    @Inject lateinit var presenter: DetailPostPresenter

    override fun getContentView(): Int = R.layout.activity_post_detail

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        setSupportActionBar(supportToolbar)
        supportActionBar?.title = "Post Details"

        val userId = intent.getStringExtra(Constant.Tag.USER_ID_POST)
        val userName = intent.getStringExtra(Constant.Tag.USER_NAME)
        val date = intent.getStringExtra(Constant.Tag.DATE_FORMAT)
        val userPhoto = intent.getStringExtra(Constant.Tag.USER_PHOTO)
        val postPhoto = intent.getStringExtra(Constant.Tag.POST_PHOTO)
        val postTitle = intent.getStringExtra(Constant.Tag.POST_TILE)
        val postDesc = intent.getStringExtra(Constant.Tag.POST_DESC)
        val postCategory = intent.getSerializableExtra(Constant.Tag.POST_CATEGORY) as HashMap<*, *>
        val auth = intent.getStringExtra(Constant.Tag.AUTH)
        val documentId = intent.getStringExtra(Constant.Tag.POST_DOCUMENT_ID)
        val postDuration = intent.getStringExtra(Constant.Tag.POST_DURATION)
        val status = intent.getStringExtra(Constant.Tag.POST_STATUS)
        val acceptedBy = intent.getStringExtra(Constant.Tag.POST_ACCEPTED_BY)
        val timeDone = intent.getStringExtra(Constant.Tag.POST_TIME_DONE)

        tvName.text = userName
        tvDate.text = date
        Picasso.get()
                .load(userPhoto)
                .placeholder(resources.getDrawable(R.drawable.logo_no_photo))
                .error(resources.getDrawable(R.drawable.logo_no_photo))
                .into(ivPhotoUser)
        Picasso.get()
                .load(postPhoto)
                .placeholder(resources.getDrawable(R.drawable.logo_no_photo))
                .error(resources.getDrawable(R.drawable.logo_no_photo))
                .into(ivPhotoPost)

        val sb = StringBuilder()
        for(i in postCategory.values) {
            sb.append("$i, ")
        }

        tvDuration.text = "${postDuration}H"
        tvCatPost.text = "Category: $sb"
        tvTitlePost.text = postTitle
        tvDescPost.text = postDesc
        tvAcceptedBy.text = "Accepted By: $acceptedBy"

        if(userId == auth)
            btnAcceptPost.gone()
        else {
            btnDonePost.gone()
            btnAcceptPost.visible()
        }

        if(acceptedBy != "None")
            btnDonePost.visible()
        else
            btnDonePost.gone()

        if(status != "Done")
            tvStatus.text = "Status: $status"
        else {
            tvStatus.text = "Status: $status, $timeDone"
            btnDonePost.gone()
        }

        btnAcceptPost.setOnClickListener { presenter.setToListHelp(this@DetailPostActivity, documentId) }
        btnDonePost.setOnClickListener {
            AlertDialog.Builder(this@DetailPostActivity)
                    .setTitle(getString(R.string.text_btn_alert_confirm))
                    .setMessage(getString(R.string.text_message_ask_help_done))
                    .setPositiveButton(getString(R.string.text_btn_yes)) { _, _ -> presenter.setDonePost(this@DetailPostActivity, documentId) }
                    .setNegativeButton(getString(R.string.text_btn_no)) { dialog, _ -> dialog.dismiss() }
                    .setCancelable(false)
                    .show()
        }

    }

    override fun getProgressBar(): View = rlProgressBar

    override fun onToListHelp() {
        startActivity(intentFor<MainActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    override fun onDonePost() {
        startActivity(intentFor<MainActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

}