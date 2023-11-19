package ensisa.ihm.connect4;

import ensisa.ihm.connect4.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Connect4Controller implements Initializable {

    public Player player1 = new Player("",1,false);
    public Player player2 = new Player("",2,false);
    private Player currentPlayer = player1;
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
        Circle lastEmptyCircle = null;
        for (int rowIndex = 5; rowIndex >= 0; rowIndex--) {
            Node node = board.getChildren().get(rowIndex + columnIndex*6);
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                if (circle.getFill().equals(Color.web("#aeaeae"))) {
                    lastEmptyCircle = circle;
                    break;
                }
            }
        }

        /**
         * filling the circle depending on the player
         */
        if (currentPlayer.id == 1) {
            lastEmptyCircle.setFill(Color.YELLOW);
            switchPlayer();
            playerTurn.setText(player2.name + " turn");

        }
        else if (currentPlayer.id == 2){
            lastEmptyCircle.setFill(Color.RED);
            switchPlayer();
            playerTurn.setText(player1.name + " turn");


        }
    }

    /**
     * The function for handling the restart button
     * @param event
     */
    @FXML
    public void restartButtonHandler(ActionEvent event){
        currentPlayer = player1;
        playerTurn.setText(player1.name + " turn");

        // Clear the board
        for (Node node : board.getChildren()) {
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                circle.setFill(Color.web("#aeaeae"));
            }
        }

        System.out.println("Restart");
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    public void startGame(String player1Name, String player2Name) {
        player1.setName(player1Name);
        player2.setName(player2Name);

        // Continue with the rest of your game setup
    }

    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("startPanel.fxml"));
            Parent root = loader.load();
            StartPanelController controller = loader.getController();
            controller.setMainController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
