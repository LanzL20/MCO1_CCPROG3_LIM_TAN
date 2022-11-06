
public class Tile {

	// All public IDs for the possible states of tiles.
	public final static int STATE_ROCK = 0;
	public final static int STATE_UNPLOWED = 1;
	public final static int STATE_PLOWED = 2;
	public final static int STATE_GROWING = 3;
	public final static int STATE_HARVESTABLE = 4;
	public final static int STATE_WITHERED = 5;

	private int state;
	private Plant plant;
	private int timesWatered;
	private int timesFertilized;
	private int daysPast;

	public Tile() {
		this.state = 1;
		this.timesWatered = 0;
		this.timesFertilized = 0;
		this.daysPast = 0;
	}

	public void addRock() {
		this.state = STATE_ROCK;
		this.resetPlant();
	}

	public boolean removeRock(Player player) {
		if (this.state == STATE_ROCK) {
			if (player.getTool(Tool.TOOL_PICKAXE).useTool(player)) {
				this.state = STATE_UNPLOWED;
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean plowTile(Player player) {
		if (this.state == STATE_UNPLOWED) {
			if (player.getTool(Tool.TOOL_PLOW).useTool(player)) {
				this.state = STATE_PLOWED;
				return true;
			}
			return false;
		}
		return false;// should we add else if for if its alr plowed else if >STATE_UNPLPOWED and else
						// theres a rock
	}

	public boolean plantSeed(int plantId, Player player, int conste) {
		if (this.state == STATE_PLOWED && (plantId >= 0 && plantId <= 7)) {
			this.resetPlant();
			this.plant = new Plant(plantId, conste);
			if (this.plant.getBuyPrice() <= player.getObjectCoins()) {
				this.state = STATE_GROWING;
				player.deductObjectCoins(this.plant.getBuyPrice());
				return true;
			} else {
				this.resetPlant();
				return false;
			}
		}
		return false;
	}

	public boolean harvestTile(Player player) {
		if (this.state == STATE_HARVESTABLE) {
			this.state = STATE_UNPLOWED;
			player.addObjectCoins(this.plant.calculateFinalPrice(player, this.timesWatered, this.timesFertilized));
			this.resetPlant();
			return true;
		} else {
			return false;
		}

	}

	public boolean removePlant(Player player) {
		if (this.state == STATE_WITHERED) {
			if (player.getTool(Tool.TOOL_SHOVEL).useTool(player)) {
				this.resetPlant();
				this.state = STATE_UNPLOWED;
				return true;
			}
			return false;
		} else if (this.state < 0 || this.state > 5) {
			return false;
		} else {
			if (player.getTool(Tool.TOOL_SHOVEL).useTool(player)) {
				this.resetPlant();
				if (this.state != STATE_ROCK) {
					this.state = STATE_UNPLOWED;
				}
				return true;
			}
			return false;

		}

	}

	public boolean waterTile(Player player) {
		if (this.state == STATE_GROWING) {
			if (player.getTool(Tool.TOOL_WATERING).useTool(player)) {
				this.timesWatered++;
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean fertilizeTile(Player player) {
		if (this.state == STATE_GROWING) {
			if (player.getTool(Tool.TOOL_FERTILIZER).useTool(player)) {
				this.timesFertilized++;
				return true;
			}
			return false;
		}
		return false;
	}

	public void advanceDay() {
		this.daysPast++;
		if (this.state == STATE_GROWING) {
			if (daysPast == this.plant.getDaysRequired()) {
				if (timesWatered >= this.plant.getRequiredWater()
						&& timesFertilized >= this.plant.getRequiredFertilizer())
					this.state = STATE_HARVESTABLE;
				else
					this.state = STATE_WITHERED;
			}
		} else if (this.state == STATE_HARVESTABLE) {
			this.state = STATE_WITHERED;
		}
	}

	private void resetPlant() {
		this.plant = null;
		this.timesFertilized = 0;
		this.timesWatered = 0;
		this.daysPast = 0;
	}

	public int getTimesWatered(){
		return this.timesWatered;
	}

	public int getTimesFertilized(){
		return this.timesFertilized;
	}

	public int getState() {
		return state;
	}

	public Plant getPlant() {
		return this.plant;
	}

	public boolean isHarvestable() {
		if (this.state == STATE_HARVESTABLE) {
			return true;
		}
		return false;
	}
}
