package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.GameState;

public class SaveFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 4326819345300905223L;
	
	/**
	 * @desc Labels.
	 */
	private JLabel prompt_label;
	
	/**
	 * @desc Buttons.
	 */
	private JButton save_button, continue_button, cancel_button;

	/**
	 * @desc Preparing and adding components to save frame.
	 * @env this.save_button, this.continue_button, this.cancel_button
	 */
	public SaveFrame(boolean exit) {
		this.setTitle("Keisuke - Save");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(350, 150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		prompt_label = new JLabel(" Do you want to save this game? ");
		prompt_label.setFont(new Font("SansSerif", Font.PLAIN, 16));
		prompt_label.setForeground(new Color(0.3f, 0.65f, 1f));
		
		save_button = new JButton("Save");
		continue_button = new JButton("Don't Save");
		cancel_button = new JButton("Cancel");
		
		save_button.setFocusable(false);
		continue_button.setFocusable(false);
		cancel_button.setFocusable(false);
		
		save_button.addActionListener(e -> {
			if (GameState.SAVED) {
				GameState.GAME_FRAME.save_game_state();
				new MenuFrame();
			} else {
				GameState.GAME_FRAME.make_file();
			}
			if (exit) System.exit(0);
			this.dispose();
		});
		continue_button.addActionListener(e -> {
			if (exit) System.exit(0);
			this.dispose();
			GameState.GAME_FRAME.dispose();
			new MenuFrame();
		});
		cancel_button.addActionListener(e -> {
			this.dispose();
		});
		
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttons_panel.add(save_button);
		buttons_panel.add(continue_button);
		buttons_panel.add(cancel_button);
		
		this.add(prompt_label);
		this.add(buttons_panel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
