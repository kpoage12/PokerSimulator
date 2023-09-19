import java.util.HashMap;

/**
 * This class represents a poker player
 */
public class Player {
    private Card[] hand = new Card[2]; // The hand a player is dealt
    private String name; // The name of the player
    private final int BUY_IN = 20; // The initial buy in is 20 dollars
    private double money; // The money a player has
    private HashMap<Chip, Integer> chips = new HashMap<>(); // The chips a player has in a hashmap
    private String bestHand; // A string representing the best hand a player has
    private Chip[] chipList = {Chip.BLACK, Chip.BLUE, Chip.RED, Chip.WHITE}; // A list with all the chip types

    /**
     * Constructor for the Player class
     * @param name the name a player will have
     */
    public Player(String name) {
        this.money = BUY_IN; // Player starts with 20 dollars
        chips.put(Chip.BLACK, 2); // Player starts with 2 black chips (4 bucks)
        chips.put(Chip.BLUE, 6); // Player starts with 6 blue chips (6 bucks)
        chips.put(Chip.RED, 12); // Player starts with 12 red chips (6 bucks)
        chips.put(Chip.WHITE, 16); // Player starts with 16 white chips (4 bucks)
        this.name = name;
    }

    /**
     * Secondary constructor for the Player class without a name parameter
     */
    public Player() {
        this.money = BUY_IN;
        chips.put(Chip.BLACK, 2);
        chips.put(Chip.BLUE, 6);
        chips.put(Chip.RED, 12);
        chips.put(Chip.WHITE, 18);
        this.name = "No-Name"; // Initialize name to "No-Name"
    }

    /**
     * Adds a card to the players hand
     * @param card card to add to hand
     */
    public void addToHand(Card card) {
        try {
            if (hand[0] == null) {
                hand[0] = card;
            } else {
                hand[1] = card;
            }
        } // If array is full, the player already has two cards
        catch (ArrayIndexOutOfBoundsException e) {
            e.getMessage();
            System.out.println("Player already has two cards");
        }
    }

    /**
     * Gets the money a player currently has
     * @return
     */
    public double getMoney() {
        double output = 0;
        // Iterates through the different chips and adds the value of the chip multiplied by the number of chips
        for (int i = 0; i < chipList.length; i++) {
            output += (chips.get(chipList[i])) * (chipList[i].getAmount());
        }
        return output;
    }

    /**
     * Folds the hand of a player
     */
    public void fold() {
        hand[0] = null;
        hand[1] = null;
    }

    /**
     * Bets a certain number of chips from a players stack
     * @param bet a hashmap representing the number of chips bet
     * @return the amount bet by the player
     */
    public double betAmount(HashMap<Chip, Integer> bet) {
        double amount = 0;
        // Iterate through chip list and see if the bet contains a type of chip
        for (int i = 0; i < chipList.length; i++) {
            /* If the bet does contain a type of chip, remove the number of chips from the player stack and add the
            bet to the amount
             */
            if (bet.containsKey(chipList[i])) {
                int numberOfChipsBet = bet.get(chipList[i]);
                int numberOfChipsHad = this.chips.get(chipList[i]);
                if (numberOfChipsHad < numberOfChipsBet) {
                    throw new IllegalArgumentException("Not enough money to  bet");
                }
                amount += numberOfChipsBet * (chipList[i].getAmount());
                int newNumberOfChips = numberOfChipsHad - numberOfChipsBet;
                chips.remove(chipList[i]);
                chips.put(chipList[i], newNumberOfChips);
            }
        }
        this.money = getMoney(); // initialize money to update the value
        return amount;
    }

    /**
     * Getter for the first card of a player's hand (hand[0])
     * @return the first card of a player's hand (hand[0])
     */
    public Card getCard1() {
        if (hand[0] != null) {
            return hand[0];
        }
        throw new IllegalArgumentException("Player has empty hand");
    }

    /**
     * Getter for the second card of a player's hand (hand[1])
     * @return the second card of a player's hand (hand[1])
     */
    public Card getCard2() {
        if (hand[1] != null) {
            return hand[1];
        }
        throw new IllegalArgumentException("Player does not have a second card");
    }

