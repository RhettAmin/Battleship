import java.util.ArrayList;

public class Tuple {
	
	public int x; 
	public int y; 
	
	public Tuple(int x, int y) { 
		this.x = x;
		this.y = y; 
	}
	
	public Integer getX(){
		return this.x;
	}
	
	public Integer getY(){
		return this.y;
	}
	
	public static Boolean checkMatch(ArrayList<Tuple> list, Tuple coordinate){
		for (Tuple x : list){
			if(x.getX() == coordinate.getX() && x.getY() == coordinate.getY()){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Tuple> getAdjacent(){
		ArrayList<Tuple> output = new ArrayList<Tuple>();
		
		if (x > 0){
			int x2 = x - 1;
			Tuple x1 = new Tuple(x2,y);
			output.add(x1);
		}
		if (x <= 8){
			int x2 = x + 1;
			Tuple x1 = new Tuple(x2,y);
			output.add(x1);
		}
		if (y > 0){
			int y2 = y -1;
			Tuple x1 = new Tuple(x,y2);
			output.add(x1);
		}
		if (y <= 8){
			int y2 = y + 1;
			Tuple x1 = new Tuple(x,y2);
			output.add(x1);
		}
		return output;
	}
} 
