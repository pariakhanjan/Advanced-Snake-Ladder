import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private int dimention;

    private Player player;

    private ArrayList<Snake> snakes;

    public Game(int dimention) {
        this.dimention = dimention;
        player = new Player();
        snakes = new ArrayList<>();
    }

    public void run(int dimention) {
        this.dimention = dimention;
        System.out.println("for rolling the Dice enter 1 \n for Exitting enter 0:");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        snakesNumbers();
        BoardPrint.getInstance().firstBoardPrint(snakes, player, dimention);
        while (!answer.equals("0") && player.getHealth() > 0) {
            int oldPalyerPositionY = player.getPositionY();
            int oldPalyerPositionX = player.getPositionX();
            Dice dice = throwDice();
            System.out.println("for rolling the Dice enter 1 \n for Exitting enter 0:");
            scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            if (dice == Dice.HEALTH
                    || (oldPalyerPositionX == player.getPositionX() && oldPalyerPositionY == player.getPositionY())) {
                BoardPrint.getInstance().boardPrint(snakes, player, dice, dimention);
                continue;
            }
            for (Snake snake : snakes) {
                if (snake.toString().equals("ir.ac.kntu.gamelogic.NormalSnake")) {
                    newNormalSnakePosition(snake);
                } else if (snake.toString().equals("ir.ac.kntu.gamelogic.KindSnake")) {
                    newKindSnakePosition(snake);
                } else {
                    newWildSnakePosition(snake);
                }
                snake.move(player);
            }
            BoardPrint.getInstance().boardPrint(snakes, player, dice, dimention);
            if (player.getPositionX() == dimention - 1 && player.getPositionY() == dimention - 1) {
                System.out.println("YOU WIN!");
                break;
            }
        }
        System.out.println("Game Finished!");
        scanner.close();
    }

    private Dice throwDice() {
        Dice[] faces = Dice.values();
        Dice face = faces[RandomHelper.nextInt(9)];
        switch (face) {
            case LEFT1:
                firstLeftDice();
                break;
            case LEFT2:
                secondLeftDice();
                break;
            case RIGHT1:
                firstRightDice();
                break;
            case RIGHT2:
                secondRightDice();
                break;
            case UP1:
                firstUpDice();
                break;
            case UP2:
                secondUpDice();
                break;
            case DOWN1:
                firstDownDice();
                break;
            case DOWN2:
                secondDownDice();
                break;
            default:
                player.increaseHealth();
                break;
        }
        return face;
    }

    private void snakesNumbers() {
        int snakeNumbers = RandomHelper.nextInt((((dimention * dimention) - 2) / 2) + 1);
        int normalSnakeNumbers = RandomHelper.nextInt(snakeNumbers + 1);
        int kindSnakes = RandomHelper.nextInt(snakeNumbers - normalSnakeNumbers + 1);
        int wildSnakes = snakeNumbers - normalSnakeNumbers - kindSnakes;
        for (int i = 0; i < normalSnakeNumbers; i++) {
            int[] positions = randomPosition();
            snakes.add(new NormalSnake(positions[0], positions[1], positions[2], positions[3]));
        }
        for (int i = 0; i < kindSnakes; i++) {
            int[] positions = kindSnakeRandomPosition();
            snakes.add(new KindSnake(positions[0], positions[1], positions[2], positions[3]));
        }
        for (int i = 0; i < wildSnakes; i++) {
            int[] positions = randomPosition();
            snakes.add(new WildSnake(positions[0], positions[1], positions[2], positions[3]));
        }
    }

    private int[] randomPosition() {
        int headPositionX = RandomHelper.nextInt(dimention);
        int headPositionY = RandomHelper.nextInt(dimention);
        int tailPositionX = RandomHelper.nextInt(dimention);
        int tailPositionY = RandomHelper.nextInt(dimention);
        int[] positions = checkPositions(headPositionX, headPositionY, tailPositionX, tailPositionY);
        return positions;
    }

    private void newWildSnakePosition(Snake snake) {
        int[] positions = randomPosition();
        snake.setHeadPositionX(positions[0]);
        snake.setHeadPositionY(positions[1]);
        snake.setTailPositionX(positions[2]);
        snake.setTailPositionY(positions[3]);
    }

    private void newNormalSnakePosition(Snake snake) {
        int[] positions = randomNormalPosition(snake.getHeadPositionX(), snake.getHeadPositionY());
        snake.setTailPositionX(positions[2]);
        snake.setTailPositionY(positions[3]);
    }

    private void newKindSnakePosition(Snake snake) {
        int[] positions = randomKindPosition(snake.getHeadPositionX(), snake.getHeadPositionY());
        snake.setTailPositionX(positions[2]);
        snake.setTailPositionY(positions[3]);
    }

    private int[] randomNormalPosition(int headPositionX, int headPositionY) {
        int tailPositionX = RandomHelper.nextInt(dimention);
        int tailPositionY = RandomHelper.nextInt(dimention);
        int[] positions = checkNormalPosition(headPositionX, headPositionY, tailPositionX, tailPositionY);
        return positions;
    }

    private int[] checkNormalPosition(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        while ((tailPositionX == 0 && tailPositionY == 0) || (headPositionY <= tailPositionY)
                || !checkNormalWithOtherSnakes(headPositionX, headPositionY, tailPositionX, tailPositionY)) {
            tailPositionX = RandomHelper.nextInt(dimention);
            tailPositionY = RandomHelper.nextInt(dimention);
        }
        int[] positions = { headPositionX, headPositionY, tailPositionX, tailPositionY };
        return positions;
    }

    private boolean checkNormalWithOtherSnakes(int headPositionX, int headPositionY, int tailPositionX,
            int tailPositionY) {
        boolean check = true;
        for (Snake snake : snakes) {
            if (tailPositionX == snake.getHeadPositionX() && tailPositionY == snake.getHeadPositionY()) {
                check = false;
            }
        }
        return check;
    }

    private int[] checkPositions(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        while ((tailPositionX == 0 && tailPositionY == 0) || (headPositionY == 0
                || (headPositionX == dimention - 1 && headPositionY == dimention - 1))
                || (headPositionY <= tailPositionY)
                || !checkWithOtherSnakes(headPositionX, headPositionY, tailPositionX, tailPositionY)) {
            tailPositionX = RandomHelper.nextInt(dimention);
            tailPositionY = RandomHelper.nextInt(dimention);
            headPositionX = RandomHelper.nextInt(dimention);
            headPositionY = RandomHelper.nextInt(dimention);
        }
        int[] positions = { headPositionX, headPositionY, tailPositionX, tailPositionY };
        return positions;
    }

    private boolean checkWithOtherSnakes(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        boolean check = true;
        for (Snake snake : snakes) {
            if (headPositionX == snake.getHeadPositionX() && headPositionY == snake.getHeadPositionY()) {
                check = false;
            }
            if (headPositionX == snake.getTailPositionX() && headPositionY == snake.getTailPositionY()) {
                check = false;
            }
            if (tailPositionX == snake.getHeadPositionX() && tailPositionY == snake.getHeadPositionY()) {
                check = false;
            }
        }
        return check;
    }

    private int[] kindSnakeRandomPosition() {
        int headPositionX = RandomHelper.nextInt(dimention);
        int headPositionY = RandomHelper.nextInt(dimention);
        int tailPositionX = RandomHelper.nextInt(dimention);
        int tailPositionY = RandomHelper.nextInt(dimention);
        return kindSnakeCheckPositions(headPositionX, headPositionY, tailPositionX, tailPositionY);
    }

    private int[] kindSnakeCheckPositions(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        while ((headPositionX == 0 && headPositionY == 0)
                || (tailPositionY == 0 || (tailPositionX == dimention - 1 && tailPositionY == dimention - 1))
                || (headPositionX == tailPositionX && headPositionY == tailPositionY)
                || (headPositionY >= tailPositionY)
                || !checkWithOtherSnakes(headPositionX, headPositionY, tailPositionX, tailPositionY)) {
            headPositionX = RandomHelper.nextInt(dimention);
            headPositionY = RandomHelper.nextInt(dimention);
            tailPositionX = RandomHelper.nextInt(dimention);
            tailPositionY = RandomHelper.nextInt(dimention);
        }
        int[] positions = { headPositionX, headPositionY, tailPositionX, tailPositionY };
        return positions;
    }

    private int[] randomKindPosition(int headPositionX, int headPositionY) {
        int tailPositionX = RandomHelper.nextInt(dimention);
        int tailPositionY = RandomHelper.nextInt(dimention);
        return checkKindPositions(headPositionX, headPositionY, tailPositionX, tailPositionY);
    }

    private int[] checkKindPositions(int headPositionX, int headPositionY, int tailPositionX, int tailPositionY) {
        while ((tailPositionY == 0 || (tailPositionX == dimention - 1 && tailPositionY == dimention - 1))
                || (headPositionX == tailPositionX && headPositionY == tailPositionY)
                || (headPositionY >= tailPositionY)
                || !checkNormalWithOtherSnakes(headPositionX, headPositionY, tailPositionX, tailPositionY)) {
            tailPositionX = RandomHelper.nextInt(dimention);
            tailPositionY = RandomHelper.nextInt(dimention);
        }
        int[] positions = { headPositionX, headPositionY, tailPositionX, tailPositionY };
        return positions;
    }

    private void firstLeftDice() {
        if (player.getPositionX() > 0) {
            player.setPositionX(player.getPositionX() - 1);
        }
    }

    private void secondLeftDice() {
        if (player.getPositionX() > 1) {
            player.setPositionX(player.getPositionX() - 2);
        }
    }

    private void firstRightDice() {
        if (player.getPositionX() < dimention - 1) {
            player.setPositionX(player.getPositionX() + 1);
        }
    }

    private void secondRightDice() {
        if (player.getPositionX() < dimention - 2) {
            player.setPositionX(player.getPositionX() + 2);
        }
    }

    private void firstUpDice() {
        if (player.getPositionY() < dimention - 1) {
            player.setPositionY(player.getPositionY() + 1);
        }
    }

    private void secondUpDice() {
        if (player.getPositionY() < dimention - 2) {
            player.setPositionY(player.getPositionY() + 2);
        }
    }

    private void firstDownDice() {
        if (player.getPositionY() > 0) {
            player.setPositionY(player.getPositionY() - 1);
        }
    }

    private void secondDownDice() {
        if (player.getPositionY() > 1) {
            player.setPositionY(player.getPositionY() - 2);
        }
    }

}
