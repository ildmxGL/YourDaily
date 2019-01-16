package com.madcamp.yourdaily;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.UUID;


public class AddPreDaily extends AppCompatActivity {
    private static final String TAG = "AddPreDaily";
    ImageView CenterImageView;
    Button UploadButton;
    private String ImageFilePath = "";
    private Uri contentUri = null;
    private String ImageName = "";
    //StorageReference storageRef = storage.getReference();

    //Variables for making preDaily
    private String ImageUri = "";
    private String Hashtag = "";
    private String Title = "";
    private String UserEmail = "";
    private String UserNick = "";

    private String preHashtag = "";

    //For sending Firebase a pic
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EditText titleEdit;
    private EditText hashEdit;

    private FirebaseAuth mAuth;

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pre_daily);
        requestMultiplePermissions();
        CenterImageView = (ImageView) findViewById(R.id.predaily_image);
        UploadButton = (Button) findViewById(R.id.upload_predaily);
        titleEdit = (EditText) findViewById(R.id.title_edittext);
        hashEdit = (EditText) findViewById(R.id.hashtag_edittext);

        mAuth = FirebaseAuth.getInstance();
        UserEmail = mAuth.getCurrentUser().getEmail();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        CenterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        UserNick = MainActivity.getUserNick();

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if(ImageFilePath.isEmpty()){
                    Log.d(TAG, "onClick: Image is Empty");
                    Toast.makeText(AddPreDaily.this, "Image is Empty!", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                Log.d(TAG, "onClick: Yeah Yeah");
                Hashtag = hashEdit.getText().toString();
                Title = titleEdit.getText().toString();

                uploadImage();



            }
        });

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    contentUri = contentURI;
                    //Toast.makeText(AddPreDaily.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    CenterImageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(AddPreDaily.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            contentUri = getImageUri(AddPreDaily.this, thumbnail);
            CenterImageView.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            //Toast.makeText(AddPreDaily.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            //FileOutputStream fo = new FileOutputStream(f);
            //fo.write(bytes.toByteArray());
            //MediaScannerConnection.scanFile(this,
            //        new String[]{f.getPath()},
            //        new String[]{"image/jpeg"}, null);
            //fo.close();
            ImageFilePath = f.getAbsolutePath();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void uploadImage() {

        if(contentUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            Log.d(TAG, "uploadImage: ");

            ImageName = UUID.randomUUID().toString();
            final StorageReference ref = storageReference.child("images/"+ ImageName);
            ref.putFile(contentUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddPreDaily.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess: ImageName : "+ImageName);
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ImageUri = uri.toString();
                                    Hashtag = Hashtag.trim();
                                    //String[] HashtagSplit = Hashtag.split("#");
                                    //Hashtag = "";
                                    //for (String hash : HashtagSplit){
                                    //    Hashtag = Hashtag + ", " + hash;
                                    //}

                                    PreDaily preDaily = new PreDaily();
                                    preDaily.setHashtag(Hashtag);
                                    preDaily.setImageUri(ImageUri);
                                    preDaily.setTitle(Title);
                                    preDaily.setUserEmail(UserEmail);
                                    preDaily.setUserNick(UserNick);
                                    new FirebaseDatabasePreDaily().addPreDaily(preDaily, new FirebaseDatabasePreDaily.DataStatus() {
                                        @Override
                                        public void DataIsLoaded(List<PreDaily> preDailies, List<String> keys) {

                                        }

                                        @Override
                                        public void DataIsInserted() {
                                            Toast.makeText(AddPreDaily.this, "Accepted Successfully", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void DataIsUpdated() {

                                        }

                                        @Override
                                        public void DataIsDeleted() {

                                        }
                                    });

                                    finish();

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddPreDaily.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFailure: ");
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



}
