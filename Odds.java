import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * This class represents the odds that a certain hand will win a game of poker with a certain number of players
 */
public class Odds {
    int successes; // Number of wins a hand gets in various trials
    int trials; // Number of trails to run
    int numberOfPlayers; // Number of players in the game
    Card[] hand; // The hand a player has
    ArrayList<Player> players = new ArrayList<>(); // Represents the players to be added into the game

    /**
     * Constructor for the Odds class
     * @param trials the number of trials to perform
     * @param hand the hand to test
     * @param numberOfPlayers the number of players in the game
     */
    public Odds(int trials, Card[] hand, int numberOfPlayers){
        this.trials = trials;
        this.hand = hand;
        this.numberOfPlayers = numberOfPlayers;
        for (int i=0; i<numberOfPlayers; i++){
            players.add(new Player());
        }
        this.successes = run1000games(trials); // Runs an amount of games to see how many wins a player gets
    }

    /**
     * Runs a given number of trials and returns the number of games the given hand wins
     * @param trials The number of trials to be run
     * @return the number of games the given hand wins
     */
    private int run1000games(int trials) {
        int count = 0;
        for (int i=0; i<trials; i++){
            // Create new game for each trail
            Game game = new Game(0, numberOfPlayers, players);
            game.dealOutCertainHand(this.hand); // Use the dealOutCertainHand() method to get a player to have the given hand
            game.firstFlop();
            game.secondFlop();
            game.finalFlop();
            // Add to successes if the winning players has the same cards as the given hand
            if (game.getWinningPlayer().getCard1().equals(hand[0]) && game.getWinningPlayer().getCard2().equals(hand[1])){
                count++;
            }
        }
        return count++;
    }

    /**
     * Returns the percentage of wins in the number of trials
     * @return the percentage of wins in the number of trials
     */
    public double getOdds(){
        return round(100*((double)(successes))/((double)(trials)), 2);
    }

    /**
     * This method I found online rounds a double a certain number of decimal places
     * @param value The value to be rounded
     * @param places The amount of places to round to
     * @author Luis Wasserman
     * @return The rounded double
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
