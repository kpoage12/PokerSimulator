public enum Chip {
    WHITE{
        @Override
        public double getAmount(){
            return 0.25;
        }
    },
    RED{
        @Override
        public double getAmount(){
            return 0.5;
        }
    },
    BLUE{
        @Override
        public double getAmount(){
            return 1;
        }
    },
    BLACK{
        @Override
        public double getAmount(){
            return 2;
        }
    };

    public double getAmount() {
        return 0;
    }
}
