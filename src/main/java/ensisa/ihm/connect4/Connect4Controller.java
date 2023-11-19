package ensisa.ihm.connect4;

import ensisa.ihm.connect4.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Connect4Controller implements Initializable {
    private Player player1 = new Player("",1,false);
    private Player player2 = new Player("",2,false);
    private Player currentPlayer = player1;
    @FXML
    Button newGame = new Button();
    @FXML
    GridPane board = new GridPane();
    @FXML
    Button addToken0 = new Button();



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
            System.out.println(rowIndex + columnIndex*6);
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
        if (currentPlayer.id == 1) {// Change the color of the last empty circle based on the current player
            lastEmptyCircle.setFill(Color.YELLOW);
            switchPlayer();
        }
        else if (currentPlayer.id == 2){
            lastEmptyCircle.setFill(Color.RED);
            switchPlayer();
        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    public void initialize(URL url, ResourceBundle rb) {

    }



}
