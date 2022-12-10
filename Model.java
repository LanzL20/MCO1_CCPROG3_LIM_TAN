import java.util.ArrayList;

// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2 

/**
 * The Model class is essentialy the main class that integrates everything in
 * the model, including the interactions between the player, the farm, the
 * plants, the tools, the gacha, and many more.
 */
public class Model {

	private int dayNo;
	private Tile farm[][];
	private Player player;
	private Gacha gacha;
	private boolean isLost;

	// Contains all the preset events that have a chance to be triggered.
	private ArrayList<Event> events[];

	public static final int FARM_WIDTH = 10;
	public static final int FARM_HEIGHT = 5;

	/**
	 * This constructor creates a Model object, initializing the farm, the player
	 * (with a specified name), all the preset events, and the gacha handler.
	 * 
	 * @param name the name of the player to be created (not currently used)
	 */
	@SuppressWarnings("unchecked")
	public Model(String name) {

		// Set all the default values...
		this.dayNo = 1;
		this.farm = new Tile[FARM_WIDTH][FARM_HEIGHT];
		for (int i = 0; i < FARM_WIDTH; i++) {
			for (int j = 0; j < FARM_HEIGHT; j++) {
				farm[i][j] = new Tile();
			}
		}
		this.player = new Player(name);
		this.gacha = new Gacha();
		this.isLost = false;

		// Instantiate the array containing all events.
		this.events = new ArrayList[3];
		this.events[0] = new ArrayList<Event>();
		this.events[1] = new ArrayList<Event>();
		this.events[2] = new ArrayList<Event>();

		// Good Event: Rain
		this.events[0].add(new FarmEvent(Event.KUSA_COINS_GOOD, "It rained! All farm tiles watered once!") {
			@Override
			public void affectFarm(Tile[][] farm, Player player) {
				for (int i = 0; i < FARM_WIDTH; i++) {
					for (int j = 0; j < FARM_HEIGHT; j++) {
						if (farm[i][j].getPlant() != null) {
							farm[i][j].waterTile(null);
						}
					}
				}
				super.addKusaCoins(player);
			}
		});
		// Good Event: Volcanic Soil
		this.events[0]
				.add(new FarmEvent(Event.KUSA_COINS_GOOD, "Woah, volcanic soil?! All farm tiles fertilized once!") {
					@Override
					public void affectFarm(Tile[][] farm, Player player) {
						for (int i = 0; i < FARM_WIDTH; i++) {
							for (int j = 0; j < FARM_HEIGHT; j++) {
								if (farm[i][j].getPlant() != null) {
									farm[i][j].fertilizeTile(null);
								}
							}
						}
						super.addKusaCoins(player);
					}
				});

		// Good Event: Farm Miracle
		this.events[0]
				.add(new FarmEvent(Event.KUSA_COINS_GOOD,
						"It's a miracle! Your entire farm is somehow ready to harvest!") {
					@Override
					public void affectFarm(Tile[][] farm, Player player) {
						for (int i = 0; i < FARM_WIDTH; i++) {
							for (int j = 0; j < FARM_HEIGHT; j++) {
								if (farm[i][j].getPlant() != null) {
									farm[i][j].makeMiracle();
								}
							}
						}
						super.addKusaCoins(player);
					}
				});

		// Good Event: Player EXP Increase
		this.events[0]
				.add(new PlayerEvent(Event.KUSA_COINS_GOOD, "You had a weird but insightful dream! Gained 50 EXP!") {
					@Override
					public void affectPlayer(Player player) {
						player.increaseExp(100);
						super.addKusaCoins(player);
					}
				});

		// Good Event: Player ObjectCoins Increase
		this.events[0]
				.add(new PlayerEvent(Event.KUSA_COINS_GOOD,
						"You found a stash of coins under your bed! Gained 50 ObjectCoins!") {
					@Override
					public void affectPlayer(Player player) {
						player.addObjectCoins(5000);
						super.addKusaCoins(player);
					}
				});

		// Good Event: Player KusaCoins Increase
		this.events[0]
				.add(new PlayerEvent(Event.KUSA_COINS_GOOD,
						"KusaCoins fall from the sky! Gained 20 KusaCoins!") {
					@Override
					public void affectPlayer(Player player) {
						player.addKusaCoins(20);
						super.addKusaCoins(player);
					}
				});

		// Good Event: Random Tool Repairing
		this.events[1]
				.add(new PlayerEvent(Event.KUSA_COINS_BAD,
						"Someone left a tool on your doorstep! Random tool repaired!") {
					@Override
					public void affectPlayer(Player player) {
						int toolID = (int) Math.floor(Math.random() * 5);
						player.replaceTool(toolID, gacha.getConstTool(toolID));
						super.addKusaCoins(player);
					}
				});

		// Bad Event: Landslide
		this.events[1].add(new FarmEvent(Event.KUSA_COINS_BAD, "Yikes, a landslide! Some tiles covered in rock!") {
			@Override
			public void affectFarm(Tile[][] farm, Player player) {
				for (int i = 0; i < 7; i++) {
					int x = (int) Math.floor(Math.random() * 10);
					int y = (int) Math.floor(Math.random() * 5);
					farm[x][y].addRock();
				}
				super.addKusaCoins(player);
			}
		});

		// Bad Event: Drought
		this.events[1].add(new FarmEvent(Event.KUSA_COINS_BAD, "Oh noes, a drought! All tiles' water reduced to 0!") {
			@Override
			public void affectFarm(Tile[][] farm, Player player) {
				for (int i = 0; i < FARM_WIDTH; i++) {
					for (int j = 0; j < FARM_HEIGHT; j++) {
						if (farm[i][j].getPlant() != null) {
							farm[i][j].getDehydrated();
						}
					}
				}
				super.addKusaCoins(player);
			}
		});

		// Bad Event: Player ObjectCoins Decrease
		this.events[1]
				.add(new PlayerEvent(Event.KUSA_COINS_BAD,
						"You got robbed! Lost a max of 25 ObjectCoins!") {
					@Override
					public void affectPlayer(Player player) {
						if (player.getObjectCoins() >= 2500)
							player.deductObjectCoins(2500);
						else
							player.deductObjectCoins(player.getObjectCoins());
						super.addKusaCoins(player);
					}
				});

		// Bad Event: Random Tool Breaking
		this.events[1]
				.add(new PlayerEvent(Event.KUSA_COINS_BAD,
						"You tripped and fell into your toolshed! Random tool broken!") {
					@Override
					public void affectPlayer(Player player) {
						int toolID = (int) Math.floor(Math.random() * 5);
						player.getTool(toolID).getBroken();
						super.addKusaCoins(player);
					}
				});

		// Neutral Events: Just Jokes
		this.events[2].add(new JokeEvent("What did the bee say to the flower? Hi honey!"));
		this.events[2].add(new JokeEvent("What did the flower study in college? STEM!"));
		this.events[2].add(new JokeEvent("What do you call a flower that runs on electricity? A power plant!"));
		this.events[2].add(new JokeEvent("What is Spring's favourite kind of pickle?"));
		this.events[2].add(new JokeEvent("Why did the tree take a nap? For rest!"));
		this.events[2].add(new JokeEvent("How do trees get on the Internet? They log on!"));
		this.events[2].add(new JokeEvent("How do plants make themselves heard? With amp-leaf-ication!"));
		this.events[2].add(new JokeEvent("Why did the gardener quit his job? His celery wasn't high enough!"));
		this.events[2].add(new JokeEvent(
				"What do you call a jungle where the animals talk about current events? A topical rainforest!"));
		this.events[2].add(new JokeEvent("There's tulips! Yours and mine!"));
	}

