public class Main {

    public static void main(String[] args) {
        int dimention = RandomHelper.nextInt(15);
        Game game = new Game(dimention);
        game.run(dimention);
    }
}