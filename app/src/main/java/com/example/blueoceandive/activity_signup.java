package com.example.blueoceandive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_signup extends AppCompatActivity {

    EditText signup_name, signup_email, signup_username, signup_password;
    TextView loginRedirect;
    Button signup_button;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Mendeklarasikan id teks dan button pada layout
        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        signup_username = findViewById(R.id.signup_username);
        signup_password = findViewById(R.id.signup_password);
        signup_button = findViewById(R.id.signup_button);
        loginRedirect = findViewById(R.id.loginRedirectText);

        // Action button register
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inisialisasi Firebase Database ke dalam aplikasi
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                // Mengambil data yang diinputkan oleh users
                String name = signup_name.getText().toString();
                String email = signup_email.getText().toString();
                String username = signup_username.getText().toString();
                String password = signup_password.getText().toString();

                // Proses validasi name, email, username password
                if (!validateName() | !validateEmail() | !validateUsername() | !validatePassword()) {
                    Toast.makeText(activity_signup.this, "Tolong inputkan data anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
                } else {
                    // Proses menyimpan data ke dalam database
                    HelperClass helperClass = new HelperClass(name, email, username, password);
                    reference.child(name).setValue(helperClass);

                    Toast.makeText(activity_signup.this, "Registrasi yang anda lakukan berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_signup.this, activity_login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Action teks login here
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_signup.this, activity_login.class);
                startActivity(intent);
            }
        });
    }

    // Validasi jika Name kosong
    public Boolean validateName(){
        String val = signup_name.getText().toString();
        if (val.isEmpty()){
            // Memberikan peringatan pada saat user mengklik kolom input
            signup_name.setError("Nama tidak boleh kosong");
            return false;
        } else {
            signup_name.setError(null);
            return true;
        }
    }

    // Validasi jika Email kosong
    public Boolean validateEmail(){
        String val = signup_email.getText().toString();
        if (val.isEmpty()){
            // Memberikan peringatan pada saat user mengklik kolom input
            signup_email.setError("Email tidak boleh kosong");
            return false;
        } else {
            signup_email.setError(null);
            return true;
        }
    }

    // Validasi jika Username kosong
    public Boolean validateUsername(){
        String val = signup_username.getText().toString();
        if (val.isEmpty()){
            // Memberikan peringatan pada saat user mengklik kolom input
            signup_username.setError("Username tidak boleh kosong");
            return false;
        } else {
            signup_username.setError(null);
            return true;
        }
    }

    // Validasi jika Password kosong
    public Boolean validatePassword(){
        String val = signup_password.getText().toString();
        if (val.isEmpty()){
            // Memberikan peringatan pada saat user mengklik kolom input
            signup_password.setError("Password tidak boleh kosong");
            return false;
        } else {
            signup_password.setError(null);
            return true;
        }
    }
}

