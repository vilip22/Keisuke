package logic;

import javax.swing.ImageIcon;

import gui.MenuFrame;

/**
 * @desc Start-up for the program.
 * @author vilip
 */
public class StartUp {

	/**
	 * @desc Preparing GameState and starting the program.
	 * @param args
	 */
	public static void main(String[] args) {
		GameState.MIN_ROWS = 4;
		GameState.MIN_COLS = 4;
		GameState.MAX_ROWS = 20;
		GameState.MAX_COLS = 20;
		GameState.LOGO_ICON = new ImageIcon("src/imgs/ke.png");
		new MenuFrame();
	}
	
}
