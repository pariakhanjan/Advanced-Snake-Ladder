public class Player {

    private int positionX;

    private int positionY;

    private int health;

    public Player() {
        this.positionX = 0;
        this.positionY = 0;
        this.health = 3;
    }

    public void wildSnakeAttack() {
        this.health = this.health - 1;
        if(this.health == 0) {
            System.out.println("YOU LOST!");
        }
    }

    public void increaseHealth() {
        if (0 < this.health && this.health < 3) {
            this.health = this.health + 1;
        }
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
