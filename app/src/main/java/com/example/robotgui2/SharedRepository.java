package com.example.robotgui2;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SharedRepository {
    private static final SharedRepository ourInstance = new SharedRepository();

    private final MutableLiveData<String> numberLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> responseLiveData = new MutableLiveData<>(); // For capturing responses

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader; // To read responses from the server

    // TODO: Add option to insert in the app start...
    private final String IP = "10.242.90.83"; // Dev machine IP
    //private final String IP = "10.242.202.105"; // Your robot's IP

    private final int PORT = 6000; // Your robot's port

    public static SharedRepository getInstance() {
        return ourInstance;
    }

    private SharedRepository() {
    }

    public void initConnection(String ip) {
        new Thread(() -> {
            try {
                if (socket == null || socket.isClosed()) {
                    //socket = new Socket(IP, PORT);
                    socket = new Socket(ip, PORT);
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    //System.out.println("Socket Connected to robot");
                    //Log.i("Socket", "Connected to robot");
                    //responseLiveData.postValue("NA1");
                    listenForResponses(); // Automatically start listening for responses

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void listenForResponses() {
        new Thread(() -> {
            BufferedReader reader = null;
            try {
                // Ensure the socket is connected and obtain the input stream
                if (socket != null && !socket.isClosed() && socket.isConnected()) {
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } else {
                    responseLiveData.postValue("Socket is not connected");
                    return; // Exit the thread if the socket is not connected
                }

                int charCode;
                StringBuilder responseBuilder = new StringBuilder();

                // Continuously read from the input stream
                while ((charCode = reader.read()) != -1) { // read() returns -1 if the end of the stream is reached
                    char character = (char) charCode;
                    // If the end of a line is reached, post the response
                    if (character == '\n' || character == '\r') {
                        // Post the response only if it's not empty (ignoring newline characters)
                        if (responseBuilder.length() > 0) {
                            String response = responseBuilder.toString();
                            responseLiveData.postValue(response); // Update LiveData with the response
                            responseBuilder.setLength(0); // Clear the StringBuilder for the next message
                        }
                    } else {
                        responseBuilder.append(character); // Append the read character to the builder
                    }

                }
            } catch (IOException e) {
                // Handle the exception, update LiveData or log the error
                responseLiveData.postValue("Error reading from socket: " + e.getMessage());
            } finally {
                // Close the BufferedReader to free resources
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // Log or handle the exception thrown from closing the reader
                    }
                }
            }
        }).start();
    }

    public MutableLiveData<String> getResponseLiveData() {
        return responseLiveData;
    }
    public String getCurrentResponse() {
        // This method returns the current value of responseLiveData.
        // It should be called on the main thread.
        return responseLiveData.getValue();
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
