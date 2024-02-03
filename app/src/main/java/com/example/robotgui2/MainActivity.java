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


public class MainActivity extends AppCompatActivity {

    Button connection;
    TextView number;
    MyViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);

        connection = findViewById(R.id.connection);
        number = findViewById(R.id.number);

        // Observe the LiveData, updating the UI any time the data changes
        viewModel.getNumber().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                number.setText(s);
            }
        });

        connection.setOnClickListener(view -> viewModel.sendNumber());
    }
}



