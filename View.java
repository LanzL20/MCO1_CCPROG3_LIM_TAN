
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
import java.awt.event.ActionListener;

// TODO: Perchange change pressed color buttons
public class View {

    private JFrame startFrame;
    private JLabel startText;
    private JTextField startTextField;
    private JButton startButton;

    private JPanel toolPanel;
    private JButton toolButtons[];

    private JPanel statPanel;
    private JLabel farmerLabel;
    private JLabel expLabel;
    private JLabel objectCoinsLabel;
    private JLabel kusaCoinsLabel;
    private JLabel registrationLabel;
    private JButton upgradeRegistrationButton;

    private JPanel gachaPanel;
    private JButton rollButton;
    private JLabel bannerLabels[];

    private JPanel textPanel;
    private JButton nextDayButton;
    private ArrayList<JLabel> textLabels;
    private JButton upScrollButton;
    private JButton downScrollButton;

    private JPanel farmPanel;
    private JButton tileButtons[];

    private JFrame mainFrame;

    private JFrame popupFrame;

    public View(ActionListener startListener) {
        // First, start a JFrame for the introductory window.
        this.startFrame = new JFrame();
        this.startFrame.setTitle("Plants VS Programmers");
        this.startFrame.setSize(400, 100);
        this.startFrame.setLayout(new FlowLayout());
        this.startFrame.setResizable(false);
        this.startFrame.setLocationRelativeTo(null);
        this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this.startFrame.setContentPane(new JLabel(new ImageIcon("StartBG.png")));
        // TODO?

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
        this.startButton.setPreferredSize(new Dimension(100, 30));
        this.startButton.addActionListener(startListener);
        this.startButton.setFocusable(false);
        this.startFrame.add(startButton);
    }

    public void setVisibleStart() {
        // Simply set the introductory window to be visible.
        this.startFrame.setVisible(true);
    }

    public String getStartTextFieldInput() {
        return this.startTextField.getText();
    }

    public void disposeStartJFrame() {
        // Simply dispose of the introductory window.
        this.startFrame.dispose();
    }

