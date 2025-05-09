// Grid.java
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {
    private int size;
    private ArrayList<Snake> snakes;
    private ArrayList<KindSnake> kindSnakes;
    private ArrayList<NormalSnake> normalSnakes;
    private ArrayList<WildSnake> wildSnakes;
    private Player player;

    public Grid(int size, Player player) {
        this.size = size;
        this.player = player;
        snakes = new ArrayList<>();
        kindSnakes = new ArrayList<>();
        normalSnakes = new ArrayList<>();
        wildSnakes = new ArrayList<>();
        addSnakes();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        int yOld = 0;
        int xOld = 0;
        Graphic graphic = new Graphic(this);
        System.out.println("\u001B[33m***************Welcome!***************\u001B[0m");
        do {
            System.out.println("Enter any character to roll dice.\nEnter 0 to exit.");
            input = scanner.nextLine();
            if (!input.equals("0")) {
                Dice dice = rollDice();
                graphic.printGame(dice);
                if (!dice.equals(Dice.HEALTH) && (player.getPlayerY() != yOld || xOld != player.getPlayerX())) {
                    changeGrid();
                }
            }
            if (player.getPlayerY() == size - 1 && player.getPlayerX() == size - 1) {
                System.out.println("You've won! Good Job.");
                System.exit(0);
            }
            yOld = player.getPlayerY();
            xOld = player.getPlayerX();
        } while (!input.equals("0") && player.getHealth() != 0);
        scanner.close();
        System.exit(0);
    }

    private Dice rollDice() {
        Dice[] options = Dice.values();
        Dice dice = options[RandomHelper.nextInt(9)];
        switch (dice) {
            case HEALTH -> health();
            case ONE_RIGHT -> oneRight();
            case TWO_RIGHT -> twoRight();
            case ONE_LEFT -> oneLeft();
            case TWO_LEFT -> twoLeft();
            case ONE_UP -> oneUp();
            case TWO_UP -> twoUp();
            case ONE_DOWN -> oneDown();
            case TWO_DOWN -> twoDown();
            default -> {
                break;
            }
        }
        return dice;
    }

    private void changeGrid() {
        for (Snake value : snakes) {
            if (value.getName().equals("KindSnake")) {
                changeKind((KindSnake) value);
            } else if (value.getName().equals("NormalSnake")) {
                changeNormal((NormalSnake) value);
            } else if (value.getName().equals("WildSnake")) {
                changeWild((WildSnake) value);
            }
        }
        for (Snake snake : snakes) {
            snake.move(player);
        }
    }

    private void changeKind(KindSnake kind) {
        int tailX;
        int tailY;
        do {
            tailX = RandomHelper.nextInt(size);
            tailY = RandomHelper.nextInt(size);
        } while ((tailX == size - 1 && tailY == size - 1) || kind.getHeadY() >= tailY ||
                (kind.getHeadX() == 0 && (kind.getHeadY()) == 0) || !uniqueSnake(0, 0, tailX, tailY));
        kind.setTailX(tailX);
        kind.setTailY(tailY);
    }

    private void changeNormal(NormalSnake normal) {
        int tailX;
        int tailY;
        do {
            tailX = RandomHelper.nextInt(size);
            tailY = RandomHelper.nextInt(size);
        } while ((normal.getHeadX() == size - 1 && normal.getHeadY() == size - 1) || tailY >= normal.getHeadY() ||
                (tailX == 0 && tailY == 0) || !uniqueSnake(0 ,0, tailX, tailY));
        normal.setTailX(tailX);
        normal.setTailY(tailY);
    }

    private void changeWild(WildSnake wild) {
        int headX;
        int headY;
        int tailX;
        int tailY;
        do {
            headX = RandomHelper.nextInt(size);
            headY = RandomHelper.nextInt(size);
            tailX = RandomHelper.nextInt(size);
            tailY = RandomHelper.nextInt(size);
        } while ((headX == size - 1 && headY == size - 1) || tailY >= headY || (tailX == 0 && tailY == 0) ||
                !uniqueSnake(headX, headY, tailX, tailY));
        wild.setTailX(tailX);
        wild.setTailY(tailY);
        wild.setHeadX(headX);
        wild.setHeadY(headY);
    }

    private void addSnakes() {
        int snakeCount = RandomHelper.nextInt((((size * size) - 2) / 2) + 2);
        int normalCount = RandomHelper.nextInt(snakeCount + 1);
        for (int i = 0; i < normalCount; i++) {
            snakes.add(wildAndNormalCheck("normal"));
            normalSnakes.add((NormalSnake) snakes.get(i));
        }
        snakeCount -= normalCount;
        int kindCount = RandomHelper.nextInt(snakeCount + 1);
        for (int i = 0; i < kindCount; i++) {
            snakes.add(kindCheck());
            kindSnakes.add((KindSnake) snakes.get(i + normalCount));
        }
        snakeCount -= kindCount;
        int wildCount = RandomHelper.nextInt(snakeCount + 1);
        for (int i = 0; i < wildCount; i++) {
            snakes.add(wildAndNormalCheck("wild"));
            wildSnakes.add((WildSnake) snakes.get(i + normalCount + kindCount));
        }
    }

    private boolean uniqueSnake(int headX, int headY, int tailX, int tailY) {
        if (snakes.size() > 0) {
            for (Snake value : snakes) {
                if (value.getHeadX() == headX && value.getHeadY() == headY) {
                    return false;
                }
                if (value.getTailX() == headX && value.getTailY() == headY) {
                    return false;
                }
                if (value.getHeadX() == tailX && value.getHeadY() == tailY) {
                    return false;
                }
            }
        }
        return true;
    }

    private Snake wildAndNormalCheck(String type) {
        int headX;
        int headY;
        int tailX;
        int tailY;
        do {
            headX = RandomHelper.nextInt(size);
            headY = RandomHelper.nextInt(size);
            tailX = RandomHelper.nextInt(size);
            tailY = RandomHelper.nextInt(size);
        } while ((headX == size - 1 && headY == size - 1) || tailY >= headY || (tailX == 0 && tailY == 0)
                || !uniqueSnake(headX, headY, tailX, tailY));
        if (type.equals("normal")) {
            return new NormalSnake(headX, headY, tailX, tailY);
        }
        if (type.equals("wild")) {
            return new WildSnake(headX, headY, tailX, tailY);
        }
        return new Snake(headX, headY, tailX, tailY);
    }

    private KindSnake kindCheck() {
        int headX;
        int headY;
        int tailX;
        int tailY;
        do {
            headX = RandomHelper.nextInt(size);
            headY = RandomHelper.nextInt(size);
            tailX = RandomHelper.nextInt(size);
            tailY = RandomHelper.nextInt(size);
        } while ((tailX == size - 1 && tailY == size - 1) || headY >= tailY || (headX == 0 && headY == 0)
                || !uniqueSnake(headX, headY, tailX, tailY));
        return new KindSnake(headX, headY, tailX, tailY);
    }

    private void health() {
        if (player.getHealth() < 3) {
            player.setHealth(player.getHealth() + 1);
        }
    }

    private void twoRight() {
        if (player.getPlayerX() < size - 2) {
            player.setPlayerX(player.getPlayerX() + 2);
        }
    }

    private void oneRight() {
        if (player.getPlayerX() < size - 1) {
            player.setPlayerX(player.getPlayerX() + 1);
        }
    }

    private void oneLeft() {
        if (player.getPlayerX() > 0) {
            player.setPlayerX(player.getPlayerX() - 1);
        }
    }

    private void twoLeft() {
        if (player.getPlayerX() > 1) {
            player.setPlayerX(player.getPlayerX() - 2);
        }
    }

    private void oneUp() {
        if (player.getPlayerY() < size - 1) {
            player.setPlayerY(player.getPlayerY() + 1);
        }
    }

    private void twoUp() {
        if (player.getPlayerY() < size - 2) {
            player.setPlayerY(player.getPlayerY() + 2);
        }
    }

    private void oneDown() {
        if (player.getPlayerY() > 0) {
            player.setPlayerY(player.getPlayerY() - 1);
        }
    }

    private void twoDown() {
        if (player.getPlayerY() > 2) {
            player.setPlayerY(player.getPlayerY() - 2);
        }
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Snake> getSnakes() {
        return new ArrayList<>(snakes);
    }

    public ArrayList<KindSnake> getKindSnakes() {
        return new ArrayList<>(kindSnakes);
    }

    public ArrayList<NormalSnake> getNormalSnakes() {
        return new ArrayList<>(normalSnakes);
    }

    public ArrayList<WildSnake> getWildSnakes() {
        return new ArrayList<>(wildSnakes);
    }

    public Player getPlayer() {
        return player;
    }
}
