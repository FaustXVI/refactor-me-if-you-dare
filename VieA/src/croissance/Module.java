/**
 * 
 */
package croissance;

/**
 * @author Chant�me Adrien, Detan Xavier, Van Houtte Vincent
 * 
 */
public class Module {
	/**
	 * La lettre qui represente l'�l�ment
	 */
	public char lettre;

	/**
	 * Le nombre associ� � l'�l�ment (en g�n�ral son �nergie)
	 */
	public double nombre;

	/**
	 * Les coordonn�es de l'�l�ment
	 */
	public int x, y;

	/**
	 * Constructeur de module
	 * 
	 * @param lettre
	 *            La lettre de l'�l�ment
	 * @param nombre
	 *            Son nombre associ�
	 * @param x
	 *            L'abcisse de l'�l�ment
	 * @param y
	 *            Son ordonn�e
	 */
	public Module(char lettre, double nombre, int x, int y) {
		this.lettre = lettre;
		this.nombre = nombre;
		this.x = x;
		this.y = y;
	}
}
