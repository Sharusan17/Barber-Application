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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText name_reg, email_reg , password_reg , phone_reg;
    private Button register;
    private TextView logintxt;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        name_reg = findViewById(R.id.name);
        email_reg = findViewById(R.id.email);
        password_reg = findViewById(R.id.password);
        phone_reg = findViewById(R.id.phone);
        register = findViewById(R.id.rgnbtn);
        logintxt = findViewById(R.id.Txtlogin);
        progressBar = findViewById(R.id.progressBar_reg);


        mAuth = FirebaseAuth.getInstance();

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = name_reg.getText().toString().trim();
                final String email = email_reg.getText().toString().trim();
                String pass = password_reg.getText().toString().trim();
                String phone = phone_reg.getText().toString().trim();

                if (name.isEmpty()) {
                    name_reg.setError("Name Required");
                    name_reg.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    email_reg.setError("Email Required");
                    email_reg.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    email_reg.setError("Invalid Email");
                    email_reg.requestFocus();
                    return;
                }

                if (pass.isEmpty()) {
                    password_reg.setError("Password Required");
                    password_reg.requestFocus();
                    return;
                }

                if (pass.length() < 6){
                    password_reg.setError("Too Short");
                    password_reg.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    phone_reg.setError("Number Required");
                    phone_reg.requestFocus();
                    return;
                }

                if (!Patterns.PHONE.matcher(phone).matches()){
                    phone_reg.setError("Invalid Number");
                    phone_reg.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            final User user = new User(name,email);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        Intent intent = new Intent(RegisterScreen.this, MenuScreen.class);
                                        startActivity(intent);
                                        Toast.makeText(RegisterScreen.this, "User Register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(RegisterScreen.this, "Failed To Register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }});
                        }else{
                            Toast.makeText(RegisterScreen.this, "Failed To Register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View view) {
    }
}