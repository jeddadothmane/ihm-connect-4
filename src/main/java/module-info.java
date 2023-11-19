module ensisa.ihm.connect4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ensisa.ihm.connect4 to javafx.fxml;
    exports ensisa.ihm.connect4;
}