/**
 * 
 */
package croissance;

/**
 * @author Chantôme Adrien, Detan Xavier, Van Houtte Vincent
 * 
 */
public class Module {
	/**
	 * La lettre qui represente l'élément
	 */
	public char lettre;

	/**
	 * Le nombre associé à l'élément (en général son énergie)
	 */
	public double nombre;

	/**
	 * Les coordonnées de l'élément
	 */
	public int x, y;

	/**
	 * Constructeur de module
	 * 
	 * @param lettre
	 *            La lettre de l'élément
	 * @param nombre
	 *            Son nombre associé
	 * @param x
	 *            L'abcisse de l'élément
	 * @param y
	 *            Son ordonnée
	 */
	public Module(char lettre, double nombre, int x, int y) {
		this.lettre = lettre;
		this.nombre = nombre;
		this.x = x;
		this.y = y;
	}
}
