package ensisa.ihm.connect4.model;

public class Player {
    public String name;
    public int id;
    public  boolean computer;

    public Player(){

    };
    public Player(String name, int id, boolean computer) {
        this.id = id;
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
