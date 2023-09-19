import java.util.ArrayList;

/**
 * This class represents a deck of 52 cards
 * @author kylepoage
 */
public class Deck {

    final private int DECK_SIZE = 52; // Decks have 52 cards

    private ArrayList<Card> cards = new ArrayList<>(); // ArrayList to hold the cards

    private Suit[] suitList = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES}; // Array of the suit types
    private Value[] valueList = {Value.TWO, Value.THREE, Value.FOUR, Value.FIVE, Value.SIX, Value.SEVEN, Value.EIGHT,
        Value.NINE, Value.TEN, Value.JACK, Value.QUEEN, Value.KING, Value.ACE}; // Array of the value types

    /**
     * Constructor for the Deck class
     */
    public Deck(){
        this.initialize(); // Add all cards to the deck
        this.shuffle(); // shuffle them
    }

    /**
     * Initializes the cards ArrayList by adding every card to the list
     */
    public void initialize(){
        // Iterate through the suitList
        for (int i = 0; i < 4; i++){
            // Iterate through the valueList
            for (int j = 0; j < 13; j++){
                Card card = new Card(suitList[i], valueList[j]);
                this.cards.add(card);
            }
        }
    }

    /**
     * Shuffles the deck randomly
     */
    public void shuffle(){
        // Create new ArrayList to add cards to
        ArrayList<Card> shuffled = new ArrayList<>();
        // Randomly remove cards from the current list until the current list is empty
        while (cards.size()>0){
            int random = (int)(Math.random()*cards.size());
            shuffled.add(cards.get(random));
            cards.remove(random);
        }
        this.cards = shuffled; // Initialize cards to the new, shuffled list
    }

    /**
     * This removes a card from the top of the deck
     * @return the card that was removed
     */
    public Card removeCard(){
        if (this.cards.size()<=0){
            throw new IllegalArgumentException("The deck is empty");
        }
        return this.cards.remove(0);
    }

    /**
     * Removes a given card from the deck
     * @param card Card to be removed
     * @return true if the card is in the deck
     */
    public Boolean removeCard(Card card){
        if (cards.contains(card)){
            cards.remove(card);
            return true;
        }
        return false;
    }

    /**
     * Returns the size of the current deck
     * @return the size of cards list
     */
    public int deckSize(){
        return cards.size();
    }

    /**
     * Getter for the cards ArrayList
     * @return the cards ArrayList
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }


    /**
     * Formatted string that returns all cards currently in the deck
     * @return String containing all cards
     */
    @Override
    public String toString(){
        String outString = "";
        for (int i = 0; i < this.cards.size(); i++){
            if (i != 51){
                outString += this.cards.get(i) + ", ";
            }
            else{
                outString += this.cards.get(i);
            }
        }
        return outString;
    }
}
