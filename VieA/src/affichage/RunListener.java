/**
 * 
 */
package affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author detantxavi
 * 
 */
public class RunListener implements ActionListener {

	/**
	 * Vrai si le thread doit tourner
	 */
	boolean actif;

	/**
	 * Le bouton qu'on ecoute
	 */
	private final JButton bouton;

	/**
	 * @param bouton1
	 *            Le bouton ecouté
	 */
	public RunListener(final JButton bouton1) {
		// on est pas actif par defaut
		this.actif = false;
		// on enregistre le bouton
		this.bouton = bouton1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		// on change d'activité
		this.actif = !this.actif;
		final ThreadRun tr = ThreadRun.getInstance();

		// on active/desactive le thread
		tr.setActif(this.actif);

		// on change le texte en fonction de la prochaine action
		if (this.actif) {
			this.bouton.setText(this.bouton.getText().replace("Lancer",
					"Arreter"));
		} else {
			this.bouton.setText(this.bouton.getText().replace("Arreter",
					"Lancer"));
		}
	}
}
