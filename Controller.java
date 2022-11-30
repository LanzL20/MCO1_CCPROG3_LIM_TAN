
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;
    private String playerName;

    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        this.model = new Model(null);
        this.view = new View();

        this.view.setStartButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = view.getStartTextFieldInput();
                transitionToMainGame();
            }
        });
        this.view.setVisibleStart();
    }

    public void transitionToMainGame(){
        this.view.disposeStartJFrame();

        this.model = new Model(playerName);
        this.view.startMainJFrame();

        this.view.setDurabilityOnTool(Tool.TOOL_PLOW, this.model.getPlayer().getTool(Tool.TOOL_PLOW).getDurability());
        this.view.setDurabilityOnTool(Tool.TOOL_WATERING, this.model.getPlayer().getTool(Tool.TOOL_WATERING).getDurability());
        this.view.setDurabilityOnTool(Tool.TOOL_FERTILIZER, this.model.getPlayer().getTool(Tool.TOOL_FERTILIZER).getDurability());
        this.view.setDurabilityOnTool(Tool.TOOL_PICKAXE, this.model.getPlayer().getTool(Tool.TOOL_PICKAXE).getDurability());
        this.view.setDurabilityOnTool(Tool.TOOL_SHOVEL, this.model.getPlayer().getTool(Tool.TOOL_SHOVEL).getDurability());

        this.view.setVisibleMain();
    }
}
