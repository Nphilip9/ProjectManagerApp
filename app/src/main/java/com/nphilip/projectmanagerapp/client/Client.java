package com.nphilip.projectmanagerapp.client;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.projectmanagerapp.MainActivity;
import com.nphilip.projectmanagerapp.model.ProjectItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private static final String SERVER_IP = "192.168.120.204";
    private static final int SERVER_PORT = 1080;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isConnected = false;
    Context context;

    public Client(Context context) {
       this.context = context;
    }

    public void connectToServer() {
        ConnectToServerTask connectTask = new ConnectToServerTask();
        connectTask.execute();
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectToServerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                isConnected = true;
                startMessageListener();
            } catch (IOException e) {
                e.printStackTrace();
                isConnected = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void startMessageListener() {
        if (isConnected) {
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        handleIncomingMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void handleIncomingMessage(String message) {
        if (message.startsWith("ITEMS")) {
            ArrayList<ProjectItem> projectItems;
            Type listType = new TypeToken<ArrayList<ProjectItem>>() {}.getType();
            projectItems = new Gson().fromJson(message.replace("ITEMS",""), listType);
            ((MainActivity) context).runOnUiThread(() -> {
                MainActivity mainActivity = (MainActivity) context;
                for (ProjectItem projectItem : projectItems) {
                    mainActivity.addProjectItem(projectItem);
                }
            });
        }
    }
}
