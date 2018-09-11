package ir.ashkanabd.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    private Scanner scn;
    private PrintWriter writer;
    private OnAnswerListener onAnswerListener;

    public Client() throws IOException {
        Socket socket = new Socket("127.0.0.1", 1898);
        socket.setKeepAlive(true);
        scn = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        new Handler(this);
    }

    void send(String line) {
        writer.println(line);
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        while (scn.hasNextLine()) {
            onAnswerListener.onAnswer(scn.nextLine());
        }
    }

    void setOnAnswerListener(OnAnswerListener onAnswerListener) {
        this.onAnswerListener = onAnswerListener;
    }
}
