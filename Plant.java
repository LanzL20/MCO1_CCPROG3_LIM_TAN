// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO1 

/**
 * This class serves as the blueprint for a plant containing the various
 * attributes that a plant usually possesses. These include its base buy and
 * sell prices, required water and fertilizer, bonus water and fertilizer caps,
 * and the range of the products produced. Most of the methods of this class are
 * getters for the various properties of the plant. The final sell price is also
 * calculated in this class through a method.
 */
public class Plant {

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

	private final static String[] PLANT_IMAGE = { "Plant-Turnip.png", "Plant-Carrot.png", "Plant-Potato.png","Plant-Rose.png","Plant-Tulips.png",
												"Plant-Sunflower.png","Plant-Mango.png","Plant-Apple.png"};
	private final static int[] BUY_PRICE = { 500, 1000, 2000, 500, 1000, 2000, 10000, 20000 }; // 100x to avoid
																								// truncation of
																								// floating point
																								// numbers
	private final static int[] SELL_PRICE = { 600, 900, 300, 500, 900, 1900, 800, 500 }; // (Base Sell Prices) 100x to
																							// avoid truncation of
																							// floating point numbers
	private final static int[] REQUIRED_WATER = { 1, 1, 3, 1, 2, 2, 7, 7 };
	private final static int[] REQUIRED_FERTILIZER = { 0, 0, 1, 0, 0, 1, 4, 5 };
	private final static int[] BONUS_WATER_CAP = { 2, 2, 4, 2, 3, 3, 7, 7 };
	private final static int[] BONUS_FERTILIZER_CAP = { 1, 1, 2, 1, 1, 2, 4, 5 };
	private final static int[] PRODUCTS_MIN = { 1, 1, 1, 1, 1, 1, 5, 10 };
	private final static int[] PRODUCTS_MAX = { 2, 2, 10, 1, 1, 1, 15, 15 };
	private final static int[] DAYS_REQUIRED = { 2, 3, 5, 1, 2, 3, 10, 10 };
	private final static int[] EXP_GAIN = { 10, 15, 25, 5, 10, 15, 50, 50 }; // 2x to avoid truncation of floating point
																				// numbers
	private final static int[] CONST_MULTIPLIER = { 100, 100, 100, 100, 100, 100, 100, 100 }; // 100x to avoid
																								// truncation of
																								// floating point
																								// numbers TODO: balance

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

	public static String getPlantImageStatic(int plantId){
		return PLANT_IMAGE[plantId];
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
	 * A method that returns the required fertilizer needed to properly grow this
	 * plant.
	 * 
	 * @return the fertilizer required by this plant
	 */

	public int getRequiredFertilizer() {
		return REQUIRED_FERTILIZER[plantId];
	}

	/**
	 * A method that returns the required number of days this plant needs to go
	 * through
	 * to be harvestable.
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
}
