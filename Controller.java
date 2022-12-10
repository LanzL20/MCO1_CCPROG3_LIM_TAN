
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private Model model;
    private View view;
    private String playerName;
    private int selectedTool = 0;

    public static void main(String[] args) {
        // Instantiates the only Controller object instance.
        new Controller();
    }

    public Controller() {
        // Instantiates the only View object instance.
        this.view = new View(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When start button is presssed, get the player name and start the main game.
                playerName = view.getStartTextFieldInput();
                if (view.getFileTextFieldInput().isEmpty()) {
                    generateRockscatterFile();
                }
                transitionToMainGame();
            }
        }, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When generate button is presssed, output a rockscatter file.
                generateRockscatterFile();
            }
        });

        // Set introductory window to be visible.
        this.view.setVisibleStart();
    }

    public void generateRockscatterFile() {
        int counter = 0;
        int temp;
        boolean found = false;

        // Generate between 10 and 30 rocks.
        int total = (int) Math.floor(Math.random() * 21) + 10;
        int rocks[] = new int[total];

        // Generate rockscatter spaces.
        String fileName;
        while (counter != total) {
            found = false;
            temp = (int) Math.floor(Math.random() * 50);
            for (int j = 0; j < counter; j++) {
                if (temp == rocks[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                rocks[counter] = temp;
                counter++;
            }
        }

        try {
            // Have the filename of the rockscatter to be the current nanoTime.
            fileName = System.nanoTime() + ".txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                // Then, generate the output rockscatter file.
                FileWriter myWriter = new FileWriter(fileName);
                for (int i = 0; i < 50; i++) {
                    boolean founder = false;
                    for (int j = 0; j < total; j++) {
                        if (i == rocks[j]) {
                            founder = true;
                            break;
                        }
                    }
                    if (founder)
                        myWriter.write("x");
                    else
                        myWriter.write("o");
                    if ((i + 1) % 10 == 0) {
                        myWriter.write("\n");
                    }
                }
                myWriter.close();
                view.setFileTextField(fileName);
            } else {
                // If file already exists, abort.
                System.out.println("File already exists. Please try again.");
            }
        } catch (IOException ee) {
            // If IO Exception occurs, abort.
            System.out.println("An error occurred. Please try again.");
            ee.printStackTrace();
        }
    }

    public void transitionToMainGame() {
        // First off, dispose the starting pop-up frame.
        this.view.disposeStartJFrame();

        // Then, create the only Model object instance.
        this.model = new Model(playerName);

        // Create actionListeners for the toolbar buttons.
        ActionListener toolListeners[] = new ActionListener[6];
        for (int i = 0; i < 6; i++) {
            final int j = i;
            toolListeners[i] = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Set selected tool based on tool buttons.
                    selectedTool = j;
                    view.setSelectedTool(j);
                }
            };
        }

        // Create actionListeners for the tile buttons.
        ActionListener tileListeners[] = new ActionListener[50];
        for (int i = 0; i < 50; i++) {
            final int j = i;
            tileListeners[i] = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (selectedTool) {
                        // If selected tool is hand...
                        case 0:
                            // If the current tile is plowed...
                            if (model.getTiles()[j % 10][j / 10].getState() == Tile.STATE_PLOWED) {
                                // Create actionListeners for the planting buttons...
                                ActionListener plantingListeners[] = new ActionListener[9];
                                for (int k = 0; k < 9; k++) {
                                    final int l = k;
                                    plantingListeners[k] = new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent f) {
                                            // Try to plant the seed if possible.
                                            if (model.getTiles()[j % 10][j / 10].plantSeed(l, model.getPlayer(),
                                                    model.getGacha().getConstPlant(l), j % 10, j / 10,
                                                    model.getTiles())) {
                                                // If successful, dispose the pop-up frame...
                                                view.disposePopupJFrame();

                                                // And update all view panels.
                                                view.addToTextPanel(
                                                        Plant.getPlantNameStatic(l) + " Planted at (" + j % 10
                                                                + ", " + j / 10 + ") with a Constellation of "
                                                                + model.getGacha().getConstPlant(l) + "!");
                                                view.updateStatPanel(model.getPlayer().getExp(),
                                                        model.getPlayer().getObjectCoins(),
                                                        model.getPlayer().getKusaCoins(),
                                                        model.getPlayer().getRegistrationName(),
                                                        model.getPlayer().getRegistrationImage());
                                                view.updateTile(model.getTiles()[j % 10][j / 10], j % 10, j / 10);
                                            } else {
                                                // If unsuccessful, display fail message.
                                                view.addToTextPanel("Planting Unsuccessful...");
                                            }
                                        }
                                    };
                                }
                                // Initiatiate the pop-up frame.
                                view.popupPlantingSeed(plantingListeners, j % 10, j / 10);
                                // If the current tile is harvestable...
                            } else if (model.getTiles()[j % 10][j / 10].getState() == Tile.STATE_HARVESTABLE) {
                                // Harvest the tile and display amount sold.
                                view.addToTextPanel(model.getTiles()[j % 10][j / 10].getPlant().getProductsProduced()
                                        + " " + model.getTiles()[j % 10][j / 10].getPlant().getPlantName()
                                        + " Produced! Sold for "
                                        + ((double) model.getTiles()[j % 10][j / 10].getPlant().calculateFinalPrice(
                                                model.getPlayer(), model.getTiles()[j % 10][j / 10].getTimesWatered(),
                                                model.getTiles()[j % 10][j / 10].getTimesFertilized()) / 100)
                                        + " ObjectCoins!");
                                model.getTiles()[j % 10][j / 10].harvestTile(model.getPlayer());
                                // And update all view panels.
                                view.updateTile(model.getTiles()[j % 10][j / 10], j % 10, j / 10);
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());
                                view.updateTile(model.getTiles()[j % 10][j / 10], j % 10, j / 10);
                            }
                            // If the current tile is growing...
                            else if (model.getTiles()[j % 10][j / 10].getState() == Tile.STATE_GROWING) {
                                // Display watering and fertilizing info.
                                view.addToTextPanel("Watered " + model.getTiles()[j % 10][j / 10].getTimesWatered()
                                        + " Times (Min: "
                                        + model.getTiles()[j % 10][j / 10].getPlant().getRequiredWater()
                                        + " / Max: "
                                        + ((int) model.getTiles()[j % 10][j / 10].getPlant().getBonusWater()
                                                + (int) model.getPlayer().getWaterBonusInc())
                                        + ") | Fertilized " + model.getTiles()[j % 10][j / 10].getTimesFertilized()
                                        + " Times (Min: "
                                        + model.getTiles()[j % 10][j / 10].getPlant().getRequiredFertilizer()
                                        + " / Max: "
                                        + ((int) model.getTiles()[j % 10][j / 10].getPlant().getBonusFertilizer()
                                                + (int) model.getPlayer().getFertilizerBonusInc())
                                        + ")");
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());
                            }
                            break;

                        // If selected tool is plow...
                        case 1:
                            // Try to plow the tile.
                            if (model.getTiles()[j % 10][j / 10].plowTile(model.getPlayer())) {
                                // If successful, then update view panels.
                                view.addToTextPanel("Tile (" + j % 10 + ", " + j / 10 + ") Plowed!");
                                view.setToolDurability(Tool.TOOL_PLOW,
                                        model.getPlayer().getTool(Tool.TOOL_PLOW).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());

                                view.updateTile(model.getTiles()[j % 10][j / 10], j % 10, j / 10);
                            } else {
                                // If unsuccessful, display fail message.
                                view.addToTextPanel("Plowing Unsuccessful...");
                            }
                            break;

                        // If selected tool is watering can...
                        case 2:
                            // Try to water the tile.
                            if (model.getTiles()[j % 10][j / 10].waterTile(model.getPlayer())) {
                                // If successful, then update view panels.
                                view.addToTextPanel("Tile (" + j % 10 + ", " + j / 10 + ") Watered!");
                                view.setToolDurability(Tool.TOOL_WATERING,
                                        model.getPlayer().getTool(Tool.TOOL_WATERING).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());
                            } else {
                                // If unsuccessful, display fail message.
                                view.addToTextPanel("Watering Unsuccessful...");
                            }
                            break;

                        // If selected tool is fertilizer...
                        case 3:
                            // Try to fertilize the tile.
                            if (model.getTiles()[j % 10][j / 10].fertilizeTile(model.getPlayer())) {
                                // If successful, then update view panels.
                                view.addToTextPanel("Tile (" + j % 10 + ", " + j / 10 + ") Fertilized!");
                                view.setToolDurability(Tool.TOOL_FERTILIZER,
                                        model.getPlayer().getTool(Tool.TOOL_FERTILIZER).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());
                            } else {
                                // If unsuccessful, display fail message.
                                view.addToTextPanel("Fertilizing Unsuccessful...");
                            }
                            break;

                        // If selected tool is pickaxe...
                        case 4:
                            // Try to pickaxe the tile.
                            if (model.getTiles()[j % 10][j / 10].removeRock(model.getPlayer())) {
                                // If successful, then update view panels.
                                view.addToTextPanel("Stone on Tile (" + j % 10 + ", " + j / 10 + ") Removed!");
                                view.setToolDurability(Tool.TOOL_PICKAXE,
                                        model.getPlayer().getTool(Tool.TOOL_PICKAXE).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());
                                view.updateTile(model.getTiles()[j % 10][j / 10], j % 10, j / 10);

                            } else {
                                // If unsuccessful, display fail message.
                                view.addToTextPanel("Stone Removal Unsuccessful...");
                            }
                            break;

                        // If selected tool is shovel...
                        case 5:
                            // Try to shovel the tile.
                            if (model.getTiles()[j % 10][j / 10].removePlant(model.getPlayer())) {
                                // If successful, then update view panels.
                                view.addToTextPanel("Shovelled Tile (" + j % 10 + ", " + j / 10 + ")!");
                                view.setToolDurability(Tool.TOOL_SHOVEL,
                                        model.getPlayer().getTool(Tool.TOOL_SHOVEL).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName(),
                                        model.getPlayer().getRegistrationImage());
                                view.updateTile(model.getTiles()[j % 10][j / 10], j % 10, j / 10);
                            } else {
                                // If unsuccessful, display fail message.
                                view.addToTextPanel("Shovelling Unsuccessful...");
                            }
                            break;
                    }
                }
            };
        }
        // Create actionListeners for the next day button.
        ActionListener nextDayListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simply call model's nextDay().
                String event = model.advanceDay();

                // Then update view panels.
                view.addToTextPanel("Advancing to Day " + model.getDayNo() + "...");
                if (event != null)
                    view.addToTextPanel(event);
                view.updateGachaBanner(model.getGacha().getBanner());
                view.updateStatPanel(model.getPlayer().getExp(),
                        model.getPlayer().getObjectCoins(),
                        model.getPlayer().getKusaCoins(),
                        model.getPlayer().getRegistrationName(),
                        model.getPlayer().getRegistrationImage());
                view.updateNextDayLabel(model.getDayNo());
                for (int i = 0; i < 5; i++) {
                    view.setToolDurability(i, model.getPlayer().getTool(i).getDurability());
                }
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 5; j++) {
                        view.updateTile(model.getTiles()[i][j], i, j);
                    }
                }

                // Lastly, check if is already lost.
                if (model.isLost()) {
                    view.popupEndgame();
                }
            }
        };

        // Create actionListeners for the roll button.
        ActionListener rollListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Try to roll on the banner.
                int result = model.getGacha().rollBanner(model.getPlayer());
                if (result != -1) {
                    // If successful, then update view panels.
                    if (result <= 8 && result >= 0) {
                        view.addToTextPanel("Rolled on " + Plant.getPlantNameStatic(result)
                                + "! Constellation raised to " + model.getGacha().getConstPlant(result) + "!");
                    } else if (result >= 9 && result <= 13) {
                        result -= 9;
                        view.addToTextPanel("Rolled on " + Tool.getToolNameStatic(result) + "! Constellation raised to "
                                + model.getGacha().getConstTool(result) + "!");
                    }
                    view.updateStatPanel(model.getPlayer().getExp(),
                            model.getPlayer().getObjectCoins(),
                            model.getPlayer().getKusaCoins(),
                            model.getPlayer().getRegistrationName(),
                            model.getPlayer().getRegistrationImage());
                } else {
                    // If unsuccessful, display fail message.
                    view.addToTextPanel("Rolling Unsuccessful...");
                }
            }
        };

        // Create actionListeners for the registration button.
        ActionListener regListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Try to upgrade the player's registration.
                if (model.getPlayer().upgradeRegistration()) {
                    // If successful, then update view panels.
                    view.updateStatPanel(model.getPlayer().getExp(),
                            model.getPlayer().getObjectCoins(),
                            model.getPlayer().getKusaCoins(),
                            model.getPlayer().getRegistrationName(),
                            model.getPlayer().getRegistrationImage());
                    view.addToTextPanel("Successfully Promoted to " + model.getPlayer().getRegistrationName() + "!");
                } else {
                    // If unsuccessful, display fail message.
                    view.addToTextPanel("Promotion Unsuccessful...");
                }
            }
        };

        // Create actionListeners for the repair button.
        ActionListener repairListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if selected tool is not hand.
                if (selectedTool > 0) {
                    // Try to repair the selected tool.
                    if (model.getPlayer().repairTool(selectedTool - 1,
                            model.getGacha().getConstTool(selectedTool - 1))) {
                        // If successful, then update view panels.
                        view.setToolDurability(selectedTool - 1,
                                model.getPlayer().getTool(selectedTool - 1).getDurability());
                        view.updateStatPanel(model.getPlayer().getExp(),
                                model.getPlayer().getObjectCoins(),
                                model.getPlayer().getKusaCoins(),
                                model.getPlayer().getRegistrationName(),
                                model.getPlayer().getRegistrationImage());
                        view.addToTextPanel("Successfully Repaired " + Tool.getToolNameStatic(selectedTool - 1) + "!");
                    } else {
                        // If unsuccessful, display fail message.
                        view.addToTextPanel("Repair Unsuccessful...");
                    }
                }
            }
        };

        // Start the main frame for the game.
        this.view.startMainJFrame(playerName, model.getTiles(), toolListeners, tileListeners, nextDayListener,
                rollListener, regListener, repairListener);
        this.view.addToTextPanel("Welcome to your humble farm, " + playerName + "!");

        // Add rocks based on file input.
        try {
            File rockFile = new File(this.view.getFileTextFieldInput());
            Scanner sc = new Scanner(rockFile);
            for (int j = 0; j < 5; j++) {
                String line = sc.nextLine();
                for (int i = 0; i < 10; i++) {
                    if (line.charAt(i) == 'x') {
                        this.model.getTiles()[i][j].addRock();
                        this.view.updateTile(this.model.getTiles()[i][j], i, j);
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Rock Input Experienced Error");
        }

        // Update all view panels before setting to visible.
        for (int i = 0; i < 5; i++) {
            this.view.setToolDurability(i, this.model.getPlayer().getTool(i).getDurability());
        }
        this.view.setSelectedTool(0);
        this.view.updateGachaBanner(this.model.getGacha().getBanner());
        view.updateStatPanel(model.getPlayer().getExp(),
                model.getPlayer().getObjectCoins(),
                model.getPlayer().getKusaCoins(),
                model.getPlayer().getRegistrationName(),
                model.getPlayer().getRegistrationImage());
        this.view.updateNextDayLabel(this.model.getDayNo());

        // Set main game frame to be visible.
        this.view.setVisibleMain();
    }
}
