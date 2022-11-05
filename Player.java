
public class Player {

	public final int REGISTRATION_BASE = 0;
	public final int REGISTRATION_REGISTERED = 1;
	public final int REGISTRATION_DISTINGUISHED = 2;
	public final int REGISTRATION_LEGENDARY = 3;

	private final int[] REG_EXP_REQUIREMENT = { 0, 1000, 2000, 3000 }; // 2x
	private final int[] BONUS_EARNINGS = { 0, 100, 200, 400 }; // 100x
	private final int[] SEED_COST_REDUCTION = { 0, 100, 200, 300 }; // 100x
	private final int[] WATER_BONUS_INC = { 0, 0, 1, 2 };
	private final int[] FERTILIZER_BONUS_INC = { 0, 0, 0, 1 };
	private final int[] REGISTRATION_FEE = { -1, 20000, 30000, 40000 }; // 100x

	private int exp;
	private int objectCoins;
	private int kusaCoins;
	private int registration;
	private String name;

	private Tool[] toolbar;

	public Player(String name) {
		this.exp = 0; // 2x
		this.objectCoins = 10000; // 100x
		this.kusaCoins = 0;
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
				&& (int) this.exp >= REG_EXP_REQUIREMENT[this.registration + 1]) {
			this.deductObjectCoins(REGISTRATION_FEE[this.registration + 1]);
			this.registration++;
			return true;
		}
		return false;
	}

	public void equipTool(int toolId, int conste){
		this.toolbar[toolId] = new Tool(toolId, conste);
	}

	public String getName() {
		return name;
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

	public int getRegistration() {
		return registration;
	}

	public Tool getTool(int toolId) {
		return toolbar[toolId];
	}

}
