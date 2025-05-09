public class WildSnake extends Snake {

    public WildSnake(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        super(headPositionX, headPositionY, tailPositionX, tailPositionY);
    }

    @Override
    public void move(Player player) {
        if (player.getPositionX() == this.getHeadPositionX() && player.getPositionY() == this.getHeadPositionY()) {
            player.setPositionX(this.getTailPositionX());
            player.setPositionY(this.getTailPositionY());
            player.wildSnakeAttack();
        }
    }

}
