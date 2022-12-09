public abstract class FarmEvent extends Event {
    public FarmEvent(int kusaCoinsGiven, String textDisplayed) {
        super(kusaCoinsGiven, textDisplayed);
    }

    public abstract void affectFarm(Tile[][] farm, Player player);
}