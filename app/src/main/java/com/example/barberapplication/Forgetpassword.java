package com.example.barberapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {

    private EditText forgetemail;
    private Button forgetbtn;

    private ProgressBar progressforget;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        forgetemail = (EditText) findViewById(R.id.forget_email);
        forgetbtn = (Button) findViewById(R.id.resetbtn);
        progressforget = (ProgressBar) findViewById(R.id.forgetprogressbar);

        mAuth = FirebaseAuth.getInstance();

        forgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = forgetemail.getText().toString().trim();

        if (email.isEmpty()) {
            forgetemail.setError("Email Required");
            forgetemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgetemail.setError("Invalid Email");
            forgetemail.requestFocus();
            return;
        }

        progressforget.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Forgetpassword.this, "Check Your Email", Toast.LENGTH_LONG).show();
                    progressforget.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                } else {
                    Toast.makeText(Forgetpassword.this, "Try Again", Toast.LENGTH_LONG).show();
                    progressforget.setVisibility(View.GONE);
                }
            }
        });
    }
}