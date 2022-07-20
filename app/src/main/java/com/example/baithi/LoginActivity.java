package com.example.baithi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email, password;
    private EditText editText_email, editText_password;
    private Button button_login;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        rememberLogin();
        initListener();
    }

    private void initUi(){
        editText_email = findViewById(R.id.edit_text_email);
        editText_password = findViewById(R.id.edit_text_password);
        button_login = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        sharedPreferences = getSharedPreferences("remember_login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private void rememberLogin(){
        String shared_email = sharedPreferences.getString("email", "");
        String shared_password = sharedPreferences.getString("password", "");

        if (shared_email != "" && shared_password != ""){
            editText_email.setText(shared_email);
            editText_password.setText(shared_password);
            checkBox.setChecked(true); ;
        }
    }
    private void initListener() {
       button_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (editText_email.getText().toString().trim().equalsIgnoreCase("")) {
                   email.setError("This field can not be blank");
               }
               else {
                   email.setErrorEnabled(false);
               }
               if (editText_password.getText().toString().trim().equalsIgnoreCase("")) {
                   password.setError("This field can not be blank");
               }
               else {
                   password.setErrorEnabled(false);
               }

               if (editText_password.getText().toString().trim().length() >= 6){
                   if (editText_email.getText().toString().trim().equalsIgnoreCase("") == false
                           && editText_password.getText().toString().trim().equalsIgnoreCase("")  == false){
                       onClickLogin();
                   }
               } else {
                   password.setError("Password must be >6 characters");
               }
           }
       });
    }

    public void onClickLogin(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String strEmail = editText_email.getText().toString().trim();
        String strPass = editText_password.getText().toString().trim();
        progressDialog.setMessage("Login...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i1);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        if (checkBox.isChecked()){
            editor.putString("email", strEmail);
            editor.putString("password", strPass);
            editor.commit();
        }
    }

    public void signup(View v){
        Intent i1 = new Intent(this, SignUpActivity.class);
        startActivity(i1);
    }
}