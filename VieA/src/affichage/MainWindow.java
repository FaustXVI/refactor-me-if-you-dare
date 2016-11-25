package affichage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author Detant Xavier <xavier.detant@gmail.com>
 * @date 9 avr. 2010
 */
public class MainWindow implements ActionListener{
	
	/**
	 * 
	 * 
	 * @author Detant Xavier <xavier.detant@gmail.com>
	 * @date 9 avr. 2010
	 */
	private void createAndShowGUI() {
		
		// Create and set up the window.
		JFrame frame = new JFrame("Pandora");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create 2 buttons
		JButton nextButton = new JButton("Next");
		//JButton playButton = new JButton("Reset");
		
		// A panel to handle buttons
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(nextButton);
		//bottomPanel.add(playButton);
		
		// Listen button action
		nextButton.addActionListener(this);
		//playButton.addActionListener(this);

		
		// Main panel on which we draw Pandora
		VueEnvironnement ninjaPanel = new VueEnvironnement();
		frame.add(ninjaPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		
		// Display the window.
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * 
	 * 
	 * @author Detant Xavier <xavier.detant@gmail.com>
	 * @date 9 avr. 2010
	 * @param args
	 */
	public static void main(String[] args) {
	
		MainWindow window = new MainWindow();
		window.createAndShowGUI();
	}

	public void actionPerformed(ActionEvent ev) {
	
		JButton btn = (JButton) ev.getSource();
		if (btn.getText()=="Next"){
			//ninjaPanel.next();
		}
		
	}
}
