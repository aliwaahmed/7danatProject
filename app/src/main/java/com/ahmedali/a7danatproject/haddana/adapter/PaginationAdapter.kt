package com.ahmedali.a7danatproject.haddana.adapter

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

class PaginationAdapter(var activity: H_home_activity, var options: DatabasePagingOptions<admin_post_mode>,
                        private val context: Context) : FirebaseRecyclerPagingAdapter<admin_post_mode, mViewholder>(options) {
    override fun onBindViewHolder(holder: mViewholder, i: Int, userData: admin_post_mode) {

        val prefs = context.getSharedPreferences("login", Context.MODE_PRIVATE)
        val name = prefs.getString("id", "No name defined") //"No name defined" is the default value.

        if(userData.admin_mail.equals(name))
{

    Glide.with(context).load(userData.img1).into(holder.itemView.imageView8);
    holder.itemView.textView3.setText(userData.details)
    holder.itemView.textView4.setText(userData.adresse)




    holder.itemView.card.setOnClickListener(View.OnClickListener {

        options!!.data.observe(activity, androidx.lifecycle.Observer {

            Log.e("idali", it.get(i)!!.ref.key.toString())

            val intent = Intent(context, ShowDetails::class.java);
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
        return mViewholder(LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false))
    }

}

class mViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)