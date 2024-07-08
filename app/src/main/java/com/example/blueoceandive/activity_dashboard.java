package com.example.blueoceandive;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_dashboard extends AppCompatActivity {

    private ImageView profileImageView;
    private Button allButton, tripPackagesButton, galleryButton;
    private DatabaseReference databaseReference;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Misalkan Anda mendapatkan email dari intent
        userEmail = getIntent().getStringExtra("email");

        // Ambil username berdasarkan email
        getUsernameFromEmail(userEmail);
        // Initialize views
        profileImageView = findViewById(R.id.profile);
        allButton = findViewById(R.id.allButton);
        tripPackagesButton = findViewById(R.id.trippackagesButton);
        galleryButton = findViewById(R.id.galleryButton);


        // Set listeners
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle profile image click
                Toast.makeText(activity_dashboard.this, "Profile clicked", Toast.LENGTH_SHORT).show();
            }
        });

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle All button click
                Toast.makeText(activity_dashboard.this, "All button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tripPackagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Trip Packages button click
                Toast.makeText(activity_dashboard.this, "Trip Packages button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Gallery button click
                Toast.makeText(activity_dashboard.this, "Gallery button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUsernameFromEmail(String email) {
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HelperClass user = snapshot.getValue(HelperClass.class);
                        String username = user.getUsername();
                        // Sekarang Anda memiliki username, Anda bisa menampilkan di UI atau melakukan sesuatu dengannya
                        Log.d("Username", "Username: " + username);
                        // Contoh menampilkan di TextView
                        TextView usernameTextView = findViewById(R.id.username);
                        usernameTextView.setText("Hi, " + username);
                    }
                } else {
                    Log.e("getUserError", "No user found with the email " + email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DatabaseError", "Error occurred: " + databaseError.getMessage());
            }
        });
    }
}