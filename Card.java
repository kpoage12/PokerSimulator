/**
 * This class represents a card in a deck.
 * @author kylepoage
 */
public class Card {
    private Suit suit; // Represents the card's suit
    private Value value; // Represents the card's value

    /**
     * Constructor for the card class
     * @param suit The suit the card will be
     * @param value The value the card will have
     */
    public Card (Suit suit, Value value){
        this.suit = suit;
        this.value = value;
    }

    /**
     * Getter for the suit of the card
     * @return the card's suit
     */
    public Suit getSuit(){
        return this.suit;
    }

    /**
     * Getter for the value of the card
     * @return the card's value
     */
    public Value getValue() {
        return this.value;
    }

    /**
     * Returns a formatted string: "CARDS_VALUE of CARDS_SUIT"
     * @return the formatted string
     */
    @Override
    public String toString(){
        return (this.value + " of " + this.suit);
    }

    /**
     * Compares two cards
     * @param other Other card to be compared
     * @return true if the suit and value are equal
     */
    @Override
    public boolean equals(Object other){
        return (other instanceof Card && this.value.equals(((Card) other).getValue()) && this.suit.equals(((Card) other).getSuit()));
    }
}
