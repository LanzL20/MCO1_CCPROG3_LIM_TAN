// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2

/**
 * FarmEvent is the class that represents a specific type of Event that
 * particularly alters the properties of the farm and its tiles.
 */
public abstract class FarmEvent extends Event {

    /**
     * This simple constructor for FarmEvent sets the values for kusaCoinsGiven and
     * textDisplayed.
     */
    public FarmEvent(int kCoins, String text) {
        super(kCoins, text);
    }

    /**
     * This abstract method is where instances of FarmEvent will modify the farm
     * proper.
     */
    public abstract void affectFarm(Tile[][] farm, Player player);
}