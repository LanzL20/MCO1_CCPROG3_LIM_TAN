// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO1 

import java.util.Scanner;

public class Model {

	private int dayNo;
	private Tile farm[][];
	private Player player;
	private Gacha gacha;
	private boolean isLost;

	// TODO: Edit/Prototype
	private Scanner sc;

	public static void main(String[] args) {
		new Model(null);
	}

	public Model(String name) {
		this.dayNo = 1;
		this.farm = new Tile[10][5];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				farm[i][j] = new Tile();
			}
		}

		// TODO: Edit/Prototype
		this.sc = new Scanner(System.in);
		System.out.println("What is your name?");
		name = sc.nextLine();

		this.player = new Player(name);
		this.gacha = new Gacha();
		this.isLost = false;

		// TODO: Edit/Prototype
		Tile t = farm[0][0];
		t.addRock();

		while (!isLost) {

			this.displayStats();
			this.displayTile();
			this.displayChoices();

			char choice = sc.next().toUpperCase().charAt(0);

			while (choice != 'L') {
				switch (choice) {
					case 'A':
						if (t.removeRock(this.player)) {
							System.out.println("Successfully removed rock!");
						} else {
							System.out.println("Unsuccessfully removed rock...");
						}
						break;
					case 'B':
						if (t.plowTile(this.player)) {
							System.out.println("Successfully plowed tile!");
						} else {
							System.out.println("Unsuccessfully plowed tile...");
						}
						break;
					case 'C':
						System.out.println("Please choose type of seed to plant...");
						for (int i = 0; i < 8; i++) {
							System.out.println(
									"\t (" + i + ") " + Plant.getPlantNameStatic(i) + ": "
											+ Plant.getBuyPriceStatic(i) / 100.0 + " OC");
						}
						int plantId = sc.nextInt();
						if (t.plantSeed(plantId, this.player, this.gacha.getConstPlant(plantId))) {
							System.out.println("Successfully planted seed!");
						} else {
							System.out.println("Unsuccessfully planted seed!...");
						}
						break;
					case 'D':
						if (t.waterTile(this.player)) {
							System.out.println("Successfully watered tile!");
						} else {
							System.out.println("Unsuccessfully watered tile...");
						}
						break;
					case 'E':
						if (t.fertilizeTile(this.player)) {
							System.out.println("Successfully fertilized tile!");
						} else {
							System.out.println("Unsuccessfully fertilized tile...");
						}
						break;
					case 'F':
						int produce;
						String plantName;
						produce = t.getPlant().getProductsProduced();
						plantName = t.getPlant().getPlantName();
						if (t.harvestTile(this.player)) {
							System.out.println("Successfully harvested tile");
							System.out.println(produce + " " + plantName + " produced");
						} else {
							System.out.println("Unsuccessfully harvested tile...");
						}
						break;
					case 'G':
						if (t.removePlant(this.player)) {
							System.out.println("Successfully shovelled tile!");
						} else {
							System.out.println("Unsuccessfully shovelled tile...");
						}
						break;
					case 'H':
						System.out.println("Level Requirement: " + this.player.upgRegExpReq() / 200);
						System.out.println("ObjectCoins Requirement: " + this.player.upgRegFee() / 100.0 + " OC");
						if (this.player.upgradeRegistration()) {
							System.out.println("Successfully upgraded registration!");
						} else {
							System.out.println("Unsuccessfully upgraded registration...");
						}
						break;
					case 'I':
						System.out.println("Which tool to replace?");
						for (int i = 0; i < 5; i++) {
							System.out.println(
									"\t (" + i + ") " + this.player.getTool(i).getToolName() + ": " + "Replace Cost - "
											+ this.player.getTool(i).getReplaceCost() / 100.0);
						}
						int toolId = sc.nextInt();

						if (this.player.replaceTool(toolId, this.gacha.getConstTool(toolId))) {
							System.out.println("Successfully replaced tool!");
						} else {
							System.out.println("Unsuccessfully replaced tool...");
						}
						break;
					case 'J':
						if (this.gacha.rollBanner(player)) {
							System.out.println("Successfully rolled on banner!");
						} else {
							System.out.println("Unsuccessfully rolled on banner...");
						}
						break;
					case 'K':
						displayStats();
						break;
				}
				this.displayTile();
				this.displayChoices();

				choice = sc.next().toUpperCase().charAt(0);
			}
			this.advanceDay();
			System.out.println("Progressing to Day " + dayNo);

			// Check for losing by first checking if the player still has enough money...
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
		}

		System.out.println("You lost! Thank you for playing!");
	}

	public void advanceDay() {
		this.dayNo++;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				this.farm[i][j].advanceDay();
			}
		}
		this.gacha.resetBanner();
	}

	public Tile[][] getTiles() {
		return farm;
	}

	public int getDayNo() {
		return dayNo;
	}

	// TODO: Edit/Prototype
	private void displayStats() {
		System.out.println("\nGood day, Farmer " + this.player.getName() + "! It is now Day " + dayNo + "...\n");
		System.out.println("ObjectCoins: " + this.player.getObjectCoins() / 100.0);
		System.out.println("KusaCoins: " + this.player.getKusaCoins());
		System.out.println("Level (Exp): " + this.player.getLevel() + " (" + this.player.getExp() / 2.0 + ")");
		System.out.println("Regsitration: " + this.player.getRegistrationName());

		System.out.println("\nToolbar...");
		for (int i = 0; i < 5; i++) {
			System.out.println("\t" + this.player.getTool(i).getToolName() + ": Usage Cost - "
					+ this.player.getTool(i).getUsageCost() / 100.0 + " OC | Durability - "
					+ this.player.getTool(i).getDurability()
					+ " | Const - " + this.player.getTool(i).getConste());
		}

		System.out.println("\nConstellations...");
		for (int i = 0; i < 8; i++) {
			System.out.println("\t" + Plant.getPlantNameStatic(i) + ": Const - " + this.gacha.getConstPlant(i));
		}
		for (int i = 0; i < 5; i++) {
			System.out.println("\t" + this.player.getTool(i).getToolName() + ": Const - " + this.gacha.getConstTool(i));
		}

		System.out.println("\nBanner...");
		int[] banner = gacha.getBanner();
		for (int i = 0; i < 4; i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			if (banner[i] < 8) {
				System.out.print(Plant.getPlantNameStatic(banner[i]));
			} else {
				System.out.print(this.player.getTool(banner[i] - 8).getToolName());
			}
		}
	}

	// TODO: Edit/Prototype
	private void displayTile() {
		System.out.println("\n");
		// For the prototype, we're going to use a single tile.
		Tile t = farm[0][0];

		switch (t.getState()) {
			case Tile.STATE_ROCK:
				System.out.println("Tile is now covered by rock.");
				break;
			case Tile.STATE_UNPLOWED:
				System.out.println("Tile is now unplowed.");
				break;
			case Tile.STATE_PLOWED:
				System.out.println("Tile is now plowed but unplanted.");
				break;
			case Tile.STATE_GROWING:
				System.out.println("Tile has a " + t.getPlant().getPlantName() + " growing on it.");
				System.out.println("It has been watered " + t.getTimesWatered() + " times.");
				System.out.println("It has been fertilized " + t.getTimesFertilized() + " times.");
				break;
			case Tile.STATE_HARVESTABLE:
				System.out.println("The " + t.getPlant().getPlantName() + " is ready for harvesting.");
				break;
			case Tile.STATE_WITHERED:
				System.out.println("The " + t.getPlant().getPlantName() + " has withered.");
				break;
		}
	}

	// TODO: Edit/Prototype
	private void displayChoices() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("\t(A) Remove Rock using the Pickaxe");
		System.out.println("\t(B) Plow a Tile");
		System.out.println("\t(C) Plant a Seed");
		System.out.println("\t(D) Water the Plant");
		System.out.println("\t(E) Fertilize the Plant");
		System.out.println("\t(F) Harvest the Plant");
		System.out.println("\t(G) Use Shovel");
		System.out.println("\t(H) Upgrade Registration");
		System.out.println("\t(I) Replace Tools");
		System.out.println("\t(J) Roll on Banner");
		System.out.println("\t(K) Redisplay Stats");
		System.out.println("\t(L) Advance Day");
	}
}
