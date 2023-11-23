package ensisa.ihm.connect4;

import ensisa.ihm.connect4.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Connect4Controller implements Initializable {

    public Player player1 = new Player("", 1, false);
    public Player player2 = new Player("", 2, true);
    private Player currentPlayer = player1;
    private Timeline computerMoveTimeline;
    @FXML
    Button restart = new Button();
    @FXML
    GridPane board = new GridPane();
    @FXML
    Button addToken0 = new Button();
    @FXML
    public Text playerTurn;
    @FXML
    public Text player1Color, player2Color;


    /**
     * This function handles the adding of the circles in the board depending on the player
     *
     * @param event
     */
    @FXML
    public void addTokenButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        int columnIndex = 0;
        switch (buttonId) {
            case "addToken0":
                columnIndex = 0;
                break;
            case "addToken1":
                columnIndex = 1;
                break;
            case "addToken2":
                columnIndex = 2;
                break;
            case "addToken3":
                columnIndex = 3;
                break;
            case "addToken4":
                columnIndex = 4;
                break;
            case "addToken5":
                columnIndex = 5;
                break;
            case "addToken6":
                columnIndex = 6;
                break;
        }

        /**
         * find the last empty circle in a column
         */
        Circle lastEmptyCircle = findLastEmptyCircle(columnIndex);

        try {
            handleHumanMove(lastEmptyCircle);

            // Schedule computer move after a delay (e.g., 1 second)
            computerMoveTimeline = new Timeline(new KeyFrame(
                    Duration.seconds(0.1),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            handleComputerMove();
                        }
                    }));
            computerMoveTimeline.setCycleCount(1);
            computerMoveTimeline.play();
        } catch (NullPointerException e) {
            System.out.println("This column is full");
        }

    }

    /**
     * The function for handling the restart button and clearing the board
     *
     * @param event
     */
    @FXML
    public void restartButtonHandler(ActionEvent event) {
        currentPlayer = player1;
        playerTurn.setText(player1.name + " turn");

        /**
         * Clearing the board
         */
        for (Node node : board.getChildren()) {
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                circle.setFill(Color.web("#aeaeae"));
            }
        }
        System.out.println("Restart ckeck");
    }

    /**
     * Allows to switch the current player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Finding the last available circle in a column
     * @param columnIndex
     * @return
     */
    private Circle findLastEmptyCircle(int columnIndex) {
        Circle lastEmptyCircle = null;
        for (int rowIndex = 5; rowIndex >= 0; rowIndex--) {
            Node node = board.getChildren().get(rowIndex + columnIndex * 6);
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                if (circle.getFill().equals(Color.web("#aeaeae"))) {
                    lastEmptyCircle = circle;
                    break;
                }
            }
        }
        return lastEmptyCircle;
    }

    /**
     * This function is responsible for handling a player
     * @param lastEmptyCircle
     */
    private void handleHumanMove(Circle lastEmptyCircle) {
        if (currentPlayer.id == 1) {
            lastEmptyCircle.setFill(Color.YELLOW);
            switchPlayer();
            playerTurn.setText(player2.name + " turn");
        } else if (currentPlayer.id == 2) {
            lastEmptyCircle.setFill(Color.RED);
            switchPlayer();
            playerTurn.setText(player1.name + " turn");
        }
    }

    /**
     * For the moment this function handles a random move from the computer
     */
    private void handleComputerMove() {
        int columnIndex = (int) (Math.random() * 7);

        Circle lastEmptyCircle = findLastEmptyCircle(columnIndex);

        if (lastEmptyCircle != null) {
            lastEmptyCircle.setFill(Color.RED); // Assuming computer's color is red
            switchPlayer();
            playerTurn.setText(player1.name + " turn");
        } else {
            System.out.println("Computer chose a full column. Choosing another column...");
            handleComputerMove();
        }
    }

}
