package com.example.timebarteryeah.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timebarteryeah.R
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.models.constant.Constant
import com.example.timebarteryeah.views.activity.DetailAcceptPostActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_post.view.*
import java.util.*

class AcceptPostAdapter(private val context: Context,
                  private val postList: List<Post?>,
                  private val userList: List<User?>) : RecyclerView.Adapter<AcceptPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_post, parent, false))

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindPost(postList[position]!!, userList[position]!!)
    }

    inner class ViewHolder(private val ui: View) : RecyclerView.ViewHolder(ui) {

        @SuppressLint("SetTextI18n")
        fun bindPost(post: Post, user: User) {
            ui.tvName.text = user.name
            Picasso.get()
                .load(user.photo)
                .placeholder(context.resources.getDrawable(R.drawable.logo_no_photo))
                .error(context.resources.getDrawable(R.drawable.logo_no_photo))
                .into(ui.ivPhotoUser)

            ui.tvTitlePost.text = post.title
            ui.tvDescPost.text = post.desc

            val dateFormat = DateFormat.format("EEE, d MMM yyyy HH:mm", Date(post.date!!))
            ui.tvDate.text = dateFormat
            ui.tvDuration.text = "${post.duration}H"

            Picasso.get()
                .load(post.photo)
                .placeholder(context.resources.getDrawable(R.drawable.logo_no_photo))
                .error(context.resources.getDrawable(R.drawable.logo_no_photo))
                .into(ui.ivPhotoPost)

            val timeDone: CharSequence?
            if(post.timeDone != null)
                timeDone = DateFormat.format("EEE, d MMM yyyy HH:mm", Date(post.timeDone))
            else
                timeDone = null

            ui.setOnClickListener {
                val toDetail = Intent(context, DetailAcceptPostActivity::class.java)
                toDetail.putExtra(Constant.Tag.USER_NAME, user.name)
                toDetail.putExtra(Constant.Tag.DATE_FORMAT, dateFormat)
                toDetail.putExtra(Constant.Tag.USER_PHOTO, user.photo)
                toDetail.putExtra(Constant.Tag.POST_PHOTO, post.photo)
                toDetail.putExtra(Constant.Tag.POST_TILE, post.title)
                toDetail.putExtra(Constant.Tag.POST_DESC, post.desc)
                toDetail.putExtra(Constant.Tag.POST_CATEGORY, post.category)
                toDetail.putExtra(Constant.Tag.POST_DOCUMENT_ID, post.documentId)
                toDetail.putExtra(Constant.Tag.POST_DURATION, post.duration.toString())
                toDetail.putExtra(Constant.Tag.POST_STATUS, post.status)
                toDetail.putExtra(Constant.Tag.POST_TIME_DONE, timeDone)
                toDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                context.startActivity(toDetail)
            }

        }

    }

}