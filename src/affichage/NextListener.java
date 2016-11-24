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
public class NextListener implements ActionListener {

	/**
	 * 
	 */
	public NextListener() {
		// Rien de spécial à la construction
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */

	public void actionPerformed(ActionEvent arg0) {
		// quand on appuis sur le bouton on avance d'un pas dans l'environnement
		Environnement.getEnvironnement().progresser();
	}

}
