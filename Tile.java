
public class Tile {
	public final static int STATE_ROCK = 0;
	public final static int STATE_UNPLOWED = 1;
	public final static int STATE_PLOWED = 2;
	public final static int STATE_GROWING = 3;
	public final static int STATE_HARVESTABLE = 4;
	public final static int STATE_WITHERED = 5;

	// x2 the specs
	// private final int[] conste={};
	// private final int[] maxConst={};
	// private final int[] constMultiplier={};

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
		// TODO: IF not only at the start of the game, then add extra conditions.
		this.state = STATE_ROCK;
	}

	public boolean removeRock(Player player) {
		// TODO: add condition if pickaxe is broken cuz durability
		if (this.state == STATE_ROCK ) {
			this.state = STATE_UNPLOWED;
			// TODO: use pickaxe
			return true;
		}
		return false;
	}

	public boolean plowTile(Player player) {
		// TODO: add condition if plow is broken cuz durability
		if (this.state == STATE_UNPLOWED) {
			this.state = STATE_PLOWED;
			// TODO: use plow
			return true;
		}
		return false;// should we add else if for if its alr plowed else if >STATE_UNPLPOWED and else
						// theres a rock
	}

	public boolean plantSeed(int plantId, Player player, int conste) {
		if (this.state == STATE_PLOWED && (plantId >= 0 && plantId <= 7)) {
			this.plant = new Plant(plantId, conste);
			if (this.plant.getBuyPrice() <= player.getObjectCoins()) {
				this.state = STATE_GROWING;
				player.deductObjectCoins(this.plant.getBuyPrice());
				return true;
			} else {
				this.plant = null;
				return false;
			}

			// TODO: reset watered and fertilzer count after plant is removied
		}
		return false;
	}

	public boolean harvestTile(Player player) {// TODO: EXP GAINS REMEMBER ITS FUCKING *2
		if (this.state == STATE_HARVESTABLE) {
			this.state = STATE_UNPLOWED;
			player.addObjectCoins(this.plant.calculateFinalPrice(player, this.timesWatered, this.timesFertilized));
			this.timesWatered = 0;
			this.timesFertilized = 0;
			this.plant = null;
			this.daysPast = 0;
			return true;
		} else {
			// TODO: do nothing or remove the plant from the tile but no rewards
			return false;
		}

	}

	public boolean removePlant(Player player) {
		if (this.state == STATE_WITHERED) {
			this.state = STATE_UNPLOWED;
			// TODO: Call use tool
			return true;
		} else if (this.state < 0 || this.state > 5) {
			return false;
		}

		else {// condition
			this.plant = null;
			if (this.state != STATE_ROCK) {
				this.state = STATE_UNPLOWED;
			}
			// TODO:USESHOVEL
			return true;
		}

	}

	public boolean waterTile(Player player) {
		// TODO: add code for tool
		if (this.state == STATE_GROWING) {
			this.timesWatered++;
			return true;
		}
		return false;
	}

	public boolean fertilizeTile(Player player) {
		// TODO: add code for tool
		if (this.state == STATE_GROWING) {
			this.timesFertilized++;
			return true;
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
			this.plant = null;
		}

		// add condition for withered
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



/*
 * Use Pickaxe (Remove Rock)
 * Use Plow (Plow a Tile)
 * Plant Plant (Add Plant)
 * Use Watering Can
 * Use Fertilizer
 * Use Hand (Harvest a Tile)
 * Use Shovel (Removing a Plant)
 * Upgrade Registration
 * Buy Tools (Replace Broken Tools)
 * Advance Day
 * */