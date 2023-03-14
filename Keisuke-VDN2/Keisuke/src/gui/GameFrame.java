package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import logic.GameState;

/**
 * @desc Frame that displays the game.
 * @author vilip
 */
public class GameFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = -7927156597267134363L;
	
	/**
	 * @desc Playing grid panel.
	 */
	private GridPanel grid_panel;
	
	/**
	 * @desc Contains values for the grid.
	 */
	private JPanel values_panel;
	
	/**
	 * @desc Contains buttons for the game.
	 */
	private JPanel header_panel;
	
	/**
	 * @desc Labels.
	 */
	private JLabel across_label, down_label, across_values_label, down_values_label, time_label, endless_label;
	
	/**
	 * @desc Combo boxes for displaying values of selected length.
	 */
	private JComboBox<Integer> across_combobox, down_combobox;
	
	/**
	 * @desc Buttons.
	 */
	private JButton menu_button, save_button, hint_button, solution_button, reset_button;

	/**
	 * @desc Preparing and adding components to game frame.
	 * @env this.grid_panel
	 */
	public GameFrame() {
		this.setTitle("Keisuke - Game");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(600, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
	            grid_panel.update_bounds();
	        }
		});
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent windowEvent) {
		    	if (GameState.SAVED) {
		    		GameState.GAME_FRAME.dispose();
		    		System.exit(0);
					GameState.TIMER.stop();
		    	} else {
		    		new SaveFrame(true);
		    	}
		    }
		});
		
		grid_panel = new GridPanel(GameState.PLAYING_GRID);
		this.add(grid_panel, BorderLayout.CENTER);
		
		setup_values_panel();
		setup_header_panel();
		
		this.setVisible(true);
		
		grid_panel.game_update();
	}
	
	/**
	 * @desc Set-up values panel.
	 * @env GameState.ACROSS_VALUES, GameState.DOWN_VALUES
	 * @env this.values_panel
	 * @env this.across_label, this.down_label, 
	 * @env this.across_values_label, this.down_values_label,
	 * @env this.across_combobox, this.down_combobox
	 */
	private void setup_values_panel() {
		values_panel = new JPanel();
		values_panel.setLayout(new BoxLayout(values_panel, BoxLayout.Y_AXIS));
		values_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		across_label = new JLabel("Across >>>    Length: ");
		down_label = new JLabel("Down: vvv    Length: ");
		
		Integer[] across_lengths = new Integer[GameState.ACROSS_VALUES.size()];
		int across_i = 0;
		for (Integer key : GameState.ACROSS_VALUES.keySet()) {
			across_lengths[across_i++] = key;
		}
		across_combobox = new JComboBox<Integer>(across_lengths);
		across_combobox.addActionListener(e -> {
			@SuppressWarnings("unchecked")
			JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
			int index = (int) (source.getSelectedIndex());
			ArrayList<String> across_values_at_length 
					= GameState.ACROSS_VALUES.get(across_lengths[index]);
			String across_values_string = "  ";
			for (String val : across_values_at_length) {
				across_values_string += val + ", ";
			}
			across_values_label.setText(
					across_values_string.substring(0, across_values_string.length()-2));
		});
		
		Integer[] down_lengths = new Integer[GameState.DOWN_VALUES.size()];
		int down_i = 0;
		for (Integer key : GameState.DOWN_VALUES.keySet()) {
			down_lengths[down_i++] = key;
		}
		down_combobox = new JComboBox<Integer>(down_lengths);
		down_combobox.addActionListener(e -> {
			@SuppressWarnings("unchecked")
			JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
			int index = (int) (source.getSelectedIndex());
			ArrayList<String> down_values_at_length 
					= GameState.DOWN_VALUES.get(down_lengths[index]);
			String down_values_string = "  ";
			for (String val : down_values_at_length) {
				down_values_string += val + ", ";
			}
			down_values_label.setText(
					down_values_string.substring(0, down_values_string.length()-2));
		});
		
		JPanel across_header_panel = new JPanel();
		across_header_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		across_header_panel.add(across_label);
		across_header_panel.add(across_combobox);
		
		JPanel down_header_panel = new JPanel();
		down_header_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		down_header_panel.add(down_label);
		down_header_panel.add(down_combobox);
		
		if (across_lengths.length > 0) {
			ArrayList<String> across_values_at_length 
					= GameState.ACROSS_VALUES.get(across_lengths[0]);
			String across_values_string = "  ";
			for (String val : across_values_at_length) {
				across_values_string += val + ", ";
			}
			across_values_label = new JLabel(
					across_values_string.substring(0, across_values_string.length()-2));
		} else {
			across_values_label = new JLabel(" ");
		}
		
		if (down_lengths.length > 0) {
			ArrayList<String> down_values_at_length 
					= GameState.DOWN_VALUES.get(down_lengths[0]);
			String down_values_string = "  ";
			for (String val : down_values_at_length) {
				down_values_string += val + ", ";
			}
			down_values_label = new JLabel(
					down_values_string.substring(0, down_values_string.length()-2));
		} else {
			down_values_label = new JLabel(" ");
		}
		
		across_header_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		across_values_label.setAlignmentX(Component.LEFT_ALIGNMENT);
		down_header_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		down_values_label.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		values_panel.add(across_header_panel);
		values_panel.add(across_values_label);
		values_panel.add(down_header_panel);
		values_panel.add(down_values_label);
		values_panel.add(new JLabel(" "));
		
		this.add(new JScrollPane(values_panel, 
				JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.SOUTH);
	}
	
	/**
	 * @desc Set-up header panel.
	 * @env this.header_panel
	 * @env this.time_label, this.endless_label
	 * @env this.menu_button, this.save_button, this.hint_button
	 * @env this.solution_button, this.reset_button
	 */
	private void setup_header_panel() {
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		menu_button = new JButton("Menu");
		save_button = new JButton("Save");
		reset_button = new JButton("Reset");
		hint_button = new JButton("Hint");
		solution_button = new JButton("Solution");
		
		menu_button.setFocusable(false);
		save_button.setFocusable(false);
		reset_button.setFocusable(false);
		hint_button.setFocusable(false);
		solution_button.setFocusable(false);
		
		menu_button.addActionListener(e -> {
			if (GameState.SAVED) {
				save_game_state();
				this.dispose();
				new MenuFrame();
			} else {
				new SaveFrame(false);
			}
		});
		save_button.addActionListener(e -> {
			make_file();
		});
		reset_button.addActionListener(e -> {
			grid_panel.reset();
		});
		hint_button.addActionListener(e -> {
			grid_panel.evaluate_cells();
		});
		solution_button.addActionListener(e -> {
			new SolutionFrame();
		});
		
		buttons_panel.add(menu_button);
		buttons_panel.add(save_button);
		buttons_panel.add(reset_button);
		buttons_panel.add(hint_button);
		buttons_panel.add(solution_button);
		
		JPanel info_panel = new JPanel();
		info_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		time_label = new JLabel();
		int h = GameState.TIME / 3600;
		int m = (GameState.TIME - h * 3600) / 60;
		int s = GameState.TIME - h * 3600 - m * 60;
		time_label.setText("Time: " + ((h > 0) ? (h + ":") : "") 
				+ ((m > 0) ? (m + ":") : "") + s + " ");
		Timer t = new Timer(100, e -> {
			int hours = GameState.TIME / 3600;
			int minutes = (GameState.TIME - hours * 3600) / 60;
			int seconds = GameState.TIME - hours * 3600 - minutes * 60;
			time_label.setText("Time: " + ((hours > 0) ? (hours + ":") : "") 
					+ ((minutes > 0) ? (minutes + ":") : "") + seconds + " ");
		});
		t.start();
		
		endless_label = new JLabel();
		if (GameState.ENDLESS) endless_label.setText("Endless mode: ON    ");
		else endless_label.setText("Endless mode: OFF    ");
		
		info_panel.add(endless_label);
		info_panel.add(time_label);
		
		header_panel = new JPanel();
		header_panel.setLayout(new BorderLayout());
		
		header_panel.add(buttons_panel);
		header_panel.add(info_panel, BorderLayout.EAST);
		
		this.add(header_panel, BorderLayout.NORTH);
	}
	
	/**
	 * @desc Creates a game file.
	 * @env GameState.SAVED,
	 * @env this.save_button
	 */
	public void make_file() {
		JFileChooser fileChooser = new JFileChooser("saves/");
		if (fileChooser.showSaveDialog(save_button) == JFileChooser.APPROVE_OPTION) {
			GameState.FILE = fileChooser.getSelectedFile();
			if (GameState.FILE  == null) {
				return;
			}
			if (!GameState.FILE .getName().toLowerCase().endsWith(".txt")) {
				GameState.FILE  = new File(GameState.FILE .getParentFile(),
						GameState.FILE .getName() + ".txt");
			}
			save_game_state();
			GameState.SAVED = true;
		}
	}
	
	/**
	 * @desc Saves game state to file.
	 * @pre The file exists.
	 * @env GameState.FILE, GameState.ROWS, GameState.COLS,
	 * @env GameState.PLAYING_GRID, GameState.SOLVED_GRID,
	 * @env GameState.ENDLESS, GameState.TIME, GameState.BLACK_SQUARES_PERCENTAGE
	 */
	public void save_game_state() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(GameState.FILE ), "utf-8"));		
			writer.flush();	
			for (int y = 0; y < GameState.ROWS; y++) {
				String row = "";
				for (int x = 0; x < GameState.COLS; x++) {
					row += GameState.PLAYING_GRID.get()[y][x] + " ";
				}
				writer.write(row.trim() + "\n");
			}
			writer.write("\n");
			
			for (int y = 0; y < GameState.ROWS; y++) {
				String row = "";
				for (int x = 0; x < GameState.COLS; x++) {
					row += GameState.SOLVED_GRID.get()[y][x] + " ";
				}
				writer.write(row.trim() + "\n");
			}
			writer.write("\n");
			writer.write(GameState.BLACK_SQUARES_PERCENTAGE + "\n");
			writer.write(GameState.ENDLESS + "\n");
			writer.write(GameState.TIME + "\n");
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
