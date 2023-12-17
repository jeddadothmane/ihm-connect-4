package ensisa.ihm.connect4.model;

import javafx.scene.paint.Color;

public class Player {
    public String name;
    public int id;
    public Color color;
    public  boolean computer;

    public Player(){

    };
    public Player(String name, int id, Color color, boolean computer) {
        this.name= name;
        this.id = id;
        this.color=color;
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setComputer(boolean computer) {
        this.computer = computer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isComputer() {
        return computer;
    }

    public void setIsComputer(boolean computer) {
        this.computer = computer;
    }
}
