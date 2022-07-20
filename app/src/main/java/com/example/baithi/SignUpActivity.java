package com.example.baithi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText editMail, editPass, editConfirmPass;
    private TextInputLayout tiplayout_email, tiplayout_pass, tiplayout_confirm_pass;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUi();
        initListener();
    }

    private void initUi(){

        editMail = findViewById(R.id.email);
        editPass = findViewById(R.id.pass);
        editConfirmPass = findViewById(R.id.confirm_pass);
        btnSignUp = findViewById(R.id.signup);
        tiplayout_email = findViewById(R.id.tiplayout_email);
        tiplayout_pass = findViewById(R.id.tiplayout_pass);
        tiplayout_confirm_pass = findViewById(R.id.tiplayout_confirm_pass);

        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMail.getText().toString().trim().equalsIgnoreCase("")) {
                    tiplayout_email.setError("This field can not be blank");
                }
                else {
                    tiplayout_email.setErrorEnabled(false);
                }
                if (editPass.getText().toString().trim().equalsIgnoreCase("")) {
                    tiplayout_pass.setError("This field can not be blank");
                }
                else {
                    tiplayout_pass.setErrorEnabled(false);
                }
                if (editConfirmPass.getText().toString().trim().equalsIgnoreCase("")) {
                    tiplayout_confirm_pass.setError("This field can not be blank");
                }
                else {
                    tiplayout_confirm_pass.setErrorEnabled(false);
                }


                if (editPass.getText().toString().trim().length() >= 6){
                    if (editMail.getText().toString().trim().equalsIgnoreCase("") == false
                            && editPass.getText().toString().trim().equalsIgnoreCase("")  == false
                            && editConfirmPass.getText().toString().trim().equalsIgnoreCase("") == false){
                        if (editPass.getText().toString().trim().equals(editConfirmPass.getText().toString().trim())){
                            //Toast.makeText(SignUpActivity.this, "Registration successful, please login!.", Toast.LENGTH_LONG).show();
                            onClickSignUp();
                        } else {
                            tiplayout_confirm_pass.setError("Re-entered password is incorrect");
                        }
                    }
                } else {
                    tiplayout_pass.setError("Password must be >6 characters");
                }

            }
        });
    }

    private void onClickSignUp() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String strEmail = editMail.getText().toString().trim();
        String strPass = editPass.getText().toString().trim();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Registration successful, please login!.", Toast.LENGTH_LONG).show();
                            Intent i1 = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(i1);
                            //finishAffinity(); //dong het tat ca activity chay truoc no
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void login(View v){
        Intent i1 = new Intent(this, LoginActivity.class);
        startActivity(i1);
    }
}