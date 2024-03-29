// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2 

/**
 * This class serves as the blueprint for a plant containing the various
 * attributes that a plant usually possesses. These include its base buy and
 * sell prices, required water and fertilizer, bonus water and fertilizer caps,
 * and the range of the products produced. Most of the methods of this class are
 * getters for the various properties of the plant. The final sell price is also
 * calculated in this class through a method.
 */
public class Plant {

	// All the public IDs for all the possible plants.
	public final static int PLANT_TURNIP = 0;
	public final static int PLANT_CARROT = 1;
	public final static int PLANT_POTATO = 2;
	public final static int PLANT_ROSE = 3;
	public final static int PLANT_TULIP = 4;
	public final static int PLANT_SUNFLOWER = 5;
	public final static int PLANT_MANGO = 6;
	public final static int PLANT_APPLE = 7;
	public final static int PLANT_LARRY = 8;

	// All the private constants associated with Plant.
	private final static String[] PLANT_NAMES = { "Turnip", "Carrot", "Potato", "Rose", "Tulips", "Sunflower", "Mango",
			"Apple", "Larry" };
	private final static String[] PLANT_IMAGE = { "Plant_Turnip.png", "Plant_Carrot.png", "Plant_Potato.png",
			"Plant_Rose.png", "Plant_Tulips.png",
			"Plant_Sunflower.png", "Plant_Mango.png", "Plant_Apple.png", "Plant_Larry.png" };
	private final static String[] GACHA_IMAGE = { "Plant_TurnipGacha.png", "Plant_CarrotGacha.png",
			"Plant_PotatoGacha.png",
			"Plant_RoseGacha.png", "Plant_TulipGacha.png", "Plant_SunflowerGacha.png", "Plant_MangoGacha.png",
			"Plant_AppleGacha.png", "Plant_LarryGacha.png" };
	// 100x to avoid the truncation of floating point numbers.
	private final static int[] BUY_PRICE = { 500, 1000, 2000, 500, 1000, 2000, 10000, 20000, 30000 };
	// (Base Sell Prices) 100x to avoid the truncation of floating point numbers.
	private final static int[] SELL_PRICE = { 600, 900, 300, 500, 900, 1900, 800, 500, 20000 };
	private final static int[] REQUIRED_WATER = { 1, 1, 3, 1, 2, 2, 7, 7, 10 };
	private final static int[] REQUIRED_FERTILIZER = { 0, 0, 1, 0, 0, 1, 4, 5, 8 };
	private final static int[] BONUS_WATER_CAP = { 2, 2, 4, 2, 3, 3, 7, 7, 20 };
	private final static int[] BONUS_FERTILIZER_CAP = { 1, 1, 2, 1, 1, 2, 4, 5, 15 };
	private final static int[] PRODUCTS_MIN = { 1, 1, 1, 1, 1, 1, 5, 10, 1 };
	private final static int[] PRODUCTS_MAX = { 2, 2, 10, 1, 1, 1, 15, 15, 1 };
	private final static int[] DAYS_REQUIRED = { 2, 3, 5, 1, 2, 3, 10, 10, 15 };
	// 2x to avoid the truncation of floating point numbers.
	private final static int[] EXP_GAIN = { 10, 15, 25, 5, 10, 15, 50, 50, 150 };
	// 100x to avoid the truncation of floating point numbers.
	private final static int[] CONST_MULTIPLIER = { 200, 200, 100, 300, 300, 300, 100, 100, 300 };

	private int plantId;
	private int conste;
	private int productsProduced;

	/**
	 * This creates a Plant object with specified plantId and conste. It also
	 * calculates how many products the plant will produce when harvested.
	 * 
	 * @param plantId toolId of the tool being created
	 * @param conste  constellation of the tool being created
	 */
	public Plant(int plantId, int conste) {
		this.plantId = plantId;
		this.conste = conste;
		this.productsProduced = ((int) Math.floor(Math.random() * (PRODUCTS_MAX[plantId] - PRODUCTS_MIN[plantId] + 1)))
				+ PRODUCTS_MIN[plantId];
	}

	/**
	 * A method that calculates the final price of a plant when harvested.
	 * 
	 * @param player          the Player object where the registration bonuses are
	 *                        stored
	 * @param timesWatered    the number of times the plant has been watered
	 * @param timesFertilized the number of times the plant has been fertilized
	 * @return the final sell cost of a plant
	 */
	public int calculateFinalPrice(Player player, int timesWatered, int timesFertilized) {

		// Calculate for initial harvestTotal...
		int harvestTotal = this.productsProduced
				* ((SELL_PRICE[plantId] + conste * CONST_MULTIPLIER[plantId]) + player.getEarningBonus());

		// Set caps on waterBonus and fertilizerBonus...
		if (timesWatered > BONUS_WATER_CAP[plantId] + player.getWaterBonusInc())
			timesWatered = BONUS_WATER_CAP[plantId] + player.getWaterBonusInc();
		if (timesFertilized > BONUS_FERTILIZER_CAP[plantId] + player.getFertilizerBonusInc())
			timesFertilized = BONUS_FERTILIZER_CAP[plantId] + player.getFertilizerBonusInc();

		// Add the bonuses associated with waterBonus and fertilizerBonus...
		int waterBonus = (int) (harvestTotal * 0.2 * (timesWatered - 1));
		int fertilizerBonus = (int) (harvestTotal * 0.5 * timesFertilized);
		int finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;

		// If flower, add flower bonus...
		if (isFlower())
			finalHarvestPrice = (int) (finalHarvestPrice * 1.1);
		return finalHarvestPrice;
	}

