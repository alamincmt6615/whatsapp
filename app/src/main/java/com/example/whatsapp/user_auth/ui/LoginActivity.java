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

public class LoginActivity extends AppCompatActivity {

    private EditText et_email_login_activity,et_password_login_activity;
    private TextView tv_forgot_password_login_activity,tv_create_new_login_activity;
    private Button bt_longin_login_activity,bt_longin_useingPhone_login_activity;
    private DatabaseReference databaseReference;
    private FirebaseAuth loginAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        et_email_login_activity = findViewById(R.id.et_email_login_activity);
        et_password_login_activity = findViewById(R.id.et_password_login_activity);
        tv_forgot_password_login_activity = findViewById(R.id.tv_forgot_password_login_activity);
        et_email_login_activity = findViewById(R.id.et_email_login_activity);

        tv_create_new_login_activity = findViewById(R.id.tv_create_new_login_activity);
        bt_longin_login_activity = findViewById(R.id.bt_longin_login_activity);
        bt_longin_useingPhone_login_activity = findViewById(R.id.bt_longin_useingPhone_login_activity);

        bt_longin_login_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_with_email();
                Toast.makeText(LoginActivity.this, "Login Button", Toast.LENGTH_SHORT).show();
            }
        });
        bt_longin_useingPhone_login_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login with phone Number", Toast.LENGTH_SHORT).show();
            }
        });
        tv_create_new_login_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ResisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login_with_email() {
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
        loginAuth = FirebaseAuth.getInstance();

            String email = et_email_login_activity.getText().toString().trim();
            String password = et_password_login_activity.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                et_email_login_activity.setError("Enter gmail Acoount");
                et_email_login_activity.requestFocus();
                return;
            }else if (TextUtils.isEmpty(password)){
                et_password_login_activity.setError("Enter Password");
                et_password_login_activity.requestFocus();
                return;
            }else {
                progressDialog.setTitle("Login New Account");
                progressDialog.setMessage("please wait.....");
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.show();
                loginAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            String messaage = task.getException().toString();
                            Toast.makeText(LoginActivity.this, "Error: "+messaage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
}
