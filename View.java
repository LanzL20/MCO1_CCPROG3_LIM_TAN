
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.event.ActionEvent;

public class View {

    private JFrame startFrame;
    private JLabel startText;
    private JTextField startTextField;
    private JButton startButton;
    private JTextField fileTextField;
    private JButton fileButton;
    private JLabel fileLabel;

    private JPanel toolPanel;
    private JButton toolButtons[];
    private JButton repairButton;
    private JLabel nextDayJLabel;

    private JPanel statPanel;
    private JLabel farmerLabel;
    private JLabel expLabel;
    private JLabel objectCoinsLabel;
    private JLabel kusaCoinsLabel;
    private JLabel registrationLabel;
    private JLabel registrationLevelLabel;
    private JButton upgradeRegistrationButton;

    private JPanel gachaPanel;
    private JButton rollButton;
    private JLabel bannerLabels[];
    private int scrollOffset = 0;

    private JPanel textPanel;
    private JButton nextDayButton;
    private ArrayList<JLabel> textLabels;
    private JButton upScrollButton;
    private JButton downScrollButton;

    private JPanel farmPanel;
    private JButton tileButtons[];

    private JFrame mainFrame;

    private JFrame popupFrame;

    public View(ActionListener startListener, ActionListener fileListener) {
        // First, start a JFrame for the introductory window.
        this.startFrame = new JFrame();
        this.startFrame.setTitle("Plants VS Programmers");
        this.startFrame.setSize(400, 170);
        this.startFrame.setLayout(new FlowLayout());
        this.startFrame.setResizable(false);
        this.startFrame.setLocationRelativeTo(null);
        this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the text for the introductory window.
        this.startText = new JLabel("Howdy there, farmer! What's ya name, buddy ol' pal?");
        this.startFrame.add(startText);

        // Add the textfield for the introductory window to allow the player to input
        // their name.
        this.startTextField = new JTextField();
        this.startTextField.setPreferredSize(new Dimension(100, 30));
        this.startFrame.add(startTextField);

        // Add the button for the introductory window to allow the player to start the
        // game.
        this.startButton = new JButton();
        this.startButton.setText("Let's start!");
        this.startButton.setPreferredSize(new Dimension(150, 30));
        this.startButton.addActionListener(startListener);
        this.startButton.setFocusable(false);
        this.startFrame.add(startButton);

        this.fileLabel = new JLabel("Please input rockscatter filename...");
        this.startFrame.add(fileLabel);

        this.fileTextField = new JTextField();
        this.fileTextField.setPreferredSize(new Dimension(150, 30));
        this.startFrame.add(fileTextField);

        this.fileButton = new JButton();
        this.fileButton.setText("Generate Random Rockscatter");
        this.fileButton.addActionListener(fileListener);
        this.fileButton.setFocusable(false);
        this.startFrame.add(fileButton);
    }

    public void setVisibleStart() {
        // Simply set the introductory window to be visible.
        this.startFrame.setVisible(true);
    }

    public String getStartTextFieldInput() {
        return this.startTextField.getText();
    }

    public void setFileTextField(String fileName) {
        this.fileTextField.setText(fileName);
    }

    public String getFileTextFieldInput() {
        return this.fileTextField.getText();
    }

    public void disposeStartJFrame() {
        // Simply dispose of the introductory window.
        this.startFrame.dispose();
    }

