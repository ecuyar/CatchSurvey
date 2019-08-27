package com.example.cs;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

public class BookSurvey extends AppCompatActivity {

    private int totalQuestion = 1;
    public double questionPoint = 0;
    public double surveyPoint = 0;
    public String finalSurveyPoint;

    private FirebaseAuth auth;
    private FirebaseDatabase fireDB;
    private FirebaseAuth.AuthStateListener authList;
    private DatabaseReference DBref, DBrefAvg;
    private FirebaseUser CurUser;
    private TextView mpuanText;

    DecimalFormat df = new DecimalFormat(".#");

    //String strDouble = String.format("%.2f");

    TextView mQuestion, mAvg;
    Button mPoint1, mPoint2, mPoint3, mPoint4, mPoint5;
    List<Double> points = new ArrayList<>();
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        mQuestion = findViewById(R.id.question);
        mPoint1 = findViewById(R.id.bPoint1);
        mPoint2 = findViewById(R.id.bPoint2);
        mPoint3 = findViewById(R.id.bPoint3);
        mPoint4 = findViewById(R.id.bPoint4);
        mPoint5 = findViewById(R.id.bPoint5);
        mAvg = findViewById(R.id.txtAvg);

        fetchQuestion();
    }

    public void calculateAverage() {

        double sum = 0;
        try {
            for (int i = 0; i < points.size(); i++)
                sum = sum + points.get(i);

            surveyPoint = (sum / points.size());

            //df.format(surveyPoint);
            //String strDouble = String.format("%.2f", surveyPoint);
            //finalSurveyPoint = strDouble.fo
            //finalSurveyPoint = df.format(surveyPoint);
            //makeRound();

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.e("Memory exceptions", "exceptions" + e);
        }

    }

/*
    private double makeRound(double surveyPoint, int places) {


        BigDecimal bd = new BigDecimal(surveyPoint);
        bd = bd.setScale(places, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

    */

    private void fetchQuestion() {

        if (totalQuestion == 9) {

            calculateAverage();

            //BigDecimal bg = new BigDecimal(surveyPoint);

            String a = Double.toString(surveyPoint);

            df.format(surveyPoint);

            mAvg.setText(a);

            //updatePoint();

            Intent afterSurvey = new Intent(this, afterSurvey.class);
            startActivity(afterSurvey);

        } else {
            reference = FirebaseDatabase.getInstance().getReference().child("questions").child("books").child(String.valueOf(totalQuestion));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Question question = dataSnapshot.getValue(Question.class);

                    mQuestion.setText(question != null ? question.getQuestion() : null);

                    mPoint1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    totalQuestion++;
                                    questionPoint = 1.0;
                                    points.add(questionPoint);
                                    fetchQuestion();
                                }
                            }, 500);
                        }
                    });

                    mPoint2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    totalQuestion++;
                                    questionPoint = 2.0;
                                    points.add(questionPoint);
                                    fetchQuestion();
                                }
                            }, 500);
                        }
                    });

                    mPoint3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    totalQuestion++;
                                    questionPoint = 3.0;
                                    points.add(questionPoint);
                                    fetchQuestion();
                                }
                            }, 500);
                        }
                    });

                    mPoint4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    totalQuestion++;
                                    questionPoint = 4.0;
                                    points.add(questionPoint);
                                    fetchQuestion();
                                }
                            }, 500);
                        }
                    });

                    mPoint5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    totalQuestion++;
                                    questionPoint = 5.0;
                                    points.add(questionPoint);
                                    fetchQuestion();
                                }
                            }, 500);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
    }

    /*
    public void updatePoint() {

        auth = FirebaseAuth.getInstance();
        CurUser = auth.getCurrentUser();
        fireDB = FirebaseDatabase.getInstance();
        DBref = fireDB.getReference("items").child("books").child("Araba Sevdası - Recaizade Mahmut Ekrem");
        DBrefAvg = DBref.child("AVG");


        Query queryAvg = DBref.orderByChild("mail").equalTo(CurUser.getEmail());

        queryAvg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String AvgStr = "" + ds.child("AVG").getValue();
                    Double AvgStrD = Double.valueOf(AvgStr);
                    Double Avg = (AvgStrD + surveyPoint) / 2;
                    String AvgS = String.valueOf(Avg);
                    DBref.child("AVG").setValue(AvgS);

                }
                   /* String AvgStr = "" + ds.child("AVG").getValue();



                    String AvgString = String.valueOf(surveyPoint);
                    DBrefAvg.setValue(AvgString);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
}

