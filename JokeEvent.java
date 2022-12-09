public class JokeEvent extends Event {
   
    public JokeEvent(String textDisplayed) {
        super(Event.KUSA_COINS_NEUTRAL, textDisplayed);
    }

    public void giveKusa(Player player){
        super.addKusaCoins(player);
    }
}
