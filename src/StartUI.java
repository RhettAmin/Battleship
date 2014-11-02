import java.io.IOException;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class StartUI {

	protected Shell shell;
	Display display;

	public Image image = new Image(display, "battleshipbg.jpg");
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StartUI window = new StartUI();
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
		shell.open();
		shell.layout();
		shell.setText("BattleShips: The Final Frontier");
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(750, 550);
		shell.setText("SWT Application");
		shell.setBackgroundImage(image);
		final HowToGUI htp = new HowToGUI();
		final StatsGui sui = new StatsGui();
		
		
		
		Button btnStartGame = new Button(shell, SWT.NONE);
		btnStartGame.setBounds(601, 253, 105, 50);
		btnStartGame.setText("Start Game");
		btnStartGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Choose choose = new Choose();
				shell.dispose();
				choose.open();
			}
		});
		
		Button btnHowToPlay = new Button(shell, SWT.NONE);
		btnHowToPlay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					htp.open();
				
			}
		});
		btnHowToPlay.setText("How to Play");
		btnHowToPlay.setBounds(601, 312, 105, 50);
		
		Button btnGameStats = new Button(shell, SWT.NONE);
		btnGameStats.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					sui.open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGameStats.setText("Game Stats");
		btnGameStats.setBounds(601, 368, 105, 50);
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setText("Exit\r\n");
		btnExit.setBounds(601, 429, 105, 50);

	}

}