    public void startMainJFrame(String playerName, Tile[][] farm, ActionListener toolListeners[],
            ActionListener tileListeners[],
            ActionListener nextDayListener, ActionListener rollListener, ActionListener regListener,
            ActionListener repairListener) {
        this.mainFrame = new JFrame();
        this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainFrame.setUndecorated(true);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.toolPanel = new JPanel();
        this.toolPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 5));
        this.toolPanel.setBackground(new Color(83, 41, 21));
        this.toolPanel.setPreferredSize(new Dimension(100, 100));
        this.toolButtons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            this.toolButtons[i] = new JButton();
            this.toolButtons[i].setPreferredSize(new Dimension(90, 90));
            this.toolButtons[i].setFocusable(false);
            this.toolPanel.add(toolButtons[i]);
            this.toolButtons[i].setHorizontalTextPosition(JButton.CENTER);
            this.toolButtons[i].setVerticalTextPosition(JButton.BOTTOM);
            this.toolButtons[i].setForeground(Color.WHITE);
            this.toolButtons[i].setBackground(new Color(83, 41, 21));
            this.toolButtons[i].setBorderPainted(false);
            if (i != 0) {
                this.toolButtons[i].setIcon(new ImageIcon(
                        new ImageIcon(Tool.getToolImageStatic(i - 1)).getImage().getScaledInstance(70, 70,
                                Image.SCALE_DEFAULT)));
            } else {
                this.toolButtons[i].setIcon(new ImageIcon(
                        new ImageIcon("Tool_Hand.png").getImage().getScaledInstance(60, 60,
                                Image.SCALE_DEFAULT)));
                this.toolButtons[i].setText(" ");
            }
            this.toolButtons[i].addActionListener(toolListeners[i]);
        }
        this.repairButton = new JButton();
        this.repairButton.setPreferredSize(new Dimension(150, 70));
        this.repairButton.setBackground(new Color(181, 101, 29));
        this.repairButton.addActionListener(repairListener);
        this.repairButton.setFocusable(false);
        this.repairButton.setIcon(new ImageIcon(new ImageIcon("Tool_Hammer.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        this.repairButton.setBorderPainted(false);
        this.toolPanel.add(repairButton);

        this.nextDayJLabel = new JLabel();
        this.nextDayJLabel.setForeground(Color.white);
        this.nextDayJLabel.setFont(new Font("Gotham", Font.PLAIN, 50));
        this.toolPanel.add(nextDayJLabel);

        this.statPanel = new JPanel();
        this.statPanel.setBackground(Color.blue);
        this.statPanel.setPreferredSize(new Dimension(125, 100));
        this.farmerLabel = new JLabel("Farmer " + playerName);
        this.expLabel = new JLabel();
        this.objectCoinsLabel = new JLabel();
        this.kusaCoinsLabel = new JLabel();
        this.registrationLabel = new JLabel();
        this.registrationLevelLabel = new JLabel();
        this.expLabel.setIcon(new ImageIcon(
                new ImageIcon("Stats-EXP.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        this.expLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.expLabel.setHorizontalTextPosition(JLabel.CENTER);

        this.registrationLabel.setIcon(new ImageIcon(
                new ImageIcon("Stats-Reg.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        this.registrationLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.registrationLabel.setHorizontalTextPosition(JLabel.CENTER);

        this.kusaCoinsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.kusaCoinsLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.kusaCoinsLabel.setIcon(new ImageIcon(new ImageIcon("Stats_KusaCoin.png").getImage()
                .getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        this.objectCoinsLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.objectCoinsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.objectCoinsLabel.setIcon(new ImageIcon(new ImageIcon("Stats_ObjectCoin.png").getImage()
                .getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        this.farmerLabel.setForeground(Color.white);
        this.farmerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.farmerLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.farmerLabel.setIcon(new ImageIcon(new ImageIcon("Stats_Farmer.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        this.registrationLevelLabel.setForeground(Color.white);
        this.expLabel.setForeground(Color.white);
        this.objectCoinsLabel.setForeground(Color.white);
        this.kusaCoinsLabel.setForeground(Color.white);
        this.registrationLabel.setForeground(Color.white);
        this.statPanel.add(farmerLabel);
        this.statPanel.add(expLabel);
        this.statPanel.add(objectCoinsLabel);
        this.statPanel.add(kusaCoinsLabel);
        this.statPanel.add(registrationLabel);
        this.statPanel.add(registrationLevelLabel);

        this.upgradeRegistrationButton = new JButton();
        this.upgradeRegistrationButton.setText("Promote!");
        this.upgradeRegistrationButton.setFocusable(false);
        this.upgradeRegistrationButton.setPreferredSize(new Dimension(90, 90));
        this.upgradeRegistrationButton.addActionListener(regListener);
        this.upgradeRegistrationButton.setFocusable(false);
        this.upgradeRegistrationButton.setIcon(new ImageIcon(new ImageIcon("Stats_Promotion.png").getImage()
                .getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        this.upgradeRegistrationButton.setHorizontalTextPosition(JButton.CENTER);
        this.upgradeRegistrationButton.setVerticalTextPosition(JButton.BOTTOM);
        this.statPanel.add(upgradeRegistrationButton);

        this.gachaPanel = new JPanel();
        this.gachaPanel.setBackground(Color.red);
        this.gachaPanel.setPreferredSize(new Dimension(125, 100));
        this.gachaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        this.rollButton = new JButton("Roll!");
        this.rollButton.setPreferredSize(new Dimension(100, 100));
        this.rollButton.setFocusable(false);
        this.rollButton.addActionListener(rollListener);
        this.rollButton.setIcon(new ImageIcon(
                new ImageIcon("Gacha_Roll.png").getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT)));
        this.rollButton.setHorizontalTextPosition(JButton.CENTER);
        this.rollButton.setVerticalTextPosition(JButton.BOTTOM);
        this.gachaPanel.add(rollButton);
        this.bannerLabels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            this.bannerLabels[i] = new JLabel();
            this.gachaPanel.add(bannerLabels[i]);
        }

        this.textPanel = new JPanel();
        this.textPanel.setLayout(null);
        this.textPanel.setBackground(Color.MAGENTA);
        this.textPanel.setPreferredSize(new Dimension(100, 100));
        this.textLabels = new ArrayList<JLabel>();
        this.nextDayButton = new JButton();
        this.nextDayButton.setBounds(1350, 15, 125, 70);
        this.nextDayButton.setText("Next Day");
        this.nextDayButton.setFocusable(false);
        this.nextDayButton.addActionListener(nextDayListener);
        this.nextDayButton.setIcon(new ImageIcon(
                new ImageIcon("Text_NextDay.png").getImage().getScaledInstance(33, 42,
                        Image.SCALE_DEFAULT)));
        this.nextDayButton.setHorizontalTextPosition(JButton.LEFT);
        this.nextDayButton.setVerticalTextPosition(JButton.CENTER);
        this.textPanel.add(nextDayButton);
        this.upScrollButton = new JButton();
        this.upScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JLabel label : textLabels) {
                    label.setLocation((int) label.getLocation().getX(), (int) label.getLocation().getY() + 32);
                }
                scrollOffset--;
            }
        });
        this.upScrollButton.setBounds(15, 14, 40, 35);
        this.upScrollButton.setFocusable(false);
        this.upScrollButton.setIcon(new ImageIcon(
                new ImageIcon("Text_Up.png").getImage().getScaledInstance(28, 20,
                        Image.SCALE_DEFAULT)));
        this.downScrollButton = new JButton();
        this.downScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scrollOffset != 0) {
                    scrollOffset++;
                    for (JLabel label : textLabels) {

                        label.setLocation((int) label.getLocation().getX(), (int) label.getLocation().getY() - 32);

                    }
                    System.out.println(scrollOffset);
                }

            }
        });
        this.downScrollButton.setBounds(15, 54, 40, 35);
        this.downScrollButton.setFocusable(false);
        this.downScrollButton.setIcon(new ImageIcon(
                new ImageIcon("Text_Down.png").getImage().getScaledInstance(28, 20,
                        Image.SCALE_DEFAULT)));
        this.textPanel.add(upScrollButton);
        this.textPanel.add(downScrollButton);

        this.farmPanel = new JPanel();
        this.farmPanel.setBackground(new Color(87, 95, 58));
        this.farmPanel.setLayout(new GridLayout(5, 10));
        this.farmPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.tileButtons = new JButton[50];
        for (int i = 0; i < 50; i++) {
            this.tileButtons[i] = new JButton();
            this.tileButtons[i].setFocusable(false);
            this.tileButtons[i].addActionListener(tileListeners[i]);
            this.farmPanel.add(tileButtons[i]);
            this.tileButtons[i].setBackground(new Color(138, 154, 91));
            this.tileButtons[i].setBorderPainted(false);
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                updateTile(farm[i][j], i, j);
            }
        }

        this.mainFrame.add(this.toolPanel, BorderLayout.NORTH);
        this.mainFrame.add(this.statPanel, BorderLayout.WEST);
        this.mainFrame.add(this.gachaPanel, BorderLayout.EAST);
        this.mainFrame.add(this.textPanel, BorderLayout.SOUTH);
        this.mainFrame.add(this.farmPanel, BorderLayout.CENTER);
    }

    public void setVisibleMain() {
        this.mainFrame.setVisible(true);
    }

    public void setSelectedTool(int selectedToolId) {
        for (int i = 0; i < 6; i++) {
            if (i == selectedToolId) {
                this.toolButtons[i].setBackground(new Color(198, 174, 146));
            } else {
                this.toolButtons[i].setBackground(new Color(83, 41, 21));
            }
        }
    }

    public void setToolDurability(int toolId, int dura) {
        this.toolButtons[toolId + 1].setText("" + dura);
    }

    public void updateNextDayLabel(int day) {
        this.nextDayJLabel.setText("Day " + day);
    }

    public void updateStatPanel(int exp, int objectCoins, int kusaCoins, String registration, String regImg) {
        this.expLabel.setText("EXP: " + ((double) exp / 2) + " (" + (exp / 200) + ")");
        this.objectCoinsLabel.setText("ObjectCoins: " + (objectCoins / 100));
        this.kusaCoinsLabel.setText("KusaCoins: " + kusaCoins);
        this.registrationLabel.setText("Registration:");
        this.registrationLevelLabel.setText(registration);
        this.registrationLabel.setIcon(new ImageIcon(
                new ImageIcon(regImg).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
    }

    public void addToTextPanel(String text) {
        for (JLabel label : textLabels) {
            label.setLocation((int) label.getLocation().getX(),
                    (int) label.getLocation().getY() - 32 + scrollOffset * 32);
        }
        this.scrollOffset = 0;
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(1600, 50));
        label.setBounds(70, 57, 1600, 55);
        label.setFont(new Font("Gotham", Font.PLAIN, 27));
        this.textPanel.add(label); // GOTHAm TODO
        textLabels.add(label);
    }

    public void updateGachaBanner(int bannerIds[]) {
        for (int i = 0; i < 4; i++) {
            if (bannerIds[i] <= 8 && bannerIds[i] >= 0) {
                this.bannerLabels[i].setText(Plant.getPlantNameStatic(bannerIds[i]));
                this.bannerLabels[i].setIcon(new ImageIcon(new ImageIcon(Plant.getGachaImageStatic(bannerIds[i]))
                        .getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            } else if (bannerIds[i] >= 9 && bannerIds[i] <= 13) {
                this.bannerLabels[i].setText(Tool.getToolNameStatic(bannerIds[i] - 9));
                this.bannerLabels[i].setIcon(new ImageIcon(new ImageIcon(Tool.getToolImageStatic(bannerIds[i] - 9))
                        .getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            }

            this.bannerLabels[i].setVerticalTextPosition(JLabel.TOP);
            this.bannerLabels[i].setHorizontalTextPosition(JLabel.CENTER);
            this.bannerLabels[i].setFont(new Font("Gotham", Font.PLAIN, 15));
        }

    }

    public void popupPlantingSeed(ActionListener plantingListeners[], int x, int y) {
        if (this.popupFrame != null) {
            this.disposePopupJFrame();
        }
        this.popupFrame = new JFrame();
        JPanel plantPanel = new JPanel();

        this.popupFrame.setTitle("Planting in tile (" + x + ", " + y + ")...");
        this.popupFrame.setSize(300, 350);
        this.popupFrame.setResizable(false);
        this.popupFrame.setLocationRelativeTo(null);
        this.popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        plantPanel.setBackground(new Color(197,136,7));
        plantPanel.setLayout(new GridLayout(3, 3));
        plantPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton plantButtons[];
        plantButtons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            plantButtons[i] = new JButton();
            plantButtons[i].addActionListener(plantingListeners[i]);
            plantPanel.add(plantButtons[i]);
            plantButtons[i].setBorderPainted(false);
            plantButtons[i].setBackground(new Color(197,136,7));
            plantPanel.setFocusable(false);
        }
        plantButtons[0].setIcon(new ImageIcon(new ImageIcon("Plant_Turnip.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[1].setIcon(new ImageIcon(new ImageIcon("Plant_Carrot.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[2].setIcon(new ImageIcon(new ImageIcon("Plant_Potato.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[3].setIcon(new ImageIcon(new ImageIcon("Plant_Rose.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[4].setIcon(new ImageIcon(new ImageIcon("Plant_Tulip.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[5].setIcon(new ImageIcon(new ImageIcon("Plant_Sunflower.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[6].setIcon(new ImageIcon(new ImageIcon("Plant_Mango.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[7].setIcon(new ImageIcon(new ImageIcon("Plant_Apple.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
        plantButtons[8].setIcon(new ImageIcon(new ImageIcon("Plant_Larry.png").getImage()
                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));

        this.popupFrame.add(plantPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton();
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposePopupJFrame();
            }
        });
        this.popupFrame.add(closeButton, BorderLayout.SOUTH);

        this.popupFrame.setVisible(true);
    }

    public void disposePopupJFrame() {
        this.popupFrame.dispose();
        this.popupFrame = null;
    }

    public void updateTile(Tile t, int x, int y) {
        switch (t.getState()) {
            case Tile.STATE_ROCK:
                this.tileButtons[x + y * 10].setIcon(new ImageIcon(
                        new ImageIcon("Tile_Rock.png").getImage().getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                break;
            case Tile.STATE_UNPLOWED:
                this.tileButtons[x + y * 10].setIcon(new ImageIcon(
                        new ImageIcon("Tile_Unplowed.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT)));
                break;
            case Tile.STATE_PLOWED:
                this.tileButtons[x + y * 10].setIcon(new ImageIcon(
                        new ImageIcon("Tile_Plowed.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT)));
                break;
            case Tile.STATE_GROWING:
                if (t.getPlant().isFlower()) {
                    this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Flower.png").getImage()
                            .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                }

                else if (t.getPlant().isRoot()) {
                    this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_RootCrop.png").getImage()
                            .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                }

                else if (t.getPlant().getPlantId() == Plant.PLANT_APPLE) {
                    this.tileButtons[x + y * 10].setIcon(new ImageIcon(
                            new ImageIcon("Tile_Apple.png").getImage().getScaledInstance(186, 186,
                                    Image.SCALE_DEFAULT)));
                }

                else if (t.getPlant().getPlantId() == Plant.PLANT_MANGO) {
                    this.tileButtons[x + y * 10].setIcon(new ImageIcon(
                            new ImageIcon("Tile_Mango.png").getImage().getScaledInstance(186, 186,
                                    Image.SCALE_DEFAULT)));
                }

                break;
            case Tile.STATE_HARVESTABLE:
                switch (t.getPlant().getPlantId()) {
                    case Plant.PLANT_TURNIP:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Turnip.png").getImage()
                                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_POTATO:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Potato.png").getImage()
                                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_CARROT:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Carrot.png").getImage()
                                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_SUNFLOWER:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Sunflower.png")
                                .getImage().getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_ROSE:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Rose.png").getImage()
                                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_TULIP:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Tulip.png").getImage()
                                .getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_APPLE:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_AppleHarvest.png")
                                .getImage().getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_MANGO:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_MangoHarvest.png")
                                .getImage().getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                    case Plant.PLANT_LARRY:
                        this.tileButtons[x + y * 10].setIcon(new ImageIcon(new ImageIcon("Tile_Larry.png")
                                .getImage().getScaledInstance(186, 186, Image.SCALE_DEFAULT)));
                        break;
                }
                break;
            case Tile.STATE_WITHERED:
                this.tileButtons[x + y * 10].setIcon(new ImageIcon(
                        new ImageIcon("Tile_Withered.png").getImage().getScaledInstance(186, 186,
                                Image.SCALE_DEFAULT)));
                break;
        }
    }

    public void popupEndgame() {
        if (this.popupFrame != null) {
            this.disposePopupJFrame();
        }
        this.popupFrame = new JFrame();
        this.popupFrame.setLayout(new FlowLayout());
        this.popupFrame.setSize(400, 70);
        this.popupFrame.add(new JLabel("Sorry, partner... but you just lost! Thanks for playin' tho!"));
        this.popupFrame.setResizable(false);
        this.popupFrame.setLocationRelativeTo(null);
        this.popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.popupFrame.setVisible(true);
    }
}
