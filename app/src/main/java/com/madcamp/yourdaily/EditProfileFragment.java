package com.madcamp.yourdaily;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";

    private EditText nickname;
    private EditText email;
    private TextView changePhoto;
    private SimpleDraweeView profile_drawee;
    private ImageView check;

    private FirebaseAuth mAuth;
    private String mEmail;
    private Profile mProfile;

    private String key;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);

        profile_drawee = (SimpleDraweeView) view.findViewById(R.id.profile_photo);
        changePhoto = (TextView)view.findViewById(R.id.changeProfilePhoto);
        nickname = (EditText)view.findViewById(R.id.username);
        email = (EditText)view.findViewById(R.id.display_name);
        check = (ImageView)view.findViewById(R.id.saveChanges);

        mAuth = FirebaseAuth.getInstance();
        mEmail = mAuth.getCurrentUser().getEmail();
        mProfile = new Profile();

        new FirebaseDatabaseProfile().readProfiles(new FirebaseDatabaseProfile.DataStatus() {
            @Override
            public void DataIsLoaded(List<Profile> profiles, List<String> keys) {
                for(int i=0; i<profiles.size(); i++){
                    if(mEmail.equals(profiles.get(i).getEmail())){
                        mProfile = profiles.get(i);
                        email.setText(mProfile.getEmail());
                        nickname.setText(mProfile.getNick());
                        profile_drawee.setImageURI(Uri.parse(mProfile.getProfileImage()));
                        key = keys.get(i);
                        break;
                    }
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Photo Change Not Implemented Yet", Toast.LENGTH_SHORT).show();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfile.setNick(nickname.getText().toString());
                mProfile.setEmail(email.getText().toString());

                new FirebaseDatabaseProfile().updatePreDaily(key, mProfile, new FirebaseDatabaseProfile.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Profile> profiles, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(getContext(), "changes saved successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        //back arrow
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to Profile Activity");
                getActivity().finish();
            }
        });

        return view;

    }


}
