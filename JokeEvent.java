// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2

/**
 * JokeEvent is the class that represents a specific type of Event that
 * simply tells a joke in the textPanel.
 */
public class JokeEvent extends Event {

    /**
     * This simple constructor for JokeEvent sets the values for kusaCoinsGiven and
     * textDisplayed.
     */
    public JokeEvent(String text) {
        super(Event.KUSA_COINS_NEUTRAL, text);
    }

    /**
     * This method simply gives the appropriate amount of kusaCoins to the player.
     */
    public void giveKusa(Player player) {
        super.addKusaCoins(player);
    }
}
