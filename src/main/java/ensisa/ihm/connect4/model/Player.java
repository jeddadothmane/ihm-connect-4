package ensisa.ihm.connect4.model;

import javafx.scene.paint.Color;

public class Player {
    public String name;
    public int id;
    public Color color;

    public Player(){

    };
    public Player(String name, int id, Color color) {
        this.name= name;
        this.id = id;
        this.color=color;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
