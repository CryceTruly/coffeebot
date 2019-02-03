package com.must.cofeeapp.cpro;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class App extends Application {
    private static final String TAG = "App";
    String user;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        if (FirebaseApp.getApps(this) != null) {
            try {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            } catch (IllegalStateException e) {


            }


        }

    }}