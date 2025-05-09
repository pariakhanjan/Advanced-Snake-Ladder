// Player.java
public class Player {
    private int playerX;
    private int playerY;
    private int health;

    public Player() {
        this.playerX = 0;
        this.playerY = 0;
        this.health = 3;
    }

    public void wildSnakeAttack() {
        this.health = this.health - 1;
        if(this.health == 0) {
            System.out.println("YOU LOST!");
        }
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
