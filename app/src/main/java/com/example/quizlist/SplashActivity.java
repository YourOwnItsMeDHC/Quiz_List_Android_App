package com.example.quizlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    public static ArrayList<Modelclass> list;

    //Create object of database of firebase :
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_main);

        //List of questions for Quiz :
        list = new ArrayList<>();

        //Get database i.e. quiz & options from firebase :
        databaseReference = FirebaseDatabase.getInstance().getReference("Question");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Now, we have all the data in the form of snapshot (above) :
                //Now, get data from snapshot

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //Get data from snapshot (until it have children), and then add it to "Modelclass" :
                    Modelclass modelclass = dataSnapshot.getValue(Modelclass.class);
                    //Now, we have all questions & options in "Modelclass", so add that into the list :
                    list.add(modelclass);
                }

                //After getting all the data, set intent to dashboard (Quiz List) :
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


////        list.add(new Model("question", "A", "B", "C", "D", "Ans"));
//        list.add(new Modelclass("Which animal lays eggs?", "Dog", "Cat",
//                "Duck", "Sheep", "Duck"));
//        list.add(new Modelclass("Which one is a fur-bearing animal?", "Hen", "Crocodile",
//                "Tortoise", "Cat", "Cat"));
//        list.add(new Modelclass("What is Earth’s only natural satellite?", "Sun", "Mars",
//                "Venus", "Moon", "Moon"));
//        list.add(new Modelclass("Which organ covers the entire body and protects it?", "Liver", "Heart",
//                "Skin", "Brain", "Skin"));
//        list.add(new Modelclass("Who invented the first functional telephone?", "Albert Einstein", "Nikola Tesla",
//                "Thomas Alva Edison", "Alexander Graham Bell", "Alexander Graham Bell"));
//        list.add(new Modelclass("Dark rain clouds can give out lightning and ____.", "Thunder", "Snow",
//                "Sunlight", "Wind", "Thunder"));
//        list.add(new Modelclass("What part of the plant conducts photosynthesis?", "Branch", "Leaf",
//                "Root", "Trunk", "Leaf"));
//        list.add(new Modelclass("Where does our food collect after we chew and swallow it?", "Small intestine", "Large intestine",
//                "Stomach", "Liver", "Stomach"));
//        list.add(new Modelclass("What is the boiling point of water?", "25°C", "50°C",
//                "75°C", "100°C", "100°C"));
//        list.add(new Modelclass("How does a dog express happiness?", "Twitching ears", "Moving head",
//                "Closing eyes", "Wagging tail", "Wagging tail"));


        //Splash Screen
        //Set intent to splash screen, and delay it tp 1 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
//                startActivity(intent);
            }
        }, 3000);
    }
}