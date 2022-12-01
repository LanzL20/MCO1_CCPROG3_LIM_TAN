
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

public class View {

    private JFrame startFrame;
    private JLabel startText;
    private JTextField startTextField;
    private JButton startButton;

    private JPanel toolPanel;
    private JButton toolButtons[];

    private JPanel statPanel;
    private JPanel gachaPanel;

    private JPanel textPanel;
    private ArrayList<JLabel> textLabels;

    private JPanel farmPanel;
    private JButton tileButtons[];

    private JFrame mainFrame;

    public View() {
        this.startFrame = new JFrame();
        this.startFrame.setTitle("Plants VS Programmers");
        this.startFrame.setSize(400, 100);
        this.startFrame.setLayout(new FlowLayout());
        this.startFrame.setResizable(false);
        this.startFrame.setLocationRelativeTo(null);

        // this.startFrame.setContentPane(new JLabel(new ImageIcon("StartBG.png")));

        this.startText = new JLabel("Howdy there, farmer! What's ya name, buddy ol' pal?");
        this.startFrame.add(startText);

        this.startTextField = new JTextField();
        this.startTextField.setPreferredSize(new Dimension(100, 30));
        this.startFrame.add(startTextField);

        this.startButton = new JButton();
        this.startButton.setText("Let's start!");
        this.startButton.setPreferredSize(new Dimension(100, 30));
        this.startFrame.add(startButton);
        

        this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setVisibleStart() {
        this.startFrame.setVisible(true);
    }

    public void setStartButtonActionListener(ActionListener actionListener) {
        this.startButton.addActionListener(actionListener);
    }

    public String getStartTextFieldInput() {
        return this.startTextField.getText();
    }

    public void disposeStartJFrame() {
        this.startFrame.dispose();
    }

    public void startMainJFrame() {
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
        }
        this.toolButtons[1].setIcon(new ImageIcon(
                new ImageIcon("Hoe1.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
        this.toolButtons[2].setIcon(new ImageIcon(
                new ImageIcon("WateringCan1.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
        this.toolButtons[3].setIcon(new ImageIcon(
                new ImageIcon("Fertilizer1.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
        this.toolButtons[4].setIcon(new ImageIcon(
                new ImageIcon("Pickaxe1.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
        this.toolButtons[5].setIcon(new ImageIcon(
                new ImageIcon("Shovel1.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));

        this.statPanel = new JPanel();
        this.statPanel.setBackground(Color.blue);
        this.statPanel.setPreferredSize(new Dimension(100, 100));

        this.gachaPanel = new JPanel();
        this.gachaPanel.setBackground(Color.red);
        this.gachaPanel.setPreferredSize(new Dimension(100, 100));

        this.textPanel = new JPanel();
        this.textPanel.setLayout(null);
        this.textPanel.setBackground(Color.MAGENTA);
        this.textPanel.setPreferredSize(new Dimension(100, 100));
        this.textLabels = new ArrayList<JLabel>();

        this.farmPanel = new JPanel();
        this.farmPanel.setBackground(Color.green);
        this.farmPanel.setLayout(new GridLayout(5, 10));
        this.farmPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.tileButtons = new JButton[50];
        for (int i = 0; i < 50; i++) {
            this.tileButtons[i] = new JButton();
            this.tileButtons[i].setFocusable(false);
            this.farmPanel.add(tileButtons[i]);
        }

        this.mainFrame.add(this.toolPanel, BorderLayout.NORTH);
        this.mainFrame.add(this.statPanel, BorderLayout.WEST);
        this.mainFrame.add(this.gachaPanel, BorderLayout.EAST);
        this.mainFrame.add(this.textPanel, BorderLayout.SOUTH);
        this.mainFrame.add(this.farmPanel, BorderLayout.CENTER);

        this.addToTextPanel("text1");
        this.addToTextPanel("sdjfsdjfoisdfosjfjosdiofdsjf");
        this.addToTextPanel("kjsd");
        this.addToTextPanel("text4");
    }

    public void setVisibleMain() {
        this.mainFrame.setVisible(true);
    }

    public void setDurabilityOnTool(int toolId, int dura) {
        this.toolButtons[toolId + 1].setText("" + dura);
        this.toolButtons[toolId + 1].setFont(new Font("Gotham", Font.PLAIN, 15));
    }

    public void addToTextPanel(String text) {
        for (JLabel label : textLabels) {
            label.setLocation((int) label.getLocation().getX(), (int) label.getLocation().getY() - 30);
        }
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(1600, 50));
        label.setBounds(15, 57, 1600, 50);
        label.setFont(new Font("Gotham", Font.PLAIN, 27));
        this.textPanel.add(label);
        textLabels.add(label);
    }
}