    /**
     * Finds the best hand of 5 cards between the flop and the player's hand (7 cards)
     * @param flop the five cards that have been flopped
     * @return an int representing the value of a hand
     */
    public int findBestHand(Card[] flop) {

        int numberOfClubs = 0; // Number of clubs between the seven cards
        int numberOfSpades = 0; // Number of spades between the seven cards
        int numberOfHearts = 0; // Number of hearts between the seven cards
        int numberOfDiamonds = 0; // Number of diamonds between the seven cards

        /*
        FOR EACH LIST OF 13, THE 0 INDEX REPRESENTS A VALUE OF TWO AND THE 12 INDEX REPRESENTS AN ACE. ALL BETWEEN
        REPRESENT THE DIFFERENT VALUES, IN ORDER
         */
        int[] clubCards = new int[13]; // List of each card that is a club between the 7 cards (1 means they have a card)
        int[] spadeCards = new int[13]; // List of each card that is a spade between the 7 cards (1 means they have a card)
        int[] diamondCards = new int[13]; // List of each card that is a diamond between the 7 cards (1 means they have a card)
        int[] heartCards = new int[13]; // List of each card that is a heart between the 7 cards (1 means they have a card)

        int[] numberOfCardsPerValue = new int[13]; // List representing the number of cards per value a player has

        // The hand must have two cards
        if (hand[0] == null || hand[1] == null) {
            throw new IllegalArgumentException("The player has folded");
        }
        // All cards must be flopped
        if (flop[4] == null) {
            throw new IllegalArgumentException("All cards have not been shown");
        }

        Card[] sevenCards = new Card[7]; // Create a list of the seven cards we are looking through

        for (int i = 0; i < 5; i++) {
            sevenCards[i] = flop[i]; // Add from the flop
        }
        for (int i = 0; i < 2; i++) {
            sevenCards[i + 5] = hand[i]; // Add from the player's hand
        }

        // Iterate through each card and update the values accordingly
        for (Card card : sevenCards) {
            /*
            If the card is a certain suit, update the number of cards of that suit and update the element at the index
            of the suits card's array representing the value of the given card to one, showing that that card exists
             */
            if (card.getSuit().equals(Suit.CLUBS)) {
                numberOfClubs++;
                findBestHandHelper(clubCards, card);
            } else if (card.getSuit().equals(Suit.SPADES)) {
                numberOfSpades++;
                findBestHandHelper(spadeCards, card);
            } else if (card.getSuit().equals(Suit.DIAMONDS)) {
                numberOfDiamonds++;
                findBestHandHelper(diamondCards, card);
            }
            else {
                numberOfHearts++;
                findBestHandHelper(heartCards, card);
            }
            /*
            If a card is a certain value, update the numberOfCardsPerValue array index that represents the value by 1
             */
            if (card.getValue().equals(Value.TWO)){
                numberOfCardsPerValue[0]++;
            }
            else if (card.getValue().equals(Value.THREE)){
                numberOfCardsPerValue[1]++;
            }
            else if (card.getValue().equals(Value.FOUR)){
                numberOfCardsPerValue[2]++;
            }
            else if (card.getValue().equals(Value.FIVE)){
                numberOfCardsPerValue[3]++;
            }
            else if (card.getValue().equals(Value.SIX)){
                numberOfCardsPerValue[4]++;
            }
            else if (card.getValue().equals(Value.SEVEN)){
                numberOfCardsPerValue[5]++;
            }
            else if (card.getValue().equals(Value.EIGHT)){
                numberOfCardsPerValue[6]++;
            }
            else if (card.getValue().equals(Value.NINE)){
                numberOfCardsPerValue[7]++;
            }
            else if (card.getValue().equals(Value.TEN)){
                numberOfCardsPerValue[8]++;
            }
            else if (card.getValue().equals(Value.JACK)){
                numberOfCardsPerValue[9]++;
            }
            else if (card.getValue().equals(Value.QUEEN)){
                numberOfCardsPerValue[10]++;
            }
            else if (card.getValue().equals(Value.KING)){
                numberOfCardsPerValue[11]++;
            }
            else{
                numberOfCardsPerValue[12]++;
            }
        }
        /*
         Create a 2d array representing all the cards of the deck. If there is a card between the flop and player hand,
         the value at the index will be 1
         */
        int[][] allCards = {clubCards, spadeCards, diamondCards, heartCards};

        /*
        Next look through the 2d array and find the best hand in order of importance
         */
        /*
        Look for royal flush, a player has a royal flush if they have a ten, jack, queen, king, and ace, all the same
        suit
         */
        if ((allCards[0][8] == 1 && allCards[0][9] == 1 && allCards[0][10] == 1 && allCards[0][11] == 1 && allCards[0][12] == 1) ||
                (allCards[1][8] == 1 && allCards[1][9] == 1 && allCards[1][10] == 1 && allCards[1][11] == 1 && allCards[1][12] == 1) ||
                (allCards[2][8] == 1 && allCards[2][9] == 1 && allCards[2][10] == 1 && allCards[2][11] == 1 && allCards[2][12] == 1) ||
                (allCards[3][8] == 1 && allCards[3][9] == 1 && allCards[3][10] == 1 && allCards[3][11] == 1 && allCards[3][12] == 1)) {
            this.setBestHand(this.name+" has a Royal Flush!!!"); // Update the player's best hand
            return 1000;
        }

        /*
         Look for straight flush. A player has a straight flush if they have 5 cards in a row, all the same suit
         */
        if (straightFlushHelper(allCards) != -1) {
            this.setBestHand(this.name+" has a Straight Flush!!!"); // Update the player's best hand
            return straightFlushHelper(allCards);
        }

        /*
         Look for Four of a Kind. A player has Four of a Kind of they have four cards of the same value
         */
        for (int i = 0; i < numberOfCardsPerValue.length; i++){
            if (numberOfCardsPerValue[i] == 4){
                this.setBestHand(this.name+" has Four of a Kind"); // Update the player's best hand
                return (800-(12-i));
            }
        }

        /*
         Look for a full house. A player has a full house if they have 3 cards of one value and 2 cards of another value
         */
        for (int i=0; i < numberOfCardsPerValue.length; i++){
            for (int j=0; j < numberOfCardsPerValue.length; j++){
                if (numberOfCardsPerValue[i] == 3 && numberOfCardsPerValue[j] == 2){
                    this.setBestHand(this.name+" has a Full House"); // Update the player's best hand
                    return (700-(12-i));
                }
            }
        }

        /*
         Look for a flush. A player has a flush if they have 5 cards of the same suit
         */
        if (numberOfClubs==5){
            this.setBestHand(this.name+" has a Flush"); // Update the player's best hand
            return flushHelper(clubCards);
        }
        if (numberOfSpades==5){
            this.setBestHand(this.name+" has a Flush"); // Update the player's best hand
            return flushHelper(spadeCards);
        }
        if (numberOfDiamonds==5){
            this.setBestHand(this.name+" has a Flush"); // Update the player's best hand
            return flushHelper(diamondCards);
        }
        if (numberOfHearts==5){
            this.setBestHand(this.name+" has a Flush"); // Update the player's best hand
            return flushHelper(heartCards);
        }

        /*
         look for straight. A player has a straight if they have 5 cards in a row
         */
        if (straightHelper(allCards) != -1) {
            this.setBestHand(this.name+" has a Straight"); // Update the player's best hand
            return straightHelper(allCards);
        }

        /*
        Look for three of a kind. A player has three of a kind of they have 3 cards of the same value
         */
        for (int i = 0; i < numberOfCardsPerValue.length; i++){
            if (numberOfCardsPerValue[i] == 3){
                this.setBestHand(this.name+" has Three of a Kind"); // Update the player's best hand
                return (400-(12-i));
            }
        }

        /*
         Look for two pair. A player has two pair if they have a pair of one value and a pair of another value
         */
        for (int i=0; i < numberOfCardsPerValue.length; i++){
            for (int j=0; j < numberOfCardsPerValue.length; j++){
                // The indexes cannot be the same or else a pair will register as two pair
                if (i != j) {
                    if (numberOfCardsPerValue[i] == 2 && numberOfCardsPerValue[j] == 2) {
                        this.setBestHand(this.name+" has Two Pair"); // Update the player's best hand
                        return (300 - (12 - j));
                    }
                }
            }
        }

        /*
        Look for a pair. A player has a pair if they have two cards with the same value
         */
        for (int i = 0; i < numberOfCardsPerValue.length; i++){
            if (numberOfCardsPerValue[i] == 2){
                this.setBestHand(this.name+" has a Pair"); // Update the player's best hand
                return (200-(12-i));
            }
        }

        /*
         Look for high card. A player has high card if they don't have any of the given hands. Their highest card is
         their high card
         */
        this.setBestHand(this.name+" has high card"); // Update the player's best hand
        return highestCard(numberOfCardsPerValue);
    }

