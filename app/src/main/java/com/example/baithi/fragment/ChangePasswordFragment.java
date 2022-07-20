package com.example.baithi.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baithi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragment extends Fragment {

    private View view;

    private EditText edit_text_password, edit_text_new_password, edit_text_new_confirm_new_password;
    private TextInputLayout text_old_password, text_new_password, text_new_confirm_new_password;

    private Button btn_change_password;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password, container,false);

        initUi();
        initListener();

        return view;
    }

    private void initUi(){
        edit_text_password = view.findViewById(R.id.edit_text_password);
        edit_text_new_password = view.findViewById(R.id.edit_text_new_password);
        edit_text_new_confirm_new_password = view.findViewById(R.id.edit_text_new_confirm_new_password);
        btn_change_password = view.findViewById(R.id.btn_change_password);
        text_old_password = view.findViewById(R.id.text_old_password);
        text_new_password = view.findViewById(R.id.text_new_password);
        text_new_confirm_new_password = view.findViewById(R.id.text_new_confirm_new_password);
        progressDialog = new ProgressDialog(getActivity());
    }

    private void initListener(){
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_text_password.getText().toString().trim().equalsIgnoreCase("")) {
                    text_old_password.setError("This field can not be blank");
                }
                else {
                    text_old_password.setErrorEnabled(false);
                }
                if (edit_text_new_password.getText().toString().trim().equalsIgnoreCase("")) {
                    text_new_password.setError("This field can not be blank");
                }
                else {
                    text_new_password.setErrorEnabled(false);
                }
                if (edit_text_new_confirm_new_password.getText().toString().trim().equalsIgnoreCase("")) {
                    text_new_confirm_new_password.setError("This field can not be blank");
                }
                else {
                    text_new_confirm_new_password.setErrorEnabled(false);
                }
                if (edit_text_new_password.getText().toString().trim().length() >= 6){
                    if (edit_text_password.getText().toString().trim().equalsIgnoreCase("") == false
                            && edit_text_new_password.getText().toString().trim().equalsIgnoreCase("")  == false
                            && edit_text_new_confirm_new_password.getText().toString().trim().equalsIgnoreCase("") == false){
                        if (edit_text_new_password.getText().toString().trim().equals(edit_text_new_confirm_new_password.getText().toString().trim())){
                            onClickChangePassword();
                        } else {
                            edit_text_new_confirm_new_password.setError("Re-entered password is incorrect");
                        }
                    }
                } else {
                    text_new_password.setError("Password must be >6 characters");
                }



            }
        });
    }

    private void onClickChangePassword() {
        String strOldPassword = edit_text_password.getText().toString().trim();
        String strNewPassword = edit_text_new_password.getText().toString().trim();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(strNewPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "User password updated.", Toast.LENGTH_LONG).show();
                            edit_text_new_password.setText("");
                            edit_text_new_confirm_new_password.setText("");
                        }
                        /*
                        else {
                            // show dialog de lay du lieu reAuthenticate();
                        }
                         */
                    }
                });
    }

    /*
    //ham nay chua can dung den

    private void reAuthenticate(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            onClickChangePassword();
                        } else {
                            //Nhap lai email va mk
                        }
                    }
                });
    }
    */
}