	/**
	 * A method that returns the plantId of this Plant object.
	 * 
	 * @return the id of this object's plant
	 */
	public int getPlantId() {
		return plantId;
	}

	/**
	 * A method that returns the name of this Plant specified in String format.
	 * 
	 * @return the name of this plant
	 */
	public String getPlantName() {
		return PLANT_NAMES[plantId];
	}

	/**
	 * A method that returns the name of a plant in String format based on the
	 * plantId provided in the parameters.
	 * 
	 * @param plantId id of the plant provided in the parameter
	 * @return the name of the plant based on the id provided in the parameter
	 */
	public static String getPlantNameStatic(int plantId) {
		return PLANT_NAMES[plantId];
	}

	/**
	 * A method that returns the filename of the image of a specific plant in
	 * String format to be used in the planting menu based on the plantId provided
	 * in the parameters.
	 * 
	 * @param plantId id of the plant provided in the parameter
	 * @return the filename of the image of a specific plant (for the planting menu)
	 *         based on the id provided in the parameter
	 */
	public static String getPlantImageStatic(int plantId) {
		return PLANT_IMAGE[plantId];
	}

	/**
	 * A method that returns the filename of the image of a specific plant in
	 * String format to be used in the gacha sidebar based on the plantId provided
	 * in the parameters.
	 * 
	 * @param plantId id of the plant provided in the parameter
	 * @return the filename of the image of a specific plant (for the gacha sidebar)
	 *         based on the id provided in the parameter
	 */
	public static String getGachaImageStatic(int plantId) {
		return GACHA_IMAGE[plantId];
	}

	/**
	 * A method that returns the base buy price of this Plant.
	 * 
	 * @return the base buy price of this plant
	 */
	public int getBuyPrice() {
		return BUY_PRICE[plantId];
	}

	/**
	 * A static method that returns the base buy price of the plant with the ID
	 * provided.
	 *
	 * @param plantId id of the plant provided in the parameter
	 * @return the base buy price based on the id provided in the parameter
	 */
	public static int getBuyPriceStatic(int plantId) {
		return BUY_PRICE[plantId];
	}

	/**
	 * A method that returns the required water needed to properly grow this plant.
	 * 
	 * @return the water required by this plant
	 */
	public int getRequiredWater() {
		return REQUIRED_WATER[plantId];
	}

	/**
	 * A method that returns the bonus water cap for this plant.
	 * 
	 * @return the bonus water cap for this plant
	 */
	public int getBonusWater() {
		return BONUS_WATER_CAP[plantId];
	}

	/**
	 * A method that returns the required fertilizer needed to properly grow this
	 * plant.
	 * 
	 * @return the fertilizer required by this plant
	 */
	public int getRequiredFertilizer() {
		return REQUIRED_FERTILIZER[plantId];
	}

	/**
	 * A method that returns the bonus fertilizer cap for this plant.
	 * 
	 * @return the bonus fertilizer cap for this plant
	 */
	public int getBonusFertilizer() {
		return BONUS_FERTILIZER_CAP[plantId];
	}

	/**
	 * A method that returns the required number of days this plant needs to go
	 * through to be harvestable.
	 * 
	 * @return the number of days this plant needs to grow
	 */
	public int getDaysRequired() {
		return DAYS_REQUIRED[plantId];
	}

	/**
	 * A method that returns the amount of experience given by harvesting this
	 * plant.
	 * 
	 * @return the experience gained from harvesting this plant
	 */
	public int getExpGain() {
		return EXP_GAIN[plantId];
	}

	/**
	 * A method that returns the number of products produced by this instance of the
	 * plant.
	 * 
	 * @return the number of products produced by the plant
	 */

	public int getProductsProduced() {
		return this.productsProduced;
	}

	/**
	 * A method that returns the whether this plant is a root plant or not.
	 *
	 * @return true if plant this plant is a root plant and false otherwise.
	 */

	public boolean isRoot() {
		return this.plantId == PLANT_TURNIP || this.plantId == PLANT_CARROT || this.plantId == PLANT_POTATO;
	}

	/**
	 * A method that returns the whether this plant is a flower or not.
	 *
	 * @return true if plant this plant is a flower and false otherwise.
	 */
	public boolean isFlower() {
		return this.plantId == PLANT_ROSE || this.plantId == PLANT_TULIP || this.plantId == PLANT_SUNFLOWER;
	}

	/**
	 * A method that returns the whether this plant is a tree or not.
	 *
	 * @return true if plant this plant is a tree and false otherwise.
	 */
	public boolean isTree() {
		return this.plantId == PLANT_MANGO || this.plantId == PLANT_APPLE;
	}

	/**
	 * A method that returns the whether this plant is larry or not.
	 *
	 * @return true if plant this plant is larry and false otherwise.
	 */
	public boolean isLarry() {
		return this.plantId == PLANT_LARRY;
	}
}
