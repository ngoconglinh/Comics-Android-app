package com.example.baithi;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

public class CategoryComicsActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Button button;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_comics);

        progressDialog = new ProgressDialog(CategoryComicsActivity.this);
        linearLayout = findViewById(R.id.linear_layout_category);
        showKK();
    }

    private void showKK(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Intent intent = getIntent();
        String strNameFolder = intent.getStringExtra("NameFolder");
        StorageReference imagesRef = storageRef.child(strNameFolder);
        //Toast.makeText(CategoryComicsActivity.this, strNameFolder, Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        imagesRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        progressDialog.dismiss();
                        String text = "";
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            text = prefix.getName();
                            button = new Button(CategoryComicsActivity.this);
                            button.setText(text);
                            button.setTextSize(30);
                            button.setTextColor(Color.parseColor("#89FF00"));
                            button.setBackgroundColor(Color.parseColor("#E91E63"));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, 300
                            );
                            params.setMargins(20, 40, 20, 0);
                            button.setLayoutParams(params);

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(CategoryComicsActivity.this ,ComicsActivity.class);
                                    intent.putExtra("NameComics",  prefix.getPath());
                                    startActivity(intent);
                                }
                            });
                            String path = prefix.getPath();
                            button.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(CategoryComicsActivity.this);
                                    builder.setMessage("Do you want to delete "+ prefix.getName() +"?");
                                    builder.setTitle("Alert !");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // When the user click yes button
                                                    StorageReference desertRef = FirebaseStorage.getInstance().getReference().child(path);
                                                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // File deleted successfully
                                                            Toast.makeText( CategoryComicsActivity.this,"Successfully deleted: " + prefix.getName(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            // Uh-oh, an error occurred!
                                                            String errorMessage = exception.getMessage();
                                                            Toast.makeText( CategoryComicsActivity.this,errorMessage, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            });


                                    builder.setNegativeButton(
                                            "No",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // If user click no
                                                    // then dialog box is canceled.
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    return true;
                                }
                            });
                            linearLayout.addView(button);
                        }
                        for (StorageReference item : listResult.getItems()) {
                            // All the items under imagesRef.

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

    }

    private void showAlertDialog() {

    }

    public void btnBack(View view){
        finish();
    }
}