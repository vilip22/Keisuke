package gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import logic.GameState;

public class SolutionFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 3947245963486290903L;
	
	/**
	 * @desc Solved grid panel.
	 */
	private GridPanel grid_panel;

	/**
	 * @desc Preparing and adding components to solution frame.
	 * @env this.grid_panel
	 */
	public SolutionFrame() {
		this.setTitle("Keisuke - Solution");
		this.setIconImage(GameState.LOGO_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
	            grid_panel.update_bounds();
	        }
		});
		
		grid_panel = new GridPanel(GameState.SOLVED_GRID);
		grid_panel.set_uneditable();
		
		this.add(grid_panel);
		
		this.setVisible(true);
	}
	
}
