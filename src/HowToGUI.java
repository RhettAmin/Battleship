import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class HowToGUI {
	
	protected Shell shlHowToPlay;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HowToGUI window = new HowToGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlHowToPlay.open();
		shlHowToPlay.layout();
		while (!shlHowToPlay.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlHowToPlay = new Shell();
		shlHowToPlay.setSize(395, 523);
		shlHowToPlay.setText("How To Play\r\n");
		
		Label lblNewLabel = new Label(shlHowToPlay, SWT.NONE);
		lblNewLabel.setBounds(17, 10, 345, 420);
		lblNewLabel.setText("------------------------------------------------------------\r\n----------------------- Setting Ships ----------------------\r\n------------------------------------------------------------\r\n1) Check the bottom of the screen for the ship's size.\r\n2) Click on the grid to place the ship. The ship must be\r\n\tplaces either horizontally or vertically.\r\n3) If you place a ship or all the ships incorrectly on your\r\n\tgrid you have the option to \"clear\" or \"clear all\"\r\n\tships; using the buttons at the bottom of the window.\r\n\r\nColour Legend:\r\n\t\tRed = currently setting the ship\r\n\t\tGray = ship is set\r\n\r\n------------------------------------------------------------\r\n------------------------ HOW TO PLAY -----------------------\r\n------------------------------------------------------------\r\n1) Set your ships.\r\n2) Set Coordinates to Fire\r\n3) Press fire button\r\n\r\nGrid Index:\r\nRED: Hit ship\r\nBLUE: Miss\r\nGRAY: Player ship\r\nBLACK: Ship is sunk\r\n\r\nGOOD LUCK~\r\n");
		
		Button btnExit = new Button(shlHowToPlay, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlHowToPlay.dispose();
			}
		});
		btnExit.setBounds(287, 450, 75, 25);
		btnExit.setText("EXIT");

	
	}

}
