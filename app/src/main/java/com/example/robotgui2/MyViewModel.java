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

public class MyViewModel extends ViewModel {

    private final MutableLiveData<String> numberLiveData = new MutableLiveData<>();
    private Socket socket;
    private PrintWriter writer;
    private int i = 0;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<String> getNumber() {
        return numberLiveData;
    }

    public void sendNumber() {
        // Use an executor to perform the background task
        executorService.execute(() -> {
            try {
                String mens = String.valueOf(i++);
                if (socket == null || socket.isClosed()) {
                    // Adjust IP and port as necessary
                    socket = new Socket("10.242.90.83", 6000);
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    Log.i("Socket", "Connected to server");
                }
                writer.println(mens); // Send the number over the socket
                numberLiveData.postValue(mens); // Update LiveData for UI
            } catch (IOException e) {
                e.printStackTrace();
                numberLiveData.postValue("Error: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Clean up resources, like closing the socket
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
