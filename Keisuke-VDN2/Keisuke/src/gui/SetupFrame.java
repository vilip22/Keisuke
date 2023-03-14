package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;

import logic.GameState;
import logic.Grid;

/**
 * @desc Frame that displays the setup menu for the game.
 * @author vilip
 */
public class SetupFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 4120194782201821752L;
	
	/**
	 * @desc Labels.
	 */
	private JLabel rows_label, cols_label, black_squares_label, endless_label;
	
	/**
	 * @desc Buttons for navigation.
	 */
	private JButton back_button, continue_button;
	
	/**
	 * @desc Combo Boxes containing possible grid sizes.
	 */
	private JComboBox<Integer> rows_combobox, cols_combobox;
	
	/**
	 * @desc Slider for black square percentage.
	 */
	private JSlider black_squares_slider;
	
	/**
	 * @desc Check Box for endless mode.
	 */
	private JCheckBox endless_checkbox;

	/**
	 * @desc Preparing and adding components to setup frame.
	 * @env this.rows_label, this.rows_combobox
	 * @env this.cols_label, this.cols_combobox
	 * @env this.back_button, this.continue_button
	 * @env this.black_squares_label, this.black_squares_slider
	 * @env this.endless_label, this.endless_checkbox
	 */
	public SetupFrame() {
		this.setTitle("Keisuke - Setup");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 5);
		
		rows_label = new JLabel("Rows: ");
		cols_label = new JLabel("Columns: ");
		endless_label = new JLabel("Endless mode: ");
		black_squares_label = new JLabel("Black squares percentage: ");
		
		Integer[] rows_values = new Integer[GameState.MAX_ROWS-GameState.MIN_ROWS+1];
		for (int i = 0; i < rows_values.length; i++) {
			rows_values[i] = i + GameState.MIN_ROWS;
		}		
		Integer[] cols_values = new Integer[GameState.MAX_COLS-GameState.MIN_COLS+1];
		for (int i = 0; i < cols_values.length; i++) {
			cols_values[i] = i + GameState.MIN_COLS;
		}

		rows_combobox = new JComboBox<Integer>(rows_values);
		cols_combobox = new JComboBox<Integer>(cols_values);
		
		black_squares_slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 20);
		black_squares_slider.setPaintTicks(true);
		black_squares_slider.setPaintLabels(true);
		black_squares_slider.setMajorTickSpacing(20);
		black_squares_slider.setMinorTickSpacing(5);
		
		endless_checkbox = new JCheckBox();
		
		back_button = new JButton("Back");
		continue_button = new JButton("Continue");
		
		back_button.setFocusable(false);
		continue_button.setFocusable(false);
		
		back_button.addActionListener(e -> {
			this.dispose();
			new MenuFrame();
		});
		continue_button.addActionListener(e -> {
			game_setup();
			this.dispose();
			GameState.GAME_FRAME = new GameFrame();
		});
		
		JComponent[] components = {
				rows_label, rows_combobox,
				cols_label, cols_combobox,
				black_squares_label, black_squares_slider,
				endless_label, endless_checkbox,
				back_button, continue_button
		};
		
		for (int i = 0; i < components.length; i+=2) {
			gbc.gridy = i;
			gbc.weightx = 0.4;
			gbc.gridx = 0;
			this.add(components[i], gbc);
			gbc.weightx = 0.6;
			gbc.gridx = 1;
			this.add(components[i+1], gbc);
		}
		
		this.setVisible(true);
	}
	
	/**
	 * @desc Set-up game state with chosen options.
	 * @env GameState.ROWS, GameState.COLS,
	 * @env GameState.PLAYING_GRID, GameState.SOLVED_GRID,
	 * @env GameState.ACROSS_VALUES, GameState.DOWN_VALUES,
	 * @env GameState.BLACK_SQUARES_PERCENTAGE, GameState.ENDLESS, GameState.TIME
	 * @env GameState.GAME_FRAME, GameState.SAVED, GameState.TIMER
	 */
	public void game_setup() {
		GameState.ROWS = (int) this.rows_combobox.getSelectedItem();
		GameState.COLS = (int) this.cols_combobox.getSelectedItem();
		GameState.BLACK_SQUARES_PERCENTAGE = black_squares_slider.getValue() / 100.0;
		GameState.ENDLESS = this.endless_checkbox.isSelected();
		GameState.PLAYING_GRID = new Grid();
		GameState.PLAYING_GRID.fill_black_squares();
		GameState.SOLVED_GRID = new Grid(GameState.PLAYING_GRID.get());
		GameState.SOLVED_GRID.fill_random();
		GameState.ACROSS_VALUES = GameState.SOLVED_GRID.get_across();
		GameState.DOWN_VALUES = GameState.SOLVED_GRID.get_down();
		GameState.TIME = 0;
		GameState.TIMER = new Timer(1000, e -> {
			GameState.TIME++;
		});
		GameState.TIMER.start();
		GameState.SAVED = false;
	}
	
}
