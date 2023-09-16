import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private Card[] hand = new Card[2];
    private final int BUY_IN = 20;
    private double money;
    private HashMap<Chip, Integer> chips = new HashMap<>();
    private Chip[] chipList = {Chip.BLACK, Chip.BLUE, Chip.RED, Chip.WHITE};

    public Player(){
        this.money = BUY_IN;
        chips.put(Chip.BLACK, 2);
        chips.put(Chip.BLUE, 6);
        chips.put(Chip.RED, 12);
        chips.put(Chip.WHITE, 18);
    }
    public void addToHand(Card card){
        try {
            if (hand[0] == null) {
                hand[0] = card;
            } else {
                hand[1] = card;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.getMessage();
            System.out.println("Player already has two cards");
        }
    }

    public double getMoney(){
        double output = 0;
        for (int i=0; i < chipList.length; i++){
            output += (chips.get(chipList[i])) * (chipList[i].getAmount());
        }
        return output;
    }

    public double betAmount(HashMap<Chip, Integer> bet){
        double amount = 0;
        for (int i = 0; i < chipList.length; i++){
            if (bet.containsKey(chipList[i])){
                int numberOfChipsBet = bet.get(chipList[i]);
                int numberOfChipsHad = this.chips.get(chipList[i]);
                if (numberOfChipsHad<numberOfChipsBet){
                    throw new IllegalArgumentException("Not enough money to  bet");
                }
                amount += numberOfChipsBet * (chipList[i].getAmount());
                int newNumberOfChips = numberOfChipsHad-numberOfChipsBet;
                chips.remove(chipList[i]);
                chips.put(chipList[i], newNumberOfChips);
            }
        }
        this.money = getMoney();
        return amount;
    }


    public Card getCard1(){
        if (hand[0] != null){
            return hand[0];
        }
        throw new IllegalArgumentException("Player has empty hand");
    }

    public Card getCard2(){
        if (hand[1] != null){
            return hand[1];
        }
        throw new IllegalArgumentException("Player has empty hand");
    }


}
