// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO1 

/**
 * Player is the class that simulates the components and fucntionality of a
 * given player, including their name, objectCoins, kusaCoins, registration,
 * experience levels, and a set of tools.
 */
public class Player {

	public final static int REGISTRATION_BASE = 0;
	public final static int REGISTRATION_REGISTERED = 1;
	public final static int REGISTRATION_DISTINGUISHED = 2;
	public final static int REGISTRATION_LEGENDARY = 3;

	private final static String[] REG_NAMES = { "Non-Registered", "Registered", "Distinguished", "Legendary" };
	private final static int[] REG_EXP_REQUIREMENT = { 0, 1000, 2000, 3000 }; // 2x
	private final static int[] BONUS_EARNINGS = { 0, 100, 200, 400 }; // 100x
	private final static int[] SEED_COST_REDUCTION = { 0, 100, 200, 300 }; // 100x
	private final static int[] WATER_BONUS_INC = { 0, 0, 1, 2 };
	private final static int[] FERTILIZER_BONUS_INC = { 0, 0, 0, 1 };
	private final static int[] REGISTRATION_FEE = { -1, 20000, 30000, 40000 }; // 100x

	private int exp;
	private int objectCoins;
	private int kusaCoins;
	private int registration;
	private String name;

	private Tool[] toolbar;

	/**
	 * This creates a player class with a specified name.
	 * 
	 * @param name the name of the player to be created
	 */
	public Player(String name) {
		this.exp = 100000; // 2x
		this.objectCoins = 10000000; // 100x
		this.kusaCoins = 50;
		this.registration = REGISTRATION_BASE;
		this.name = name;
		this.toolbar = new Tool[5];
		this.toolbar[Tool.TOOL_PLOW] = new Tool(Tool.TOOL_PLOW, 0);
		this.toolbar[Tool.TOOL_WATERING] = new Tool(Tool.TOOL_WATERING, 0);
		this.toolbar[Tool.TOOL_FERTILIZER] = new Tool(Tool.TOOL_FERTILIZER, 0);
		this.toolbar[Tool.TOOL_PICKAXE] = new Tool(Tool.TOOL_PICKAXE, 0);
		this.toolbar[Tool.TOOL_SHOVEL] = new Tool(Tool.TOOL_SHOVEL, 0);
	}

	/**
	 * This method checks whether or not the player is able to upgrade their
	 * registration and does so if possible.
	 * 
	 * @return a boolean value indicating whether or not the registration upgrade
	 *         was successful
	 */
	public boolean upgradeRegistration() {
		if (this.registration != REGISTRATION_LEGENDARY && (int) this.exp >= this.upgRegExpReq()
				&& this.objectCoins >= upgRegFee()) {
			this.deductObjectCoins(REGISTRATION_FEE[this.registration + 1]);
			this.registration++;
			return true;
		}
		return false;
	}

	/**
	 * This method checks whether or not the player is able to replace a specified
	 * tool (indicated by toolId) and does so if possible.
	 * 
	 * @param toolId the ID of the tool to be replaced with a new tool
	 * @param conste the constellation of the new tool to be created.
	 *
	 * @return a boolean value indicating whether or not the tool replacement
	 *         was successful
	 */
	public boolean replaceTool(int toolId, int conste) {
		if (this.toolbar[toolId].getReplaceCost() <= this.objectCoins) {
			this.toolbar[toolId] = new Tool(toolId, conste);
			this.deductObjectCoins(this.toolbar[toolId].getReplaceCost());
			return true;
		}
		return false;
	}

	/**
	 * This method returns the name of the player.
	 * 
	 * @return the name of the player as a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the current experience of the player.
	 * 
	 * @return the experience of the player as an int
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * This method returns the name of the current registration of the player.
	 * 
	 * @return the registration name of the player as a String
	 */
	public String getRegistrationName() {
		return REG_NAMES[this.registration];
	}

