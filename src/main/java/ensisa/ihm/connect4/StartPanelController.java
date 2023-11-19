package ensisa.ihm.connect4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class StartPanelController {
    @FXML
    Button startGameButton = new Button();

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    private Connect4Controller mainController;

    @FXML
    private ChoiceBox<String> playerTypeChoiceBox1;
    @FXML
    private ChoiceBox<String> playerTypeChoiceBox2;
    @FXML
    private AnchorPane startPanel;


    public void initialize() {
        playerTypeChoiceBox1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (mainController != null) {
                mainController.startGame(newValue, getOpponentType(newValue));
            }
        });
    }

    public void setMainController(Connect4Controller mainController) {
        this.mainController = mainController;
    }
    private String getOpponentType(String playerType) {
        return playerType.equals("Computer") ? "Human" : "Computer";
    }

    @FXML
    public void startGameButtonHandler(ActionEvent event) {
        String player1NameText = player1Name.getText();
        String player2NameText = player2Name.getText();

            try {
                if (!player1NameText.isEmpty() && !player2NameText.isEmpty()) {

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = stage.getScene();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
                    Parent root = loader.load();

                    /**
                     * Setting some different values of Connect4Controller's attributes needed
                     */
                    Connect4Controller newController = loader.getController();
                    newController.player1.setName(player1NameText);
                    newController.player2.setName(player2NameText);
                    newController.player1.setId(1);
                    newController.player2.setId(2);
                    newController.playerTurn.setText(player1NameText + " turn");
                    newController.player1Color.setText(player1NameText);
                    newController.player2Color.setText(player2NameText);
                    newController.isStartPanelLoaded = true;


                    scene.setRoot(root);
                    newController.startGame(player1NameText, player2NameText);

                    /**
                     * Hide the starting panel
                     */
                    startPanel.setVisible(false);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }



    }

}
