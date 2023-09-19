/**
 * This enum represents all the different chip types
 */
public enum Chip {
    /**
     * White chips are worth 25 cents
     */
    WHITE{
        @Override
        public double getAmount(){
            return 0.25;
        }
    },
    /**
     * Red chips are worth 50 cents
     */
    RED{
        @Override
        public double getAmount(){
            return 0.5;
        }
    },
    /**
     * Blue chips are worth a dollar
     */
    BLUE{
        @Override
        public double getAmount(){
            return 1;
        }
    },
    /**
     * Black chips are worth two dollars
     */
    BLACK{
        @Override
        public double getAmount(){
            return 2;
        }
    };
    /**
     * Returns the value of the chip type
     * @return the value of the chip type
     */
    public double getAmount() {
        return 0;
    }
}
