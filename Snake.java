// Snake.java
public class Snake {
    private int headX;
    private int headY;
    private int tailX;
    private int tailY;

    public Snake(int headX, int headY, int tailX, int tailY) {
        this.headX = headX;
        this.headY = headY;
        this.tailX = tailX;
        this.tailY = tailY;
    }

    public void move(Player player) {
        if (player.getPlayerX() == this.headX && player.getPlayerY() == this.headY) {
            player.setPlayerX(this.getTailX());
            player.setPlayerY(this.getTailY());
        }
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public int getHeadX() {
        return headX;
    }

    public void setHeadX(int headX) {
        this.headX = headX;
    }

    public int getHeadY() {
        return headY;
    }

    public void setHeadY(int headY) {
        this.headY = headY;
    }

    public int getTailX() {
        return tailX;
    }

    public void setTailX(int tailX) {
        this.tailX = tailX;
    }

    public int getTailY() {
        return tailY;
    }

    public void setTailY(int tailY) {
        this.tailY = tailY;
    }
}