    public void startMainJFrame(String playerName, ActionListener toolListeners[], ActionListener tileListeners[],
            ActionListener nextDayListener, ActionListener rollListener, ActionListener regListener) {
        this.mainFrame = new JFrame();
        this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainFrame.setUndecorated(true);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.toolPanel = new JPanel();
        this.toolPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 5));
        this.toolPanel.setBackground(new Color(98, 74, 46));
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
            this.toolButtons[i].setBackground(new Color(98, 74, 46));
            this.toolButtons[i].setBorderPainted(false);
            if (i != 0) {
                this.toolButtons[i].setIcon(new ImageIcon(
                        new ImageIcon(Tool.getToolImageStatic(i - 1)).getImage().getScaledInstance(70, 70,
                                Image.SCALE_DEFAULT)));
            } else {
                this.toolButtons[i].setIcon(new ImageIcon(
                        new ImageIcon("Tool-Hand.png").getImage().getScaledInstance(125, 94,
                                Image.SCALE_DEFAULT)));
                this.toolButtons[i].setText(" ");
            }
            this.toolButtons[i].addActionListener(toolListeners[i]);
        }

        this.statPanel = new JPanel();
        this.statPanel.setBackground(Color.blue);
        this.statPanel.setPreferredSize(new Dimension(125, 100));
        this.farmerLabel = new JLabel("Farmer " + playerName);
        this.expLabel = new JLabel();
        this.objectCoinsLabel = new JLabel();
        this.kusaCoinsLabel = new JLabel();
        this.registrationLabel = new JLabel();
        this.expLabel.setIcon(new ImageIcon(
                new ImageIcon("Stats-EXP.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        this.registrationLabel.setIcon(new ImageIcon(
                new ImageIcon("Stats-Reg.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        this.expLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.objectCoinsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.kusaCoinsLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.registrationLabel.setVerticalTextPosition(JLabel.BOTTOM);
        this.expLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.objectCoinsLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.kusaCoinsLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.registrationLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.farmerLabel.setForeground(Color.white);
        this.expLabel.setForeground(Color.white);
        this.objectCoinsLabel.setForeground(Color.white);
        this.kusaCoinsLabel.setForeground(Color.white);
        this.registrationLabel.setForeground(Color.white);
        this.statPanel.add(farmerLabel);
        this.statPanel.add(expLabel);
        this.statPanel.add(objectCoinsLabel);
        this.statPanel.add(kusaCoinsLabel);
        this.statPanel.add(registrationLabel);
        this.upgradeRegistrationButton = new JButton();
        this.upgradeRegistrationButton.setText("Promote!");
        this.upgradeRegistrationButton.setFocusable(false);
        this.upgradeRegistrationButton.setPreferredSize(new Dimension(90, 30));
        this.upgradeRegistrationButton.addActionListener(regListener);
        this.statPanel.add(upgradeRegistrationButton);

        this.gachaPanel = new JPanel();
        this.gachaPanel.setBackground(Color.red);
        this.gachaPanel.setPreferredSize(new Dimension(125, 100));
        this.gachaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        this.rollButton = new JButton("Roll!");
        this.rollButton.setPreferredSize(new Dimension(80, 30));
        this.rollButton.setFocusable(false);
        this.rollButton.addActionListener(rollListener);
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
        this.nextDayButton.setBounds(1400, 15, 100, 70);
        this.nextDayButton.setText("Next Day ➜");
        this.nextDayButton.setFocusable(false);
        this.nextDayButton.addActionListener(nextDayListener);
        this.textPanel.add(nextDayButton);
        this.upScrollButton = new JButton("🠕");
        this.upScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JLabel label : textLabels) {
                    label.setLocation((int) label.getLocation().getX(), (int) label.getLocation().getY() + 32);
                }
            }
        });
        this.upScrollButton.setBounds(15, 20, 40, 25);
        this.upScrollButton.setFocusable(false);
        this.downScrollButton = new JButton("🠗");
        this.downScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JLabel label : textLabels) {
                    label.setLocation((int) label.getLocation().getX(), (int) label.getLocation().getY() - 32);
                }
            }
        });
        this.downScrollButton.setBounds(15, 60, 40, 25);
        this.downScrollButton.setFocusable(false);
        this.textPanel.add(upScrollButton);
        this.textPanel.add(downScrollButton);

        this.farmPanel = new JPanel();
        this.farmPanel.setBackground(Color.green);
        this.farmPanel.setLayout(new GridLayout(5, 10));
        this.farmPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.tileButtons = new JButton[50];
        for (int i = 0; i < 50; i++) {
            this.tileButtons[i] = new JButton();
            this.tileButtons[i].setFocusable(false);
            this.tileButtons[i].addActionListener(tileListeners[i]);
            this.tileButtons[i].setText("Unplowed");
            this.farmPanel.add(tileButtons[i]);
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
                this.toolButtons[i].setBackground(new Color(98, 74, 46));
            }
        }
    }

    public void setToolDurability(int toolId, int dura) {
        this.toolButtons[toolId + 1].setText("" + dura);
    }

    public void updateStatPanel(int exp, int objectCoins, int kusaCoins, String registration) {
        this.expLabel.setText("EXP: " + ((double) exp / 2) + " (" + (exp / 200) + ")");
        this.objectCoinsLabel.setText("ObjectCoins: " + (objectCoins / 100));
        this.kusaCoinsLabel.setText("KusaCoins: " + kusaCoins);
        this.registrationLabel.setText("Registration: " + registration);
    }

    public void addToTextPanel(String text) {
        for (JLabel label : textLabels) {
            label.setLocation((int) label.getLocation().getX(), (int) label.getLocation().getY() - 32);
        }
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(1600, 50));
        label.setBounds(70, 57, 1600, 55);
        label.setFont(new Font("Gotham", Font.PLAIN, 27));
        this.textPanel.add(label);
        textLabels.add(label);
    }

    public void updateGachaBanner(int bannerIds[]) {
        for (int i = 0; i < 4; i++) {
            if (bannerIds[i] <= 7 && bannerIds[i] >= 0) {
                this.bannerLabels[i].setText(Plant.getPlantNameStatic(bannerIds[i]));
                this.bannerLabels[i].setIcon(new ImageIcon(new ImageIcon(Plant.getPlantImageStatic(bannerIds[i]))
                        .getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
            } else if (bannerIds[i] >= 8 && bannerIds[i] <= 12) {
                this.bannerLabels[i].setText(Tool.getToolNameStatic(bannerIds[i] - 8));
                this.bannerLabels[i].setIcon(new ImageIcon(new ImageIcon(Tool.getToolImageStatic(bannerIds[i] - 8))
                        .getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
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
        this.popupFrame.setTitle("Planting in tile (" + x + ", " + y + ")...");
        this.popupFrame.setSize(300, 350);
        this.popupFrame.setResizable(false);
        this.popupFrame.setLocationRelativeTo(null);
        this.popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel plantPanel = new JPanel();
        plantPanel.setBackground(Color.orange);
        plantPanel.setLayout(new GridLayout(3, 3));
        plantPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < 8; i++) {
            // TODO to 9 once gacha plant
            JButton plantButton = new JButton();
            plantButton.addActionListener(plantingListeners[i]);
            plantPanel.add(plantButton);
        }
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
    // TODO day today top right
}
