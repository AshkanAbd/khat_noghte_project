package ir.ashkanabd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private Label teamALabel;
    @FXML
    private Label teamBLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private AnchorPane windowPane;
    @FXML
    private Label teamAScore;
    @FXML
    private Label teamBScore;

    private AnchorPane gamePane;
    private Node[][] nodeMatrix;
    private int time;
    private Timer timer;

    public void initialize() {
        teamALabel.setFont(Font.font(22));
        timeLabel.setFont(Font.font(22));
        teamBLabel.setFont(Font.font(22));
        teamAScore.setFont(Font.font(22));
        teamBScore.setFont(Font.font(22));
        setupGameUI();
        addNodes();
    }

    public void startTime() {
        timer = new Timer();
        time = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time++;
                Platform.runLater(() -> timeLabel.setText((time / 60 < 10 ? "0" + time / 60 : "" + time / 60)
                        + " : " + (time % 60 < 10 ? "0" + time % 60 : "" + time % 60)));
            }
        }, 0, 1000);
    }

    public Timer getTimer() {
        return timer;
    }

    void setTeamAName(String name) {
        teamALabel.setText(name);
    }

    public int getAScore() {
        return Integer.parseInt(teamAScore.getText());
    }

    public int getBScore() {
        return Integer.parseInt(teamBScore.getText());
    }

    public void setAScore(int score) {
        teamAScore.setText(score + "");
    }

    public void setBScore(int score) {
        teamBScore.setText(score + "");
    }

    void setTeamBName(String name) {
        teamBLabel.setText(name);
    }

    public Node[][] getNodeMatrix() {
        return nodeMatrix;
    }

    private void setupGameUI() {
        gamePane = new AnchorPane();
        gamePane.setLayoutX(75);
        gamePane.setLayoutY(75);
        gamePane.setPrefWidth(450);
        gamePane.setMaxWidth(450);
        gamePane.setMinWidth(450);
        gamePane.setPrefHeight(450);
        gamePane.setMaxHeight(450);
        gamePane.setMinHeight(450);
        gamePane.setStyle("-fx-background-color: snow");
        windowPane.getChildren().add(gamePane);
    }

    private void addNodes() {
        nodeMatrix = new Node[9][9];
        Circle circle;
        Ellipse ellipse;
        Rectangle rectangle;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    circle = new Circle(19);
                    circle.setStyle("-fx-fill: green");
                    circle.setCenterX(i * 51.5 + 19);
                    circle.setCenterY(j * 51.5 + 19);
                    gamePane.getChildren().add(circle);
                    nodeMatrix[i][j] = circle;
                } else if (i % 2 == 1 && j % 2 == 1) {
                    rectangle = new Rectangle();
                    rectangle.setStyle("-fx-fill: snow");
                    rectangle.setHeight(61);
                    rectangle.setWidth(61);
                    rectangle.setX((((i + 1) / 2) - 1) * 103 + 40);
                    rectangle.setY((((j + 1) / 2) - 1) * 103 + 40);
                    gamePane.getChildren().add(rectangle);
                    nodeMatrix[i][j] = rectangle;
                } else if (i % 2 == 0 && j % 2 == 1) {
                    ellipse = new Ellipse();
                    ellipse.setStyle("-fx-fill: snow");
                    ellipse.setRadiusX(7);
                    ellipse.setRadiusY(30);
                    ellipse.setCenterX(i * 51.5 + 19);
                    ellipse.setCenterY((((j + 1) / 2) - 1) * 103 + 70.5);
                    gamePane.getChildren().add(ellipse);
                    nodeMatrix[i][j] = ellipse;
                } else if (i % 2 == 1 && j % 2 == 0) {
                    ellipse = new Ellipse();
                    ellipse.setStyle("-fx-fill: snow");
                    ellipse.setRadiusX(30);
                    ellipse.setRadiusY(7);
                    ellipse.setCenterX((((i + 1) / 2) - 1) * 103 + 70.5);
                    ellipse.setCenterY(j * 51.5 + 19);
                    gamePane.getChildren().add(ellipse);
                    nodeMatrix[i][j] = ellipse;
                }
            }
        }
    }
}