	/**
	 * This method returns the earning bonus of the current registration of the
	 * player.
	 * 
	 * @return the earning bonus of the current registration of the player as an int
	 */
	public int getEarningBonus() {
		return BONUS_EARNINGS[this.registration];
	}

	/**
	 * This method returns the seed cost reduction of the current registration of
	 * the player.
	 * 
	 * @return the seed cost reduction of the current registration of the player as
	 *         an int
	 */
	public int getSeedCostReduction() {
		return SEED_COST_REDUCTION[this.registration];
	}

	/**
	 * This method returns the water bonus increase of the current registration of
	 * the player.
	 * 
	 * @return the water bonus increase of the current registration of the player as
	 *         an int
	 */
	public int getWaterBonusInc() {
		return WATER_BONUS_INC[this.registration];
	}

	/**
	 * This method returns the fertilizer bonus increase of the current registration
	 * of the player.
	 * 
	 * @return the fertilizer bonus increase of the current registration of the
	 *         player as an int
	 */
	public int getFertilizerBonusInc() {
		return FERTILIZER_BONUS_INC[this.registration];
	}

	/**
	 * This method returns the experience requirement for upgrading to the next
	 * level of registration.
	 * 
	 * @return the required experience for upgrading the player's registration.
	 */
	public int upgRegExpReq() {
		if (this.registration != REGISTRATION_LEGENDARY)
			return REG_EXP_REQUIREMENT[this.registration + 1];
		else
			return -1;
	}

	/**
	 * This method returns the objectCoins fee for upgrading to the next level of
	 * registration.
	 * 
	 * @return the objectCoins fee for upgrading the player's registration.
	 */
	public int upgRegFee() {
		if (this.registration != REGISTRATION_LEGENDARY)
			return REGISTRATION_FEE[this.registration + 1];
		else
			return -1;
	}

	/**
	 * This method returns the current objectCoins of the player.
	 * 
	 * @return the amount of objectCoins the player currently has
	 */
	public int getObjectCoins() {
		return objectCoins;
	}

	/**
	 * This method returns the current kusaCoins of the player.
	 * 
	 * @return the amount of kusaCoins the player currently has
	 */
	public int getKusaCoins() {
		return kusaCoins;
	}

	/**
	 * This method returns the current level of the player.
	 * 
	 * @return the current level the player currently is on based on their
	 *         accumulated experience points
	 */
	public int getLevel() {
		return (int) Math.floor(exp / 200); // Already accounts for the 2x.
	}

	/**
	 * This method returns a tool being equipped by the player based on the
	 * specified toolId
	 * 
	 * @param toolId the ID of the tool to be returned
	 * 
	 * @return a Tool object currently equipped by the player based on toolId
	 */
	public Tool getTool(int toolId) {
		return toolbar[toolId];
	}

	/**
	 * This method increases the player's experience points.
	 * 
	 * @param exp the amount of experience to be gained by the player
	 */
	public void increaseExp(int exp) {
		this.exp += exp;
	}

	/**
	 * This method increases the player's objectCoins.
	 * 
	 * @param coins the amount of objectCoins to be gained by the player
	 */
	public void addObjectCoins(int coins) {
		this.objectCoins += coins;
	}

	/**
	 * This method decreases the player's objectCoins.
	 * 
	 * @param coins the amount of objectCoins to be lost by the player
	 */
	public void deductObjectCoins(int coins) {
		this.objectCoins -= coins;
	}

	/**
	 * This method increases the player's kusaCoins.
	 * 
	 * @param coins the amount of kusaCoins to be gained by the player
	 */
	public void addKusaCoins(int coins) {
		this.kusaCoins += coins;
	}

	/**
	 * This method decreases the player's kusaCoins.
	 * 
	 * @param coins the amount of kusaCoins to be lost by the player
	 */
	public void deductKusaCoins(int coins) {
		this.kusaCoins -= coins;
	}

}
