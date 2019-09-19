package com.example.whatsapp.user_auth.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.MainActivity;
import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResisterActivity extends AppCompatActivity {
    private EditText et_email_resister_activity,et_password_resister_activity;
    private Button bt_create_new_account_resister_activity;
    private TextView tv_already_have_resister_activity;
    private DatabaseReference databaseReference;
    private FirebaseAuth resistationAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        progressDialog = new ProgressDialog(this);

        tv_already_have_resister_activity = findViewById(R.id.tv_already_have_resister_activity);
        bt_create_new_account_resister_activity = findViewById(R.id.bt_create_new_account_resister_activity);
        et_password_resister_activity = findViewById(R.id.et_password_resister_activity);
        et_email_resister_activity = findViewById(R.id.et_email_resister_activity);
        bt_create_new_account_resister_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_an_accoutn();

                Toast.makeText(ResisterActivity.this, "Create New Account", Toast.LENGTH_SHORT).show();
            }
        });
        tv_already_have_resister_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void create_an_accoutn() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        resistationAuth = FirebaseAuth.getInstance();

        String email = et_email_resister_activity.getText().toString().trim();
        String password = et_password_resister_activity.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            et_email_resister_activity.setError("Enter gmail Acoount");
            et_email_resister_activity.requestFocus();
            return;
        }else if (TextUtils.isEmpty(password)){
            et_password_resister_activity.setError("Enter Password");
            et_password_resister_activity.requestFocus();
            return;
        }else {
            progressDialog.setTitle("Creating New Account");
            progressDialog.setMessage("please wait while we are crating new account for you");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            resistationAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ResisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(ResisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        String messaage = task.getException().toString();
                        Toast.makeText(ResisterActivity.this, "Error: "+messaage, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
