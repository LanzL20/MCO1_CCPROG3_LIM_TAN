public abstract class PlayerEvent extends Event {
    public PlayerEvent(int kusaCoinsGiven, String textDisplayed) {
        super(kusaCoinsGiven, textDisplayed);
    }

    public abstract void affectPlayer(Player player);
}