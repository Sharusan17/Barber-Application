package com.example.barberapplication;

//todo: draw the homepage of the barber app

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText emailID, passwordID;
    private Button loginbtnID;
    private TextView createAccID , ForgetPasswordID;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        emailID = findViewById(R.id.Email);
        passwordID = findViewById(R.id.Password);

        loginbtnID = findViewById(R.id.lgnbtn);
        loginbtnID.setOnClickListener(this);
        createAccID = findViewById(R.id.Txtreg);
        createAccID.setOnClickListener(this);
        ForgetPasswordID = findViewById(R.id.forgetpw);
        ForgetPasswordID.setOnClickListener(this);

        progressBar = findViewById(R.id.lgnprogressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Txtreg:
                startActivity(new Intent(getApplicationContext(), RegisterScreen.class));
                break;

            case R.id.lgnbtn:
                UserLogin();
                break;

            case R.id.forgetpw:
                startActivity(new Intent(getApplicationContext(), Forgetpassword.class));
                break;
        }
    }


    private void UserLogin() {
        String email_lgn = emailID.getText().toString().trim();
        String pass_lgn = passwordID.getText().toString().trim();

        if (email_lgn.isEmpty()) {
            emailID.setError("Email Required");
            emailID.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_lgn).matches()) {
            emailID.setError("Invalid Email");
            emailID.requestFocus();
            return;
        }

        if (pass_lgn.isEmpty()) {
            passwordID.setError("Password Required");
            passwordID.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email_lgn, pass_lgn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        startActivity(new Intent(LoginScreen.this, MenuScreen.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginScreen.this, "Email Not Verified", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(LoginScreen.this, "Login Failed", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }
}