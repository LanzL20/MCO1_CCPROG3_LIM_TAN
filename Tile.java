// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO1 

/**
 * This class stores the various properties of a given tile, including its
 * current state, the plant it currently contains, the times of times it has
 * been watered and fertilized, and the number of days past since a seed has
 * been planted. Furthermore, it also contains methods that facilate
 * modifications within the plant and the tile itself.
 */
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

	/**
	 * This constructor creates a Tile with the default settings (i.e., unplowed).
	 */
	public Tile() {
		this.state = 1;
		this.timesWatered = 0;
		this.timesFertilized = 0;
		this.daysPast = 0;
	}

	/**
	 * This method changes the state of the tile to being rocked and resets the
	 * plant accordingly.
	 */
	public void addRock() {
		this.state = STATE_ROCK;
		this.resetPlant();
	}

	/**
	 * This method attempts to remove the rock if conditions are satisfied, updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently removing the rock
	 * @return a boolean value indicating whether or not the removal was successful
	 */
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

	/**
	 * This method attempts to plow the tile if conditions are satisfied, updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently plowing the tile
	 * @return a boolean value indicating whether or not the plowing was successful
	 */
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

	/**
	 * This method attempts to plant a seed if conditions are satisfied, updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param plantId the ID of the plant to be planted on this tile
	 * @param player  the Player object currently planting a seed
	 * @param coste   the constellation value of the plant to be planted
	 * @return a boolean value indicating whether or not the planting was successful
	 *         TODO update javadoc
	 */
	public boolean plantSeed(int plantId, Player player, int conste, int x, int y, Tile farm[][]) {
		if (this.state == STATE_PLOWED && (plantId >= 0 && plantId <= 7)) {
			this.resetPlant();
			this.plant = new Plant(plantId, conste);
			if (this.plant.getBuyPrice() <= player.getObjectCoins()) {
				if ((this.plant.isTree() && !(x == 0 || x == 9 || y == 0 || y == 4)) || !this.plant.isTree()) {
					if (!this.plant.isTree()) {
						this.state = STATE_GROWING;
						player.deductObjectCoins(this.plant.getBuyPrice() - player.getSeedCostReduction());
						return true;
					}

					else {
						boolean plantFound = false;
						for (int i = -1; i < 2; i++) {
							for (int j = 1; j > -2; j--) {
								if (!(farm[x - i][y - j].getState() == STATE_UNPLOWED || farm[x - i][y - j].getState() == STATE_PLOWED) && !(x - i == x && y - j == y)) {
									plantFound = true;
									break;
								}
							}
							if(plantFound)
								break;
						}
						if (!plantFound) {
							this.state = STATE_GROWING;
							player.deductObjectCoins(this.plant.getBuyPrice() - player.getSeedCostReduction());
							return true;
						} else {
							this.resetPlant();
							return false;
						}
					}
				}
			} else {
				this.resetPlant();
				return false;
			}
		} else {
			this.resetPlant();
			return false;
		}
		return false;

	}

	/**
	 * This method attempts to water the tile if conditions are satisfied, updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently watering the tile
	 * @return a boolean value indicating whether or not the watering was successful
	 */
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

	/**
	 * This method attempts to fertilize the tile if conditions are satisfied,
	 * updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently fertilizing the tile
	 * @return a boolean value indicating whether or not the fertilizing was
	 *         successful
	 */
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

	/**
	 * This method attempts to harvest the tile if conditions are satisfied,
	 * updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently harvesting the tile
	 * @return a boolean value indicating whether or not the harvesting was
	 *         successful
	 */
	public boolean harvestTile(Player player) {
		if (this.state == STATE_HARVESTABLE) {
			this.state = STATE_UNPLOWED;
			player.addObjectCoins(this.plant.calculateFinalPrice(player, this.timesWatered, this.timesFertilized));
			player.increaseExp(this.plant.getExpGain());
			this.resetPlant();
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method attempts to shovel the tile if conditions are satisfied, updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently shovelling the tile
	 * @return a boolean value indicating whether or not the shovelling was
	 *         successful
	 */
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

	/**
	 * This method updates the state of the tile as a day progresses in accordance
	 * with the rules laid out in the project specifications.
	 */
	public void advanceDay() {
		if (this.state == STATE_GROWING) {
			this.daysPast++;
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

	/**
	 * This method essentially resets all values pertaining to the plant, including
	 * the plant itself, the times it has been watered, the times it has been
	 * fertilized, and the number of days past since the planting of its seed.
	 */
	private void resetPlant() {
		this.plant = null;
		this.timesFertilized = 0;
		this.timesWatered = 0;
		this.daysPast = 0;
	}

	/**
	 * This method returns the current state of the tile in terms of the final
	 * static varibles initialized above.
	 * 
	 * @return the current state of the tile
	 */
	public int getState() {
		return state;
	}

	/**
	 * This method returns the number of times the plant on this tile has been
	 * watered.
	 * 
	 * @return the current number of times the plant has been watered
	 */
	public int getTimesWatered() {
		return this.timesWatered;
	}

	/**
	 * This method returns the number of times the plant on this tile has been
	 * fertilied.
	 * 
	 * @return the current number of times the plant has been fertilized
	 */
	public int getTimesFertilized() {
		return this.timesFertilized;
	}

	/**
	 * This method returns the Plant object stored within this tile.
	 * 
	 * @return the instance of the Plant object currently stored in this tile
	 */
	public Plant getPlant() {
		return this.plant;
	}

	/**
	 * This method returns whether or not the plant on this tile is harvestable.
	 * 
	 * @return a boolean indicating whether or not the plant is harvestable
	 */
	public boolean isHarvestable() {
		if (this.state == STATE_HARVESTABLE) {
			return true;
		}
		return false;
	}
}
