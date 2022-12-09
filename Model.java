import java.util.ArrayList;
import java.util.Random;

// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO1 

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
	private ArrayList<Event> events[];

	public static final int FARM_WIDTH = 10;
	public static final int FARM_HEIGHT = 5;

	/**
	 * This constructor creates a Model object, initializing the farm, the player
	 * (with a specified name), and the gacha handler.
	 * 
	 * @param name the name of the player to be created (not currently used)
	 */
	@SuppressWarnings("unchecked")
	public Model(String name) {
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
	// TODO: add events and calamities TODO update UML

	/**
	 * This method simulates the action of progressing a day, appropriately updating
	 * all tiles on the farm and reseting the gacha banner as intended. TODO
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
	 * @return all the tiles on the farm currently.
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

	public Gacha getGacha() {
		return gacha;
	}

	public boolean isLost() {
		return isLost;
	}
}
/*
 * Code/Prototype
 * 
 * private Scanner sc;
 * this.sc = new Scanner(System.in);
 * System.out.println("What is your name?");
 * name = sc.nextLine();
 * 
 * 
 * Tile t = farm[0][0];
 * t.addRock();
 * 
 * // Check for losing by first checking if the player still has enough money...
 * if (this.player.getObjectCoins() < 500) {
 * // Then checking if the farm is currently desolate.
 * boolean farmEmpty = true;
 * for (int i = 0; i < 10; i++) {
 * for (int j = 0; j < 5; j++) {
 * if (farm[i][j].getState() == Tile.STATE_GROWING
 * || farm[i][j].getState() == Tile.STATE_HARVESTABLE) {
 * farmEmpty = false;
 * break;
 * }
 * }
 * }
 * // If both losing conditions are satisfied, then end the game.
 * if (farmEmpty) {
 * this.isLost = true;
 * }
 * }
 * }
 * 
 * System.out.println("You lost! Thank you for playing!");
 * 
 * while (!isLost) {
 * 
 * this.displayStats();
 * this.displayTile();
 * this.displayChoices();
 * 
 * char choice = sc.next().toUpperCase().charAt(0);
 * 
 * while (choice != 'L') {
 * switch (choice) {
 * case 'A':
 * if (t.removeRock(this.player)) {
 * System.out.println("Successfully removed rock!");
 * } else {
 * System.out.println("Unsuccessfully removed rock...");
 * }
 * break;
 * case 'B':
 * if (t.plowTile(this.player)) {
 * System.out.println("Successfully plowed tile!");
 * } else {
 * System.out.println("Unsuccessfully plowed tile...");
 * }
 * break;
 * case 'C':
 * System.out.println("Please choose type of seed to plant...");
 * for (int i = 0; i < 8; i++) {
 * System.out.println(
 * "\t (" + i + ") " + Plant.getPlantNameStatic(i) + ": "
 * + Plant.getBuyPriceStatic(i) / 100.0 + " OC");
 * }
 * int plantId = sc.nextInt();
 * if (t.plantSeed(plantId, this.player, this.gacha.getConstPlant(plantId))) {
 * System.out.println("Successfully planted seed!");
 * } else {
 * System.out.println("Unsuccessfully planted seed!...");
 * }
 * break;
 * case 'D':
 * if (t.waterTile(this.player)) {
 * System.out.println("Successfully watered tile!");
 * } else {
 * System.out.println("Unsuccessfully watered tile...");
 * }
 * break;
 * case 'E':
 * if (t.fertilizeTile(this.player)) {
 * System.out.println("Successfully fertilized tile!");
 * } else {
 * System.out.println("Unsuccessfully fertilized tile...");
 * }
 * break;
 * case 'F':
 * int produce;
 * String plantName;
 * produce = t.getPlant().getProductsProduced();
 * plantName = t.getPlant().getPlantName();
 * if (t.harvestTile(this.player)) {
 * System.out.println("Successfully harvested tile");
 * System.out.println(produce + " " + plantName + " produced");
 * } else {
 * System.out.println("Unsuccessfully harvested tile...");
 * }
 * break;
 * case 'G':
 * if (t.removePlant(this.player)) {
 * System.out.println("Successfully shovelled tile!");
 * } else {
 * System.out.println("Unsuccessfully shovelled tile...");
 * }
 * break;
 * case 'H':
 * System.out.println("Level Requirement: " + this.player.upgRegExpReq() / 200);
 * System.out.println("ObjectCoins Requirement: " + this.player.upgRegFee() /
 * 100.0 + " OC");
 * if (this.player.upgradeRegistration()) {
 * System.out.println("Successfully upgraded registration!");
 * } else {
 * System.out.println("Unsuccessfully upgraded registration...");
 * }
 * break;
 * case 'I':
 * System.out.println("Which tool to replace?");
 * for (int i = 0; i < 5; i++) {
 * System.out.println(
 * "\t (" + i + ") " + this.player.getTool(i).getToolName() + ": " +
 * "Replace Cost - "
 * + this.player.getTool(i).getReplaceCost() / 100.0);
 * }
 * int toolId = sc.nextInt();
 * 
 * if (this.player.replaceTool(toolId, this.gacha.getConstTool(toolId))) {
 * System.out.println("Successfully replaced tool!");
 * } else {
 * System.out.println("Unsuccessfully replaced tool...");
 * }
 * break;
 * case 'J':
 * if (this.gacha.rollBanner(player)) {
 * System.out.println("Successfully rolled on banner!");
 * } else {
 * System.out.println("Unsuccessfully rolled on banner...");
 * }
 * break;
 * case 'K':
 * displayStats();
 * break;
 * }
 * this.displayTile();
 * this.displayChoices();
 * 
 * choice = sc.next().toUpperCase().charAt(0);
 * }
 * this.advanceDay();
 * System.out.println("Progressing to Day " + dayNo);
 * 
 * private void displayStats() {
 * System.out.println("\nGood day, Farmer " + this.player.getName() +
 * "! It is now Day " + dayNo + "...\n");
 * System.out.println("ObjectCoins: " + this.player.getObjectCoins() / 100.0);
 * System.out.println("KusaCoins: " + this.player.getKusaCoins());
 * System.out.println("Level (Exp): " + this.player.getLevel() + " (" +
 * this.player.getExp() / 2.0 + ")");
 * System.out.println("Regsitration: " + this.player.getRegistrationName());
 * 
 * System.out.println("\nToolbar...");
 * for (int i = 0; i < 5; i++) {
 * System.out.println("\t" + this.player.getTool(i).getToolName() +
 * ": Usage Cost - "
 * + this.player.getTool(i).getUsageCost() / 100.0 + " OC | Durability - "
 * + this.player.getTool(i).getDurability()
 * + " | Const - " + this.player.getTool(i).getConste());
 * }
 * 
 * System.out.println("\nConstellations...");
 * for (int i = 0; i < 8; i++) {
 * System.out.println("\t" + Plant.getPlantNameStatic(i) + ": Const - " +
 * this.gacha.getConstPlant(i));
 * }
 * for (int i = 0; i < 5; i++) {
 * System.out.println("\t" + this.player.getTool(i).getToolName() + ": Const - "
 * + this.gacha.getConstTool(i));
 * }
 * 
 * System.out.println("\nBanner...");
 * int[] banner = gacha.getBanner();
 * for (int i = 0; i < 4; i++) {
 * if (i != 0) {
 * System.out.print(", ");
 * }
 * if (banner[i] < 8) {
 * System.out.print(Plant.getPlantNameStatic(banner[i]));
 * } else {
 * System.out.print(this.player.getTool(banner[i] - 8).getToolName());
 * }
 * }
 * }
 * 
 * private void displayTile() {
 * System.out.println("\n");
 * // For the prototype, we're going to use a single tile.
 * Tile t = farm[0][0];
 * 
 * switch (t.getState()) {
 * case Tile.STATE_ROCK:
 * System.out.println("Tile is now covered by rock.");
 * break;
 * case Tile.STATE_UNPLOWED:
 * System.out.println("Tile is now unplowed.");
 * break;
 * case Tile.STATE_PLOWED:
 * System.out.println("Tile is now plowed but unplanted.");
 * break;
 * case Tile.STATE_GROWING:
 * System.out.println("Tile has a " + t.getPlant().getPlantName() +
 * " growing on it.");
 * System.out.println("It has been watered " + t.getTimesWatered() + " times.");
 * System.out.println("It has been fertilized " + t.getTimesFertilized() +
 * " times.");
 * break;
 * case Tile.STATE_HARVESTABLE:
 * System.out.println("The " + t.getPlant().getPlantName() +
 * " is ready for harvesting.");
 * break;
 * case Tile.STATE_WITHERED:
 * System.out.println("The " + t.getPlant().getPlantName() + " has withered.");
 * break;
 * }
 * }
 * 
 * private void displayChoices() {
 * System.out.println("\nWhat would you like to do?");
 * System.out.println("\t(A) Remove Rock using the Pickaxe");
 * System.out.println("\t(B) Plow a Tile");
 * System.out.println("\t(C) Plant a Seed");
 * System.out.println("\t(D) Water the Plant");
 * System.out.println("\t(E) Fertilize the Plant");
 * System.out.println("\t(F) Harvest the Plant");
 * System.out.println("\t(G) Use Shovel");
 * System.out.println("\t(H) Upgrade Registration");
 * System.out.println("\t(I) Replace Tools");
 * System.out.println("\t(J) Roll on Banner");
 * System.out.println("\t(K) Redisplay Stats");
 * System.out.println("\t(L) Advance Day");
 * }
 */