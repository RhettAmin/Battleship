import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class Pause extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 * @throws InterruptedException 
	 */
	public Pause(Shell parent, int style) {
		super(parent, style);
		this.setText("BattleShip");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.TITLE);
		shell.setMinimumSize(new Point(300, 125));
		shell.setSize(300, 169);
		shell.setLocation(250,300);
		shell.setText(getText());
		
		Button bResume = new Button(shell, SWT.NONE);
		bResume.setBounds(10, 45, 75, 25);
		bResume.setText("Resume");
		bResume.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.dispose();
			}
		});
		
		Button bQuit = new Button(shell, SWT.NONE);
		bQuit.setBounds(209, 45, 75, 25);
		bQuit.setText("Quit Game");
		bQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.exit(0);
			}
		});
		
		Label lblGamePaused = new Label(shell, SWT.NONE);
		lblGamePaused.setBounds(113, 10, 75, 15);
		lblGamePaused.setText("Game Paused");
		
		shell.pack();
		
		

	}
}
