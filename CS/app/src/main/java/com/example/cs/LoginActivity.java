package com.example.cs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText txtMail, txtPassword;
    private Button btnlogin;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    private void init() {

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        txtMail = findViewById(R.id.txtMail);
        txtPassword = findViewById(R.id.txtPassword);
        btnlogin = findViewById(R.id.btnlogin);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
            }
        });

    }

    private void loginUser() {

        String email = txtMail.getText().toString();
        String password = txtPassword.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"E-mail alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)){

            Toast.makeText(this,"Şifre alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else {

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        checkEmailVerification();
                    } else {

                        Toast.makeText(LoginActivity.this,"Giriş Yapılamadı!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(LoginActivity.this, MainActivity.class));

        if(emailflag){
            Toast.makeText(LoginActivity.this,"Giriş Başarılı!", Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }else{
            Toast.makeText(this, "Lütfen hesabınızı onaylayın.", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }
    }

    public void PasswordActivity(View view) {
        Intent forgetpassword = new Intent(getApplicationContext(),PasswordActivity.class);
        startActivity(forgetpassword);
    }

    public void register(View view) {
        Intent register = new Intent(getApplicationContext(),register.class);
        startActivity(register);
    }
}
