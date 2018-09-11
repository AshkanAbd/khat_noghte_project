package ir.ashkanabd.network;

import ir.ashkanabd.AI.TeamAI;

import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.*;

public class Handler {

    private Cell cellMap[][];
    private AI teamAI;
    private Client client;
    private ExecutorService executor;

    public Handler() throws IOException {
        this.client = new Client();
        cellMap = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cellMap[i][j] = new Cell(i, j);
            }
        }
        teamAI = new TeamAI();
        client.setOnAnswerListener(this::onAnswer);
        client.start();
        client.send(teamAI.getTeamName());
        executor = Executors.newSingleThreadExecutor();
    }

    private void onAnswer(String line) {
        prepareMap(decode(line));
    }

    private void prepareMap(String msg) {
        Scanner scn = new Scanner(msg);
        String line;
        line = scn.nextLine();
        teamAI.setMyID(Integer.parseInt(line.split("-")[0]));
        teamAI.setOppID(Integer.parseInt(line.split("-")[1]));
        line = scn.nextLine();
        teamAI.setMyScore(Integer.parseInt(line.split("-")[0]));
        teamAI.setOppScore(Integer.parseInt(line.split("-")[1]));
        for (int i = 0; i < 9; i++) {
            line = scn.nextLine();
            for (int j = 0; j < 9; j++) {
                cellMap[i][j].reset();
                if (line.charAt(j) == 'A') cellMap[i][j].setMarked(1);
                if (line.charAt(j) == 'B') cellMap[i][j].setMarked(2);
                if (line.charAt(j) == '-') cellMap[i][j].setFree();
                if (line.charAt(j) == '@') cellMap[i][j].setNode();
                if (line.charAt(j) == '#') cellMap[i][j].setBlock();
            }
        }
        if (!teamAI.isTimeout()) {
            client.send(encode(prepareResult(teamAI.think(cellMap))));
        } else {
            try {
                Future<Cell> future = executor.submit(() -> teamAI.think(cellMap));
                client.send(encode(prepareResult(future.get(1, TimeUnit.MINUTES))));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                System.out.println("Timeout");
            }
        }
    }

    private String prepareResult(Cell result) {
        if (result == null) return null;
        return result.getX() + "-" + result.getY();
    }

    private String decode(String line) {
        return new String(Base64.getDecoder().decode(line));
    }

    private String encode(String line) {
        if (line == null) return null;
        return Base64.getEncoder().encodeToString(line.getBytes());
    }
}
