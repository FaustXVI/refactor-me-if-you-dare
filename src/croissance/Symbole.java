/**
 * 
 */
package croissance;

import java.util.List;

import param.Parametres;

/**
 * @author Chant�me Adrien, Detan Xavier, Van Houtte Vincent
 *
 */
public abstract class Symbole 
{
	
	/**
	 * Rend � l'environnement l'�n�rgie n�cessaire pour la survie de l'�l�ment
	 */
	public abstract void rendreCoutVie();
	
	/**
	 * Fonction renvoyant la chaine de modules utilis�e pour l'affichage de la pante.
	 * @return Une list de module
	 */
	public abstract List<Module> getModules();
	
	/**
	 * Fait �voluer la plante d'une "�tape".
	 * @return le symbole modifi� celon les r�gles sp�cifiques.
	 */
	public abstract Symbole step();

	/**
	 * Active le symbole (permet de calculer la prochaine g�n�ration)
	 */
	public void activer()
	{
		// on ne fais rien :D
	}
	/**
	 * La longueur moyenne d'une tige.
	 */
	protected static int longueurMoyenne = Parametres.LONGUEUR_MOYENNE_TIGE;
	
	/**
	 * l'angle moyen lors d'une rotation.
	 */
	protected static int angleMoyen = Parametres.ANGLE_MOYEN_PLANTE;
	
	/**
	 * le payement du cout de la vie est null pour tous les �l�ments qui ne coutent rien.
	 */
	public void payerVie()
	{
		//Ne rien faire
	}
	
	/**
	 * l'objet s'occupant de g�rer l'�nergie capt�e et le nombre de capteurs.
	 */
	protected Capteur capteur;
	
	/**
	 * @return the capteur
	 */
	public Capteur getCapteur() {
		return capteur;
	}

}
