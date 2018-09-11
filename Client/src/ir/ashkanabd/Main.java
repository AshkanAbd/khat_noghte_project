package ir.ashkanabd;

import ir.ashkanabd.network.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}
