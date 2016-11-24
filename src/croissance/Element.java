/**
 * 
 */
package croissance;

/**
 * @author Chantôme Adrien, Detan Xavier, Van Houtte Vincent
 * 
 */
public abstract class Element extends Symbole {

	/**
	 * L'importance permet de répartir l'énergie captée sur les utilisateurs de
	 * façon inégale.
	 */
	protected int importance;

	/**
	 * il s'agit ici de l'énergie propre du symbole.
	 */
	protected double energie;

	/**
	 * Le cout énergétique de création.
	 */
	protected double coutCreation;

	/**
	 * L'emplacement des éléments sur le terrain
	 */
	protected int x, y;

	/**
	 * L'élément retourne son cout de création en énergie.
	 */
	public double getCout() {
		return coutCreation;
	}

	/**
	 * Chaque élément renvoie la quantité d'énergie contenue dans cet élément.
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
