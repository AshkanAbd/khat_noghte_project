package ir.ashkanabd.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String name;
    private Room room;
    private Socket socket;
    private InputStream inStream;
    private OutputStream outStream;

    Client(Socket socket, InputStream inStream, OutputStream outStream) {
        this.socket = socket;
        this.inStream = inStream;
        this.outStream = outStream;
        name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStream getOutStream() {
        return outStream;
    }

    public InputStream getInStream() {
        return inStream;
    }

    @Override
    public String toString() {
        return name + " : " + super.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode() + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Client && this.socket.equals(((Client) obj).socket);
    }
}
