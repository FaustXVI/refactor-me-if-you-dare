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
public class EltR extends Element {

	/**
	 * Constructeur d'une racine
	 * 
	 * @param x
	 *            L'abcisse d'une racine
	 * @param y
	 *            Son ordonnée
	 * @param capteur
	 *            Le capteur assoié à la plante
	 */
	public EltR(int x, int y, Capteur capteur) {
		importance = 1;
		energie = 0;
		coutCreation = Parametres.COUT_CREATION_R;

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
		// on produit notre module
		ArrayList<Module> listeRetour = new ArrayList<Module>();
		listeRetour.add(new Module('R', energie, x, y));
		// et on le retourne dans une liste
		return listeRetour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#step()
	 */
	public Symbole step() {
		// on ne change jamais
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
		capteur.addRacine(this);
	}
}
