import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI_MineSweeper extends JFrame {    //My JFrame
	
	public static final int NUM_ROWS = 10;
	public static final int NUM_COLS = 10;
	public static final int NUM_BOMBS = 25;
	public int winningNum = NUM_ROWS * NUM_COLS - NUM_BOMBS;
	
	private JPanel jpMain;
	private MineSweeperBoard minesweeperboard; 
	private Grid grid;
	private boolean[][] bombGrid;
	private int[][] countGrid;
	
	public GUI_MineSweeper() {
		
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		
		grid = new Grid(NUM_ROWS, NUM_COLS, NUM_BOMBS);
		
		grid.PrintGridCount();
		grid.PrintGridBoo();
		
		bombGrid = grid.getBombGrid();
		countGrid = grid.getCountGrid();
		
		minesweeperboard = new MineSweeperBoard(); //created the board
		
		jpMain.add(minesweeperboard); //adding the board to the panel
		add(jpMain); //adding panel to the frame
		
		setTitle("MineSweeper");
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	private class MineSweeperBoard extends JPanel implements ActionListener {   //My JPanel
		
		private JButton [][] board;
		
		ImageIcon img = new ImageIcon("src/Bomb.png");
		ImageIcon bomb = new ImageIcon(img.getImage().getScaledInstance(35, 35, Image.SCALE_FAST));    //image for the bomb
		

		
		private MineSweeperBoard() {
			setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
			//setBackground(Color.YELLOW);
			displayBoard();
			
			
		}

		private void displayBoard() {
			
			board = new JButton[NUM_ROWS][NUM_COLS];
			
			for(int row=0; row<NUM_ROWS; row++){
				for(int col=0; col<board[row].length; col++){
					
					board[row][col] = new JButton(); //creating & initializing the button to the array
					board[row][col].setBackground(Color.GRAY);
					board[row][col].setOpaque(true);
					board[row][col].setSize(30, 30);
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
					board[row][col].setForeground(Color.BLACK);
					board[row][col].setFont(bigFont);
					board[row][col].addActionListener(this);

					
					board[row][col].setEnabled(true); 
					this.add(board[row][col]);            //add this button to the panel

			
			
		     }
			}
		  }
		
			public void actionPerformed(ActionEvent e) {  //the method for the ActionListener
				 
				int numbtnClicked = 0;
				JButton btnClicked = (JButton) e.getSource();
                                                                       //the user click the button
				int row = 0, col = 0;
				        
				      for (int r = 0; r < NUM_ROWS; r++) {
				    	  
				    	 for (int c = 0; c < NUM_COLS; c++) {
				    		 if (board[r][c].equals(btnClicked)) {
				    			
				    			 row = r;
				    			 col = c;
				    			 
				    			 
				    		 }
				    	
				    	 }
				      }
				
				if (bombGrid[row][col] == true) {
					//btnClicked.setForeground(Color.RED);
					btnClicked.setIcon(bomb);


					for (int r = 0; r < NUM_ROWS; r++) {   //revealing all the buttons
				    	  
				    	 for (int c = 0; c < NUM_COLS; c++) {
				    		 if (bombGrid [r][c] == true) { //found the bombs and revealing it
								//board[r][c].setForeground(Color.RED);
								
				    			board [r][c].setIcon(bomb);

				    		 }
				    		 else {
				    			 
				    			 int count = countGrid[r][c];
									String cnt = Integer.toString(count);
									board [r][c].setText(cnt);
				    		 }
				    	 }
				      }
					
					displayLost();
					askPlayAgain();
				}
				else {
					
					int count = countGrid[row][col];
					String cnt = Integer.toString(count);
					btnClicked.setText(cnt);
					
					
					if(count == 0) {               //we hit 0 thats why it reveals all buttons surrounding it
						
						if (row > 0) board[row - 1][col].setText(Integer.toString(countGrid[row - 1][col])); 

			            if (row > 0 && col > 0) board[row - 1][col - 1] .setText(Integer.toString(countGrid[row - 1][col - 1])); 

			            if (row > 0 && col < NUM_COLS - 1) board[row - 1][col + 1].setText(Integer.toString(countGrid[row - 1][col + 1])); 

			            if (row < NUM_ROWS - 1) board[row + 1][col] .setText(Integer.toString(countGrid[row + 1][col])); 

			            if (row < NUM_ROWS - 1 && col > 0) board[row + 1][col - 1] .setText(Integer.toString(countGrid[row + 1][col - 1])); 

			            if (col > 0) board[row][col - 1] .setText(Integer.toString(countGrid[row][col - 1])); 

			            if (col < NUM_COLS - 1) board[row][col + 1] .setText(Integer.toString(countGrid[row][col + 1]));

			            if (col < NUM_COLS - 1 && row < NUM_ROWS - 1) board[row + 1][col + 1] .setText(Integer.toString(countGrid[row + 1][col + 1]));
					}
					
					 for (int r = 0; r < NUM_ROWS; r++) {
				    	  
				    	 for (int c = 0; c < NUM_COLS; c++) {
				    		 if (!(board[r][c].getText().equals(""))) {
				    			 numbtnClicked ++; 
				    			 
				    			 if(numbtnClicked == winningNum) {
							        	
				 					displayWinner();
				 					askPlayAgain();
				 				}
				    			
				    			 
				    		 }
				    	
				    	 }
				      }
					 
					
			}
		}
			
			public void displayLost() {
				JOptionPane.showMessageDialog(null, "Oops! You Lost!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			
			}
			public void displayWinner() {
				JOptionPane.showMessageDialog(null, "Yay!! You Win!","Game Over", JOptionPane.INFORMATION_MESSAGE);
				

			}
			
			private void askPlayAgain(){
				int yesNo = JOptionPane.showConfirmDialog(null, "Do You Want To Play Again?", "Yes or No", JOptionPane.YES_NO_OPTION);
				if(yesNo == JOptionPane.YES_OPTION){					
					clearBoard();
				}
				else{
					System.exit(EXIT_ON_CLOSE);
				}
			}

			private void clearBoard() {
				
				for(int row=0; row<NUM_ROWS; row++){
					
					for(int col=0; col<board[row].length; col++){
						if(bombGrid[row][col] == true)board [row][col].setIcon(null);
						else board[row][col].setText("");	
					}
				}
				
				grid = new Grid(NUM_ROWS, NUM_COLS, NUM_BOMBS);      //Creating a new grid
				
				grid.PrintGridCount();
				grid.PrintGridBoo();
				bombGrid = grid.getBombGrid();
				countGrid = grid.getCountGrid();
			
			}
      }
}
