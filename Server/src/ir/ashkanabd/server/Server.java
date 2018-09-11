package ir.ashkanabd.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private OnClientAnswer onClientAnswer;
    private OnClientConnected onClientConnected;
    private int counter = 0;

    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    private void accept() throws Exception {
        Socket socket = serverSocket.accept();
        Client client = new Client(socket, socket.getInputStream(), socket.getOutputStream());
        onClientConnected.onConnected(client);
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        while (counter < 2) {
            try {
                accept();
            } catch (Exception e) {
                e.printStackTrace();
            }
            counter++;
        }
    }

    public void setOnClientAnswer(OnClientAnswer onClientAnswer) {
        this.onClientAnswer = onClientAnswer;
    }

    public void setOnClientConnected(OnClientConnected onClientConnected) {
        this.onClientConnected = onClientConnected;
    }
}
