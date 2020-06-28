package com.ahmedali.a7danatproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.ahmedali.a7danatproject.haddana.H_LoginActivity;
import com.ahmedali.a7danatproject.normal_user.U_LoginActivity;

public class MainActivity extends AppCompatActivity {

    CardView user , inc ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id._user);
        inc = findViewById(R.id._hospital);

        SharedPreferences sharedPreferences =getSharedPreferences("login",MODE_PRIVATE);
        String name = sharedPreferences.getString("id_type", "-") ;//"No name defined" is the default value.

        if(name.equals("admin"))
        {
            user.setVisibility(View.INVISIBLE);
            inc.setVisibility(View.VISIBLE);
        }
        else if(name.equals("user"))
        {
            inc.setVisibility(View.INVISIBLE);

            user.setVisibility(View.VISIBLE);
        }
        else
        {
            inc.setVisibility(View.VISIBLE);

            user.setVisibility(View.VISIBLE);
        }
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this  , U_LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this  , H_LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }
}