import java.util.ArrayList;

public class Deck {

    final private int DECK_SIZE = 52;

    private ArrayList<Card> cards = new ArrayList<>();

    private Suit[] suitList = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};
    private Value[] valueList = {Value.TWO, Value.THREE, Value.FOUR, Value.FIVE, Value.SIX, Value.SEVEN, Value.EIGHT,
        Value.NINE, Value.TEN, Value.JACK, Value.QUEEN, Value.KING, Value.ACE};

    public Deck(){
        this.initialize();
        this.shuffle();
    }

    public void initialize(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 13; j++){
                Card card = new Card(suitList[i], valueList[j]);
                this.cards.add(card);
            }
        }
    }

    public void shuffle(){
        ArrayList<Card> shuffled = new ArrayList<>();
        while (cards.size()>0){
            int random = (int)(Math.random()*cards.size());
            shuffled.add(cards.get(random));
            cards.remove(random);
        }
        this.cards = shuffled;
    }

    public Card removeCard(){
        if (this.cards.size()<=0){
            throw new IllegalArgumentException("The deck is empty");
        }
        return this.cards.remove(0);
    }

    public int deckSize(){
        return cards.size();
    }

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
