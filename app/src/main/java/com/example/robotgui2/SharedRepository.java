package com.example.robotgui2;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SharedRepository {
    private static final SharedRepository ourInstance = new SharedRepository();

    private final MutableLiveData<String> numberLiveData = new MutableLiveData<>();

    private Socket socket;
    private PrintWriter writer;
    private final String IP = "10.242.90.83"; // Your robot's IP
    private final int PORT = 6000; // Your robot's port

    public static SharedRepository getInstance() {
        return ourInstance;
    }

    private SharedRepository() {
    }

    public void initConnection() {
        new Thread(() -> {
            try {
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(IP, PORT);
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    System.out.println("Socket Connected to robot");
                    Log.i("Socket", "Connected to robot");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            if (writer != null) {
                writer.println(message);
                numberLiveData.postValue(message); // Update LiveData for UI
                System.out.println("Message sent: " + message);
            }
        }).start();
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
