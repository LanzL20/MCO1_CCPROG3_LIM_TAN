// Authored by: Lanz Kendall Y. Lim and Tyler Justin H. Tan, CCPROG3 MCO2 

/**
 * Tool is the class that simulates the functions that accompanies all five
 * tools, including the plow, watering can, fertilizer, pickaxe, and shovel. It
 * also includes all values associated with tools, such as their names, usage
 * cost, replacement cost experience gain, and durability.
 */
public class Tool {

    // All public IDs for all the types of tools.
    public final static int TOOL_PLOW = 0;
    public final static int TOOL_WATERING = 1;
    public final static int TOOL_FERTILIZER = 2;
    public final static int TOOL_PICKAXE = 3;
    public final static int TOOL_SHOVEL = 4;

    // All the private constants associated with Tool.
    private final static String[] TOOL_NAME = { "Plow", "Watering Can", "Fertilizer", "Pickaxe", "Shovel" };
    // 100x to avoid the truncation of floating point numbers.
    private final static int[] USAGE_COST = { 0, 0, 1000, 5000, 700 };
    // 100x to avoid the truncation of floating point numbers.
    private final static int[] REPLACE_COST = { 10000, 10000, 25000, 15000, 25000 };
    // 2x to avoid the truncation of floating point numbers.
    private final static int[] EXP_GAIN = { 1, 1, 8, 30, 4 };
    private final static int[][] TOOL_DURABILITY = { { 100, 100, 20, 10, 10 },
            { 75, 150, 30, 15, 25 },
            { 100, 200, 40, 20, 30 },
            { 150, 300, 60, 30, 45 } };
    private final static String[] TOOL_IMAGE = { "Tool_Plow.png", "Tool_Watering.png", "Tool_Fertilizer.png",
            "Tool_Pickaxe.png", "Tool_Shovel.png" };

    private int toolId;
    private int conste;
    private int durability;

    /**
     * This creates a Tool object with a specified toolId and constellation.
     * 
     * @param toolId toolId of the tool being created
     * @param const  constellation of the tool being created.
     */
    public Tool(int toolId, int conste) {
        this.toolId = toolId;
        this.conste = conste;
        this.durability = TOOL_DURABILITY[conste][toolId];
    }

    /**
     * A method that simulates the usage of the tool being used.
     * 
     * @param player the Player object which uses this tool to perform an action
     */
    public boolean useTool(Player player) {
        // If durability is at 0 or the player doesn't have enough objectCoins, don't
        // let them use tool.
        if (this.durability <= 0 || player.getObjectCoins() < USAGE_COST[toolId])
            return false;
        else {
            // If player is able to use tool, decrease durability and objectCoins.
            this.durability--;
            player.deductObjectCoins(USAGE_COST[toolId]);
            player.increaseExp(EXP_GAIN[toolId]);
            return true;
        }
    }

    /**
     * A simple method that sets the durability of this tool to 0, effectively
     * breaking it. This is to be used for certain events.
     */
    public void getBroken() {
        this.durability = 0;
    }

    /**
     * A method that returns the name of the tool in String format.
     * 
     * @return the name of the tool as a String
     */
    public String getToolName() {
        return TOOL_NAME[toolId];
    }

    /**
     * A method that returns the name of a tool in String format based on the
     * toolId provided in the parameters.
     * 
     * @param toolId id of the tool provided in the parameter
     * @return the name of the tool based on the id provided in the parameter
     */
    public static String getToolNameStatic(int toolId) {
        return TOOL_NAME[toolId];
    }

    /**
     * A method that returns the usage cost of the tool as an integer.
     * 
     * @return the usage cost of the tool as an int
     */
    public int getUsageCost() {
        return USAGE_COST[toolId];
    }

    /**
     * A method that returns the replace cost of the tool as an integer.
     * 
     * @return the replace cost of the tool as an int
     */
    public int getReplaceCost() {
        return REPLACE_COST[toolId];
    }

    /**
     * A method that returns the constellation value of the tool as an integer.
     * 
     * @return the constellation value of the tool as an int
     */
    public int getConste() {
        return conste;
    }

    /**
     * A method that returns the durability of the tool as an integer.
     * 
     * @return the durability of the tool as an int
     */
    public int getDurability() {
        return durability;
    }

    /**
     * A method that returns whether or not the tool is at durability 0 or below.
     * 
     * @return a boolean indicating whether or not the tool is at durability 0 or
     *         below.
     */
    public boolean isBroken() {
        return this.durability <= 0;
    }

    /**
     * A method that returns the filename of the image of a specific tool in String
     * format based on the toolId provided in the parameters.
     * 
     * @param toolId id of the tool provided in the parameter
     * @return the filename of the image of the tool based on the id provided in the
     *         parameter
     */
    public static String getToolImageStatic(int toolId) {
        return TOOL_IMAGE[toolId];
    }
}
