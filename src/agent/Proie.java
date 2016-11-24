/**
 * 
 */
package agent;

/**
 * @author detantxavi
 *
 */
public interface Proie {

	/**
	 * @return L'abscisse de la proie
	 */
	public double getX();
	
	/**
	 * @return L'ordonnee de la proie
	 */
	public double getY();
	
	/**
	 * Permet de manger la proie
	 * @return L'energie de la proie
	 */
	public double manger();
	
}
