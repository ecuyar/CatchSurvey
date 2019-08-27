package com.example.cs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private EditText txtName, txtSurname, txtUsername, txtPassword , txtMail, txtCity, txtBirthDate;
    private Button btnregister;
    private FirebaseAuth auth;
    private FirebaseUser FBuser;
    private DatabaseReference rootref;

    public void init() {

        auth = FirebaseAuth.getInstance();
        rootref = FirebaseDatabase.getInstance().getReference();

        txtUsername = findViewById(R.id.txtUsername);
        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtPassword = findViewById(R.id.txtPassword);
        txtMail = findViewById(R.id.txtMail);
        txtCity = findViewById(R.id.txtCity);
        txtBirthDate = findViewById(R.id.txtBirthDate);
        btnregister = findViewById(R.id.btnregister);
    }

    private void createNewAccount() {

        final String username = txtUsername.getText().toString();
        final String name = txtName.getText().toString();
        final String surname = txtSurname.getText().toString();
        final String password = txtPassword.getText().toString();
        final String mail = txtMail.getText().toString();
        final String city = txtCity.getText().toString();
        final String birthdate = txtBirthDate.getText().toString();
        final String point = "0";

        if(TextUtils.isEmpty(username)){

            Toast.makeText(this,"Kullanıcı adı alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(name)){

            Toast.makeText(this,"Ad alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(surname)){

            Toast.makeText(this,"Soyad alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)){

            Toast.makeText(this,"Şifre alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mail)){

            Toast.makeText(this,"Mail alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(city)){

            Toast.makeText(this,"Şehir alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(birthdate)){

            Toast.makeText(this,"Doğum günü alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FBuser = auth.getCurrentUser();

                        UserPro userIns = new UserPro(username,name,surname,password,mail,city,birthdate,point);

                        rootref.child("users").child(FBuser.getUid()).setValue(userIns)

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            sendEmailVerification();
                                        }
                                    }
                                });
                    } else {

                        Toast.makeText(register.this, "Bir hata oluştu!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewAccount();
            }
        });
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Bilgileriniz başarıyla kaydedildi, hesabınızı onaylamayı unutmayın.",Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(register.this,LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }else{
                        Toast.makeText(register.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
