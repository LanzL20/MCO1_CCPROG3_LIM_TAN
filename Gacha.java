// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO1 

/**
 * Gacha is the handler class that deals with all the logic behind plant and
 * tool banners, as well as their constellations.
 */

public class Gacha {
	public final static int MAX_CONST = 3;
	public final static int PULL_COST = 20;

	private int[] constPlants;
	private int[] constTools;

	private int[] banner;

	/**
	 * This creates a Gacha object. At the start of the game, it declares all tool
	 * constellations at 0. Moreover, generate the 4 plants/tools that will appear
	 * in the banner at the start of the game.
	 */
	public Gacha() {
		this.constPlants = new int[9];
		for (int i = 0; i < 8; i++) {
			this.constPlants[i] = 0;
		}
		this.constPlants[8] = -1;
		this.constTools = new int[5];
		for (int i = 0; i < 5; i++) {
			this.constTools[i] = 0;
		}
		this.banner = new int[4];
		resetBanner();
	}

	/**
	 * This method is in charge of changing the plants/tools that appear in the
	 * banner everyday. Plants/Tools cannot appear twice in the same day.
	 */
	public void resetBanner() {
		int counter = 0;
		int temp;
		boolean found = false;
		while (counter != 4) {
			found = false;
			temp = (int) Math.floor(Math.random() * 14);
			for (int j = 0; j < counter; j++) {
				if (temp == banner[j]) {
					found = true;
					break;
				}
			}
			if (!found) {
				banner[counter] = temp;
				counter++;
			}
		}
	}

	/**
	 * This method rolls on the banner as specified by banner[], appropriately deducting
	 * KusaCoins and increasing the constellations based on chance.
	 * 
	 * @param player Player object where we deduct the cost of using rollBanner
	 * @return if successful, the plant/tool const gotten; if not, -1
	 */
	public int rollBanner(Player player) {
		if (player.getKusaCoins() >= PULL_COST) {
			player.deductKusaCoins(PULL_COST);
			int roll = (int) Math.floor(Math.random() * 4);
			if (banner[roll] < 9) {
				constPlants[banner[roll]]++;
				if (constPlants[banner[roll]] > MAX_CONST)
					constPlants[banner[roll]] = MAX_CONST;
			} else {
				constTools[banner[roll] - 9]++;
				if (constTools[banner[roll] - 9] > MAX_CONST)
					constTools[banner[roll] - 9] = MAX_CONST;
			}
			return banner[roll];
		}
		return -1;
	}

	/**
	 * A method that returns the constellation of a plant given its ID.
	 * 
	 * @param plantId ID of the plant being inquired
	 * @return the constellation of the plant specified by plantId
	 */
	public int getConstPlant(int plantId) {
		return constPlants[plantId];
	}

	/**
	 * A method that returns the constellation of a tool given its ID.
	 * 
	 * @param toolId ID of the tool being inquired
	 * @return the constellation of the tool specified by toolId
	 */
	public int getConstTool(int toolId) {
		return constTools[toolId];
	}

	/**
	 * A method that returns the current plants/tools available on the banner.
	 * 
	 * @return the current plants/tools that are available on the banner currently.
	 */
	public int[] getBanner() {
		return banner;
	}
}
