package ensisa.ihm.connect4.model;

public class Player {
    public String name;
    public int id;
    public  boolean computer;

    public Player(String name, int id, boolean computer) {
        this.name = name;
        this.id = id;
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setComputer(boolean computer) {
        this.computer = computer;
    }
//    public Player() {
//
//    }
}
