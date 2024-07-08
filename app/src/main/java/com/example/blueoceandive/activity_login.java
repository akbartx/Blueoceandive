package com.example.blueoceandive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_login extends AppCompatActivity {

    EditText login_username, login_password;
    Button login_button;
    TextView SignupRedirect;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        // Mendeklarasikan id teks dan button pada layout
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        SignupRedirect = findViewById(R.id.signupRedirectText);

        // Action Button Login
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginButton", "Button clicked!");
                if (!validateUsername() | !validatePassword()) {
                    Toast.makeText(activity_login.this, "Tolong Inputkan Data Anda Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    checkUser();
                }
            }
        });

        SignupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsignup = new Intent(activity_login.this, com.example.blueoceandive.activity_signup.class);
                startActivity(pindahsignup);
            }
        });
    }

    // Validasi pada kolom username apabila kosong
    public Boolean validateUsername() {
        String val = login_username.getText().toString();
        if (val.isEmpty()) {
            // Jika kosong akan diberikan peringatan
            login_username.setError("Username Tidak Boleh Kosong");
            return false;
        } else {
            login_username.setError(null);
            return true;
        }
    }

    // Validasi pada kolom password apabila kosong
    public Boolean validatePassword() {
        String val = login_password.getText().toString();
        if (val.isEmpty()) {
            // Jika kosong akan diberikan peringatan
            login_password.setError("Password Tidak Boleh Kosong");
            return false;
        } else {
            login_password.setError(null);
            return true;
        }
    }

    // Validasi data user yang diinputkan dengan yang didaftarkan
    public void checkUser() {
        // Mengambil data yang diinputkan oleh user pada kolom
        String userUsername = login_username.getText().toString().trim();
        String userPassword = login_password.getText().toString().trim();

        // Melakukan interaksi dengan Firebase database untuk mencari data user yang memiliki username yang sama dengan yang diinputkan
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        // Proses verifikasi data user dalam database
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Proses pengecekan adanya user dengan data yang sama dalam database
                if (snapshot.exists()) {
                    // Menghapus pesan error apabila username cocok
                    login_username.setError(null);

                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // Mengambil data password dari username yang cocok
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        // Proses pengecekan data password yang diinputkan dengan yang ada di database pada username yang cocok
                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            login_password.setError(null);

                            // Mengambil data name dan email dari username yang cocok
                            String nameFromDB = userSnapshot.child("name").getValue(String.class);
                            String emailFromDB = userSnapshot.child("email").getValue(String.class);

                            // Proses perpindahan activity jika username dan password cocok, beserta dengan data username, name dan email
                            Intent intent = new Intent(activity_login.this, activity_dashboard.class);
                            intent.putExtra("username", userUsername);
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("email", emailFromDB);

                            // Memulai dashboard activity dan menutup login activity
                            startActivity(intent);
                            finish();
                        } else {
                            // Memberikan peringatan jika password tidak cocok
                            login_password.setError("Password Invalid!");
                            login_password.requestFocus();
                        }
                    }

                } else {
                    // Memberikan peringatan jika username tidak cocok
                    login_username.setError("Username Invalid!");
                    login_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Log.e("DatabaseError", error.getMessage());
            }
        });
    }
}