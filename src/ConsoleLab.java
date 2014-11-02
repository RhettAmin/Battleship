import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;


public class ConsoleLab extends Composite {
	private Text txtTurnXxPlayer;
	private String data;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConsoleLab(Composite parent, int style) {
		super(parent, style);
		
		txtTurnXxPlayer = new Text(this, SWT.BORDER|SWT.V_SCROLL);
		txtTurnXxPlayer.setEditable(false);
		txtTurnXxPlayer.setText("Welcome to BattleShips");
		txtTurnXxPlayer.setBounds(10, 22, 286, 121);

	}

	public void newConsoleLine(int turn,int user, Tuple location, Boolean hit){
		data = txtTurnXxPlayer.getText();
		String turnStr = String.valueOf(turn);
		String fired = String.format("%s%s%s", "(",convert(location),")");
		String person = null;
		if (user == 0){
			person = "Computer";
		}
		if (user == 1){
			person = "Player";
		}
		
		String newInput = "Turn : " + turnStr + " " + person + " fired at: " + fired + " Did Hit: "+ hit;
		
		txtTurnXxPlayer.setText(data + "\r\n" + newInput);
		txtTurnXxPlayer.setTopIndex(txtTurnXxPlayer.getLineCount()-1);
	}
	
	public void errMessage() {
		data = txtTurnXxPlayer.getText();
		String newInput = "Fired at Invalid Point!";
		txtTurnXxPlayer.setText(data + "\r\n" + newInput);
		txtTurnXxPlayer.setTopIndex(txtTurnXxPlayer.getLineCount()-1);
	}
	
	public void newSunk(){
		data = txtTurnXxPlayer.getText();
		String newInput = "A BattleShip has been sunk!";
		txtTurnXxPlayer.setText(data + "\r\n" + newInput);
		txtTurnXxPlayer.setTopIndex(txtTurnXxPlayer.getLineCount()-1);
	}
	
	public String convert(Tuple location) {
		
		int tempY = location.getX() + 1;
		int tempX = location.getY();
		String y = String.valueOf(tempY);
		String x;
		
		switch(tempX){
		case 0:
			x = "A";
			return x += "," + y;
		case 1:
			x = "B";
			return x += "," + y;
		case 2:
			x = "C";
			return x += "," + y;
		case 3:
			x = "D";
			return x += "," + y;
		case 4:
			x = "E";
			return x += "," + y;
		case 5:
			x = "F";
			return x += "," + y;
		case 6:
			x = "G";
			return x += "," + y;
		case 7:
			x = "H";
			return x += "," + y;
		case 8:
			x = "I";
			return x += "," + y;
		case 9:
			x = "J";
			return x += "," + y;
		default: 
			return null;
		}
		
		
		
	}
	
	
}
