package ir.ashkanabd.server;

import java.io.PrintWriter;

public class Room {
    private Client clientA;
    private Client clientB;
    private PrintWriter outA;
    private PrintWriter outB;
    private boolean clients[] = {false, false};

    public Room(Client clientA, Client clientB) {
        this.clientA = clientA;
        this.clientB = clientB;
        outA = new PrintWriter(clientA.getOutStream(), true);
        outB = new PrintWriter(clientB.getOutStream(), true);
        clients[0] = true;
        clients[1] = true;
    }

    public Room(Client clientA) {
        this.clientA = clientA;
        this.outA = new PrintWriter(clientA.getOutStream(), true);
        clients[0] = true;
    }

    public boolean isA(Client client) {
        return client.equals(clientA);
    }

    public boolean isB(Client client) {
        return client.equals(clientB);
    }

    public void sendA(String line) {
        outA.println(line);
    }

    public void sendB(String line) {
        outB.println(line);
    }

    public void sendAll(String line) {
        sendA(line);
        sendB(line);
    }

    public void addClient(Client clientB) {
        this.clientB = clientB;
        outB = new PrintWriter(clientB.getOutStream(), true);
        clients[1] = true;
    }

    public boolean[] getClients() {
        return clients;
    }

    public Client getClientB() {
        return clientB;
    }

    public Client getClientA() {
        return clientA;
    }

    @Override
    public int hashCode() {
        return clientA.hashCode() + outA.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            Room r = (Room) obj;
            return r.clientA.equals(this.clientA) && r.clientB.equals(this.clientB);
        }
        return false;
    }

    @Override
    public String toString() {
        return clientA.toString() + clientB.toString();
    }
}
