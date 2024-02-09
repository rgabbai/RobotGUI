package com.example.robotgui2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.Observer;

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
import android.widget.EditText;
import android.widget.TextView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button connection;
    TextView responseView;
    //MyViewModel viewModel;

    ImageButton MoveAwayButton;
    ImageButton FollowMeButton;
    ImageButton JoyButton;
    ImageButton ReplayButton;

    //EditText ipAddr = findViewById(R.id.ipAddressInput);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);

        // Initiate TCP socket connection
        //SharedRepository.getInstance().initConnection();


        connection = findViewById(R.id.connection);
        responseView = findViewById(R.id.response);
        responseView.setText("Robot offline");

        // Get response from server (robot)
        SharedRepository.getInstance().getResponseLiveData().observe(this, response -> {
            // Update your UI here with the new response
            responseView.setText(response);
            //Log.d("StatusDebug", "Status: " + response);
            if ("Robot online".equals(response)) {
                responseView.setTextColor(getResources().getColor(R.color.text_colorGreen));
            }
        });

        

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
        //connection.setOnClickListener(view -> SharedRepository.getInstance().sendMessage("Hello Robot"));

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Connect clicked!", Toast.LENGTH_SHORT).show();
                // Handle the ImageButton click
                //viewModel.Write2WebSocket("joy");
                SharedRepository.getInstance().sendMessage("Hello Robot");
                // Initiate TCP socket connection
                EditText ipAddressInput = findViewById(R.id.ipAddressInput); // Assuming you have an EditText for IP input
                String ipAddress = ipAddressInput.getText().toString();
                SharedRepository.getInstance().initConnection(ipAddress);
            }
        });


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


        // You can now use the ipAddress string to connect, save, or validate.
        /*
        ipAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing needed here.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Validate the IP address here if needed.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This is called after the text has changed.
                // Additional validation can be performed here if necessary.

            }
        });
       */

    }
}



