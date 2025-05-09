

public class Graphic {

    public static final String RESET = "\u001B[0m";

    public static final String CYAN = "\u001B[36m";

    public static final String PURPLE = "\u001B[35m";

    public static final String GREEN = "\u001B[32m";

    public static final String RED = "\u001B[31m";

    public static final String YELLOW = "\u001B[33m";

    public static final String BLUE = "\u001B[34m";

    private Grid grid;

    public Graphic(Grid grid) {
        this.grid = grid;
    }

    public void printGame(Dice dice) {
        System.out.println(YELLOW+"Dice: " + dice.toString());
        System.out.println("Health: " + grid.getPlayer().getHealth()+RESET);
        int size = grid.getSize();
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                int spaceCount = 6;
                for (int i = 0; i < grid.getSnakes().size(); i++) {
                    if (x == grid.getSnakes().get(i).getHeadX() && y == grid.getSnakes().get(i).getHeadY()) {
                        spaceCount -= snakeHeadPrint(grid.getSnakes().get(i));
                    }
                    if (x == grid.getSnakes().get(i).getTailX() && y == grid.getSnakes().get(i).getTailY()) {
                        spaceCount -= snakeTailPrint(grid.getSnakes().get(i));
                    }
                }
                if (x == grid.getPlayer().getPlayerX() && y == grid.getPlayer().getPlayerY()) {
                    System.out.print(PURPLE + "p" + RESET);
                    spaceCount--;
                }
                if (x == size - 1 && y == size - 1) {
                    System.out.print(BLUE + "$" + RESET);
                    spaceCount--;
                }
                if (x == 0 && y == 0) {
                    System.out.print(BLUE + "*" + RESET);
                    spaceCount--;

                }
                for (int i = 0; i < spaceCount; i++) {
                    System.out.print(" ");
                }
                System.out.print(CYAN + "|" + RESET);
            }
            System.out.println();
            for (int k = 0; k < 7 * grid.getSize(); k++) {
                System.out.print(CYAN + "- " + RESET);
            }
            System.out.println();
        }
    }

    public int snakeHeadPrint(Snake snake) {
        int charCount = 0;
        if (snake.getName().equals("NormalSnake")) {
            System.out.print(YELLOW + "s" + (grid.getNormalSnakes().indexOf(snake) + 1) + RESET);
            charCount+=2;
        }
        if (snake.getName().equals("WildSnake")) {
            System.out.print(RED + "S" + (grid.getWildSnakes().indexOf(snake) + 1) + RESET);
            charCount+=2;
        }
        if (snake.getName().equals("KindSnake")) {
            System.out.print(GREEN + "l" + (grid.getKindSnakes().indexOf(snake) + 1) + RESET);
            charCount+=2;
        }
        return charCount;
    }

    public int snakeTailPrint(Snake snake) {
        int charCount = 0;
        if (snake.getName().equals("NormalSnake")) {
            System.out.print(YELLOW + "d" + (grid.getNormalSnakes().indexOf(snake) + 1) + RESET);
            charCount+=2;
        }
        if (snake.getName().equals("WildSnake")) {
            System.out.print(RED + "D" + (grid.getWildSnakes().indexOf(snake) + 1) + RESET);
            charCount+=2;
        }
        if (snake.getName().equals("KindSnake")) {
            System.out.print(GREEN + "r" + (grid.getKindSnakes().indexOf(snake) + 1) + RESET);
            charCount+=2;
        }
        return charCount;
    }

}