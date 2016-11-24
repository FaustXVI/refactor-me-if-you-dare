/**
 * 
 */
package croissance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public abstract class Mouvement extends Symbole {

	/**
	 * La lettre qui represente le mouvement
	 */
	protected char lettre;

	/**
	 * Constructeur de mouvement
	 * 
	 * @param lettre
	 *            La lettre representant le mouvement
	 */
	public Mouvement(char lettre) {
		this.lettre = lettre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#getModules()
	 */

	public List<Module> getModules() {
		// on crée notre module
		ArrayList<Module> listeRetour = new ArrayList<Module>();
		listeRetour.add(new Module(lettre, angleMoyen, 0, 0));
		// et on le retourne dans une liste
		return listeRetour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#step()
	 */
	public Symbole step() {
		// on évolue jamais
		return this;
	}

	/* (non-Javadoc)
	 * @see croissance.Symbole#rendreCoutVie()
	 */
	public void rendreCoutVie() {
		// on coute rien nous on est cool
	}
	
}
