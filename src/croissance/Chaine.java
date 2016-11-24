/**
 * 
 */
package croissance;

import java.util.ArrayList;
import java.util.List;

import param.Parametres;

/**
 * @author Chantôme Adrien, Detan Xavier, Van Houtte Vincent
 * 
 */
public class Chaine extends Symbole {

	/**
	 * L'age de la chaine
	 */
	protected int age;

	/**
	 * L'age max des symboles
	 */
	protected int MAX_AGE = Parametres.MAX_AGE;

	/**
	 * Les symboles de la chaine (ses éléments)
	 */
	private ArrayList<Symbole> symboles;

	/**
	 * L'initialisation de l'objet se fait en lui indiquant directement la liste
	 * des symboles qu'il contient.
	 * 
	 * @param symboles
	 *            : la liste des symboles a l'initialisation de la chaine.
	 */
	public Chaine(ArrayList<Symbole> symboles, Capteur capteur) {
		MAX_AGE = Parametres.MAX_AGE;

		this.symboles = symboles;
		this.capteur = capteur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#getModules()
	 */
	public List<Module> getModules() {
		ArrayList<Module> chaineRetour;
		chaineRetour = new ArrayList<Module>();

		// pour tous les symboles de la chaine
		for (int i = 0; i < symboles.size(); i++) {
			// on recupère leurs modules
			chaineRetour.addAll(symboles.get(i).getModules());
		}
		// on retourne la liste des modules ainsi crée
		return chaineRetour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#step()
	 */
	public Symbole step() {
		// si on est trop vieux
		if (age >= MAX_AGE) {
			// alors on meurt
			return null;
		}
		// sinon on fait evoluer tous nos symboles
		for (int i = 0; i < symboles.size(); i++) {
			Symbole temp = symboles.get(i).step();
			// si le symbole n'est pas mort
			if (temp != null) {
				// on l'enregistre à sa place
				symboles.set(i, temp);
			} else {
				// sinon on l'enlève
				symboles.remove(i);
				// et on adapte le compteur pour continuer correctement
				i--;
			}
		}
		// on vielli d'un tour
		age++;

		return this;
	}

	

	public double getCoutCreation() {
		double cout = 0;
		for (Symbole symbole : symboles) {
			if (symbole instanceof Element) {
				cout += ((Element)symbole).getCout(); 
			}
		}
		return cout;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#activer()
	 */
	public void activer() {
		// pour activer une chaine, on active tous ses symboles
		for (int i = 0; i < symboles.size(); i++) {
			symboles.get(i).activer();
		}
	}

	/**
	 * Cette fonction permet de faire payer le cout de la vie a la plante. Nous
	 * prenons le partit de considérer que la réserve d'energie est réduite a 0
	 * si la nécessité en énergie est supérieur a la quantité d'énergie stockée
	 * avant le coutd e la vie.
	 * 
	 * On obtiendra ainsi une stagnation de la taille de la plante.
	 */
	public void payerVie() {
		for (int i = 0; i < symboles.size(); i++) {
			symboles.get(i).payerVie();
		}
	}

	/**
	 * cette fonction permet de rendre au terrain le cout énergétique consommé.
	 */
	public void rendreCoutVie() {
		for (int i = 0; i < symboles.size(); i++) {
			symboles.get(i).rendreCoutVie();
		}
	}


	
}
