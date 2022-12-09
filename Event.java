public abstract class Event {
    private int kusaCoinsGiven;
    private String textDisplayed;

    public static final int KUSA_COINS_GOOD = 5;
    public static final int KUSA_COINS_NEUTRAL = 10;
    public static final int KUSA_COINS_BAD = 20;

    public Event(int kusaCoinsGiven, String textDisplayed){
        this.kusaCoinsGiven = kusaCoinsGiven;
        this.textDisplayed = textDisplayed;
    }

    protected void addKusaCoins(Player player){
        player.addKusaCoins(kusaCoinsGiven);
    }

    public String getTextDisplayed(){
        return this.textDisplayed;
    }
}
