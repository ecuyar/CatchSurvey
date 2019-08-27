package com.example.cs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleSurvey extends AppCompatActivity {

    private EditText txtName, txtSurname, txtAge, txtJob, txtMail, txtTel;
    private Button btnapply;
    private FirebaseAuth auth;
    private FirebaseUser FBuser;
    private DatabaseReference rootref;

    public void init() {

        auth = FirebaseAuth.getInstance();
        rootref = FirebaseDatabase.getInstance().getReference();

        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtAge = findViewById(R.id.txtAge);
        txtJob = findViewById(R.id.txtJob);
        txtMail = findViewById(R.id.txtMail);
        txtTel = findViewById(R.id.txtTel);
        btnapply = findViewById(R.id.btnapply);
    }

    private void createNewApply() {

        final String name = txtName.getText().toString();
        final String surname = txtSurname.getText().toString();
        final String age = txtAge.getText().toString();
        final String job = txtJob.getText().toString();
        final String mail = txtMail.getText().toString();
        final String tel = txtTel.getText().toString();

        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Ad adı alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(surname)) {

            Toast.makeText(this, "Soyad alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(age)) {

            Toast.makeText(this, "Yaş alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(job)) {

            Toast.makeText(this, "Meslek alanı boş olamaz!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mail)) {

            Toast.makeText(this, "Mail alanı boş olamaz!", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(tel)) {

            Toast.makeText(this, "Telefon alanı boş olamaz!", Toast.LENGTH_LONG).show();
        }else {

                FBuser = auth.getCurrentUser();

                UserApply userIns = new UserApply(name,surname,age,job,mail,tel);

                rootref.child("applies").child("Ford").child(FBuser.getUid()).setValue(userIns)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent backmain = new Intent(VehicleSurvey.this, MainActivity.class);
                                    startActivity(backmain);
                                    finish();
                                }
                            }
                        });
            }
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_apply);
        init();

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewApply();
            }
        });
    }
}
