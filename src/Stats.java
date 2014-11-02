import java.io.*;
import java.util.*;
public class Stats {
    public static ArrayList<String[]> aList = new ArrayList<String[]>(); 
    
    public static void getData() throws IOException{
	//get data from data.score
	//store in an array?!
        String input;
        FileReader read = new FileReader("data.score");
        BufferedReader br = new BufferedReader(read);
                
        while ((input = br.readLine()) != null){
            String [] entry = input.split(",");
            aList.add(entry);
        }
        
        br.close();                
    }
    
    public static void saveData() throws IOException{
    	FileWriter textFileWriter = new FileWriter("data.score");
		BufferedWriter textWriter = new BufferedWriter(textFileWriter);
        for(int i = 0; i < aList.size(); i++){
            String[] asd = (String[]) aList.get(i);
            String name = asd[0];
            String score = asd[1];
            String myOutput = String.format("%s%s%s", name,",",score);
            textWriter.write (myOutput);
            textWriter.newLine();
        }
        textWriter.close();
    }
	
    public static ArrayList<String[]> getStat(){
    	return aList;
    }
    
    public static void switchAdd(int i,int p, String[]  y){
    	aList.add(i, y);
    	aList.remove(p+1);
    }
    
    public void newScore(String playerName, int playerScore){
    	String score = String.valueOf(playerScore);
    	String[] newScore = {playerName, score};
    	Boolean newPlayer = true;
    	Boolean remove = false;
    	int p = 0;
    	for(int i = 0; i < aList.size();i++){
    		String[] x = aList.get(i);
    		if(playerName.equals(x[0])){
    			if(playerScore > Integer.parseInt(x[1])){
    				p = i;
    				remove = true;    				
    			}
    			newPlayer=false;
    		}
    		
    	}
    	if (remove){
    		newPlayer = true;
    		aList.remove(p);
    	}
    	
    	if (newPlayer){
    		int i = aList.size();
    		
    		for(String[] x: aList){
    			if (playerScore >= Integer.parseInt(x[1])){
    				i--;
    			}
    			
    		}
    		aList.add(i, newScore);
    	}
    	
    	
    }
    
    public static void main(String[] args) throws IOException{
    }

	public static int size() {
		int x = aList.size();
		return x;
	}

	public static String[] get(int i) {
		String[] x = aList.get(i);
		return x;
	}

	public static void remove(int i) {
		aList.remove(i);
		
	}
	public static void clearData(){
		aList.clear();
	}

}
