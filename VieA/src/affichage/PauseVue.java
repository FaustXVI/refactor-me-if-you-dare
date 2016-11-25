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
public class PauseVue implements ActionListener {

	/**
	 * La vue de l'environnement traitee
	 */
	private final VueLourde ve;

	/**
	 * Le bouton qu'on ecoute
	 */
	private final JButton bouton;

	/**
	 * Permet de mettre en pause des vues lourdes
	 * 
	 * @param ve1
	 *            La vue lourde à mettre en pause
	 * @param bouton1
	 */
	public PauseVue(final VueLourde ve1, final JButton bouton1) {
		this.ve = ve1;
		this.bouton = bouton1;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		// on switch l'activité de la vue
		this.ve.setActif(!this.ve.isActif());

		// on change le texte en fonction de la prochaine action
		if (this.ve.isActif()) {
			this.bouton.setText(this.bouton.getText().replace("Lancer",
					"Arreter"));
		} else {
			this.bouton.setText(this.bouton.getText().replace("Arreter",
					"Lancer"));
		}
	}

}
