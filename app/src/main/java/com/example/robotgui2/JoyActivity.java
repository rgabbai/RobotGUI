package com.example.robotgui2;// SecondActivity.java
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class JoyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joy);

        Button returnButton = findViewById(R.id.button_return);

        ImageButton leftB   = findViewById(R.id.leftButton);
        ImageButton rightB  = findViewById(R.id.rightButton);
        ImageButton topB    = findViewById(R.id.topButton);
        ImageButton bottomB = findViewById(R.id.bottomButton);
        ImageButton stopB   = findViewById(R.id.stopButton);

        leftB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedRepository.getInstance().sendMessage("Joy_left");
            }
        });
        rightB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedRepository.getInstance().sendMessage("Joy_right");
            }
        });
        topB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedRepository.getInstance().sendMessage("Joy_top");
            }
        });
        bottomB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedRepository.getInstance().sendMessage("Joy_bottom");
            }
        });

        stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedRepository.getInstance().sendMessage("Joy_stop");
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity, which will return to previous activity in the stack
                finish();
            }
        });
    }
}
