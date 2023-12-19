package ensisa.ihm.connect4;

import ensisa.ihm.connect4.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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

    public Player player1 = new Player("Player 1", 1, Color.YELLOW);
    public Player player2 = new Player("Player 2", 2, Color.RED);
    private Player currentPlayer = player1;
    private boolean isHumanVsComputer = false;
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
    @FXML
    public Button help;

    /**
     * Initializes the game mode popup
     */
    @FXML
    public void initialize() {
        showGameModePopup();
    }

    /**
     * Handles the restart button and clearing the board
     */
    @FXML
    public void restartButtonHandler() {
        showRestartConfirmation();
    }

    /**
     * Handles the cancel button and clearing the board
     */
    @FXML
    public void cancelButtonHandler() {
        showExitConfirmationDialog();
    }

    /**
     * Handles the help button
     */
    @FXML
    public void helpButtonHandler() {
        showHelpDialog();
    }

    /**
     * This function handles the adding of the circles in the board depending on the player
     *
     * @param event event
     */
    @FXML
    public void addTokenButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        int columnIndex = Integer.parseInt(buttonId.replace("addToken", ""));

        Circle lastEmptyCircle = findLastEmptyCircle(columnIndex);

        try {
            lastEmptyCircle.setFill(Color.DARKGRAY);

            Timeline colorTransitionTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.2), new KeyValue(lastEmptyCircle.fillProperty(), currentPlayer.color))
            );
            colorTransitionTimeline.play();

            handleHumanMove(lastEmptyCircle);

            if (isHumanVsComputer && !gameOver()) {
                Timeline computerMoveTimeline = new Timeline(new KeyFrame(
                        Duration.seconds(0.5),
                        event1 -> handleComputerMove()
                ));
                computerMoveTimeline.setCycleCount(1);
                computerMoveTimeline.play();
            }

        } catch (NullPointerException e) {
            showColumnFullAlert();
        }

        if (isHumanVsComputer && isWinner(player2)) {
            System.out.println(player2.name + " is the winner!");
        } else if (!isHumanVsComputer && isWinner(player1)) {
            System.out.println(player1.name + " is the winner!");
        }
        if (isWinner(currentPlayer)) {
            System.out.println(currentPlayer.name + " is the winner!");
        }
        if (gameOver()) {
            System.out.println("Game Over !!");
        }
    }

    /**
     * Checks if there are 4 circles of the same player in row
     *
     * @param player player
     * @param col column
     * @param row row
     * @param dCol dCol
     * @param dRow dRow
     *  @return true if the player has 4 in row, else false
     */
    private boolean checkFourInARow(Player player, int col, int row, int dCol, int dRow) {
        int consecutiveCount = 1;
        Color targetColor = player.color;

        int curCol = col;
        int curRow = row;

        while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) {
            int index = (curCol * 6) + curRow;

            Node node = board.getChildren().get(index);

            if (node instanceof Circle circle) {
                if (circle.getFill().equals(targetColor)) {
                    consecutiveCount++;
                } else {
                    consecutiveCount = 1;
                }

                if (consecutiveCount > 4) {
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
     * @param player the player to check
     * @return return true if the current player is winner, or false if he's not a winner
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

        // Checks diagonals
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
     * @return true if the board is full
     */
    private boolean isBoardFull() {
        return numberTokens == 42;
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
     * @param columnIndex the index of the column to be checked
     * @return the last circle of the column
     */
    private Circle findLastEmptyCircle(int columnIndex) {
        Circle lastEmptyCircle = null;
        for (int rowIndex = 5; rowIndex >= 0; rowIndex--) {
            Node node = board.getChildren().get(rowIndex + columnIndex * 6);
            if (node instanceof Circle circle) {
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
     * @param lastEmptyCircle the last empty circle in the column that will be affected
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
            lastEmptyCircle.setFill(Color.RED);
            switchPlayer();
            playerTurn.setText(player1.name + " turn");
        } else {
            System.out.println("Computer chose a full column. Choosing another column...");
            handleComputerMove();
        }
    }

    /**
     * method to know if the game is over and to show the popup of game over
     * @return true if the game is over, or false if it is not over yet
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
        alert.setTitle("Exit");
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

    /**
     * Shows an alert with a title and a content
     */
    private void showColumnFullAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Full column");
        alert.setHeaderText(null);
        alert.setContentText("This column is full. Choose another column.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    /**
     * Shows a popup that contains the rules of the connect4 game
     */
    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Connect 4 is a game for two players. Each player picks " +
                "a color and drops discs into a vertical grid with seven columns and six rows." +
                " The goal is to connect four discs of your color in a row, either vertically, " +
                "horizontally, or diagonally, before your opponent does.\n" +
                "Players take turns dropping discs from the top, " +
                "and the discs fall down to the lowest available spot in the chosen column.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
    /**
     * Shows the popup to choose the game mode
     */
    private void showGameModePopup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Game Mode");
        alert.setHeaderText("Choose the game mode: ");

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
     * Shows the popup of the confirmation of restarting a game
     */
    private void showRestartConfirmation(){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Restart");
        confirmationAlert.setContentText("Are you sure you want to restart ?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        confirmationAlert.initModality(Modality.APPLICATION_MODAL);

        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                currentPlayer = player1;
                playerTurn.setText(player1.name + " turn");

                for (Node node : board.getChildren()) {
                    if (node instanceof Circle circle) {
                        circle.setFill(Color.web("#aeaeae"));
                    }
                }
                numberTokens = 0;
                System.out.println("Game restarted");
            }
        });

    }

    /**
     * Shows the popup of the game over
     * @param message the message to print if the game is over
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
                currentPlayer = player1;
                playerTurn.setText(player1.name + " turn");

                for (Node node : board.getChildren()) {
                    if (node instanceof Circle) {
                        Circle circle = (Circle) node;
                        circle.setFill(Color.web("#aeaeae"));
                    }
                }
                numberTokens = 0;
                System.out.println("Game restarted");
            } else {
                System.exit(0);
            }
        });
    }


}