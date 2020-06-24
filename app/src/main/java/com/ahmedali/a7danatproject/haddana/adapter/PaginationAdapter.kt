package com.ahmedali.a7danatproject.haddana.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedali.a7danatproject.R
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode
import com.bumptech.glide.Glide
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter
import com.shreyaspatil.firebase.recyclerpagination.LoadingState
import kotlinx.android.synthetic.main.post_item.view.*

class PaginationAdapter(options: DatabasePagingOptions<admin_post_mode>, private val context: Context) : FirebaseRecyclerPagingAdapter<admin_post_mode, mViewholder>(options) {
    override fun onBindViewHolder(holder: mViewholder, i: Int, userData: admin_post_mode) {


        Glide.with(context).load(userData.img1).into( holder.itemView.imageView8);



        holder.itemView.textView3.setText(userData.details)
        holder.itemView.textView4.setText(userData.adresse)


    }
    override fun onLoadingStateChanged(state: LoadingState) {
        if (state == LoadingState.LOADING_INITIAL) {
            Log.e("init", "init")
        } else if (state == LoadingState.LOADING_MORE) {
            Log.e("more", "more")
        } else if (state == LoadingState.LOADED) {
        } else if (state == LoadingState.FINISHED) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewholder {
        return mViewholder(LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false))
    }

}

class mViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)