public class Tool {

    public final static int TOOL_PLOW = 0;
    public final static int TOOL_WATERING = 1;
    public final static int TOOL_FERTILIZER = 2;
    public final static int TOOL_PICKAXE = 3;
    public final static int TOOL_SHOVEL = 4;

    private final int[] USAGE_COST = { 0, 0, 1000, 5000, 700 }; // 100x
    private final int[] EXP_GAIN = { 1, 1, 8, 30, 4 }; // 2x
    private final int[][] TOOL_DURABILITY = { { 50, 100, 20, 10, 15 },
            { 75, 150, 30, 15, 25 },
            { 100, 200, 40, 20, 30 },
            { 150, 300, 60, 30, 45 } };

    private int toolId;
    private int conste; // TODO: make conste add to durability
    private int durability;

    public Tool(int toolId, int conste) {
        this.toolId = toolId;
        this.conste = conste;
        this.durability = TOOL_DURABILITY[toolId][conste];
    }

    public boolean useTool(Player player) {
        if (this.durability <= 0)
            return false;
        else {
            this.durability--;
            player.deductObjectCoins(USAGE_COST[toolId]);
            player.increaseExp(EXP_GAIN[toolId]);
            return true;
        }
    }

    public int getConste(){
        return conste;
    }
    public boolean isBroken(){
        return this.durability <= 0;
    }
}
