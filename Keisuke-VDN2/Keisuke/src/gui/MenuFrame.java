package gui;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.InputStreamReader;
import java.util.ArrayList;

import logic.GameState;
import logic.Grid;

/**
 * @desc Frame that displays the menu.
 * @author vilip
 */
public class MenuFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 5469304007304180616L;
	
	/**
	 * @desc Buttons.
	 */
	private JButton new_button, load_button, help_button, exit_button;
	
	/**
	 * @desc Preparing and adding components to menu frame.
	 * @env this.new_button, this.load_button, this.help_button, this.exit_button.
	 */
	public MenuFrame() {
		this.setTitle("Keisuke - Menu");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420, 420);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(4, 1, 0, 2));
		
		new_button = new JButton("New");
		load_button = new JButton("Load");
		help_button = new JButton("Help");
		exit_button = new JButton("Exit");
		
		new_button.setFocusable(false);
		load_button.setFocusable(false);
		help_button.setFocusable(false);
		exit_button.setFocusable(false);
		
		new_button.addActionListener(e -> {
			this.dispose();
			new SetupFrame();
		});
		load_button.addActionListener(e -> {
			read_file();
		});
		help_button.addActionListener(e -> {
			this.dispose();
			new HelpFrame();
		});
		exit_button.addActionListener(e -> {
			this.dispose();
			System.exit(0);
		});
		
		this.add(new_button);
		this.add(load_button);
		this.add(help_button);
		this.add(exit_button);
		
		this.setVisible(true);
	}
	
	
	/**
	 * @desc Reads from a game file.
	 * @env GameState.FILE, GameState.ROWS, GameState.COLS,
	 * @env GameState.PLAYING_GRID, GameState.SOLVED_GRID,
	 * @env GameState.ACROSS_VALUES, GameState.DOWN_VALUES,
	 * @env GameState.BLACK_SQUARES_PERCENTAGE, GameState.ENDLESS, GameState.TIME
	 * @env GameState.GAME_FRAME, GameState.SAVED
	 * @env this.load_button
	 */
	public void read_file() {
		JFileChooser fileChooser = new JFileChooser("saves/");
		if (fileChooser.showOpenDialog(load_button) == JFileChooser.APPROVE_OPTION) {
			GameState.FILE = fileChooser.getSelectedFile();
			if (GameState.FILE  == null) {
				return;
			}
			if (!GameState.FILE .getName().toLowerCase().endsWith(".txt")) {
				GameState.FILE  = new File(GameState.FILE .getParentFile(),
						GameState.FILE .getName() + ".txt");
			}
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(GameState.FILE ), "utf-8"));
				String row;
				ArrayList<String> rows = new ArrayList<>();
				do {
					row = reader.readLine();
					if (row.length() > 0) rows.add(row);
				} while (row.length() > 0);
				GameState.ROWS = rows.size();
				GameState.COLS = rows.get(0).split(" ").length;
				GameState.PLAYING_GRID = new Grid();
				for (int y = 0; y < GameState.ROWS; y++) {
					String[] row_data = rows.get(y).split(" ");
					for (int x = 0; x < GameState.COLS; x++) {
						GameState.PLAYING_GRID.set(y, x, row_data[x].charAt(0));
					}
				}
				row = "";
				rows = new ArrayList<>();
				do {
					row = reader.readLine();
					if (row.length() > 0) rows.add(row);
				} while (row.length() > 0);
				GameState.SOLVED_GRID = new Grid();
				for (int y = 0; y < GameState.ROWS; y++) {
					String[] row_data = rows.get(y).split(" ");
					for (int x = 0; x < GameState.COLS; x++) {
						GameState.SOLVED_GRID.set(y, x, row_data[x].charAt(0));
					}
				}
				GameState.ACROSS_VALUES = GameState.SOLVED_GRID.get_across();
				GameState.DOWN_VALUES = GameState.SOLVED_GRID.get_down();
				
				GameState.BLACK_SQUARES_PERCENTAGE = Double.parseDouble(reader.readLine());
				GameState.ENDLESS = Boolean.parseBoolean(reader.readLine());
				GameState.TIME = Integer.parseInt(reader.readLine());
				GameState.SAVED = true;
				
				this.dispose();
				GameState.GAME_FRAME = new GameFrame();
				
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
