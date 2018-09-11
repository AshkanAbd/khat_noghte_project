package ir.ashkanabd.game;

import ir.ashkanabd.Controller;
import ir.ashkanabd.server.Room;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.*;

public class Game {

    private static int CLIENT_A = 1;
    private static int CLIENT_B = 2;

    private String map[][];
    private Node nodeMap[][];
    private Room gameRoom;
    private Scanner scnA, scnB;
    private ExecutorService executor;
    private Controller controller;
    private boolean stop;

    public static Game buildGame(Controller controller) {
        String[][] map = new String[9][9];
        Node[][] nodeMap = controller.getNodeMatrix();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (nodeMap[i][j] instanceof Ellipse) {
                    map[i][j] = "-";
                } else if (nodeMap[i][j] instanceof Circle) {
                    map[i][j] = "@";
                } else if (nodeMap[i][j] instanceof Rectangle) {
                    map[i][j] = "#";
                }
            }
        }
        return new Game(map, controller);
    }

    private Game(String map[][], Controller controller) {
        this.map = map;
        this.executor = Executors.newSingleThreadExecutor();
        this.nodeMap = controller.getNodeMatrix();
        this.controller = controller;
        this.stop = false;
    }

    public void start() {
        controller.startTime();
        mainLoop:
        {
            while (true) {
                while (proccess(decode(streamA()), CLIENT_A)) {
                    if (stop) break mainLoop;
                }
                while (proccess(decode(streamB()), CLIENT_B)) {
                    if (stop) break mainLoop;
                }
            }
        }
        controller.getTimer().cancel();
    }

    private String streamA() {
        gameRoom.sendA(encode(prepareMap(CLIENT_A)));
        Future<String> answer = executor.submit(() -> scnA.nextLine());
        return getAnswer(answer);
    }

    private String streamB() {
        gameRoom.sendB(encode(prepareMap(CLIENT_B)));
        Future<String> answer = executor.submit(() -> scnB.nextLine());
        return getAnswer(answer);
    }

    private String getAnswer(Future<String> answer) {
        try {
            return answer.get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException e) {
            return null;
        } catch (TimeoutException e) {
            System.out.println("timeout");
            return null;
        }
    }

    public void setScnA(InputStream inputStream) {
        this.scnA = new Scanner(inputStream);
    }

    public void setScnB(InputStream inputStream) {
        this.scnB = new Scanner(inputStream);
    }

    private String prepareMap(int clientID) {
        StringBuilder builder = new StringBuilder();
        if (clientID == CLIENT_A) {
            builder.append(CLIENT_A).append("-").append(CLIENT_B).append("\n");
            builder.append(controller.getAScore()).append("-").append(controller.getBScore()).append("\n");
        } else {
            builder.append(CLIENT_B).append("-").append(CLIENT_A).append("\n");
            builder.append(controller.getBScore()).append("-").append(controller.getAScore()).append("\n");
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                builder.append(map[i][j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private String decode(String line) {
        if (line == null) return null;
        return new String(Base64.getDecoder().decode(line));
    }

    private String encode(String line) {
        return Base64.getEncoder().encodeToString(line.getBytes());
    }

    private boolean proccess(String commend, int clientID) {
        if (commend == null) {
            return false;
        }
        String indexesString[] = commend.split("-");
        Integer indexesInteger[] = {Integer.parseInt(indexesString[0]), Integer.parseInt(indexesString[1])};
        if (map[indexesInteger[0]][indexesInteger[1]].equals("-")) {
            map[indexesInteger[0]][indexesInteger[1]] = clientID == CLIENT_A ? "A" : "B";
            updateUI(clientID, nodeMap[indexesInteger[0]][indexesInteger[1]]);
            return setScore(clientID);
        }
        return false;
    }

    private void updateUI(int clientID, Node markingNode) {
        if (clientID == CLIENT_A) {
            Platform.runLater(() -> markingNode.setStyle("-fx-fill: blue"));
        } else if (clientID == CLIENT_B) {
            Platform.runLater(() -> markingNode.setStyle("-fx-fill: red"));
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean setScore(int clientID) {
        boolean result = false;
        for (int i = 1; i < 9; i += 2) {
            for (int j = 1; j < 9; j += 2) {
                if (nodeMap[i][j].getStyle().contains("snow")) {
                    if (!map[i - 1][j].equals("-") && !map[i + 1][j].equals("-")
                            && !map[i][j - 1].equals("-") && !map[i][j + 1].equals("-")) {
                        int finalI = i;
                        int finalJ = j;
                        if (clientID == CLIENT_A) {
                            Platform.runLater(() -> {
                                nodeMap[finalI][finalJ].setStyle("-fx-fill: blue");
                                controller.setAScore(controller.getAScore() + 1);
                            });
                            result = true;
                        } else if (clientID == CLIENT_B) {
                            Platform.runLater(() -> {
                                nodeMap[finalI][finalJ].setStyle("-fx-fill: red");
                                controller.setBScore(controller.getBScore() + 1);
                            });
                            result = true;
                        }
                    }
                }
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        if (stopCondition()) stop = true;
        return result;
    }

    private boolean stopCondition() {
        for (int i = 1; i < 9; i += 2) {
            for (int j = 1; j < 9; j += 2) {
                if (nodeMap[i][j].getStyle().contains("snow")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setGameRoom(Room gameRoom) {
        this.gameRoom = gameRoom;
    }
}
