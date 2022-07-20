package com.example.baithi.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baithi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class NewComicsFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private static final int RESULT_OK = -1;
    private View view;
    private Button btn_upload, image;
    private TextView alert;
    private RadioButton radio_btn_detective;
    private RadioButton radio_btn_fairy_tales;
    private RadioButton radio_btn_funny;
    private EditText edit_text_name_comics;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private Uri ImageUri;
    private ProgressDialog progressDialog;
    private int upload_count = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_comic, container,false);
        initUi();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChoose();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpLoad();
            }
        });
        return view;
    }

    private void initUi(){
        btn_upload = view.findViewById(R.id.btn_upload_new_comics);
        image = view.findViewById(R.id.btn_select_image);
        radio_btn_detective = view.findViewById(R.id.radio_btn_detective);
        radio_btn_fairy_tales = view.findViewById(R.id.radio_btn_fairy_tales);
        radio_btn_funny = view.findViewById(R.id.radio_btn_funny);
        edit_text_name_comics = view.findViewById(R.id.edit_text_name_comics);
        alert = view.findViewById(R.id.alert);
        progressDialog = new ProgressDialog(getActivity());

    }

    private void onClickChoose(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK){
                if (data.getClipData() != null){
                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSelect = 0;
                    while (currentImageSelect<countClipData){
                        ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        ImageList.add(ImageUri);
                        currentImageSelect = currentImageSelect +1;
                    }
                    alert.setText("You have selected " + ImageList.size() + " image");

                } else {
                    Toast.makeText(getActivity(), "Please select more than 2 image", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    private void onClickUpLoad(){
        if (edit_text_name_comics.getText().toString().trim().equalsIgnoreCase("")){
            edit_text_name_comics.setError("Please enter Comics name");
        } else {
            progressDialog.setMessage("Image Uploading...");
            progressDialog.show();
            setFolder();
        }
    }
    private void setFolder(){
        String nameFoler = "Garbage";
        if (radio_btn_detective.isChecked()){
            nameFoler = "Detective";
        }
        if (radio_btn_fairy_tales.isChecked()){
            nameFoler = "Fairy_Tales";
        }
        if (radio_btn_funny.isChecked()){
            nameFoler = "Funny";
        }

        for (int i = 0; i <= 1; i++){
            if (i == 0){
                upload(nameFoler);
            }
            if (i == 1){
                nameFoler = "New";
                upload(nameFoler);
            }
        }
    }
    private void upload(String nameFoler){
        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child(nameFoler);
        for(upload_count = 0; upload_count <ImageList.size(); upload_count++){
            Uri IndividualImage = ImageList.get(upload_count);
            String ComicsName = edit_text_name_comics.getText().toString().trim();
            StorageReference Imagename = ImageFolder.child(ComicsName + "/" + ComicsName+ "_" + ImageList.get(upload_count).getLastPathSegment());
            progressDialog.dismiss();
            UploadTask uploadTask = Imagename.putFile(IndividualImage);
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getActivity(), "Uploaded failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(getActivity(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                }
            });




 /*
            Imagename.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                    alert.setText("");


                    Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            StoreLink(url);
                        }

                    });

                }
            });

             */

        }
    }
    /*
    // phan code nay dung de luu ten file anh vao realtime database
    private void StoreLink(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Userone");
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Imglink", url);
        databaseReference.push().setValue(hashMap);
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
    }

     */
}
