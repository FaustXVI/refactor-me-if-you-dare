/**
 * 
 */
package croissance;

import java.util.ArrayList;
import java.util.List;

import param.Parametres;
import environnement.Environnement;

/**
 * @author Administrator
 * 
 */
public class EltT extends Element {

	/**
	 * Constructeur de tige
	 * 
	 * @param x
	 *            L'abscisse de la tige
	 * @param y
	 *            Son ordonnée
	 * @param capteur
	 *            Le capteur centralisant les informations de la plante à
	 *            laquelle on est ratachée
	 * 
	 */
	public EltT(int x, int y, Capteur capteur) {
		importance = 0;
		energie = 0;
		coutCreation = Parametres.COUT_CREATION_T;

		this.x = x;
		this.y = y;

		this.capteur = capteur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#getModules()
	 */
	public List<Module> getModules() {
		// on crée notre module
		ArrayList<Module> listeRetour = new ArrayList<Module>();
		listeRetour.add(new Module('T', longueurMoyenne, x, y));
		// et on le retourne dans une liste
		return listeRetour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#step()
	 */
	public Symbole step() {
		// on evolue jamais
		return this;
	}

	/* (non-Javadoc)
	 * @see croissance.Symbole#rendreCoutVie()
	 */
	public void rendreCoutVie() {
		Environnement.getEnvironnement().rendreEnergie(coutCreation, x, y);
	}

	/* (non-Javadoc)
	 * @see croissance.Symbole#payerVie()
	 */
	public void payerVie() {
		Environnement.getEnvironnement().rendreEnergie(
				coutCreation * (Parametres.POURCENTAGE_COUT_VIE / 100), x, y);
		capteur.addEnergieCaptee(-coutCreation * (Parametres.POURCENTAGE_COUT_VIE / 100));
	}

	public void activer() {
		capteur.addTige(this);
	}

}
