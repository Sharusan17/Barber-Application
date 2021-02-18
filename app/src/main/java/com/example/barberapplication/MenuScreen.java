package com.example.barberapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MenuScreen extends AppCompatActivity {

    TextView showtext;
    FirebaseAuth mAuth;
    DatabaseReference reff;
    ImageButton autojoinbtn,bookappointbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        mAuth = FirebaseAuth.getInstance();

        showtext = findViewById(R.id.showtext);

        FirebaseUser user = mAuth.getCurrentUser();

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username =snapshot.child("name").getValue().toString();
                showtext.setText(username);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });

        autojoinbtn = findViewById(R.id.AutoJoinbtn);

        autojoinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuScreen.this, AutoJoin.class));
            }
        });

        bookappointbtn = findViewById(R.id.BookAppointbtn);

        bookappointbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuScreen.this, BookAppointment.class));
            }
        });





    }
}