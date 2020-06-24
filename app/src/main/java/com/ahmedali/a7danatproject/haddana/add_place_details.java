package com.ahmedali.a7danatproject.haddana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedali.a7danatproject.R;
import com.ahmedali.a7danatproject.haddana.model.admin_post_mode;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_place_details extends AppCompatActivity {

    admin_post_mode post;
    EditText place_name, place_phone, place_details, place_price, Count, address;
    private StorageReference mStorageRef;
    ProgressDialog pd;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_details);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        post = new admin_post_mode();
        place_name = findViewById(R.id.place_name);
        place_phone = findViewById(R.id.place_phone);
        place_details = findViewById(R.id.place_details);
        place_price = findViewById(R.id.place_price);
        Count = findViewById(R.id.Count);
        address = findViewById(R.id.address);

        pd = new ProgressDialog(add_place_details.this);


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

//                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
//                    String uid = current_user.getUid();
//
//                    mDatabase = FirebaseDatabase.getInstance().getReference().child("h_Users")
//                            .child("id");
//                    mDatabase.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

                    post.setAdmin_name("q");
                    post.setAdmin_img("q");
                    post.setAdmin_mail("q");
                    post.setName(place_name.getText().toString());
                    post.setPhone(place_phone.getText().toString());
                    post.setDetails(place_details.getText().toString());
                    post.setPrice(place_price.getText().toString());
                    post.setCount(Count.getText().toString());
                    post.setAdresse(address.getText().toString());


                    if ((!post.getAdmin_img().isEmpty()) &&
                            (post.getImg1() != null) &&
                            (post.getImg2() != null) &&
                            (post.getImg3() != null) &&
                            (!post.getName().isEmpty()) &&
                            (!post.getAdmin_mail().isEmpty()) &&
                            (!post.getAdmin_name().isEmpty()) &&
                            (!post.getPhone().isEmpty()) &&
                            (!post.getAdresse().isEmpty()) &&
                            (!post.getPrice().isEmpty()) &&
                            (!post.getCount().isEmpty()) &&
                            (!post.getDetails().isEmpty()) &&
                            (!post.getPrice().isEmpty())) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.setMessage("loading");
                                pd.show();
                                pd.setCancelable(false);
                            }
                        });


                        StorageReference riversRef = mStorageRef.child(String.valueOf(System.currentTimeMillis()) + Uri.parse(post.getImg1()).getLastPathSegment());
                        riversRef.putFile(Uri.parse(post.getImg1()))
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Get a URL to the uploaded content

                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                post.setImg1(String.valueOf(uri));

                                            }
                                        });


                                        StorageReference riversRef = mStorageRef.child(String.valueOf(System.currentTimeMillis()) + Uri.parse(post.getImg2()).getLastPathSegment());

                                        riversRef.putFile(Uri.parse(post.getImg2()))
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                post.setImg2(String.valueOf(uri));

                                                            }
                                                        });
                                                        StorageReference riversRef = mStorageRef.child(String.valueOf(System.currentTimeMillis()) + Uri.parse(post.getImg3()).getLastPathSegment());

                                                        riversRef.putFile(Uri.parse(post.getImg3()))
                                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                                                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                            @Override
                                                                            public void onSuccess(Uri uri) {
                                                                                post.setImg3(String.valueOf(uri));

                                                                            }
                                                                        });

                                                                        // Write a message to the database
                                                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                                        DatabaseReference myRef = database.getReference("adminposts").push();

                                                                        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                onBackPressed();
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {

                                                                                if (pd.isShowing()) {
                                                                                    pd.dismiss();
                                                                                    pd.cancel();
                                                                                    onBackPressed();
                                                                                }
                                                                            }
                                                                        });


                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception exception) {
                                                                        // Handle unsuccessful uploads
                                                                        // ...
                                                                        if (pd.isShowing()) {
                                                                            pd.dismiss();
                                                                            pd.cancel();

                                                                            onBackPressed();
                                                                        }
                                                                    }
                                                                });


                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception exception) {
                                                        // Handle unsuccessful uploads
                                                        // ...
                                                        if (pd.isShowing()) {
                                                            pd.dismiss();
                                                            pd.cancel();

                                                            onBackPressed();
                                                        }
                                                    }
                                                });


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                        if (pd.isShowing()) {
                                            pd.dismiss();
                                            pd.cancel();

                                            onBackPressed();
                                        }
                                    }
                                });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getApplicationContext(), "Check your Data", Toast.LENGTH_LONG).show();

                            }
                        });

                    }


                } catch (final Exception e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                            onBackPressed();
                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    });

                }


            }
        });


        findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(add_place_details.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(100);
            }
        });

        findViewById(R.id.imageView7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(add_place_details.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(200);
            }
        });
        findViewById(R.id.imageView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(add_place_details.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(300);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case 100:
                    ImageView imageView1 = findViewById(R.id.imageView2);
                    imageView1.setImageURI(data.getData());
                    post.setImg1(String.valueOf(data.getData()));

                    break;
                case 200:
                    ImageView imageView2 = findViewById(R.id.imageView3);
                    imageView2.setImageURI(data.getData());
                    post.setImg2(String.valueOf(data.getData()));

                    break;
                case 300:
                    ImageView imageView3 = findViewById(R.id.imageView4);
                    imageView3.setImageURI(data.getData());
                    post.setImg3(String.valueOf(data.getData()));

                    break;

            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}