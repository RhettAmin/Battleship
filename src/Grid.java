import java.util.ArrayList;

public class Grid {

	private ArrayList<BattleShip> floatingShips =  new ArrayList<BattleShip>();
	boolean isComp;
	
	private final int MISS = 1;		//Blue
	private final int HIT = 2;		//Red
	private final int SUNK = 3;		//Black
	
	private Composs composs;
	
	public Grid(boolean isComp, Composs composs){
		this.isComp = isComp;
		this.composs = composs;

	}
	
	public Grid(boolean isComp, Composs composs, ArrayList<BattleShip> x){
		this.isComp = isComp;
		this.composs = composs;
		this.floatingShips = x;
		if (isComp == false){
			drawShip();
		}
	}
	
	public void setShips(ArrayList<BattleShip> bShips) {
		this.floatingShips = bShips;
		drawShip();
	}
	
	public boolean lose(){
		if(floatingShips.size()==0){
			return true;
		}
		return false;
	}
	
	public BattleShip hitAtBS(Tuple location){
		for(BattleShip i : floatingShips){
			if(i.isFloating(location)){
				composs.setColour(location, HIT);
				didSink(i);
				return i;
			}
		}
		
		composs.setColour(location, MISS);
		return null;
	}
	
	public Boolean didSink(BattleShip i){
		if(i.didSink()){
			ArrayList<Tuple> shipCoordinates = i.getSunkCoordinates();
			for (Tuple x : shipCoordinates){
				composs.setColour(x, SUNK);
			}
			floatingShips.remove(i);
			return true;
		}
		return false;
	}
	
	public void set(ArrayList<Tuple> x, int y){
		for(Tuple i: x){
			composs.setColour(i,y);
		}
	}
	
	public void drawShip(){
		for(BattleShip x : floatingShips){
			for(Tuple i: x.coord()){
				composs.setColour(i,4);
			}
		}
	}
	
	public int getShipsRemaining(){
		return floatingShips.size();
	}

}
