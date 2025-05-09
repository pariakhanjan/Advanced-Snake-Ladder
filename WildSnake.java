// WildSnake.java
public class WildSnake extends Snake {
    public WildSnake(int headX, int headY, int tailX, int tailY) {
        super(headX, headY, tailX, tailY);
    }

    @Override
    public void move(Player player) {
        if (player.getPlayerX() == this.getHeadX() && player.getPlayerY() == this.getHeadY()) {
            player.setPlayerX(this.getTailX());
            player.setPlayerY(this.getTailY());
            player.wildSnakeAttack();
        }
    }
}
