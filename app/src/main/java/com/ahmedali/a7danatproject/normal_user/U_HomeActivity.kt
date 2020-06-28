package com.ahmedali.a7danatproject.normal_user

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedali.a7danatproject.R
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.*
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions
import kotlinx.android.synthetic.main.activity_h_home_activity.*

class U_HomeActivity : AppCompatActivity() {
    private lateinit var options: DatabasePagingOptions<admin_post_mode>
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mPaginationAdapter: PaginationAdapter1

    private lateinit var online_user: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u__home)


        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("adminposts").ref























        firebaseDatabase = FirebaseDatabase.getInstance()
        online_user = firebaseDatabase!!.getReference("adminposts")

        LoadOnlineUsers()
    }
    fun LoadOnlineUsers() {

        //Initialize PagedList Configuration
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()


        //Initialize FirebasePagingOptions
        options = DatabasePagingOptions.Builder<admin_post_mode>()
                .setLifecycleOwner(this)
                .setQuery(
                        online_user,
                        config,
                        SnapshotParser { snapshot -> snapshot.getValue(admin_post_mode::class.java)!! })
                .setQuery(online_user, config, admin_post_mode::class.java)

                .build()
        online_user.keepSynced(true)
        mPaginationAdapter =
                PaginationAdapter1(this@U_HomeActivity,
                        options,
                        this
                )

        recyclerView.layoutManager = GridLayoutManager(this,1)
        recyclerView.setAdapter(mPaginationAdapter)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



}