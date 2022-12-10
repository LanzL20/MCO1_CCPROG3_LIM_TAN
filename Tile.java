// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2 

/**
 * This class stores the various properties of a given tile, including its
 * current state, the plant it currently contains, the times of times it has
 * been watered and fertilized, and the number of days past since a seed has
 * been planted. Furthermore, it also contains methods that facilate
 * modifications within the plant and the tile itself.
 */
public class Tile {

	// All the public IDs for the possible states of tiles.
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
		// If the tile is in a rocked state...
		if (this.state == STATE_ROCK) {
			// And the player is able to use a pickaxe...
			if (player.getTool(Tool.TOOL_PICKAXE).useTool(player)) {
				// Set tile to an unplowed state.
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
		// If the tile is in a unplowed state...
		if (this.state == STATE_UNPLOWED) {
			// And the player is able to use a plow...
			if (player.getTool(Tool.TOOL_PLOW).useTool(player)) {
				// Set tile to an plowed state.
				this.state = STATE_PLOWED;
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * This method attempts to plant a seed if conditions are satisfied, updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param plantId the ID of the plant to be planted on this tile
	 * @param player  the Player object currently planting a seed
	 * @param coste   the constellation value of the plant to be planted
	 * @param x       the x coordinate of the tile to be planted on
	 * @param y       the y coordinate of the tile to be planted on
	 * @param farm    the 2d array of tiles representing the entire farm
	 * @return a boolean value indicating whether or not the planting was successful
	 */
	public boolean plantSeed(int plantId, Player player, int conste, int x, int y, Tile farm[][]) {
		// If larry hasn't been unlocked yet, then don't plant them.
		if (conste == -1) {
			return false;
		}
		// If the tile is in a plowed state...
		if (this.state == STATE_PLOWED && (plantId >= 0 && plantId <= 8)) {
			this.resetPlant();
			this.plant = new Plant(plantId, conste);
			// And the player is able to use a purchase the seed...
			if (this.plant.getBuyPrice() <= player.getObjectCoins()) {
				// And, if tree, it is not planted in the corner...
				if ((this.plant.isTree() && !(x == 0 || x == 9 || y == 0 || y == 4)) || !this.plant.isTree()) {
					// If it is not a tree...
					if (!this.plant.isTree()) {
						// Set tile to an growing state.
						this.state = STATE_GROWING;
						player.deductObjectCoins(this.plant.getBuyPrice() - player.getSeedCostReduction());
						return true;
					}
					// If it is a tree...
					else {
						// Check if there is enough space...
						boolean plantFound = false;
						for (int i = -1; i < 2; i++) {
							for (int j = 1; j > -2; j--) {
								if (!(farm[x - i][y - j].getState() == STATE_UNPLOWED
										|| farm[x - i][y - j].getState() == STATE_PLOWED)
										&& !(x - i == x && y - j == y)) {
									plantFound = true;
									break;
								}
							}
							if (plantFound)
								break;
						}
						if (!plantFound) {
							// Set tile to an growing state.
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
	 * @param player the Player object currently watering the tile, null if
	 *               triggered by an event
	 * @return a boolean value indicating whether or not the watering was successful
	 */
	public boolean waterTile(Player player) {
		// If player is null, the watering is triggered by an event.
		if (player == null) {
			this.timesWatered++;
			return true;
		}
		// If player is not null, watering is triggered by the player.
		else {
			// If the tile is in a growing state...
			if (this.state == STATE_GROWING) {
				// And the player is able to use a use the watering can...
				if (player.getTool(Tool.TOOL_WATERING).useTool(player)) {
					// Increment times watered.
					this.timesWatered++;
					return true;
				}
				return false;
			}
			return false;
		}
	}

	/**
	 * This method attempts to fertilize the tile if conditions are satisfied,
	 * updating
	 * both the tile and player appropriately if successful.
	 * 
	 * @param player the Player object currently fertilizing the tile, null if
	 *               triggered by an event
	 * @return a boolean value indicating whether or not the fertilizing was
	 *         successful
	 */
	public boolean fertilizeTile(Player player) {
		// If player is null, the fertilizing is triggered by an event.
		if (player == null) {
			this.timesFertilized++;
			return true;
		}
		// If player is not null, fertilizing is triggered by the player.
		else {
			// If the tile is in a growing state...
			if (this.state == STATE_GROWING) {
				// And the player is able to use a use the fertilizer...
				if (player.getTool(Tool.TOOL_FERTILIZER).useTool(player)) {
					// Increment times fertilized.
					this.timesFertilized++;
					return true;
				}
				return false;
			}
			return false;
		}
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
		// If the tile is in a harvestable state...
		if (this.state == STATE_HARVESTABLE) {
			// Harvest the plant and reset state to unplowed.
			this.state = STATE_UNPLOWED;
			player.addObjectCoins(this.plant.calculateFinalPrice(player, this.timesWatered, this.timesFertilized));
			if (this.plant.getPlantId() == Plant.PLANT_LARRY) {
				player.addKusaCoins(30);
			}
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
		// If the tile is in a withered state...
		if (this.state == STATE_WITHERED) {
			// And the player is able to use a use the shovel...
			if (player.getTool(Tool.TOOL_SHOVEL).useTool(player)) {
				// Reset state to unplowed.
				this.resetPlant();
				this.state = STATE_UNPLOWED;
				return true;
			}
			return false;
		} else if (this.state < 0 || this.state > 5) {
			return false;
		}
		// If the tile is in any other state other than rocked...
		else {
			// And the player is able to use a use the shovel...
			if (player.getTool(Tool.TOOL_SHOVEL).useTool(player)) {
				// Reset state to unplowed and remove the plant.
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
		// Update conditions to proceed onto the next day.
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
	 * This method instantly makes this tile harvestable, all with maximum water
	 * and fertilizer bonuses, represented as a miracle. This is to be used by
	 * certain events.
	 */
	public void makeMiracle() {
		this.daysPast = this.plant.getDaysRequired() - 1;
		this.timesWatered = 1000;
		this.timesFertilized = 1000;
	}

	/**
	 * This method instantly makes this tile lose all water, reseting the value for
	 * timesWatered back to 0. This is to be used by certain events.
	 */
	public void getDehydrated() {
		this.timesWatered = 0;
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
