/**
 * 
 */
package affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import environnement.Environnement;

/**
 * @author detantxavi
 * 
 */
public class ResetListener implements ActionListener {

	/**
	 * La fenetre où on est situé
	 */
	private final VieA fenetre;

	/**
	 * @param fenetre1
	 * 
	 */
	public ResetListener(final VieA fenetre1) {
		// on enregistre la fenetre
		this.fenetre = fenetre1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */

	public void actionPerformed(final ActionEvent arg0) {
		// on inisialise l'environnement
		Environnement.getEnvironnement().initialiser();
		// et la fenetre
		this.fenetre.initialiser();
	}

}
