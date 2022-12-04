
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Controller {
    private Model model;
    private View view;
    private String playerName;
    private int selectedTool = 0;

    public static void main(String[] args) {
        // Instantiates the only Controller object instance. // TODO GAme over
        new Controller();
    }

    public Controller() {
        // Instantiates the only View object instance.
        this.view = new View(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When start button is presssed, get the player name and start the main game.
                playerName = view.getStartTextFieldInput();
                transitionToMainGame();
            }
        });

        // Set introductory window to be visible.
        this.view.setVisibleStart();
    }

    public void transitionToMainGame() {
        this.view.disposeStartJFrame();

        this.model = new Model(playerName);
        ActionListener toolListeners[] = new ActionListener[6];
        for (int i = 0; i < 6; i++) {
            final int j = i;
            toolListeners[i] = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedTool = j;
                    view.setSelectedTool(j);
                }
            };
        }
        ActionListener tileListeners[] = new ActionListener[50];
        for (int i = 0; i < 50; i++) {
            final int j = i;
            tileListeners[i] = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (selectedTool) {
                        case 0:
                            if (model.getTiles()[j % 10][j / 10].getState() == Tile.STATE_PLOWED) {
                                ActionListener plantingListeners[] = new ActionListener[9];
                                for (int k = 0; k < 9; k++) {
                                    final int l = k;
                                    plantingListeners[k] = new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent f) {
                                            if (model.getTiles()[j % 10][j / 10].plantSeed(l, model.getPlayer(),
                                                    model.getGacha().getConstPlant(l), j % 10, j / 10,
                                                    model.getTiles())) {
                                                view.disposePopupJFrame();
                                                view.addToTextPanel(
                                                        Plant.getPlantNameStatic(l) + " Planted at (" + j % 10
                                                                + ", " + j / 10 + ")!");
                                                view.updateStatPanel(model.getPlayer().getExp(),
                                                        model.getPlayer().getObjectCoins(),
                                                        model.getPlayer().getKusaCoins(),
                                                        model.getPlayer().getRegistrationName());
                                                ((JButton) e.getSource()).setText(Plant.getPlantNameStatic(l));
                                            } else {
                                                view.addToTextPanel("Planting Unsuccessful...");
                                            }
                                        }
                                    };
                                }
                                view.popupPlantingSeed(plantingListeners, j % 10, j / 10);
                            } else if (model.getTiles()[j % 10][j / 10].getState() == Tile.STATE_HARVESTABLE) {
                                view.addToTextPanel(model.getTiles()[j % 10][j / 10].getPlant().getProductsProduced()
                                        + " " + model.getTiles()[j % 10][j / 10].getPlant().getPlantName()
                                        + " Produced! Sold for "
                                        + model.getTiles()[j % 10][j / 10].getPlant().calculateFinalPrice(
                                                model.getPlayer(), model.getTiles()[j % 10][j / 10].getTimesWatered(),
                                                model.getTiles()[j % 10][j / 10].getTimesFertilized()) + " ObjectCoins!");
                                model.getTiles()[j % 10][j / 10].harvestTile(model.getPlayer());
                                ((JButton) e.getSource()).setText("Unplowed");
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());
                            } else if (model.getTiles()[j % 10][j / 10].getState() == Tile.STATE_GROWING) {
                                view.addToTextPanel("Watered " + model.getTiles()[j % 10][j / 10].getTimesWatered()
                                        + " Times | Fertilized " + model.getTiles()[j % 10][j / 10].getTimesFertilized()
                                        + " Times");
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());
                            }
                            break;

                        case 1:
                            if (model.getTiles()[j % 10][j / 10].plowTile(model.getPlayer())) {
                                view.addToTextPanel("Tile (" + j % 10 + ", " + j / 10 + ") Plowed!");
                                view.setToolDurability(Tool.TOOL_PLOW,
                                        model.getPlayer().getTool(Tool.TOOL_PLOW).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());
                                ((JButton) e.getSource()).setText("Plowed");

                            } else {
                                view.addToTextPanel("Plowing Unsuccessful...");
                            }
                            break;
                        case 2:
                            if (model.getTiles()[j % 10][j / 10].waterTile(model.getPlayer())) {
                                view.addToTextPanel("Tile (" + j % 10 + ", " + j / 10 + ") Watered!");
                                view.setToolDurability(Tool.TOOL_WATERING,
                                        model.getPlayer().getTool(Tool.TOOL_WATERING).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());

                            } else {
                                view.addToTextPanel("Watering Unsuccessful...");
                            }
                            break;
                        case 3:
                            if (model.getTiles()[j % 10][j / 10].fertilizeTile(model.getPlayer())) {
                                view.addToTextPanel("Tile (" + j % 10 + ", " + j / 10 + ") Fertilized!");
                                view.setToolDurability(Tool.TOOL_FERTILIZER,
                                        model.getPlayer().getTool(Tool.TOOL_FERTILIZER).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());

                            } else {
                                view.addToTextPanel("Fertilizing Unsuccessful...");
                            }
                            break;
                        case 4:
                            if (model.getTiles()[j % 10][j / 10].removeRock(model.getPlayer())) {
                                view.addToTextPanel("Stone on Tile (" + j % 10 + ", " + j / 10 + ") Removed!");
                                view.setToolDurability(Tool.TOOL_PICKAXE,
                                        model.getPlayer().getTool(Tool.TOOL_PICKAXE).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());

                            } else {
                                view.addToTextPanel("Stone Removal Unsuccessful...");
                            }
                            break;
                        case 5:
                            if (model.getTiles()[j % 10][j / 10].removePlant(model.getPlayer())) {
                                view.addToTextPanel("Shovelled Tile (" + j % 10 + ", " + j / 10 + ")!");
                                view.setToolDurability(Tool.TOOL_SHOVEL,
                                        model.getPlayer().getTool(Tool.TOOL_SHOVEL).getDurability());
                                view.updateStatPanel(model.getPlayer().getExp(),
                                        model.getPlayer().getObjectCoins(),
                                        model.getPlayer().getKusaCoins(),
                                        model.getPlayer().getRegistrationName());

                            } else {
                                view.addToTextPanel("Shovelling Unsuccessful...");
                            }
                            break;
                    }
                }
            };
        }
        ActionListener nextDayListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: update tiles for harvesting
                // TODO INTERnal docu
                model.advanceDay();
                view.addToTextPanel("Advancing to Day " + model.getDayNo() + "...");
                view.updateGachaBanner(model.getGacha().getBanner());
            }
        };
        ActionListener rollListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = model.getGacha().rollBanner(model.getPlayer());
                if (result != -1) {
                    if (result <= 7 && result >= 0) {
                        view.addToTextPanel("Rolled on " + Plant.getPlantNameStatic(result) + "!");
                    } else if (result >= 8 && result <= 12) {
                        result -= 8;
                        view.addToTextPanel("Rolled on " + Tool.getToolNameStatic(result) + "!");
                    }
                    view.updateStatPanel(model.getPlayer().getExp(),
                            model.getPlayer().getObjectCoins(),
                            model.getPlayer().getKusaCoins(),
                            model.getPlayer().getRegistrationName());
                } else {
                    view.addToTextPanel("Rolling Unsuccessful...");
                }
            }
        };
        ActionListener regListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getPlayer().upgradeRegistration()) {
                    view.updateStatPanel(model.getPlayer().getExp(),
                            model.getPlayer().getObjectCoins(),
                            model.getPlayer().getKusaCoins(),
                            model.getPlayer().getRegistrationName());
                    view.addToTextPanel("Successfully Promoted to " + model.getPlayer().getRegistrationName() + "!");
                } else {
                    view.addToTextPanel("Promotion Unsuccessful...");
                }
            }
        };
        this.view.startMainJFrame(playerName, toolListeners, tileListeners, nextDayListener, rollListener, regListener);
        this.view.addToTextPanel("Welcome to your humble farm, " + playerName + "!");

        for (int i = 0; i < 5; i++) {
            this.view.setToolDurability(i, this.model.getPlayer().getTool(i).getDurability());
        }
        this.view.setSelectedTool(0);

        this.view.updateGachaBanner(this.model.getGacha().getBanner());

        this.view.updateStatPanel(this.model.getPlayer().getExp(), this.model.getPlayer().getObjectCoins(),
                this.model.getPlayer().getKusaCoins(), this.model.getPlayer().getRegistrationName());

        this.view.setVisibleMain();
    }
    //TODO:Game Over
}
