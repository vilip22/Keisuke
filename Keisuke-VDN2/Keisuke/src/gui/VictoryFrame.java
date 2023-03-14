package gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logic.GameState;

public class VictoryFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = -8066117055361638510L;
	
	/**
	 * @desc Panels.
	 */
	private JPanel victory_message_panel, buttons_panel;;
	
	/**
	 * @desc Buttons.
	 */
	private JButton menu_button, review_button;

	/**
	 * @desc Preparing and adding components to victory frame.
	 * @env this.victory_message_panel, this.buttons_panel
	 * @env this.menu_button, this.review_button
	 */
	public VictoryFrame() {
		this.setTitle("Keisuke - Victory");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(200, 120);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		victory_message_panel = new JPanel();
		victory_message_panel.setLayout(new BoxLayout(victory_message_panel, BoxLayout.Y_AXIS));
		
		JLabel a = new JLabel("Congratulations!");
		a.setAlignmentX(Component.CENTER_ALIGNMENT);
		a.setAlignmentY(Component.CENTER_ALIGNMENT);
		a.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel b = new JLabel("You won!");
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setAlignmentY(Component.CENTER_ALIGNMENT);
		b.setHorizontalAlignment(SwingConstants.CENTER);
		
		victory_message_panel.add(a);
		victory_message_panel.add(b);
		
		buttons_panel = new JPanel();
		
		menu_button = new JButton("Menu");
		review_button = new JButton("Review");
		
		menu_button.setFocusable(false);
		review_button.setFocusable(false);
		
		menu_button.addActionListener(e -> {
			this.dispose();
			if (!GameState.SAVED) new SaveFrame(false);
			else {
				GameState.GAME_FRAME.dispose();
				new MenuFrame();
			}
		});
		review_button.addActionListener(e -> {
			this.dispose();
		});
		
		buttons_panel.add(menu_button);
		buttons_panel.add(review_button);
		
		this.add(victory_message_panel);
		this.add(buttons_panel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