    /**
     * Returns the highest index of an array with a 1 as its value
     * @param numberOfCardsPerValue the array to iterate over
     * @return the highest index of an array with a 1 as its value
     */
    private int highestCard(int[] numberOfCardsPerValue) {
        int highest = 0;
        for (int i=0; i<numberOfCardsPerValue.length; i++){
            // If the int at the index is 1, the player has that card
            if (numberOfCardsPerValue[i]==1 && i>highest){
                highest = i;
            }
        }
        return highest;
    }

    /**
     * Checks if a two-dimensional array representing all possible cards of a deck has a straight
     * @param allCards the two-dimensional array representing all possible cards
     * @return an int representing the highest straight a player has, -1 if the player doesn't have a straight
     */
    private int straightHelper(int[][] allCards) {
        /**
         * Checks if a player has a 10, jack, queen, king, and ace of any suit
         */
        if ((allCards[0][8] == 1 || allCards[1][8] == 1 || allCards[2][8] == 1 || allCards[3][8]==1) &&
                (allCards[0][9] == 1 || allCards[1][9] == 1 || allCards[2][9] == 1 || allCards[3][9] ==1) &&
                (allCards[0][10] == 1 || allCards[1][10] == 1 || allCards[2][10] == 1 || allCards[3][10]==1) &&
                (allCards[0][11] == 1 || allCards[1][11] == 1 || allCards[2][11] == 1 || allCards[3][11]==1) &&
                (allCards[0][12] == 1 || allCards[1][12] == 1 || allCards[2][12] == 1 || allCards[3][12]==1)){
            return 500;
        }
        /**
         * Checks if a player has a 9, 10, jack, queen, and king any suit
         */
        else if ((allCards[0][7] == 1 || allCards[1][7] == 1 || allCards[2][7] == 1 || allCards[3][7]==1) &&
                (allCards[0][8] == 1 || allCards[1][8] == 1 || allCards[2][8] == 1 || allCards[3][8] ==1) &&
                (allCards[0][9] == 1 || allCards[1][9] == 1 || allCards[2][9] == 1 || allCards[3][9]==1) &&
                (allCards[0][10] == 1 || allCards[1][10] == 1 || allCards[2][10] == 1 || allCards[3][10]==1) &&
                (allCards[0][11] == 1 || allCards[1][11] == 1 || allCards[2][11] == 1 || allCards[3][11]==1)){
            return 499;
        }
        /**
         * Checks if a player has an 8, 9, 10, jack, and queen of  any suit
         */
        else if ((allCards[0][7] == 1 || allCards[1][7] == 1 || allCards[2][7] == 1 || allCards[3][7]==1) &&
                (allCards[0][8] == 1 || allCards[1][8] == 1 || allCards[2][8] == 1 || allCards[3][8] ==1) &&
                (allCards[0][9] == 1 || allCards[1][9] == 1 || allCards[2][9] == 1 || allCards[3][9]==1) &&
                (allCards[0][10] == 1 || allCards[1][10] == 1 || allCards[2][10] == 1 || allCards[3][10]==1) &&
                (allCards[0][6] == 1 || allCards[1][6] == 1 || allCards[2][6] == 1 || allCards[3][6]==1)){
            return 498;
        }

        /**
         * Checks if a player has a 7, 8, 9, 10, and jack of  any suit
         */
        else if ((allCards[0][7] == 1 || allCards[1][7] == 1 || allCards[2][7] == 1 || allCards[3][7]==1) &&
                (allCards[0][8] == 1 || allCards[1][8] == 1 || allCards[2][8] == 1 || allCards[3][8] ==1) &&
                (allCards[0][9] == 1 || allCards[1][9] == 1 || allCards[2][9] == 1 || allCards[3][9]==1) &&
                (allCards[0][5] == 1 || allCards[1][5] == 1 || allCards[2][5] == 1 || allCards[3][5]==1) &&
                (allCards[0][6] == 1 || allCards[1][6] == 1 || allCards[2][6] == 1 || allCards[3][6]==1)){
            return 497;
        }

        /**
         * Checks if a player has a 6, 7, 8, 9, and 10 of  any suit
         */
        else if ((allCards[0][7] == 1 || allCards[1][7] == 1 || allCards[2][7] == 1 || allCards[3][7]==1) &&
                (allCards[0][8] == 1 || allCards[1][8] == 1 || allCards[2][8] == 1 || allCards[3][8] ==1) &&
                (allCards[0][4] == 1 || allCards[1][4] == 1 || allCards[2][4] == 1 || allCards[3][4]==1) &&
                (allCards[0][5] == 1 || allCards[1][5] == 1 || allCards[2][5] == 1 || allCards[3][5]==1) &&
                (allCards[0][6] == 1 || allCards[1][6] == 1 || allCards[2][6] == 1 || allCards[3][6]==1)){
            return 496;
        }

        /**
         * Checks if a player has a 5, 6, 7, 8, and 9 of any suit
         */
        else if ((allCards[0][7] == 1 || allCards[1][7] == 1 || allCards[2][7] == 1 || allCards[3][7]==1) &&
                (allCards[0][3] == 1 || allCards[1][3] == 1 || allCards[2][3] == 1 || allCards[3][3] ==1) &&
                (allCards[0][4] == 1 || allCards[1][4] == 1 || allCards[2][4] == 1 || allCards[3][4]==1) &&
                (allCards[0][5] == 1 || allCards[1][5] == 1 || allCards[2][5] == 1 || allCards[3][5]==1) &&
                (allCards[0][6] == 1 || allCards[1][6] == 1 || allCards[2][6] == 1 || allCards[3][6]==1)){
            return 495;
        }

        /**
         * Checks if a player has a 4, 5, 6, 7, and 8 of any suit
         */
        else if ((allCards[0][2] == 1 || allCards[1][2] == 1 || allCards[2][2] == 1 || allCards[3][2]==1) &&
                (allCards[0][3] == 1 || allCards[1][3] == 1 || allCards[2][3] == 1 || allCards[3][3] ==1) &&
                (allCards[0][4] == 1 || allCards[1][4] == 1 || allCards[2][4] == 1 || allCards[3][4]==1) &&
                (allCards[0][5] == 1 || allCards[1][5] == 1 || allCards[2][5] == 1 || allCards[3][5]==1) &&
                (allCards[0][6] == 1 || allCards[1][6] == 1 || allCards[2][6] == 1 || allCards[3][6]==1)){
            return 494;
        }

        /**
         * Checks if a player has a 3, 4, 5, 6, and 7 of any suit
         */
        else if ((allCards[0][2] == 1 || allCards[1][2] == 1 || allCards[2][2] == 1 || allCards[3][2]==1) &&
                (allCards[0][3] == 1 || allCards[1][3] == 1 || allCards[2][3] == 1 || allCards[3][3] ==1) &&
                (allCards[0][4] == 1 || allCards[1][4] == 1 || allCards[2][4] == 1 || allCards[3][4]==1) &&
                (allCards[0][5] == 1 || allCards[1][5] == 1 || allCards[2][5] == 1 || allCards[3][5]==1) &&
                (allCards[0][1] == 1 || allCards[1][1] == 1 || allCards[2][1] == 1 || allCards[3][1]==1)){
            return 493;
        }
        /**
         * Checks if a player has a 2, 3, 4, 5, and 6 of any suit
         */
        else if ((allCards[0][2] == 1 || allCards[1][2] == 1 || allCards[2][2] == 1 || allCards[3][2]==1) &&
                (allCards[0][3] == 1 || allCards[1][3] == 1 || allCards[2][3] == 1 || allCards[3][3] ==1) &&
                (allCards[0][4] == 1 || allCards[1][4] == 1 || allCards[2][4] == 1 || allCards[3][4]==1) &&
                (allCards[0][0] == 1 || allCards[1][0] == 1 || allCards[2][0] == 1 || allCards[3][0]==1) &&
                (allCards[0][1] == 1 || allCards[1][1] == 1 || allCards[2][1] == 1 || allCards[3][1]==1)){
            return 492;
        }
        // If player has no straights, return -1
        return -1;
    }

