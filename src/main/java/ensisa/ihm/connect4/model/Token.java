package ensisa.ihm.connect4.model;

public class Token {
    public String color;
    public Player player;
    public int col;
    public int row;

    public Token(String color, Player player, int col, int row) {
        this.color = color;
        this.player = player;
        this.col = col;
        this.row = row;
    }
}