	/**
	 * This method simulates the action of progressing a day, appropriately updating
	 * all tiles on the farm, triggering an event if chance permits, reseting
	 * the gacha banner as intended, and checking for gameover.
	 */
	public String advanceDay() {
		String eventText = null;

		// Check if event is going to be triggered.
		int event = (int) Math.floor(Math.random() * 10);
		if (event == 0) {

			// Check which type of event is going to be triggered.
			int type = (int) Math.floor(Math.random() * 3);

			// Check which specfici event to be triggered.
			int id = (int) Math.floor(Math.random() * events[type].size());

			// Trigger the random event.
			if (events[type].get(id) instanceof FarmEvent) {
				((FarmEvent) events[type].get(id)).affectFarm(farm, player);
			} else if (events[type].get(id) instanceof PlayerEvent) {
				((PlayerEvent) events[type].get(id)).affectPlayer(player);
			} else if (events[type].get(id) instanceof JokeEvent) {
				((JokeEvent) events[type].get(id)).giveKusa(player);
			}

			// Save event text for displaying in View.
			eventText = events[type].get(id).getTextDisplayed();
		}
		this.dayNo++;
		for (int i = 0; i < FARM_WIDTH; i++) {
			for (int j = 0; j < FARM_HEIGHT; j++) {
				this.farm[i][j].advanceDay();
			}
		}
		this.gacha.resetBanner();

		// Check for losing conditions...
		if (this.player.getObjectCoins() < 500) {
			// Then checking if the farm is currently desolate.
			boolean farmEmpty = true;
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					if (farm[i][j].getState() == Tile.STATE_GROWING
							|| farm[i][j].getState() == Tile.STATE_HARVESTABLE) {
						farmEmpty = false;
						break;
					}
				}
			}
			// If both losing conditions are satisfied, then end the game.
			if (farmEmpty) {
				this.isLost = true;
			}
		}

		return eventText;
	}

	/**
	 * This method returns the current day number.
	 * 
	 * @return the current day number the player is at
	 */
	public int getDayNo() {
		return dayNo;
	}

	/**
	 * This method returns all tiles on the farm.
	 * 
	 * @return all the tiles on the farm currently
	 */
	public Tile[][] getTiles() {
		return farm;
	}

	/**
	 * This method returns the player as a Player object.
	 * 
	 * @return the Player object containing the player's stats and tools
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * This method returns the gacha object stored within the model.
	 * 
	 * @return the Gacha object containing all the functions and values associated
	 *         with the gacha system
	 */
	public Gacha getGacha() {
		return gacha;
	}

	/**
	 * This method returns whether or not the game is lost.
	 * 
	 * @return a boolean indicating whether or not the game is lost
	 */
	public boolean isLost() {
		return isLost;
	}
}