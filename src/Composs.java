import java.util.ArrayList;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;


public class Composs extends Composite {
	ArrayList<Canvas> set = new ArrayList<Canvas>();
	
	public Composs(Composite parent, int style) {
		super(parent, style);
		//setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		
		Label lblTop = new Label(this, SWT.NONE);
		lblTop.setBounds(29, 10, 382, 15);
		lblTop.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTop.setText("1       2        3        4        5        6        7        8       9      10");
		
		Label lblABC = new Label(this, SWT.NONE);
		lblABC.setFont(SWTResourceManager.getFont("Arial", 8, SWT.NORMAL));
		lblABC.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblABC.setBounds(13, 29, 8, 291);
		lblABC.setText("A\r\n\r\nB\r\n\r\nC\r\n\r\nD\r\n\r\nE\r\n\r\nF\r\n\r\nG\r\n\r\nH\r\n\r\nI\r\n\r\nJ");

		int posX = 25;
		int posY = 25;
		for (int y = 0; y < 10;y++){
			for(int x = 0 ; x < 10; x++){
				final Canvas canvas = new Canvas(this, SWT.NONE);
				canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				set.add(canvas);
				canvas.setBounds(posX, posY, 25, 25);
				posX = posX+28;
			}
			posY = posY+28;
			posX = 25;
		}
	}

	public void setColour(Tuple input, int colour){
		int tenth = input.getY();
		int dec = input.getX();
		int index = (tenth*10) + dec;
		Canvas x = set.get(index);
		
		switch (colour){
		case 1: x.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		break;
		case 2: x.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		break;
		case 3: x.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		break;
		case 4: x.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		break;
		default:  x.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		break;
		}
	}
	public void setRed(){
		//y.set
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}


}
