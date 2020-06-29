package com.ahmedali.a7danatproject.normal_user

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ahmedali.a7danatproject.R
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_u_details.*


class u_details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_u_details)
        val sliderView = findViewById<SliderView>(R.id.imageSlider)
        val adapter = SliderAdapterExample(this)
        sliderView.setSliderAdapter(adapter)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 1 //set scroll delay in seconds :
        sliderView.startAutoCycle()
        val post_mode: admin_post_mode = intent.getParcelableExtra("iteam")
        adapter.addItem(post_mode.img1)
        adapter.addItem(post_mode.img2)
        adapter.addItem(post_mode.img3)
        adapter.notifyDataSetChanged()

        textView24.setText(post_mode.details.toString())
        textView22.setText(post_mode.name.toString())
        textView14.setText(post_mode.adresse.toString())
        textView20.setText(post_mode.count.toString())
        textView16.setText(post_mode.price.toString())
        textView18.setText(post_mode.phone.toString())

        button3.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+post_mode.phone.toString())
            startActivity(intent)
        })

        button4.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this

            intent.putExtra(Intent.EXTRA_EMAIL,post_mode.admin_mail)
            intent.putExtra(Intent.EXTRA_SUBJECT, "استفسار حول الحضانه")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        })
    }
}