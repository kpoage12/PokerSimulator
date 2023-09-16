import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private Card[] flop = new Card[5];
    private int dealerIndex;
    private int littleBlindIndex;
    private int bigBlindIndex;
    private double pot = 0;
    private int numberOfTurns = 0;

    public Game(int numberOfPlayers, int dealerIndex) {
        if (numberOfPlayers<3){
            throw new IllegalArgumentException("Must have 3 players to begin!");
        }
        if (dealerIndex<0 || dealerIndex>=numberOfPlayers){
            throw new IllegalArgumentException("Dealer must be within the number of players");
        }
        for (int i=0; i<numberOfPlayers; i++){
            players.add(new Player());
        }
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

    public void firstFlop(){
        deck.removeCard();
        for (int i=0; i<3; i++){
            this.flop[i] = deck.removeCard();
        }
    }

    public void secondFlop(){
        deck.removeCard();
        this.flop[3] = deck.removeCard();
    }

    public void turn(Player current){
    }

    public void finalFlop(){
        deck.removeCard();
        this.flop[4] = deck.removeCard();
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getHands(){
        String output = "";
        for (int i = 0; i < players.size(); i++){
            String playerOutput = "";
            playerOutput+="Player " + i + " has: \n" + players.get(i).getCard1()+"\n" + players.get(i).getCard2() +"\n";
            output += playerOutput;
        }
        return output;
    }

    public String getFlop(){
        String output = "";
        for (int i=0; i<5; i++){
            if (flop[i]==null){
                output+="Unknown card\n";
            }
            else{
                output += flop[i].toString() + "\n";
            }
        }
        return output;
    }
}
