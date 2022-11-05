
public class Handler {

	private int dayNo;
	private Tile farm[][];
	private Player player;
	private Gacha gacha;
 // TODO: Add ending conditions;
	public Handler(String name) {
		this.dayNo = 1;
		this.farm = new Tile[10][5];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				farm[i][j] = new Tile();
			}
		}
		this.player = new Player(name);
		this.gacha = new Gacha();
	}

	// TODO:IF STATEMENTS FOR TOOLS AND USING THEM
	public void advanceDay() {
		this.dayNo++;
		this.farm[0][0].advanceDay(); // TODO: For prototype only// for full code for loop the advance day
		this.gacha.resetBanner();
	}

	public Tile[][] getTiles() {
		return farm;
	}
	public int getDayNo(){
		return dayNo;
	}
}
