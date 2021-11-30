
public class Run_Main {

	public static void main(String[] args) {
		
javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GUI_MineSweeper gui = new GUI_MineSweeper();
				
			}
		});

 }

}