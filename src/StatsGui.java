import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;


public class StatsGui {
	public static Shell shlStat;
	public static Table table_1;
	private final static FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private static Text txtTest;

	
	//method opens up the window
	public static void main(String[] args) {
		try {
			StatsGui window = new StatsGui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the shell.
	 * @throws IOException 
	 */
	public void open() throws IOException {
		Display display = Display.getDefault();
		createContents();
		shlStat.open();
		shlStat.layout();
		while (!shlStat.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	//updates the table values
	public static void update(){
		
		table_1.removeAll();
		for (int i = 0; i < Stats.size(); i++) {
			
		    TableItem item = new TableItem(table_1, SWT.NONE);
		      
		    String[] score = Stats.get(i);
		    String rank = String.valueOf(i+1);
		    item.setText(0, rank);
		    item.setText(1, score[0]);
		    item.setText(2, score[1]);
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() throws IOException {
		shlStat = new Shell();
		shlStat.setSize(340, 417);
		shlStat.setText("Statistics");
		shlStat.setLayout(new FormLayout());
		
		//gets the data thats read from the text file.
		Stats.getData();
		
		//Place the componenets on the screen
		Button btnClearTopScore = new Button(shlStat, SWT.NONE);
		FormData fd_btnClearTopScore = new FormData();
		btnClearTopScore.setLayoutData(fd_btnClearTopScore);
		btnClearTopScore.setText("Clear Top Score");
		btnClearTopScore.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(table_1.getItemCount()!= 0){
					Stats.remove(0);
				}
				update();
				try {
					Stats.saveData();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Closes the window
		Button btnExit = new Button(shlStat, SWT.NONE);
		fd_btnClearTopScore.top = new FormAttachment(btnExit, 0, SWT.TOP);
		fd_btnClearTopScore.right = new FormAttachment(btnExit, -6);
		FormData fd_btnExit = new FormData();
		fd_btnExit.right = new FormAttachment(100, -21);
		fd_btnExit.left = new FormAttachment(0, 257);
		fd_btnExit.bottom = new FormAttachment(100, -10);
		btnExit.setLayoutData(fd_btnExit);
		btnExit.setText("Exit");
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlStat.dispose();
			}
		});
		
		// Label at the top, nothing special, just a nice label to make the user feel welcome'd
		Label lblNewLabel = formToolkit.createLabel(shlStat, "High Scores for Battleships!", SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(0, 158);
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		
		// The table that's shown
		table_1 = new Table(shlStat, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table_1 = new FormData();
		fd_table_1.bottom = new FormAttachment(btnClearTopScore, -23);
		fd_table_1.top = new FormAttachment(lblNewLabel, 6);
		fd_table_1.left = new FormAttachment(0, 10);
		fd_table_1.right = new FormAttachment(100, -21);
		table_1.setLayoutData(fd_table_1);
		formToolkit.adapt(table_1);
		formToolkit.paintBordersFor(table_1);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		// The coloumn titles
		String[] titles = {"Rank","Name","Score"};
		for (int i = 0; i < titles.length; i++){
			TableColumn column = new TableColumn(table_1,SWT.NONE);
			column.setText(titles[i]);
			
		}
		
		//populate the table
		for (int i = 0; i < Stats.size(); i++) {
		      TableItem item = new TableItem(table_1, SWT.NONE);
		      String[] score = Stats.get(i);
		      String rank = String.valueOf(i+1);
		      item.setText(0, rank);
		      item.setText(1, score[0]);
		      item.setText(2, score[1]);
		    }
		for (int i=0; i<titles.length; i++) {
		      table_1.getColumn (i).pack ();
		    }
		table_1.setSize(table_1.computeSize(SWT.DEFAULT, 9));
		
		// Removes highscores by rank
		Button btnRemoveByRank = new Button(shlStat, SWT.NONE);
		btnRemoveByRank.setText("Remove by Rank");
		FormData fd_btnRemoveByRank = new FormData();
		fd_btnRemoveByRank.top = new FormAttachment(table_1, 23);
		fd_btnRemoveByRank.left = new FormAttachment(0, 10);
		btnRemoveByRank.setLayoutData(fd_btnRemoveByRank);
		btnRemoveByRank.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String input = txtTest.getText(); // get Input
				if (!(input.length() <= 2)){		//check if not empty
					Float o = Float.valueOf(input);
					Integer op = o.intValue();
					if(op <= Stats.size()){
						Stats.remove(op-1);
						update();
						try {
							Stats.saveData();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		// Where input is done , its a text field
		txtTest = new Text(shlStat, SWT.BORDER);
		txtTest.setText("\r\n");
		FormData fd_txtTest = new FormData();
		fd_txtTest.right = new FormAttachment(btnClearTopScore, -6);
		fd_txtTest.top = new FormAttachment(btnClearTopScore, 2, SWT.TOP);
		fd_txtTest.left = new FormAttachment(btnRemoveByRank, 6);
		txtTest.setLayoutData(fd_txtTest);
		formToolkit.adapt(txtTest, true, true);
		
		shlStat.open();
		
		

	}

}
