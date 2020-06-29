package com.ahmedali.a7danatproject.haddana

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedali.a7danatproject.R
import com.ahmedali.a7danatproject.haddana.adapter.PaginationAdapter
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode
import com.ahmedali.a7danatproject.normal_user.U_profileActivity
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions
import kotlinx.android.synthetic.main.activity_h_home_activity.*


class H_home_activity : AppCompatActivity() {

    private lateinit var options: DatabasePagingOptions<admin_post_mode>
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mPaginationAdapter: PaginationAdapter

    private lateinit var online_user: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_home_activity)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar2) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        findViewById<View>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(applicationContext, add_place_details::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id",getIntent().getStringExtra("id"))
            startActivity(intent)


        }


        firebaseDatabase = FirebaseDatabase.getInstance()
        online_user = firebaseDatabase!!.getReference("adminposts");
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
                        SnapshotParser { snapshot -> snapshot.getValue(admin_post_mode::class.java)!!
                        })
                .setQuery(online_user, config, admin_post_mode::class.java)

                .build()
        online_user.keepSynced(true)
        mPaginationAdapter =
                PaginationAdapter(this@H_home_activity,
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return when (id) {
            R.id.profile -> {
                val i = Intent(this, H_profileActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                true
            }
            R.id.logout -> {
                val editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit()
                editor.putString("id", "-1")
                editor.putString("id_type", "-1")

                editor.apply()
                Toast.makeText(applicationContext, "logout", Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext, "logout", Toast.LENGTH_LONG).show()

                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}