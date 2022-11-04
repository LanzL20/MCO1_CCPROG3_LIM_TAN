
public class Plant {//note might need to revise cuz it might be better to take from a list of predefined plants and just assign the pre assigned plants to the tiles

	public final int PLANT_TURNIP=1;
	public final int PLANT_CARROT=2;
	public final int PLANT_POTATO=3;
	public final int PLANT_ROSE=4;
	public final int PLANT_TULIP=5;
	public final int PLANT_SUNFLOWER=6;
	public final int PLANT_MANGO=7;
	public final int PLANT_APPLE=8;
	
	public final int CONST_MULTIPLIER_TURNIP=1;
	public final int CONST_MULTIPLIER_CARROT=1;
	public final int CONST_MULTIPLIER_POTATO=1;
	public final int CONST_MULTIPLIER_ROSE=1;
	public final int CONST_MULTIPLIER_TULIP=1;
	public final int CONST_MULTIPLIER_SUNFLOWER=1;
	public final int CONST_MULTIPLIER_MANGO=1;
	public final int CONST_MULTIPLIER_APPLE=1;

	private int buyPrice;
	private int sellPrice;
	private int requiredWater;
	private int requiredFertilizer;
	private int bonusWaterCap;
	private int bonusFertilizerCap;
	private int daysRequired;
	private int expGain;
	private int conste;
	private int maxConst;
	private int constMultiplier;
	
	public Plant(int buyPrice, int requiredWater, int requiredFertilizer, int bonusWaterCap, int daysRequired, int expGain, int conste, int maxConst,
			int constMultiplier) {
		this.buyPrice=buyPrice;
		this.requiredWater=requiredWater;
		this.requiredFertilizer=requiredFertilizer;
		this.bonusWaterCap=bonusWaterCap;
		this.daysRequired=daysRequired;
		this.expGain=expGain;
		this.conste=conste;
		this.maxConst=maxConst;
		this.constMultiplier=constMultiplier;
	}
	

	
	public int getExpGain() {
		return expGain;
	}
	
	public int getBuyPrice() {
		return buyPrice;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
	
}
