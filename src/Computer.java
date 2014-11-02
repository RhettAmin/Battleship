import java.util.*;

public class Computer {
	private ArrayList<Tuple> locationsHit = new ArrayList<Tuple>();
	private ArrayList<Tuple> hitChecker = new ArrayList<Tuple>();
	private static ArrayList<Tuple> shipMake = new ArrayList<Tuple>();
	private int[] shipSize = {1,1,2,2,3,4,5};
	private ConsoleLab console;
	private int conTurn = 0;
	private int conPlayer = 0;
	private Tuple conTup;
	private boolean conHit;
	private boolean conSunk;

	private Grid myGrid;
	
	public Computer(Grid myGrid,ConsoleLab con){
		 this.myGrid = myGrid;
		 this.console = con;
	}
	
	public static Tuple getMove(){		
		//Declare Variables
		int minNum = 0;
		int maxNum = 10;
		int number1;
		int number2;
		
		number1 = minNum + (int)(Math.random() * maxNum);
		number2 = minNum + (int)(Math.random() * maxNum);	
		Tuple set = new Tuple(number1, number2);
		return set;
	}
	public static ArrayList<Tuple> aiShips(int size){
		ArrayList<Tuple> output = new ArrayList<Tuple>();
		Tuple start = new Tuple (0,0);
		boolean success = false;
		start = getMove();
		//orientation of ship: horiz = 0 ; vert = 1
		int orie = (int) (Math.random()*2);
		if (orie == 0){
			while (success == false){
				ArrayList<Tuple> possiblePlace = new ArrayList<Tuple>();
				
				
				while(Tuple.checkMatch(shipMake, start)){
					start = getMove();
				}
				possiblePlace.add(start);
				
				
				//shipMake.add(start);
				
				boolean didfail = false;
				if((start.getX()+ size) <= 10 ){
					
					for(int i = 1; i < size; i++){
						int newX = (start.getX() + i);
						int newY = (start.getY());
						Tuple next = new Tuple(newX,newY);
						if(Tuple.checkMatch(shipMake, next) == false){
							possiblePlace.add(next);
						} else{
							didfail = true;
							possiblePlace.clear();
						}
					}
				} else {
					didfail = true;
				}
				if(didfail == false){
					for(Tuple x: possiblePlace){
						shipMake.add(x);
						output.add(x);
					}
					success = true;
				} else {
					//fail
					start = getMove();
				}
			}	
		}
		
		if (orie == 1){
			while (success == false){
				ArrayList<Tuple> possiblePlace = new ArrayList<Tuple>();
				boolean didfail = false;
				
				while(Tuple.checkMatch(shipMake, start)){
					start = getMove();
				}
				possiblePlace.add(start);
				
				if((start.getY()+ (size-1)) <= 9 ){
					for(int i = 1; i < size; i++){
						int newX = (start.getX());
						int newY = (start.getY() + i);
						Tuple next = new Tuple(newX,newY);
						if(Tuple.checkMatch(shipMake, next)==false){
							possiblePlace.add(next);
						} else{
							didfail = true;
						}
					}
				} else {
					didfail = true;
				}
				if(didfail == false){
					for(Tuple x: possiblePlace){
						shipMake.add(x);
						output.add(x);
					}
					success = true;
				} else {
					//fail
					start = getMove();
				}
			}	
		}
		return output;
	}

	public ArrayList<BattleShip> aiShipSet(){
		ArrayList<BattleShip> output = new ArrayList<BattleShip>();
		for(int i:shipSize){
			ArrayList<Tuple> shipCoord = new ArrayList<Tuple>();
			shipCoord = aiShips(i);
			BattleShip newShip = new BattleShip(shipCoord);
			output.add(newShip);
		}
		return output;
	}
	public void compTurn(){
		//Declare Variable
		Tuple turnMove = new Tuple(0,0);
		conTurn = conTurn+2;
		if (hitChecker.size() == 0){
			Boolean notMatch = false;
			turnMove = getMove();
			//Checks if randomized location already called if not adds to locationsHit
			while(notMatch == false){
				Boolean check = Tuple.checkMatch(locationsHit, turnMove);
				if (check == true){
					turnMove = getMove();			
				} else {
					notMatch = true;
					locationsHit.add(turnMove);
					conTup = turnMove;
				}
			}
			
			//Checks if location hit target; If hit adds to hitChecker
			BattleShip didHit =  myGrid.hitAtBS(turnMove);
			if (didHit != null){
				conHit = true;
				//adds next possible tuples to array
				ArrayList<Tuple> nextPos = new ArrayList<Tuple>();
				nextPos = turnMove.getAdjacent();
				for(Tuple x : nextPos){
					if(!Tuple.checkMatch(locationsHit, x)){
						hitChecker.add(x);
					}
					
				}
				if (didHit.didSink()){
					hitChecker.clear();
					conSunk = true;
				}
			} else {
				conHit = false;
			}
			
		} else {
			//Chooses a random position form hitChecker; Sets to turnMove; Removes that tuple from hitChecker
			//Generate random position
			int nextHit = (int) (Math.random() * (hitChecker.size()));
			turnMove = hitChecker.get(nextHit);
			hitChecker.remove(nextHit);
			//adds to locationHit
			locationsHit.add(turnMove);
			conTup = turnMove;
			//Checks if location hit target; If hit adds to hitChecker
			BattleShip didHit =  myGrid.hitAtBS(turnMove);
			if (didHit != null){
				conHit = true;
				ArrayList<Tuple> nextPossible = turnMove.getAdjacent();
				//Adds next possible tuples to array
				//Needs to check if next possible hits are already been used.
				if (didHit.didSink()){
					conSunk = true;
					//hitChecker.clear();
				} else {
					for (int i = 0; i < nextPossible.size(); i++){
						Tuple check = nextPossible.get(i);
						Boolean match = Tuple.checkMatch(locationsHit, check);
						if(match == false){
							hitChecker.add(check);
							locationsHit.add(check);
						}
					}
				}
			} else {
				conHit = false;
			}
		}
		console.newConsoleLine(conTurn, conPlayer, conTup, conHit);
		if (conSunk == true){
			console.newSunk();
			conSunk = false;
		}
	}	
}


