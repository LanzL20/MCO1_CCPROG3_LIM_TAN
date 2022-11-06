public class Tool {

    public final static int TOOL_PLOW = 0;
    public final static int TOOL_WATERING = 1;
    public final static int TOOL_FERTILIZER = 2;
    public final static int TOOL_PICKAXE = 3;
    public final static int TOOL_SHOVEL = 4;

    private final String[] TOOL_NAME = {"Plow", "Watering Can", "Fertilizer", "Pickaxe", "Shovel"};
    private final int[] USAGE_COST = { 0, 0, 1000, 5000, 700 }; // 100x
    private final int[] REPLACE_COST = { 10000, 10000, 50000, 200000, 400000 }; // 100x
    private final int[] EXP_GAIN = { 1, 1, 8, 30, 4 }; // 2x
    private final int[][] TOOL_DURABILITY = { { 50, 100, 20, 10, 15 },
            { 75, 150, 30, 15, 25 },
            { 100, 200, 40, 20, 30 },
            { 150, 300, 60, 30, 45 } };

    private int toolId;
    private int conste;
    private int durability;

    public Tool(int toolId, int conste) {
        this.toolId = toolId;
        this.conste = conste;
        this.durability = TOOL_DURABILITY[conste][toolId];
    }

    public boolean useTool(Player player) {
        if (this.durability <= 0 || player.getObjectCoins() < USAGE_COST[toolId])
            return false;
        else {
            this.durability--;
            player.deductObjectCoins(USAGE_COST[toolId]);
            player.increaseExp(EXP_GAIN[toolId]);
            return true;
        }
    }

    public String getToolName(){
        return TOOL_NAME[toolId];
    }

    public int getUsageCost(){
        return USAGE_COST[toolId];
    }

    public int getReplaceCost(){
        return REPLACE_COST[toolId];
    }

    public int getConste(){
        return conste;
    }

    public int getDurability(){
        return durability;
    }

    public boolean isBroken(){
        return this.durability <= 0;
    }
}
