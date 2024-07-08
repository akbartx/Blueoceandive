package com.example.blueoceandive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blueoceandive.R;

public class LoginActivity extends AppCompatActivity {

    Button pindahActivity;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        pindahActivity = findViewById(R.id.login_button);

        pindahActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pindahsignup = new Intent(LoginActivity.this, com.example.blueoceandive.SignupActivity.class);
                startActivity(pindahsignup);
            }
        });
    }
}
