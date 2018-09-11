package ir.ashkanabd;

import ir.ashkanabd.game.Game;
import ir.ashkanabd.server.Client;
import ir.ashkanabd.server.Room;
import ir.ashkanabd.server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    private Controller controller;
    private Room gameRoom;
    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI.fxml"));
        controller = new Controller();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 550);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Data project");
        primaryStage.setScene(scene);
        primaryStage.show();
        game = Game.buildGame(controller);
        Server server = new Server(1898);
        server.setOnClientConnected(this::onConnect);
        server.start();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private void onConnect(Client client) {
        Scanner scn = new Scanner(client.getInStream());
        client.setName(scn.nextLine());
        if (gameRoom == null) {
            gameRoom = new Room(client);
            client.setRoom(gameRoom);
            game.setScnA(client.getInStream());
            try {
                Platform.runLater(() -> controller.setTeamAName(client.getName()));
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        } else {
            gameRoom.addClient(client);
            client.setRoom(gameRoom);
            game.setScnB(client.getInStream());
            try {
                Platform.runLater(() -> controller.setTeamBName(client.getName()));
                Thread.sleep(100);
            } catch (Exception ignored) {
            }
            game.setGameRoom(gameRoom);
            game.start();
        }
    }
}
