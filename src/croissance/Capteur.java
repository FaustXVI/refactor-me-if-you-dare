/**
 * 
 */
package croissance;

import java.util.ArrayList;
import java.util.List;

import environnement.Environnement;

/**
 * @author Administrator
 * 
 */
public class Capteur {

	/**
	 * L'energie en stock dans la plante qui n'a pas été consomée
	 */
	private double energieCaptee;

	/**
	 * Le nombre d'elements qui requière des ressources dans la plante
	 */
	private int nbCapteurs;

	/**
	 * La plante a laquelle on est affectée
	 */
	private Chaine plante;

	/**
	 * Contient les graines de la plante
	 */
	private ArrayList<EltS> graines;

	/**
	 * Contient les racines de la plante
	 */
	private ArrayList<EltR> racines;

	/**
	 * Contient les appexs de la plante
	 */
	private ArrayList<EltA> appexs;
	
	/**
	 * Contient les tiges de la plante
	 */
	private ArrayList<EltT> tiges;
	
	/**
	 * L'abcisse de l'origine de la plante
	 */
	private int xOrigine;

	/**
	 * L'ordonnée de l'origine de la plante
	 */
	private int yOrigine;

	/**
	 * Constructeur du capteur
	 * 
	 * @param x
	 *            L'abcisse de l'origine de la plante
	 * @param y
	 *            L'ordonnée de l'origine de la plante
	 */
	public Capteur(int x, int y) {
		super();
		// on enregistre l'origine de la plante
		xOrigine = x;
		yOrigine = y;
		// on initialise tout
		energieCaptee = 0;
		nbCapteurs = 0;
		graines = new ArrayList<EltS>();
		racines = new ArrayList<EltR>();
		tiges = new ArrayList<EltT>();
		appexs = new ArrayList<EltA>();
	}

	/**
	 * Lecture de l'energie en stock
	 * 
	 * @return L'energie stockée par la plante
	 */
	public double getEnergieCaptee() {
		return energieCaptee;
	}

	/**
	 * Ajoute de l'énèrgie captée
	 * 
	 * @param energieCaptee
	 *            L'energie a captée
	 */
	public void addEnergieCaptee(double energieCaptee) {
		this.energieCaptee += energieCaptee;
	}

	/**
	 * Lecture du nombre de capteurs de la plante
	 * 
	 * @return Le nombre de capteurs de la plante
	 */
	public int getNbCapteurs() {
		return nbCapteurs;
	}

	/**
	 * Ajoute un certain nombre de capteurs
	 * 
	 * @param nbCapteurs
	 *            Le nombre de capteurs a ajouter
	 */
	public void addNbCapteurs(int nbCapteurs) {
		this.nbCapteurs += nbCapteurs;
	}

	/**
	 * Lecture des graines de la plante
	 * 
	 * @return Les graines de la plante
	 */
	public List<EltS> getGraines() {
		return graines;
	}

	/**
	 * Ajoute une graine a la liste
	 * 
	 * @param graine
	 *            La graine a ajouter
	 */
	public void addGraine(EltS graine) {
		graines.add(graine);
	}

	/**
	 * Enleve une graine a la liste
	 * 
	 * @param graine
	 *            La graine a enlever
	 */
	public void removeGraine(EltS graine) {
		graines.remove(graine);
	}

	/**
	 * Ajoute une racine a la liste
	 * 
	 * @param racine
	 *            La racine a ajouter
	 */
	public void addRacine(EltR racine) {
		racines.add(racine);
	}

	/**
	 * Ajoute une appex a la liste
	 * 
	 * @param appex
	 *            L'appex a ajouter
	 */
	public void addAppex(EltA appex) {
		appexs.add(appex);
	}
	
	/**
	 * Enleve une appex a la liste
	 * 
	 * @param appex
	 *            L'appex a enlever
	 */
	public void removeAppex(EltA appex) {
		appexs.remove(appex);
	}
	
	/**
	 * Ajoute une tige a la liste
	 * 
	 * @param tige
	 *            La tige a ajouter
	 */
	public void addTige(EltT tige) {
		tiges.add(tige);
	}
	
	/**
	 * Capte l'energie dans le sol
	 */
	public void capte() {
		// on fait capter toutes les racines
		for (EltR racine : racines) {
			addEnergieCaptee(Environnement.getEnvironnement().getEnergie(racine.getAbcisse(),
					racine.getOrdonnee()));	
		}
	}
	
	/**
	 * @return Toute l'energie contenu dans la plante
	 */
	public double energieContenue() {
		double energie = 0;
		
		// pour toutes les graines de la plante
		for (EltS graine : graines) {
			// on ajoute son energie
			energie += graine.energieContenue();
		}
		
		// pour toutes les appexs de la plante
		for (EltA appex : appexs) {
			// on ajoute son energie
			energie += appex.energieContenue();
		}
		
		return energie;
	}
	
	/**
	 * @return Le cout en energie de la plante
	 */
	public double getCout() {
		double cout = 0;
		
		// pour toutes les graines de la plante
		/*for (EltS graine : graines) {
			// on ajoute son cout
			cout += graine.getCout();
		}
		
		// pour toutes les appexs de la plante
		for (EltA appex : appexs) {
			// on ajoute son cout
			cout += appex.getCout();
		}*/
		
		// pour toutes les racines de la plante
		for (EltR racine : racines) {
			// on ajoute son cout
			cout += racine.getCout();
		}
		
		// pour toutes les tiges de la plante
		for (EltT tige : tiges) {
			// on ajoute son cout
			cout += tige.getCout();
		}
		
		return cout;
	}
	
	/**
	 * Lecture de la plante du capteur
	 * 
	 * @return La plante du capteur
	 */
	public Chaine getPlante() {
		return plante;
	}

	/**
	 * Affecter le capteur a une plante
	 * 
	 * @param plante
	 *            La plante du capteur
	 */
	public void setPlante(Chaine plante) {
		this.plante = plante;
	}

	/**
	 * @return L'abcisse de l'origine de la plante
	 */
	public int getxOrigine() {
		return xOrigine;
	}

	/**
	 * @return L'ordonnée de l'origine de la plante
	 */
	public int getyOrigine() {
		return yOrigine;
	}

}
