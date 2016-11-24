/**
 * 
 */
package affichage;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @author detantxavi
 * 
 */
public class VieA extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3952456104805033221L;

	/**
	 * Le graph contenant les courbes
	 */
	private Graph courbes;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public VieA(String arg0) throws HeadlessException {
		super(arg0);
		// par defaut on quitte quand on clique la croix
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		// on ajoute les graphs
		courbes = new Graph();
		//add(courbes, BorderLayout.WEST);
		
		// on ajoute la vue textuelle
		TextEnvironnement texte = new TextEnvironnement();
		add(texte, BorderLayout.EAST);
		
		// on a la vue de l'environnement
		VueEnvironnement vue = new VueEnvironnement();
		add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,vue,courbes), BorderLayout.CENTER);
		
		// on ajoute un panel en bas pour les boutons
		JPanel bottomPanel = new JPanel();

		// on a un bouton Next pour avancer d'un pas
		JButton runButton = new JButton("Lancer la simulation");
		// on ajoute le lisener d'avancement d'un pas de temps
		runButton.addActionListener(new RunListener(runButton));
		bottomPanel.add(runButton);

		// on ajoute un bouton qui fait pause
		JButton pauseButtonGraph = new JButton("Arreter le graph");
		// on ajoute le lisener qui met la vue en pause
		pauseButtonGraph.addActionListener(new PauseVue(courbes,pauseButtonGraph));
		bottomPanel.add(pauseButtonGraph);
		
		// on ajoute un bouton qui fait pause
		JButton pauseButton = new JButton("Arreter la vue du monde");
		// on ajoute le lisener qui met la vue en pause
		pauseButton.addActionListener(new PauseVue(vue,pauseButton));
		bottomPanel.add(pauseButton);
		
		// on a un bouton Next pour avancer d'un pas
		JButton nextButton = new JButton("Next");
		// on ajoute le lisener d'avancement d'un pas de temps
		nextButton.addActionListener(new NextListener());
		bottomPanel.add(nextButton);

		// on a un bouton Reset pour tout remettre à 0
		JButton resetButton = new JButton("Reset");
		// on ajoute le listener d initialisation
		resetButton.addActionListener(new ResetListener(this));
		bottomPanel.add(resetButton);
		
		// on ajoute le panel à la fenetre
		add(bottomPanel,BorderLayout.SOUTH);
		
		// on pack la fenetre
		this.pack();
	}

	/**
	 * Reinitialise les vues qui en on besoin
	 */
	public void initialiser() {
		courbes.init();
	}
	
}
