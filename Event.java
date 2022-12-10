// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2

/**
 * Event is the general abstract class that represents all aspects of a
 * randomized event, including the amount of kusaCoins given to the player and
 * the text to be displayed to View.
 */

public abstract class Event {
    private int kusaCoinsGiven;
    private String textDisplayed;

    // All the public constants associated with Event.
    public static final int KUSA_COINS_GOOD = 5;
    public static final int KUSA_COINS_NEUTRAL = 10;
    public static final int KUSA_COINS_BAD = 20;

    /**
     * This simple constructor for Event sets the values for kusaCoinsGiven and
     * textDisplayed.
     */
    public Event(int kCoins, String text) {
        this.kusaCoinsGiven = kCoins;
        this.textDisplayed = text;
    }

    /**
     * This method add to the kusaCoins count for the player inputted in the
     * parameters.
     * 
     * @param player the Player object to which the kusaCoins would be added to
     */
    protected void addKusaCoins(Player player) {
        player.addKusaCoins(kusaCoinsGiven);
    }

    /**
     * A method that returns the text associated with this event to be displayed on
     * the GUI within View.
     */
    public String getTextDisplayed() {
        return this.textDisplayed;
    }
}
