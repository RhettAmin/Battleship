import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;


public class Choose {

	//Initialize variables
	protected Display display;
	protected Shell shell;
	public MainScreen ms = new MainScreen();
	
	private ArrayList<Tuple> locations = new ArrayList<Tuple>();
	private ArrayList<Canvas> hit = new ArrayList<Canvas>();
	private ArrayList<Canvas> finalHit = new ArrayList<Canvas>();
	private ArrayList<BattleShip> allShip = new ArrayList<BattleShip>();
	private String prompt = "Create Ship with size: ";
	private int[] shipSize = {1,1,2,2,3,4,5};
	private int count = 0;
	private Image bg = new Image(display, "small.jpg");
	
	
	public static void main(String[] args) {
		try {
			Choose window = new Choose();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	//Checks if the points clicks are valid, then makes a ship there.
	private void checkTup(){
		if (locations.size()==shipSize[count]){ //check if the amount of points clicked equals the ship size needed
			boolean possible = isShipValid(locations); // checks if the points picked are valid shapes
			if (possible){
				ArrayList<Tuple> temp = new ArrayList<Tuple>();
				for(Tuple x: locations){
					temp.add(x);
				}
				addShip(temp);
				count++;
				for(Canvas canvas: hit){
					canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
					finalHit.add(canvas);
				}
				locations.clear();
				hit.clear();
			}
		}
	}
	
	//method used for method above, adds ship to an array holding pointers to all ships
	private void addShip(ArrayList<Tuple> x){
		BattleShip newShip = new BattleShip(x);
		this.allShip.add(newShip);
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(375, 500);
		shell.setText("Choose Your BattleShips");
		shell.setBackgroundImage(bg);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		//label tellingyou what ship size is needed
		final Label lbPrompt = new Label(shell, SWT.NONE);
		lbPrompt.setBounds(39, 340, 222, 20);
		lbPrompt.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lbPrompt.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbPrompt.setText(prompt + shipSize[count]);
		
		// button for clearing last known points not, used to make a ship
		Button btnClear = new Button(shell, SWT.NONE);
		btnClear.setBounds(39, 361, 75, 25);
		btnClear.setText("CLEAR");
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				locations.clear();
				for(Canvas canvas: hit){
					canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				hit.clear();
			}
		});
		
		// button to start the game
		final Button btnFinish = new Button(shell, SWT.NONE);
		btnFinish.setBounds(39, 392, 150, 25);
		btnFinish.setText("FINISH : START GAME");
		btnFinish.setVisible(false);
		btnFinish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				ms.setShips(allShip);
				ms.open();
			}
		});
		
		//clears all ships placed if positions not desired.
		Button btnClearAllShip = new Button(shell, SWT.NONE);
		btnClearAllShip.setBounds(120, 361, 157, 25);
		btnClearAllShip.setText("CLEAR ALL SHIP ENTRIES");
		btnClearAllShip.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for(Canvas canvas: finalHit){
					canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				for(Canvas canvas: hit){
					canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				count = 0;
				locations.clear();
				allShip.clear();
				hit.clear();
				lbPrompt.setText(prompt + shipSize[count]);
				btnFinish.setVisible(false);
			}
		});
		
		// draws the grid
		int posX = 40;
		int posY = 40;
		for (int y = 0; y < 10;y++){
			for(int x = 0 ; x < 10; x++){
				final int x2 = x;
				final int y2 = y;
				final Canvas canvas = new Canvas(shell, SWT.NONE);
				canvas.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent e) {
						if (count < 7){
							if(canvas.getBackground().equals((SWTResourceManager.getColor(SWT.COLOR_WHITE)))){
								canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
								Tuple input = new Tuple(x2,y2);
								locations.add(input);
								hit.add(canvas);
								if (count < 7){
									checkTup();
								}
								if (count <= 6){
									lbPrompt.setText(prompt + shipSize[count]);
								}
								if (count == 7){
									btnFinish.setVisible(true);
									lbPrompt.setText("Finished setting all Ships! Begin Game");
								}
							}
						}
					}
				});

				canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				canvas.setBounds(posX, posY, 25, 25);
				posX = posX+28;
			}
			posY = posY+28;
			posX = 40;
		}
	}
	
	
	//Checks if the entire ship is either vertical or horizontal
	private Boolean isShipValid(ArrayList<Tuple> coordinates){
		int[] x = new int[coordinates.size()];
		int[] y = new int[coordinates.size()];
		
		for(int i = 0; i < coordinates.size(); i++){
			x[i] = coordinates.get(i).getX();
			y[i] = coordinates.get(i).getY();
		}
		
		boolean isVertical = true;
		boolean isHorizontal = true;
		for(int i = 1; i < coordinates.size(); i++){
			if (x[i] != x[0]){ isVertical = false; break;}
		}
		for(int i = 1; i < coordinates.size(); i++){
			if (y[i] != y[0]){ isHorizontal = false; break;}
		}
		
		if(isVertical){
			return checkConsecutivity(y);
		}else if(isHorizontal){
			return checkConsecutivity(x);
		}else{
			return false;
		}
		
	}
	
	//Checks if the points chosen are grouped together linearly
	private Boolean checkConsecutivity(int[] myArray){
		Arrays.sort(myArray);
		
		int min = myArray[0];
		int pos = 0;
		for(int i = min; i < min + myArray.length; i++){
			if (i != myArray[pos]){
				return false;
			}
			pos++;
		}
		
		return true;
	}
}
