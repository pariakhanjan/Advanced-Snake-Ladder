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
        // Print game header
        System.out.println(YELLOW + "Dice: " + (dice != null ? dice.toString() : "Ready to roll") + RESET);
        System.out.println("Health: " + grid.getPlayer().getHealth());

        // Print game board
        for (int y = grid.getSize()-1; y >= 0; y--) {
            printHorizontalBorder();
            for (int x = 0; x < grid.getSize(); x++) {
                System.out.print(CYAN + "|" + RESET);
                printCellContents(x, y);
            }
            System.out.println(CYAN + "|" + RESET);
        }
        printHorizontalBorder();
    }

    private void printHorizontalBorder() {
        for (int i = 0; i < grid.getSize(); i++) {
            System.out.print(CYAN + "+-------" + RESET);
        }
        System.out.println(CYAN + "+" + RESET);
    }

    private void printCellContents(int x, int y) {
        String cellContent = "      ";
        int contentLength = 6;

        // Check for player first
        if (x == grid.getPlayer().getPlayerX() && y == grid.getPlayer().getPlayerY()) {
            cellContent = centerText(PURPLE + "PLAYER" + RESET, 6);
            contentLength -= 6;
        }
        // Check for win position
        else if (x == grid.getSize()-1 && y == grid.getSize()-1) {
            cellContent = centerText(BLUE + "GOAL" + RESET, 6);
            contentLength -= 4;
        }
        // Check for start position
        else if (x == 0 && y == 0) {
            cellContent = centerText(BLUE + "START" + RESET, 6);
            contentLength -= 5;
        }

        // Check for snakes (may overlap with other elements)
        for (Snake snake : grid.getSnakes()) {
            if (x == snake.getHeadX() && y == snake.getHeadY()) {
                String headText = getSnakeHeadText(snake);
                cellContent = combineCellContent(cellContent, headText);
                contentLength -= headText.replaceAll("\u001B\\[[;\\d]*m", "").length();
            }
            if (x == snake.getTailX() && y == snake.getTailY()) {
                String tailText = getSnakeTailText(snake);
                cellContent = combineCellContent(cellContent, tailText);
                contentLength -= tailText.replaceAll("\u001B\\[[;\\d]*m", "").length();
            }
        }

        System.out.print(cellContent);
    }

    private String getSnakeHeadText(Snake snake) {
        switch (snake.getName()) {
            case "NormalSnake": return YELLOW + "S" + (grid.getNormalSnakes().indexOf(snake)+1) + RESET;
            case "WildSnake": return RED + "W" + (grid.getWildSnakes().indexOf(snake)+1) + RESET;
            case "KindSnake": return GREEN + "K" + (grid.getKindSnakes().indexOf(snake)+1) + RESET;
            default: return "";
        }
    }

    private String getSnakeTailText(Snake snake) {
        switch (snake.getName()) {
            case "NormalSnake": return YELLOW + "T" + (grid.getNormalSnakes().indexOf(snake)+1) + RESET;
            case "WildSnake": return RED + "t" + (grid.getWildSnakes().indexOf(snake)+1) + RESET;
            case "KindSnake": return GREEN + "t" + (grid.getKindSnakes().indexOf(snake)+1) + RESET;
            default: return "";
        }
    }

    private String centerText(String text, int width) {
        int padSize = (width - text.replaceAll("\u001B\\[[;\\d]*m", "").length()) / 2;
        return String.format("%" + (padSize + text.length()) + "s", text);
    }

    private String combineCellContent(String original, String newContent) {
        // Replace the first available space with new content
        return original.replaceFirst("\\s{" + newContent.replaceAll("\u001B\\[[;\\d]*m", "").length() + "}", newContent);
    }
}