    /**
     * Returns an int representing the value of a flush with higher cards  being a better hand
     * @param cards the list of cards to look through
     * @return an int representing the value of a flush with higher cards  being a better hand
     */
    private int flushHelper(int[] cards) {
        int highestIndex = 0;
        for (int i=0; i<cards.length; i++){
            if (cards[i]==1 && i > highestIndex){
                highestIndex = i;
            }
        }
        return 600-(12-highestIndex);
    }

    /**
     * Determines if a player has a straight flush
     * @param allCards the cards to check
     * @return an int representing the hand, -1 if no  straight flush
     */
    private int straightFlushHelper(int[][] allCards) {
        /**
         * Checks if a player has a 9, 10, Jack, Queen, and King all the same suit
         */
        if ((allCards[0][7] == 1 && allCards[0][8] == 1 && allCards[0][9] == 1 && allCards[0][10] == 1 && allCards[0][11] == 1) ||
                (allCards[1][7] == 1 && allCards[1][8] == 1 && allCards[1][9] == 1 && allCards[1][10] == 1 && allCards[1][11] == 1) ||
                (allCards[2][7] == 1 && allCards[2][8] == 1 && allCards[2][9] == 1 && allCards[2][10] == 1 && allCards[2][11] == 1) ||
                (allCards[3][7] == 1 && allCards[3][8] == 1 && allCards[3][9] == 1 && allCards[3][10] == 1 && allCards[3][11] == 1)) {
            return 900;
        }

        /**
         * Checks if a player has an 8, 9, 10, Jack, and Queen all the same suit
         */
        else if ((allCards[0][6] == 1 && allCards[0][7] == 1 && allCards[0][8] == 1 && allCards[0][9] == 1 && allCards[0][10] == 1) ||
                (allCards[1][6] == 1 && allCards[1][7] == 1 && allCards[1][8] == 1 && allCards[1][9] == 1 && allCards[1][10] == 1) ||
                (allCards[2][6] == 1 && allCards[2][7] == 1 && allCards[2][8] == 1 && allCards[2][9] == 1 && allCards[2][10] == 1) ||
                (allCards[3][6] == 1 && allCards[3][7] == 1 && allCards[3][8] == 1 && allCards[3][9] == 1 && allCards[3][10] == 1)) {
            return 889;
        }

        /**
         * Checks if a player has a 7, 8, 9, 10, and Jack, all the same suit
         */
        else if ((allCards[0][5] == 1 && allCards[0][6] == 1 && allCards[0][7] == 1 && allCards[0][8] == 1 && allCards[0][9] == 1) ||
                (allCards[1][5] == 1 && allCards[1][6] == 1 && allCards[1][7] == 1 && allCards[1][8] == 1 && allCards[1][9] == 1) ||
                (allCards[2][5] == 1 && allCards[2][6] == 1 && allCards[2][7] == 1 && allCards[2][8] == 1 && allCards[2][9] == 1) ||
                (allCards[3][5] == 1 && allCards[3][6] == 1 && allCards[3][7] == 1 && allCards[3][8] == 1 && allCards[3][9] == 1)) {
            return 888;
        }

        /**
         * Checks if a player has a 6, 7, 8, 9, and 10 all the same suit
         */
        else if ((allCards[0][4] == 1 && allCards[0][5] == 1 && allCards[0][6] == 1 && allCards[0][7] == 1 && allCards[0][8] == 1) ||
                (allCards[1][4] == 1 && allCards[1][5] == 1 && allCards[1][6] == 1 && allCards[1][7] == 1 && allCards[1][8] == 1) ||
                (allCards[2][4] == 1 && allCards[2][5] == 1 && allCards[2][6] == 1 && allCards[2][7] == 1 && allCards[2][8] == 1) ||
                (allCards[3][4] == 1 && allCards[3][5] == 1 && allCards[3][6] == 1 && allCards[3][7] == 1 && allCards[3][8] == 1)) {
            return 887;
        }

        /**
         * Checks if a player has a 5, 6, 7, 8, and 9 all the same suit
         */
        else if ((allCards[0][3] == 1 && allCards[0][4] == 1 && allCards[0][5] == 1 && allCards[0][6] == 1 && allCards[0][7] == 1) ||
                (allCards[1][3] == 1 && allCards[1][4] == 1 && allCards[1][5] == 1 && allCards[1][6] == 1 && allCards[1][7] == 1) ||
                (allCards[2][3] == 1 && allCards[2][4] == 1 && allCards[2][5] == 1 && allCards[2][6] == 1 && allCards[2][7] == 1) ||
                (allCards[3][3] == 1 && allCards[3][4] == 1 && allCards[3][5] == 1 && allCards[3][6] == 1 && allCards[3][7] == 1)) {
            return 886;
        }

        /**
         * Checks if a player has a 4, 5, 6, 7, and 8 all the same suit
         */
        else if ((allCards[0][2] == 1 && allCards[0][3] == 1 && allCards[0][4] == 1 && allCards[0][5] == 1 && allCards[0][6] == 1) ||
                (allCards[1][2] == 1 && allCards[1][3] == 1 && allCards[1][4] == 1 && allCards[1][5] == 1 && allCards[1][6] == 1) ||
                (allCards[2][2] == 1 && allCards[2][3] == 1 && allCards[2][4] == 1 && allCards[2][5] == 1 && allCards[2][6] == 1) ||
                (allCards[3][2] == 1 && allCards[3][3] == 1 && allCards[3][4] == 1 && allCards[3][5] == 1 && allCards[3][6] == 1)) {
            return 885;
        }

        /**
         * Checks if a player has a 3, 4, 5, 6, and 7 all the same suit
         */
        else if ((allCards[0][1] == 1 && allCards[0][2] == 1 && allCards[0][3] == 1 && allCards[0][4] == 1 && allCards[0][5] == 1) ||
                (allCards[1][1] == 1 && allCards[1][2] == 1 && allCards[1][3] == 1 && allCards[1][4] == 1 && allCards[1][5] == 1) ||
                (allCards[2][1] == 1 && allCards[2][2] == 1 && allCards[2][3] == 1 && allCards[2][4] == 1 && allCards[2][5] == 1) ||
                (allCards[3][1] == 1 && allCards[3][2] == 1 && allCards[3][3] == 1 && allCards[3][4] == 1 && allCards[3][5] == 1)) {
            return 884;
        }

        /**
         * Checks if a player has a 2, 3, 4, 5, and 6 all the same suit
         */
        else if ((allCards[0][0] == 1 && allCards[0][1] == 1 && allCards[0][2] == 1 && allCards[0][3] == 1 && allCards[0][4] == 1) ||
                (allCards[1][0] == 1 && allCards[1][1] == 1 && allCards[1][2] == 1 && allCards[1][3] == 1 && allCards[1][4] == 1) ||
                (allCards[2][0] == 1 && allCards[2][1] == 1 && allCards[2][2] == 1 && allCards[2][3] == 1 && allCards[2][4] == 1) ||
                (allCards[3][0] == 1 && allCards[3][1] == 1 && allCards[3][2] == 1 && allCards[3][3] == 1 && allCards[3][4] == 1)) {
            return 883;
        }
        // If no straight flush, return -1
        return -1;
    }


