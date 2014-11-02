import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class MainScreen {
	
	//Intialize all global variables needed 
	protected Display display; 
	protected Shell shl;
	
	private ArrayList<BattleShip> allbs = new ArrayList<BattleShip>();
	
	private Text xCoord;
	private Text yCoord;
	
	private int userShipNum = 7;
	private int aiShipNum = 7;
	
	private int numOfHits = 0;
	
	// console updates
	private int conTurn = -1;
	private int conPlayer = 1;
	private boolean conHit;
	private boolean conSunk;
	
	private Button bPause;
	
	private Grid userGrid;
	private Grid aiGrid;
	private Computer ai;
	
	private ConsoleLab console;
	private Label remainShips_1;
	private Label remainShips_2;
	private Label remainShips_3;
	private Label remainShips_4;
	
	private Image bg = new Image(display, "background.png");
	
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainScreen window = new MainScreen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shl.open();
		shl.layout();
		while (!shl.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shl = new Shell(display);
		shl.setBackgroundImage(bg);
		shl.setBackgroundMode(SWT.INHERIT_DEFAULT); // makes all componenets in the shell have a background alpha of 0
		shl.setMinimumSize(new Point(720, 720));
		shl.setSize(720, 720);
		shl.setLocation(10, 0);
		shl.setText("BATTLESHIP");
		
		ArrayList<BattleShip> aiShip = new ArrayList<BattleShip>();

		//add most componenets to the window
		addElements(shl);
		
		//initialize the grids
		Composs userComp = new Composs(shl, SWT.NONE);
		userComp.setBounds(10, 181, 309, 309);
		
		Composs aiComp = new Composs(shl, SWT.NONE);
		aiComp.setBounds(385, 181, 309, 309);
		
		//draw the console
		console = new ConsoleLab(shl,SWT.NONE);
		console.setBounds(385, 509, 309, 163);
		
		// draw the grids for the user and the ai
		userGrid = new Grid(false, userComp,allbs);
		ai = new Computer(userGrid,console);
		aiShip = ai.aiShipSet();
		aiGrid = new Grid(true, aiComp,aiShip);
		
		shl.pack();
	}

	// initialize and add componenets to the window
	public void addElements(Shell shl) {

		//Coordinate elements
		Label coordLbl = new Label(shl, SWT.NONE);
		coordLbl.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		coordLbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		coordLbl.setAlignment(SWT.CENTER);
		coordLbl.setBounds(213, 526, 166, 25);
		coordLbl.setText("Enter Coordinates");
			
		//coordinate entry boxes
		yCoord = new Text(shl, SWT.NONE);
		yCoord.setBounds(255, 557, 30, 25);
		
		xCoord = new Text(shl, SWT.NONE);
		xCoord.setBounds(300, 557, 30, 25);
			
		//fire confirm button
		Button bFire = new Button(shl, SWT.NONE);
		bFire.setBounds(255, 588, 75, 25);
		bFire.setText("FIRE!");
		bFire.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // can hit the space key to fire, when the button is selected
				if (e.keyCode == SWT.SPACE){
					if (!userGrid.lose()){
						fireAtShips();
					}
				}
			}
		});
		bFire.addMouseListener(new MouseAdapter() { // can click the fire button to fire
			@Override
			public void mouseUp(MouseEvent e) {
				if (!userGrid.lose()){
					fireAtShips();
				}
			}
		});
		
		//pause button
		bPause = new Button(shl, SWT.NONE);
		bPause.setTouchEnabled(true);
		bPause.setBounds(255, 619, 75, 25);
		bPause.setText("Pause");
		bPause.addMouseListener(new MouseAdapter() {
			@Override 	
			public void mouseUp(MouseEvent e) {
				pauseGame();
			}
		});
		
		// Ship number labels, show ship numbers
		remainShips_1 = new Label(shl, SWT.NONE);
		remainShips_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		remainShips_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		remainShips_1.setAlignment(SWT.CENTER);
		remainShips_1.setBounds(10, 526, 178, 25);
		remainShips_1.setText("Remaining User Ships");
		
		remainShips_2 = new Label(shl, SWT.NONE);
		remainShips_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		remainShips_2.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		remainShips_2.setAlignment(SWT.CENTER);
		remainShips_2.setBounds(10, 606, 190, 25);
		remainShips_2.setText("Remaining Enemy Ships");
		
		remainShips_3 = new Label(shl, SWT.NONE);
		remainShips_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		remainShips_3.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		remainShips_3.setAlignment(SWT.CENTER);
		remainShips_3.setBounds(78, 565, 36, 35);
		remainShips_3.setText(String.valueOf(userShipNum));
		
		remainShips_4 = new Label(shl, SWT.NONE);
		remainShips_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		remainShips_4.setAlignment(SWT.CENTER);
		remainShips_4.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		remainShips_4.setBounds(78, 637, 36, 35);
		remainShips_4.setText(String.valueOf(aiShipNum));
	}

	// methods gets all the user ship positions
	public void setShips(ArrayList<BattleShip> bShips) {
		this.allbs = bShips;
	}
	
	//method to pause the game
	public void pauseGame() {
		Pause pauseMenu = new Pause(shl, SWT.NONE); 
		pauseMenu.open();
		
	}
	
	// method used to fire on enemy ships
	public void fireAtShips() {
		if ((yCoord != null || xCoord != null)) {
			Tuple fireLocation = new Tuple(convertX(xCoord.getText()), convertY(yCoord.getText()));
			// check if the fire location is in a valid location
			if ((fireLocation.getX() > 9 || fireLocation.getX() < 0) || (fireLocation.getY() > 10 || fireLocation.getY() < 0)) {
				console.errMessage();
				yCoord.setText("");
				xCoord.setText("");
			}
			else{
				yCoord.setText("");
				xCoord.setText("");
				
				conTurn+=2;
				
				//Checks if location hit target; If hit adds to hitChecker
				BattleShip didHit =  aiGrid.hitAtBS(fireLocation);
				numOfHits++;
				if (didHit != null){
					conHit = true;
					if (didHit.didSink()){
						conSunk = true;
					}
					
				} else {
					conHit = false;
				}
				
				//print actions to the console
				console.newConsoleLine(conTurn,conPlayer, fireLocation, conHit);
				
				if (conSunk){
					console.newSunk();
					updateShipNum(0);  // updates the ship number labels
					conSunk = false;
				}
				
				if (aiGrid.lose()){
					//open the victory screen
					VictoryUI vui = new VictoryUI(true, numOfHits);
					shl.dispose();
					try {
						vui.open();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					//computers turn
					ai.compTurn();
					userShipNum = userGrid.getShipsRemaining();
					updateShipNum(1);
					if (userGrid.lose()){
						//show defeat screen
						VictoryUI vui = new VictoryUI(false, numOfHits);
						shl.dispose();
						try {
							vui.open();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}	// end else
			}
		}
	} // end method
	
	//updates the label display number of ships left
	public void updateShipNum(int num){
		// 0 -> update ai label
		// 1 -> update user label
		if (num == 0) {
			aiShipNum--;
			remainShips_4.setText(String.valueOf(aiShipNum));
		} 
		else if (num == 1) {
			remainShips_3.setText(String.valueOf(userShipNum));
		}
	}
	
	//converts the input from user, to a form usable by the back-end
	public int convertY(String letter) {
		String temp = letter.toUpperCase();
		temp = temp.trim();
		switch(temp) {
		case "A":
			return 0;
		case "B":
			return 1;
		case "C":
			return 2;
		case "D":
			return 3;
		case "E":
			return 4;
		case "F":
			return 5;
		case "G":
			return 6;
		case "H":
			return 7;
		case "I":
			return 8;
		case "J":
			return 9;
		default:
			return -1;
		}
	}
	
	//converts the second input to be usabel by the back-end
	public int convertX(String letter) {
		letter = letter.trim();
		switch(letter) {
		case "1":
			return 0;
		case "2":
			return 1;
		case "3":
			return 2;
		case "4":
			return 3;
		case "5":
			return 4;
		case "6":
			return 5;
		case "7":
			return 6;
		case "8":
			return 7;
		case "9":
			return 8;
		case "10":
			return 9;
		default:
			return -1;
		}
	}
}


