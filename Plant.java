
public class Plant {// note might need to revise cuz it might be better to take from a list of
					// predefined plants and just assign the pre assigned plants to the tiles

	public final static int PLANT_TURNIP = 0;
	public final static int PLANT_CARROT = 1;
	public final static int PLANT_POTATO = 2;
	public final static int PLANT_ROSE = 3;
	public final static int PLANT_TULIP = 4;
	public final static int PLANT_SUNFLOWER = 5;
	public final static int PLANT_MANGO = 6;
	public final static int PLANT_APPLE = 7;

	private final static String[] PLANT_NAMES = { "Turnip", "Carrot", "Potato", "Rose", "Tulips", "Sunflower", "Mango",
			"Apple" };
	private final static int[] BUY_PRICE = { 500, 1000, 2000, 500, 1000, 2000, 10000, 20000 }; // 100x
	private final static int[] SELL_PRICE = { 600, 900, 300, 500, 900, 1900, 800, 500 }; // (Base Sell Prices) 100x
	private final static int[] REQUIRED_WATER = { 1, 1, 3, 1, 2, 2, 7, 7 };
	private final static int[] REQUIRED_FERTILIZER = { 0, 0, 1, 0, 0, 1, 4, 5 };
	private final static int[] BONUS_WATER_CAP = { 2, 2, 4, 2, 3, 3, 7, 7 };
	private final static int[] BONUS_FERTILIZER_CAP = { 1, 1, 2, 1, 1, 2, 4, 5 };
	private final static int[] PRODUCTS_MIN = { 1, 1, 1, 1, 1, 1, 5, 10 };
	private final static int[] PRODUCTS_MAX = { 2, 2, 10, 1, 1, 1, 15, 15 };
	private final static int[] DAYS_REQUIRED = { 2, 3, 5, 1, 2, 3, 10, 10 };
	private final static int[] EXP_GAIN = { 10, 15, 25, 5, 10, 15, 50, 50 }; // 2x
	private final static int[] CONST_MULTIPLIER = { 100, 100, 100, 100, 100, 100, 100, 100 }; // 100x

	private int plantId;
	private int conste;// only set by Gacha
	private int productsProduced;

	public Plant(int plantId, int conste) {
		this.plantId = plantId;
		this.conste = conste;
		this.productsProduced = ((int) Math.floor(Math.random() * (PRODUCTS_MAX[plantId] - PRODUCTS_MIN[plantId] + 1)))
				+ PRODUCTS_MIN[plantId];// hatdog
	}

	// TODO: NOT SURE IF OOP
	public static String getPlantNameStatic(int plantId) {
		return PLANT_NAMES[plantId];
	}

	public String getPlantName() {
		return PLANT_NAMES[plantId];
	}

	// TODO: NOT SURE IF OOP
	public static int getBuyPriceStatic(int plantId) {
		return BUY_PRICE[plantId];
	}

	public int getPlantId(){
		return plantId;
	}

	public int getBuyPrice() {
		return BUY_PRICE[plantId];
	}

	public int getRequiredWater() {
		return REQUIRED_WATER[plantId];
	}

	public int getRequiredFertilizer() {
		return REQUIRED_FERTILIZER[plantId];
	}

	public int getDaysRequired() {
		return DAYS_REQUIRED[plantId];
	}

	public int getExpGain() {
		return EXP_GAIN[plantId];
	}

	public int calculateFinalPrice(Player player, int timesWatered, int timesFertilized) {// buying*100, selling plants
																							// *100, buying tools*100,
																							// using tools*100
																							//TODO:ADDREGISTRATION

		int harvestTotal = this.productsProduced
				* ((SELL_PRICE[plantId] + conste * CONST_MULTIPLIER[plantId]) + player.getEarningBonus());
		if (timesWatered > BONUS_WATER_CAP[plantId] + player.getWaterBonusInc())
			timesWatered = BONUS_WATER_CAP[plantId] + player.getWaterBonusInc();
		if (timesFertilized > BONUS_FERTILIZER_CAP[plantId] + player.getFertilizerBonusInc())
			timesFertilized = BONUS_FERTILIZER_CAP[plantId] + player.getFertilizerBonusInc();
		int waterBonus = (int) (harvestTotal * 0.2 * (timesWatered - 1));
		int fertilizerBonus = (int) (harvestTotal * 0.5 * timesFertilized);
		int finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;

		if (isFlower())
			finalHarvestPrice = (int) (finalHarvestPrice * 1.1);
		return finalHarvestPrice;
	}

	public boolean isRoot() {
		return this.plantId == PLANT_TURNIP || this.plantId == PLANT_CARROT || this.plantId == PLANT_POTATO;
	}

	public boolean isFlower() {
		return this.plantId == PLANT_ROSE || this.plantId == PLANT_TULIP || this.plantId == PLANT_SUNFLOWER;
	}

	public boolean isTree() {
		return this.plantId == PLANT_MANGO || this.plantId == PLANT_APPLE;
	}

	public int getProductsProduced(){
		return this.productsProduced;
	}
}
