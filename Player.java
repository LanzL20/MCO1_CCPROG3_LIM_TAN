
public class Player {//TODO:TEST FERTILIZER BONUS AND WATERBONUS AND SEED COST REDUCTION, REPAIR TOOLS

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

	public Player(String name) {
		this.exp = 2000; // 2x
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

	public void increaseExp(int exp) {
		this.exp += exp;
	}

	public boolean upgradeRegistration() {// PLEASE PUT *2 IN ALL EXP GAINS tool use harvesting
		if (this.registration != REGISTRATION_LEGENDARY && this.objectCoins >= REGISTRATION_FEE[this.registration + 1]
				&& (int) this.exp >= this.upgRegExpReq()) {
			this.deductObjectCoins(REGISTRATION_FEE[this.registration + 1]);
			this.registration++;
			return true;
		}
		return false;
	}

	public int upgRegFee() {
		if (this.registration != REGISTRATION_LEGENDARY)
			return REGISTRATION_FEE[this.registration + 1];
		else
			return -1;
	}

	public int upgRegExpReq() {//
		if (this.registration != REGISTRATION_LEGENDARY)
			return REG_EXP_REQUIREMENT[this.registration + 1];
		else
			return -1;
	}

	public boolean replaceTool(int toolId, int conste) {
		if (this.toolbar[toolId].getReplaceCost() <= this.objectCoins) {
			this.toolbar[toolId] = new Tool(toolId, conste);
			this.deductObjectCoins(this.toolbar[toolId].getReplaceCost());
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public String getRegistrationName() {
		return REG_NAMES[this.registration];
	}

	public int getEarningBonus() {
		return BONUS_EARNINGS[this.registration];
	}

	public int getSeedCostReduction() {
		return SEED_COST_REDUCTION[this.registration];
	}

	public int getWaterBonusInc() {
		return WATER_BONUS_INC[this.registration];
	}

	public int getFertilizerBonusInc() {
		return FERTILIZER_BONUS_INC[this.registration];
	}

	public int getKusaCoins() {
		return kusaCoins;
	}

	public int getObjectCoins() {
		return objectCoins;
	}

	public int getLevel() {
		return (int) Math.floor(exp / 200); // Already accounts for the 2x.
	}

	public int getExp() {
		return exp;
	}

	public void addKusaCoins(int coins) {
		this.kusaCoins += coins;
	}

	public void deductKusaCoins(int coins) {
		this.kusaCoins -= coins;
	}

	public void addObjectCoins(int coins) {
		this.objectCoins += coins;
	}

	public void deductObjectCoins(int coins) {
		this.objectCoins -= coins;
	}

	public Tool getTool(int toolId) {
		return toolbar[toolId];
	}

}
