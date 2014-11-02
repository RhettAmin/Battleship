import java.util.ArrayList;

public class BattleShip {
	
	// Arrays that store which ship parts are afloat, and which ones have been hit
	private ArrayList<Tuple> floatingCoordinates;
	private ArrayList<Tuple> sunkCoordinates;
	
	// Constructor - Sets the floating portions of the ship object
	public BattleShip(ArrayList<Tuple> shipCoordinates){
		this.floatingCoordinates = shipCoordinates;
		ArrayList<Tuple> start = new ArrayList<Tuple>();
		this.sunkCoordinates = start;
	}
	
	//Is there a ship floating at this coordinate?
	public Boolean isFloating(Tuple coordinate){
		for (Tuple x : floatingCoordinates){
			if(x.getX() == coordinate.getX() && x.getY() == coordinate.getY()){
				sunkCoordinates.add(x);
				floatingCoordinates.remove(x);
				return true;
			}
		}
		return false;
	}
	
	// checks to see if the ship has sunk
	public Boolean didSink(){
		return floatingCoordinates.isEmpty();
	}
	
	// gets which coordinates have been hitt
	public ArrayList<Tuple> getSunkCoordinates(){
		return sunkCoordinates;
	}
	
	// gets the coordiantes of all the parts taht haven't been hit
	public ArrayList<Tuple> coord(){
		return floatingCoordinates;
	}
	
}