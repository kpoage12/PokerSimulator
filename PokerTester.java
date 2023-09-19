import java.util.ArrayList;
import java.util.Scanner;

public class PokerTester {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("How many players?");
        int numberOfPlayers = scan.nextInt();
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++){
            Scanner scan2 = new Scanner(System.in);
            System.out.println("Enter a name for player " + (i+1));
            String name = scan2.nextLine();
            players.add(new Player(name));
        }
        Game game = new Game(0, numberOfPlayers, players);
        game.dealOut();
        System.out.println(game.getHands());
        game.firstFlop();
        game.secondFlop();
        game.finalFlop();
        System.out.println(game.getFlopString());
        game.getWinningPlayer();
        System.out.println(game.getAllHighHands());
        System.out.println(game.getWinningPlayer().getName() + " wins! " +  game.getWinningPlayer().getBestHand());
        /*
        Card card1 = new Card(Suit.HEARTS, Value.FIVE);
        Card card2 = new Card(Suit.SPADES,Value.FIVE);
        Card[] hand = {card1, card2};
        Odds odds = new Odds(10000, hand, 5);
        System.out.println(100*odds.getOdds()+"%");

         */
    }
}
