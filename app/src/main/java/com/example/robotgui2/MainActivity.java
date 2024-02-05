package com.example.robotgui2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button connection;
    TextView number;
    //MyViewModel viewModel;

    ImageButton MoveAwayButton;
    ImageButton FollowMeButton;
    ImageButton JoyButton;
    ImageButton ReplayButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);

        // Initiate TCP socket connection
        SharedRepository.getInstance().initConnection();


        connection = findViewById(R.id.connection);
        number = findViewById(R.id.number);

        // Functions buttons
        MoveAwayButton = findViewById(R.id.MoveAwayButton);
        FollowMeButton = findViewById(R.id.FollowMeButton);
        JoyButton = findViewById(R.id.JoyButton);
        ReplayButton = findViewById(R.id.ReplayButton);

        // Observe the LiveData, updating the UI any time the data changes
        //viewModel.getNumber().observe(this, new Observer<String>() {
        //    @Override
        //    public void onChanged(String s) {
        //        number.setText(s);
        //    }
        //});

        //connection.setOnClickListener(view -> viewModel.Write2WebSocket("Connect"));
        connection.setOnClickListener(view -> SharedRepository.getInstance().sendMessage("Hello Robot"));


        JoyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "JoyButton clicked!", Toast.LENGTH_SHORT).show();
                // Handle the ImageButton click
                //viewModel.Write2WebSocket("joy");
                SharedRepository.getInstance().sendMessage("Joy");
                Intent intent = new Intent(MainActivity.this, JoyActivity.class);
                startActivity(intent);
            }
        });

        MoveAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "MoveAwayButton clicked!", Toast.LENGTH_SHORT).show();
                //viewModel.Write2WebSocket("MoveAway");
                SharedRepository.getInstance().sendMessage("MoveAway");
                Intent intent = new Intent(MainActivity.this, MoveAwayActivity.class);
                startActivity(intent);
                // Handle the ImageButton click
            }
        });

        FollowMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "FollowMeButton clicked!", Toast.LENGTH_SHORT).show();
                //viewModel.Write2WebSocket("FollowMe");
                SharedRepository.getInstance().sendMessage("FollowMe");
                Intent intent = new Intent(MainActivity.this, FollowMeActivity.class);
                startActivity(intent);
            }
        });

        ReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ReplayButton clicked!", Toast.LENGTH_SHORT).show();
                //viewModel.Write2WebSocket("Replay");
                SharedRepository.getInstance().sendMessage("Replay");
                // Handle the ImageButton click
            }
        });

    }
}



