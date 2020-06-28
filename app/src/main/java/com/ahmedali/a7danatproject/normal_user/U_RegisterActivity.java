package com.ahmedali.a7danatproject.normal_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedali.a7danatproject.R;
import com.ahmedali.a7danatproject.haddana.H_LoginActivity;
import com.ahmedali.a7danatproject.haddana.H_RegisterActivity;
import com.ahmedali.a7danatproject.haddana.H_home_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class U_RegisterActivity extends AppCompatActivity {


    //vars
    TextInputEditText name , email , address , pass , phone;
    Button register ;
    ImageView back ;

    //firebase vars
    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u__register);

        //vars initialization
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.password);
        register = findViewById(R.id.register_btn);
        back = findViewById(R.id.back_btn);



        //dialog display
        mProgress = new ProgressDialog(this);

        //firebase auth init
        mAuth = FirebaseAuth.getInstance();

        //back button click
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(U_RegisterActivity.this , U_HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        //register button click
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //take vars here
                String getemail = email.getText().toString().trim();
                String getcha_name = name.getText().toString().trim();
                String getpassword = pass.getText().toString().trim();
                String getphone = phone.getText().toString().trim();
                String getaddress = address.getText().toString().trim();


                //check if vars are empty ..
                if (!TextUtils.isEmpty(getemail) || !TextUtils.isEmpty(getpassword)){
                    mProgress.setTitle("Registering User");
                    mProgress.setMessage("Wait while we create your account");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    callsignup(getemail, getpassword, getcha_name , getphone , getaddress );
                }

            }
        });
    }

    //add data to firebase database & authentication .
    private void callsignup(final String email,final  String password, final String getcha_name,
                            final String getphone, final String getaddress) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //testing line
                        Log.d("Testing","Signup successful" + task.isSuccessful());

                        //check if process not successful
                        if (!task.isSuccessful()){
                            Toast.makeText(U_RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }
                        //if successful
                        else {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                         //   String uid = current_user.getUid();



                            mDatabase = FirebaseDatabase.getInstance().getReference().child("h_Users")
                                    .child(String.valueOf(email.replace("@",""))
                                            .replace(".",""));
                            //adding data to firebase database (Realtime-database)
                            HashMap<String , String> userMap = new HashMap<>();
                            userMap.put("h_email", email);
                            userMap.put("h_name", getcha_name);
                            userMap.put("h_phone",getphone);
                            userMap.put("h_address",getaddress);
                            userMap.put("image","");
                            userMap.put("thumb_image","");

                            mDatabase.setValue(userMap);

                        }
                        //if completely successful go to pharmacy home activity
                        if (task.isSuccessful()){
                            userProfile();
                            Toast.makeText(U_RegisterActivity.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                            mProgress.hide();
                            Intent i = new Intent(U_RegisterActivity.this , U_HomeActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            UserProfileChangeRequest profileupdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim()).build();

            user.updateProfile(profileupdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Log.d("TESTING","User profile updated.");
                    }
                }
            });
        }

    }
}