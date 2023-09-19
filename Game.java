import java.util.ArrayList;

/**
 * This class represents a game of poker
 */
public class Game {
    private ArrayList<Player> players; // The players in a game
    private Deck deck = new Deck(); // The deck being used
    private Card[] flop = new Card[5]; // The flop of cards
    private int dealerIndex; // The index of the dealer
    private int littleBlindIndex; // The index of the littleBlind
    private int bigBlindIndex; // The index of the big blind
    private double pot = 0; // The amount of money in the pot
    private int numberOfTurns = 0; // What turn the game is on

    /**
     * Constructor for the Game class
     * @param dealerIndex The index of the dealer
     * @param numberOfPlayers The number of players in a game
     * @param players the ArrayList representing the players in a game
     */
    public Game(int dealerIndex, int numberOfPlayers, ArrayList<Player> players) {
        // Must have 3 players to play
        if (numberOfPlayers<3){
            throw new IllegalArgumentException("Must have 3 players to begin!");
        }
        if (dealerIndex<0 || dealerIndex>=numberOfPlayers){
            throw new IllegalArgumentException("Dealer must be within the number of players");
        }
        this.players = players;
        this.dealerIndex = dealerIndex;
        if (dealerIndex == players.size()-1){
            littleBlindIndex = 0;
            bigBlindIndex = 1;
        }
        if (dealerIndex == players.size()-2){
            littleBlindIndex = players.size()-1;
            bigBlindIndex = 0;
        }
        else{
            littleBlindIndex = dealerIndex+1;
            bigBlindIndex = dealerIndex+2;
        }
    }

    /**
     * Deal out two cards to each player starting at the little blind index
     */
    public void dealOut() {
        for (int j = 0; j < 2; j++) {
            for (int i = littleBlindIndex; i < players.size(); i++) {
                players.get(i).addToHand(deck.removeCard());
            }
            for (int i = 0; i < littleBlindIndex; i++) {
                players.get(i).addToHand(deck.removeCard());
            }
        }
    }

    /**
     * Deal out to each player but give one player a certain hand. Used for Odds
     * @param hand The hand the user wants a player to have
     */
    public void dealOutCertainHand(Card[] hand){
        if (hand.length!=2){
            throw new IllegalArgumentException("Hand must have two cards");
        }
        // Remove the cards from the deck, so we don't have duplicate
        deck.removeCard(hand[0]);
        deck.removeCard(hand[1]);

        // Deal out to all players but the first one
        for (int j = 0; j < 2; j++) {
            for (int i = 1; i < players.size(); i++) {
                players.get(i).addToHand(deck.removeCard());
            }
        }
        // Give the first player the hand we want them to have
        players.get(0).addToHand(hand[0]);
        players.get(0).addToHand(hand[1]);
    }

    /**
     * Flops the first three cards after burning one card
     */
    public void firstFlop(){
        deck.removeCard(); // Burn card
        for (int i=0; i<3; i++){
            this.flop[i] = deck.removeCard(); // Add three cards to the flop
        }
    }

    /**
     * Flops the fourth card after burning a card
     */
    public void secondFlop(){
        deck.removeCard(); // Burn card
        this.flop[3] = deck.removeCard(); // Add one card to the flop
    }

    /**
     * Flops the final card after burning a card
     */
    public void finalFlop(){
        deck.removeCard(); // Burn card
        this.flop[4] = deck.removeCard(); // Add one card to the flop
    }

    /**
     * Getter for the players ArrayList
     * @return the players ArrayList
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for the deck
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Getter for the flop
     * @return the flop array
     */
    public Card[] getFlop(){
        return this.flop;
    }


    /**
     * Returns a string representation of all hands in play with the players cards and odds of winning
     * @return a string representation of all hands in play with the players cards and odds of winning
     */
    public String getHands(){
        String output = "";
        // Cycle through each player and return their cards and odds of winning
        for (int i = 0; i < players.size(); i++){
            String playerOutput = "";
            // Add cards to the player output
            playerOutput+=players.get(i).getName() + " has: \n" + players.get(i).getCard1()+"\n" + players.get(i).getCard2() +"\n";
            Card[] hand = {players.get(i).getCard1(), players.get(i).getCard2()};
            Odds playerOdds = new Odds(10000, hand, players.size());
            double odds = playerOdds.getOdds();
            // Calculate and add the odds of winning the player output
            playerOutput+= players.get(i).getName() + " has a " + odds + "% chance of winning \n";
            output += playerOutput;
        }
        return output;
    }

    /**
     * Returns a string of each card in the flop and "Unknown Card" if the card hasn't been flopped yet
     * @return a string of each card in the flop
     */
    public String getFlopString(){
        String output = "";
        for (int i=0; i<5; i++){
            if (flop[i]==null){
                output+="Unknown card\n"; // "Unknown card" if no flopped card
            }
            else{
                output += flop[i].toString() + "\n";
            }
        }
        return output;
    }

    /**
     * Returns each player's best hand of 5 cards out of the flopped cards and their hand
     * @return a string showing each player's best hand of 5 cards
     */
    public String getAllHighHands(){
        String output = "";
        for (int i = 0; i < players.size(); i++){
            output += players.get(i).getBestHand() + "\n";
        }
        return output;
    }

    /**
     * Returns the player who has the best hand
     * @return the player with the best hand
     */
    public Player getWinningPlayer(){
        // If only one player, that player has won and all others have folded
        if (players.size()==1) {
            return players.get(0);
        }
        // All cards must be flopped
        if (flop[4]==null){
            throw new IllegalArgumentException("All cards must be shown");
        }
        Player winningPlayer = players.get(0);
        // For each player in the ArrayList, compare their best hand with the current winning player
        for (Player current: players){
            int winningPlayerBestHand = winningPlayer.findBestHand(flop);
            int currentPlayerBestHand = current.findBestHand(flop);
            // If the current player has a better hand then the winning player, they are now the winning player
            if (currentPlayerBestHand>winningPlayerBestHand){
                winningPlayer = current;
            }
        }
        return winningPlayer;
    }
}
