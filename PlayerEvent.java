// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2

/**
 * PlayerEvent is the class that represents a specific type of Event that
 * particularly alters the properties of the player and their stats.
 */
public abstract class PlayerEvent extends Event {

    /**
     * This simple constructor for PlayerEvent sets the values for kusaCoinsGiven
     * and textDisplayed.
     */
    public PlayerEvent(int kCoins, String text) {
        super(kCoins, text);
    }

    /**
     * This abstract method is where instances of PlayerEvent will modify the player
     * proper.
     */
    public abstract void affectPlayer(Player player);
}