    /**
     * This method initializes an element of an array to 1 if the card has a certain value
     * @param cards the list to be updated
     * @param card the card to be checked
     */
    private void findBestHandHelper ( int[] cards, Card card){
        if (card.getValue() == Value.TWO) {
            cards[0] = 1;
        }
        if (card.getValue() == Value.THREE) {
            cards[1] = 1;
        }
        if (card.getValue() == Value.FOUR) {
            cards[2] = 1;
        }
        if (card.getValue() == Value.FIVE) {
            cards[3] = 1;
        }
        if (card.getValue() == Value.SIX) {
            cards[4] = 1;
        }
        if (card.getValue() == Value.SEVEN) {
            cards[5] = 1;
        }
        if (card.getValue() == Value.EIGHT) {
            cards[6] = 1;
        }
        if (card.getValue() == Value.NINE) {
            cards[7] = 1;
        }
        if (card.getValue() == Value.TEN) {
            cards[8] = 1;
        }
        if (card.getValue() == Value.JACK) {
            cards[9] = 1;
        }
        if (card.getValue() == Value.QUEEN) {
            cards[10] = 1;
        }
        if (card.getValue() == Value.KING) {
            cards[11] = 1;
        }
        if (card.getValue() == Value.ACE) {
            cards[12] = 1;
        }
    }

    /**
     * Getter for a player's name
     * @return a player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the players best hand
     * @param bestHand the string to set the hand to
     */
    public void setBestHand(String bestHand){
        this.bestHand = bestHand;
    }

    /**
     * Getter for the player's best hand
     * @return a player's best hand
     */
    public String getBestHand(){
        return bestHand;
    }

    /**
     * Setter for the players first card
     * @param card card to set hand[0] to
     */
    private void setCard1(Card card){
        this.hand[0] = card;
    }

    /**
     * Setter for the players second card
     * @param card card to set hand[1] to
     */
    private void setCard2(Card card){
        this.hand[1] = card;
    }
}

