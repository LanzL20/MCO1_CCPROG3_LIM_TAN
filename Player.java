
public class Player {
	public final int REGISTRATION_BASE=0;
	public final int REGISTRATION_REGISTERED=0;
	public final int REGISTRATION_DISTINGUISHED=0;
	public final int REGISTRATION_LEGENDARY=0;
	
	private int exp;
	private int objectCoins;
	private int kusaCoins;
	private int registration;
	private int bonusWater;
	private int bonusFertilizer;
	private String name;
	
	public Player(String name){
		
	}
	
	public void increaseExp() {
		
	}
	
	public boolean upgradeRegistration() {
		
	}
	
	public void setObjectCoins(int objectCoins){
		this.objectCoins=objectCoins;
	}

	public int getObjectCoins(){
		return objectCoins;
	}
}
