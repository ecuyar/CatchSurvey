package com.example.cs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseDatabase fireDB;
    private FirebaseAuth.AuthStateListener authList;
    private DatabaseReference DBref;
    private FirebaseUser CurUser;
    private ListView uList;
    private TextView proName, proSurname, proUsername, proMail, proCity, proBirthDate, proPoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        proName = v.findViewById(R.id.Name);
        proSurname = v.findViewById(R.id.Surname);
        proUsername = v.findViewById(R.id.Username);
        proMail = v.findViewById(R.id.Mail);
        proCity = v.findViewById(R.id.City);
        proBirthDate = v.findViewById(R.id.BirthDate);
        proPoint = v.findViewById(R.id.Totalscore);

        auth = FirebaseAuth.getInstance();
        CurUser = auth.getCurrentUser();
        fireDB = FirebaseDatabase.getInstance();
        DBref = fireDB.getReference("users");

        Query query = DBref.orderByChild("mail").equalTo(CurUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = "" + ds.child("name").getValue();
                    String surname = "" + ds.child("surname").getValue();
                    String username = "" + ds.child("username").getValue();
                    String mail = "" + ds.child("mail").getValue();
                    String city = "" + ds.child("city").getValue();
                    String birthdate = "" + ds.child("birthdate").getValue();
                    String point = "" + ds.child("point").getValue();

                    proName.setText(name);
                    proSurname.setText(surname);
                    proUsername.setText(username);
                    proMail.setText(mail);
                    proCity.setText(city);
                    proBirthDate.setText(birthdate);
                    proPoint.setText(point);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }


}