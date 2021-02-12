package com.example.barberapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class MainActivity extends AppCompatActivity {
    ProSwipeButton loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn = findViewById(R.id.getstarted);

        loginbtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            }
        });
    }
}