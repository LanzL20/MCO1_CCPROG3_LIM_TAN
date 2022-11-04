
public class Tile {
	public final int STATE_ROCK=0;
	public final int STATE_UNPLOWED=1;
	public final int STATE_PLOWED=2;
	public final int STATE_GROWING=3;
	public final int STATE_HARVESTABLE=4;
	public final int STATE_WITHERED=5;
	
	private int state;
	private Plant plant;
	private int timesWatered;
	private int timesFertilized;
	private int daysPast;
	
	public Tile(Plant plant) {
		this.state=1;
		this.plant=plant;//remove
		this.timesWatered=0;
		this.timesFertilized=0;
		this.daysPast=0;
	}
	
	public boolean removeRock() {
		if(this.state==STATE_ROCK) {
			this.state++;//deduct coins
			return true;
		}
		return false;
	}
	
	public boolean plowTile() {
		if(this.state==STATE_UNPLOWED) {
			this.state++;
			return true;
		}
		return false;//should we add else if for if its alr plowed else if >STATE_UNPLPOWED and else theres a rock
	}
	
	public boolean plantSeed(String name) {//take input of which plant was planted//Plant plant
		if (this.state==STATE_PLOWED) {
			//multiple if statements/switch statements to instantiate different types of plants
			this.state++;
			//if(name.comparetoignorecase(rose){instantiate plant()}
			return true;//deduct coins
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
