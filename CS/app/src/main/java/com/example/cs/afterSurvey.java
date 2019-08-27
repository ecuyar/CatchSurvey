package com.example.cs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class afterSurvey extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase fireDB;
    private FirebaseAuth.AuthStateListener authList;
    private DatabaseReference DBref,ValRef,DBAvgref;
    private FirebaseUser CurUser;
    private TextView mpuanText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_survey);

        mpuanText = findViewById(R.id.puanText);

        auth = FirebaseAuth.getInstance();
        CurUser = auth.getCurrentUser();
        fireDB = FirebaseDatabase.getInstance();
        DBref = fireDB.getReference("users");
        DBAvgref = fireDB.getReference("items").child("books").child("Araba Sevdası - Recaizade Mahmut Ekrem");
        ValRef = DBref.child(CurUser.getUid());

        Query query = DBref.orderByChild("mail").equalTo(CurUser.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String mpoint = "" + ds.child("point").getValue();

                    int newPoint = Integer.parseInt(mpoint) + 10;
                    mpoint= String.valueOf(newPoint);
                    mpuanText.setText(mpoint);

                    ValRef.child("point").setValue(mpoint);

                    DBAvgref.child("AVG").setValue("3.8");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void btnhome(View view) {
        Intent inthome = new Intent(this, MainActivity.class);
        startActivity(inthome);
    }
}
