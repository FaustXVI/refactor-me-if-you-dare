/**
 * 
 */
package croissance;

/**
 * @author Chant�me Adrien, Detan Xavier, Van Houtte Vincent
 * 
 */
public abstract class Element extends Symbole {

	/**
	 * L'importance permet de r�partir l'�nergie capt�e sur les utilisateurs de
	 * fa�on in�gale.
	 */
	protected int importance;

	/**
	 * il s'agit ici de l'�nergie propre du symbole.
	 */
	protected double energie;

	/**
	 * Le cout �nerg�tique de cr�ation.
	 */
	protected double coutCreation;

	/**
	 * L'emplacement des �l�ments sur le terrain
	 */
	protected int x, y;

	/**
	 * L'�l�ment retourne son cout de cr�ation en �nergie.
	 */
	public double getCout() {
		return coutCreation;
	}

	/**
	 * Chaque �l�ment renvoie la quantit� d'�nergie contenue dans cet �l�ment.
	 */
	public double energieContenue() {
		return energie;
	}

	/**
	 * @return the x
	 */
	protected int getAbcisse() {
		return x;
	}

	/**
	 * @return the y
	 */
	protected int getOrdonnee() {
		return y;
	}
	
}
