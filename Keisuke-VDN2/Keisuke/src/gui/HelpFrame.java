package gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logic.GameState;

public class HelpFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 7744684220646471300L;
	
	private JLabel help_label;
	
	private JButton back_button;

	public HelpFrame() {
		this.setTitle("Keisuke - Help");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		help_label = new JLabel();
		help_label.setText("<html><body>  Welcome to Keisuke! <br>"
				+ "<br> Keisuke is played on a rectangular grid, in which some cells of the grid are shaded. "
				+ "Additionally, external to the grid, several numeric values are given,"
				+ " some denoted as horizontal, and some denoted as vertical.<br>"
				+ " The puzzle functions as a simple numeric crossword puzzle."
				+ "The object is to fill in the empty cells with single digits, "
				+ "such that the given numeric values appear on the grid in the orientation specified."
				+ "<br><br>  To get started click on \"new\" or \"load\" button in the main menu."
				+ "<br>  \"new\" button allows you to set up a new game with chosen settings."
				+ "<br>  \"load\" button allows you to open an existing saved game."
				+ "<br><br>  Controls: "
				+ "<br>  mouse_wheel - zoom in / zoom out"
				+ "<br>  mouse_drag - move playing field <br> "
				+ "</body></html>");
		help_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		help_label.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JPanel a = new JPanel();
		a.setLayout(new BoxLayout(a, BoxLayout.Y_AXIS));
		a.add(new JScrollPane(help_label, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		this.add(a, BorderLayout.NORTH);
		
		back_button = new JButton("Back");
		back_button.setFocusable(false);
		back_button.addActionListener(e -> {
			this.dispose();
			new MenuFrame();
		});
		
		JPanel b = new JPanel();
		b.add(back_button);
		this.add(b, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
	
}
