import java.io.IOException;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class VictoryUI {

	protected Shell shlGameOver;
	private boolean winStatus;
	private Text txtInput;
	private Label lblScore;
	private int score;
	Display display;
	public Image imageVictory = new Image(display, "victory.png");
	public Image imageDefeat= new Image(display, "defeat.png");
	private Stats stats;
	private StatsGui statsGui = new StatsGui();
	
	public VictoryUI(boolean win){
		this.winStatus = win;	
	}
	public VictoryUI(boolean win , int numOfHits){
		this.winStatus = win;
		this.score = 5000-numOfHits*25;
	}
	public static void main(String[] args) {
		try {
			VictoryUI window = new VictoryUI(true);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void open() throws IOException {
		Display display = Display.getDefault();
		createContents();
		shlGameOver.open();
		shlGameOver.layout();
		while (!shlGameOver.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	protected void createContents() throws IOException {
		shlGameOver = new Shell();
		stats = new Stats();
		
		Stats.getData();
		
		shlGameOver.setSize(739, 491);
		shlGameOver.setText("Game Over!");
		
		
			final Button btnOpenStatistics = new Button(shlGameOver, SWT.NONE);
			btnOpenStatistics.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						Stats.clearData();
						statsGui.open();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnOpenStatistics.setText("Open Statistics");
			btnOpenStatistics.setFont(SWTResourceManager.getFont("Calibri", 13, SWT.NORMAL));
			btnOpenStatistics.setBounds(427, 410, 141, 30);
			
			Button btnExit = new Button(shlGameOver, SWT.NONE);
			btnExit.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shlGameOver.dispose();
				}
			});
			btnExit.setText("Exit");
			btnExit.setFont(SWTResourceManager.getFont("Calibri", 13, SWT.NORMAL));
			btnExit.setBounds(623, 410, 90, 30);
			
			shlGameOver.setBackgroundImage(imageDefeat);
			shlGameOver.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		if (winStatus){	
			shlGameOver.setBackgroundImage(imageVictory);
			shlGameOver.setBackgroundMode(SWT.INHERIT_DEFAULT);
			txtInput = new Text(shlGameOver, SWT.BORDER);
			btnOpenStatistics.setVisible(false);
			txtInput.setFont(SWTResourceManager.getFont("Calibri", 14, SWT.NORMAL));
			txtInput.setBounds(130, 411, 141, 28);
				
			Label lblEnterName = new Label(shlGameOver, SWT.NO_BACKGROUND);
			lblEnterName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblEnterName.setFont(SWTResourceManager.getFont("Calibri", 16, SWT.NORMAL));
			lblEnterName.setBounds(10, 410, 114, 28);
			lblEnterName.setText("Enter Name :");
							
			lblScore = new Label(shlGameOver, SWT.NO_BACKGROUND);
			lblScore.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lblScore.setFont(SWTResourceManager.getFont("Calibri", 22, SWT.NORMAL));
			lblScore.setBounds(10, 368, 193, 37);
			lblScore.setText("Score : " + score);
			
			final Button btnSave = new Button(shlGameOver, SWT.NONE);
			btnSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String pika = txtInput.getText().trim();
					if (pika.length() != 0){
						stats.newScore(txtInput.getText(), score);
						try {
							Stats.saveData();
							Stats.clearData();
							btnOpenStatistics.setVisible(true);
							btnSave.setVisible(false);
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					}
				});
				btnSave.setFont(SWTResourceManager.getFont("Calibri", 13, SWT.NORMAL));
				btnSave.setBounds(277, 410, 90, 30);
				btnSave.setText("Save");
		}
		

	}
}
