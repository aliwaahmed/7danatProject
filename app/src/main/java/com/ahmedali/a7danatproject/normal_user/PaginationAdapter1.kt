package com.ahmedali.a7danatproject.normal_user

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedali.a7danatproject.R
import com.ahmedali.a7danatproject.haddana.H_home_activity
import com.ahmedali.a7danatproject.haddana.ShowDetails.ShowDetails
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode
import com.bumptech.glide.Glide
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter
import com.shreyaspatil.firebase.recyclerpagination.LoadingState
import kotlinx.android.synthetic.main.post_item.view.*
import kotlinx.android.synthetic.main.post_item.view.card
import kotlinx.android.synthetic.main.post_item.view.imageView8
import kotlinx.android.synthetic.main.post_item.view.textView3
import kotlinx.android.synthetic.main.post_item.view.textView4
import kotlinx.android.synthetic.main.post_item1.view.*

class PaginationAdapter1(var activity: U_HomeActivity, var options: DatabasePagingOptions<admin_post_mode>,
                         private val context: Context) : FirebaseRecyclerPagingAdapter<admin_post_mode, mViewholder>(options) {
    override fun onBindViewHolder(holder: mViewholder, i: Int, userData: admin_post_mode) {


        if(userData.enable.equals("true")) {

            Glide.with(context).load(userData.img1).into(holder.itemView.imageView8);
            holder.itemView.textView3.setText(userData.details)
            holder.itemView.textView4.visibility=View.GONE


            holder.itemView.location.setText(userData.adresse.toString())
            holder.itemView.textView8.setText(userData.price)


            holder.itemView.card.setOnClickListener(View.OnClickListener {

                options!!.data.observe(activity, androidx.lifecycle.Observer {

                    Log.e("idali", it.get(i)!!.ref.key.toString())

                val intent = Intent(context, u_details::class.java);
                intent.putExtra("iteam", userData)
                intent.putExtra("post_id", it.get(i)!!.ref.key.toString())
                context.startActivity(intent)


                })

            })
        }
        else
        {
            holder.itemView.card.visibility=View.GONE
        }

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
        return mViewholder(LayoutInflater.from(parent.context).inflate(R.layout.post_item1, parent, false))
    }

}

class mViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)