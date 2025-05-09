

public class Snake {

    private int headPositionX;

    private int headPositionY;

    private int tailPositionX;

    private int tailPositionY;

    public Snake(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        this.headPositionX = headPositionX;
        this.headPositionY = headPositionY;
        this.tailPositionX = tailPositionX;
        this.tailPositionY = tailPositionY;
    }

    public void move(Player player) {
        if (player.getPositionX() == this.headPositionX && player.getPositionY() == this.headPositionY) {
            player.setPositionX(this.getTailPositionX());
            player.setPositionY(this.getTailPositionY());
        }
    }

    public int getHeadPositionX() {
        return headPositionX;
    }

    public void setHeadPositionX(int headPositionX) {
        this.headPositionX = headPositionX;
    }

    public int getHeadPositionY() {
        return headPositionY;
    }

    public void setHeadPositionY(int headPositionY) {
        this.headPositionY = headPositionY;
    }

    public int getTailPositionX() {
        return tailPositionX;
    }

    public void setTailPositionX(int tailPositionX) {
        this.tailPositionX = tailPositionX;
    }

    public int getTailPositionY() {
        return tailPositionY;
    }

    public void setTailPositionY(int tailPositionY) {
        this.tailPositionY = tailPositionY;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
