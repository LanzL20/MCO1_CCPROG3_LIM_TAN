
public class Tile {
	public final int STATE_ROCK=0;
	public final int STATE_UNPLOWED=1;
	public final int STATE_PLOWED=2;
	public final int STATE_GROWING=3;
	public final int STATE_HARVESTABLE=4;
	public final int STATE_WITHERED=5;
	
	private final int[] buyPriceList={5,10,20,5,10,20,100,200};
	private final int[] sellPriceList={6,9,3,5,9,19,8,5};
	private final int[] requiredWaterList={1,1,3,1,2,2,7,7};
	private final int[] requiredFertilizerList={0,0,1,0,0,1,4,5};
	private final int[] bonusWaterCapList={2,2,4,2,3,3,7,7};
	private final int[] bonusFertilizerCapList={1,1,2,1,1,2,4,5};
	private final int[] daysRequiredList={2,3,5,1,2,3,10,10};
	private final int[] expGainList={10,15,25,5,10,15,50,50};// x2 the specs
	//private final int[] conste={};
	//private final int[] maxConst={};
	//private final int[] constMultiplier={};


	private int state;
	private Plant plant;
	private int timesWatered;
	private int timesFertilized;
	private int daysPast;
	
	public Tile() {
		this.state=1;
		this.timesWatered=0;
		this.timesFertilized=0;
		this.daysPast=0;
	}
	
	public boolean removeRock(Player player) {
		if(this.state==STATE_ROCK) {
			this.state++;
			//use pickaxe
			return true;
		}
		return false;
	}
	
	public boolean plowTile(Player player) {
		if(this.state==STATE_UNPLOWED) {
			this.state++;
			//use plow
			return true;
		}
		return false;//should we add else if for if its alr plowed else if >STATE_UNPLPOWED and else theres a rock
	}
	
	public boolean plantSeed(int plantno,Player player) {//take input of which plant was planted//Plant plant
		if (this.state==STATE_PLOWED && (plantno>=0 || plantno<=7) ) {
			this.plant = new Plant(buyPriceList[plantno] , sellPriceList[plantno] , requiredWaterList[plantno] , requiredFertilizerList[plantno] , bonusWaterCapList[plantno] , bonusFertilizerCapList[plantno] , daysRequiredList[plantno] , expGainList[plantno]);
			this.state++;
			player.setObjectCoins(player.getObjectCoins()-this.plant.getBuyPrice());//deducting coins from player//edit
			return true;
		}
		return false;
	}
	
	public boolean harvestTile(Player player) {
		if(this.state==STATE_HARVESTABLE) {
			this.state=STATE_UNPLOWED;
		}
		//call calculateFinalPrice
	}
	
	public boolean removeWithered() {
		if(this.state==STATE_WITHERED) {
			this.state=1;
			return true;//deduct coins
		}
		
	}
	
	public void waterTile() {
		this.timesWatered++;
	}
	
	public void fertilizeTile() {
		this.timesFertilized++;
	}
	
	public void advanceDay() {
		this.daysPast++;
		if(daysPast==this.plant.daysRequired) {
			this.state==STATE_HARVESTABLE;
		}
		//add condition for withered
	}
	
	public int getState() {
		return state;
	}
	
	public Plant getPlant() {
		return this.plant;
	}
	
	public boolean isHarvestable() {
		if(this.state==STATE_HARVESTABLE) {
			return true;
		}
		return false;
	}
	
	public int calculteFinalPrice() {
		//final price logic
	}
}
