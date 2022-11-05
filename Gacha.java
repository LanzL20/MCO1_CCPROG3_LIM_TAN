// GACHA

public class Gacha {
	public final static int MAX_CONST = 3;
	public final static int PULL_COST = 20;

	private int[] constPlants;
	private int[] constTools;

	private int[] banner;

	public Gacha() {
		this.constPlants = new int[8];
		for (int i = 0; i < 8; i++) {
			this.constPlants[i] = 0;
		}
		this.constTools = new int[5];
		for (int i = 0; i < 5; i++) {
			this.constTools[i] = 0;
		}
		this.banner = new int[4];
		resetBanner();
	}

	public void resetBanner() {
		int counter = 0;
		int temp;
		boolean found = false;
		while (counter != 4) {
			found = false;
			temp = (int) Math.floor(Math.random() * 13);
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

	public boolean rollBanner(Player player) {
		if(player.getKusaCoins() >= PULL_COST){
			player.deductKusaCoins(PULL_COST);
			int roll = (int) Math.floor(Math.random() * 4);
			if(roll < 8){
				constPlants[roll]++;
				if(constPlants[roll] > MAX_CONST)
					constPlants[roll] = MAX_CONST;
			}else{
				roll -= 8;
				constTools[roll]++;
				if(constTools[roll] > MAX_CONST)
					constTools[roll] = MAX_CONST;
			}
			return true;
		}
		return false;
	}

	public int getConstPlant(int plantId) {
		return constPlants[plantId];
	}

	public int getConstTool(int toolId) {
		return constTools[toolId];
	}
}
