package com.ahmedali.a7danatproject.haddana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedali.a7danatproject.MainActivity;
import com.ahmedali.a7danatproject.R;
import com.ahmedali.a7danatproject.normal_user.U_profileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class H_profileActivity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    private DatabaseReference mUserDatabase , mDatabase;
    private FirebaseUser mCurrentUser;
    private CircleImageView mDisplayImage;
    private TextView mName, phoneNumber, special , address;
    private TextView editinfo;
    private Button change_image;
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDilog;
    private DatabaseReference mPatientDatabase ;
    private ProgressDialog mProgress ;


    // random name for images
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_profile);
        mDisplayImage = findViewById(R.id.profile_pic);
        mName = findViewById(R.id.info_name);
        phoneNumber = findViewById(R.id.info_phone);
        address = findViewById(R.id.info_address);
        change_image = findViewById(R.id.changeImage);
        editinfo = findViewById(R.id.editinfo);

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                editor.putString("id", "-1");
                editor.putString("id_type", "-1");

                editor.apply();

                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sharedPreferences =getSharedPreferences("login",MODE_PRIVATE);
        String name = sharedPreferences.getString("id", "No name defined") ;//"No name defined" is the default value.

        mPatientDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(name);

        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(H_profileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.edit_profile_dialog , null);
                final EditText Dname = view.findViewById(R.id.dia_name);
                final EditText Dphone = view.findViewById(R.id.dia_phone);
                final EditText Daddress = view.findViewById(R.id.dia_address);
                final Button Dsave = view.findViewById(R.id.dia_btn);
                alert.setView(view);
                final AlertDialog dialog = alert.create();
                dialog.show();

                //data
                mUserDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String info_name = dataSnapshot.child("h_name").getValue().toString();
                        String info_phone = dataSnapshot.child("h_phone").getValue().toString();
                        String info_add = dataSnapshot.child("h_address").getValue().toString();

                        Dname.setText(info_name);
                        Dphone.setText(info_phone);
                        Daddress.setText(info_add);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Dsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProgress = new ProgressDialog(H_profileActivity.this);
                        mProgress.setTitle("Saving Changes");
                        mProgress.setMessage("Please wait while we save the changes");
                        mProgress.show();

                        String DoctorName = Dname.getText().toString();
                        String DoctorAddress = Daddress.getText().toString();
                        String DoctorPhone = Dphone.getText().toString();

                        mUserDatabase.child("h_name").setValue(DoctorName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    mProgress.dismiss();


                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                        mUserDatabase.child("h_phone").setValue(DoctorPhone).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                        mUserDatabase.child("h_address").setValue(DoctorAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                        dialog.dismiss();
                    }
                });
            }
        });

        //firebase instance
        mImageStorage = FirebaseStorage.getInstance().getReference();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(H_profileActivity.this);
            }
        });

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        SharedPreferences  sharedPreferences1 =getSharedPreferences("login",MODE_PRIVATE);
        String name1= sharedPreferences.getString("id", "No name defined") ;//"No name defined" is the default value.

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("h_Users").child(name);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("h_name").getValue().toString();
                String phone = dataSnapshot.child("h_phone").getValue().toString();
                String Iaddress = dataSnapshot.child("h_address").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                phoneNumber.setText(phone);
                address.setText(Iaddress);

                //
                if (!image.equals("")) {
                    Picasso.with(H_profileActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.ic_launcher_background).into(mDisplayImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(H_profileActivity.this).load(image).placeholder(R.drawable.ic_launcher_background).into(mDisplayImage);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(H_profileActivity.this);
        }
        //profile image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mProgressDilog = new ProgressDialog(H_profileActivity.this);
                mProgressDilog.setTitle("Uploading Image...");
                mProgressDilog.setMessage("Please wait until uploading your image");
                mProgressDilog.setCanceledOnTouchOutside(false);
                mProgressDilog.show();

                final Uri resultUri = result.getUri();

                //   File thumb_filePath=new File(resultUri.getPath());

                String current_user_id = mCurrentUser.getUid();
                Uri imageUri = data.getData();

                // uploading photo to page all users
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //  imageUri.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();

                final StorageReference filepath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");
                final StorageReference thumb_filepath = mImageStorage.child("profile_images").child("thumb").child(current_user_id + ".jpg");

                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                final String downloadUrl = uri.toString();
                                mUserDatabase.child("image").setValue(downloadUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mProgressDilog.dismiss();
                                                if (task.isSuccessful()) {
                                                    // Intent selfIntent = new Intent(Settings.this, Settings.class);
                                                    //startActivity(selfIntent);
                                                    Toast.makeText(H_profileActivity.this, "Profile image stored to firebase database successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(H_profileActivity.this, "Error Occured..." + message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                mUserDatabase.child("thumb_image").setValue(downloadUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mProgressDilog.dismiss();
                                                if (task.isSuccessful()) {
                                                    Intent selfIntent = new Intent(H_profileActivity.this, H_profileActivity.class);
                                                    startActivity(selfIntent);
                                                    Toast.makeText(H_profileActivity.this, "Profile image stored to firebase database successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(H_profileActivity.this, "Error Occured..." + message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }
}