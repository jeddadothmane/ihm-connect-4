package ensisa.ihm.connect4;

import ensisa.ihm.connect4.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.util.Duration;


public class Connect4Controller {

    public Player player1 = new Player("Player 1", 1, Color.YELLOW, false);
    public Player player2 = new Player("Player 2", 2, Color.RED, false);
    private Player currentPlayer = player1;
    private boolean isHumanVsComputer = false;

    private Timeline computerMoveTimeline;
    private int numberTokens = 0;
    @FXML
    Button restart = new Button();
    @FXML
    Button exit = new Button();

    @FXML
    GridPane board = new GridPane();
    @FXML
    Button addToken0 = new Button();
    @FXML
    public Text playerTurn;
    @FXML
    public Text player2Color;

    /**
     * Initializes the games
     */
    @FXML
    public void initialize() {
        showGameModePopup();
    }

    /**
     * Shows the popup to choose the game mode
     */
    private void showGameModePopup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Game Mode");
        alert.setHeaderText("Select the game mode");
        alert.setContentText("Choose the game mode:");

        ButtonType humanVsHumanButton = new ButtonType("Human vs Human");
        ButtonType humanVsComputerButton = new ButtonType("Human vs Computer");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(humanVsHumanButton, humanVsComputerButton, cancelButton);

        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == humanVsHumanButton) {
                isHumanVsComputer = false;
                player2.name = "Player 2";
            } else if (buttonType == humanVsComputerButton) {
                isHumanVsComputer = true;
                player2.name = "Computer";
                player2Color.setText(player2.name);
            } else {
                System.exit(0);
            }
        });
    }

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

            if (isHumanVsComputer && !gameOver()) {
                // Schedule computer move after a delay (e.g., 1 second)
                computerMoveTimeline = new Timeline(new KeyFrame(
                        Duration.seconds(0.5),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                handleComputerMove();
                            }
                        }));
                computerMoveTimeline.setCycleCount(1);
                computerMoveTimeline.play();
            }

        } catch (NullPointerException e) {
            System.out.println("This column is full");
        }

        if (isHumanVsComputer && isWinner(player2)) {
            System.out.println(player2.name + " is the winner!");
        } else if (!isHumanVsComputer && isWinner(player1)) {
            System.out.println(player1.name + " is the winner!");
        }
        if (isWinner(currentPlayer)) {
            System.out.println(currentPlayer.name + " is the winner!");
        }
        if(gameOver()){
            System.out.println("Game Over !!");
        };
    }

    /**
     * Checks if there are 4 circles of the same player in row
     *
     * @param player
     * @param col
     * @param row
     * @param dCol
     * @param dRow
     * @return
     */
    private boolean checkFourInARow(Player player, int col, int row, int dCol, int dRow) {
        int count = 1;
        Color targetColor = player.color;

        int curCol = col;
        int curRow = row;

        while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) {
            int index = (curCol * 6) + curRow;

            Node node = board.getChildren().get(index);

            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                if (circle != null && circle.getFill().equals(targetColor)) {
                    count++;
                }

                if (count > 4) {
                    return true;
                }
            }
            curCol += dCol;
            curRow += dRow;
        }
        return false;
    }

    /**
     * Checks if player is winner of the game
     * @param player
     * @return
     */
    private boolean isWinner(Player player) {
        // Check rows
        for (int row = 0; row < 7; row++) {
            if (checkFourInARow(player, 0, row, 1, 0)) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 7; col++) {
            if (checkFourInARow(player, col, 0, 0, 1)) {
                return true;
            }

        }

        for (int row = 0; row < 7; row++) {
            if (checkFourInARow(player, 0, row, 1, 1)) {
                return true;
            }
            if (checkFourInARow(player, 6, row, -1, 1)) {
                return true;
            }
        }
        for (int row = 0; row < 7; row++) {
            if (checkFourInARow(player, 0, row, 1, 1)) {
                return true;
            }
            if (checkFourInARow(player, 7, row, -1, 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the board is full
     * @return
     */
    private boolean isBoardFull() {
        return numberTokens == 42;
    }


    /**
     * Handles the restart button and clearing the board
     */
    @FXML
    public void restartButtonHandler() {
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
        numberTokens = 0;

        System.out.println("Restart the game");
    }

    /**
     * Handles the cancel button and clearing the board
     */
    @FXML
    public void cancelButtonHandler() {
        showExitConfirmationDialog();
    }

    /**
     * Allows to switch the current player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }


    /**
     * Finding the last available circle in a column
     *
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
     *
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

    /**
     * Shows the popup of the game over
     * @param message
     */
    private void showGameOverPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(message);
        alert.setContentText("Do you want to play again?");

        ButtonType playAgainButton = new ButtonType("Play Again");
        ButtonType exitButton = new ButtonType("Exit");

        alert.getButtonTypes().setAll(playAgainButton, exitButton);

        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == playAgainButton) {
                restartButtonHandler();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * method to know if the game is over and show the popup of game over
     * @return
     */
    private boolean gameOver() {
        if (isWinner(player1)) {
            showGameOverPopup(player1.name + " is the winner!");
            return true;
        } else if (isWinner(player2)) {
            showGameOverPopup(player2.name + " is the winner!");
            return true;
        } else if (isBoardFull()) {
            showGameOverPopup("It's a draw! The board is full.");
            return true;
        }
        return false;
    }

    /**
     * The exit confirmation
     */
    private void showExitConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                System.exit(0);
            }
        });
    }

}