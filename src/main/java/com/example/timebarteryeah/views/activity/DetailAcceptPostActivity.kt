package com.example.timebarteryeah.views.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.timebarteryeah.R
import com.example.timebarteryeah.extensions.gone
import com.example.timebarteryeah.models.constant.Constant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.support_toolbar.*

class DetailAcceptPostActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        btnDonePost.gone()
        btnAcceptPost.gone()
        tvAcceptedBy.gone()

        setSupportActionBar(supportToolbar)
        supportActionBar?.title = "Post Details"

        val userName = intent.getStringExtra(Constant.Tag.USER_NAME)
        val date = intent.getStringExtra(Constant.Tag.DATE_FORMAT)
        val userPhoto = intent.getStringExtra(Constant.Tag.USER_PHOTO)
        val postPhoto = intent.getStringExtra(Constant.Tag.POST_PHOTO)
        val postTitle = intent.getStringExtra(Constant.Tag.POST_TILE)
        val postDesc = intent.getStringExtra(Constant.Tag.POST_DESC)
        val postCategory = intent.getSerializableExtra(Constant.Tag.POST_CATEGORY) as HashMap<*, *>
        val postDuration = intent.getStringExtra(Constant.Tag.POST_DURATION)
        val status = intent.getStringExtra(Constant.Tag.POST_STATUS)
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
        tvStatus.text = "Status: $status"

        if(status != "Done")
            tvStatus.text = "Status: $status"
        else
            tvStatus.text = "Status: $status, $timeDone"

    }

}