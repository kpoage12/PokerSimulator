

public class PokerTester {

    public static void main(String[] args){
        Game game = new Game(5, 1);
        game.dealOut();
        System.out.println(game.getDeck().deckSize());
        System.out.println(game.getHands());
        game.firstFlop();
        System.out.println(game.getDeck().deckSize());
        game.secondFlop();
        game.finalFlop();
        System.out.println(game.getFlop());
        System.out.println(game.getDeck().deckSize());

    }
}
