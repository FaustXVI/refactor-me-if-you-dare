/**
 * 
 */
package croissance;

import java.util.List;

import param.Parametres;

/**
 * @author Chantôme Adrien, Detan Xavier, Van Houtte Vincent
 *
 */
public abstract class Symbole 
{
	
	/**
	 * Rend à l'environnement l'énèrgie nécessaire pour la survie de l'élément
	 */
	public abstract void rendreCoutVie();
	
	/**
	 * Fonction renvoyant la chaine de modules utilisée pour l'affichage de la pante.
	 * @return Une list de module
	 */
	public abstract List<Module> getModules();
	
	/**
	 * Fait évoluer la plante d'une "étape".
	 * @return le symbole modifié celon les règles spécifiques.
	 */
	public abstract Symbole step();

	/**
	 * Active le symbole (permet de calculer la prochaine génération)
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
	 * le payement du cout de la vie est null pour tous les éléments qui ne coutent rien.
	 */
	public void payerVie()
	{
		//Ne rien faire
	}
	
	/**
	 * l'objet s'occupant de gérer l'énergie captée et le nombre de capteurs.
	 */
	protected Capteur capteur;
	
	/**
	 * @return the capteur
	 */
	public Capteur getCapteur() {
		return capteur;
	}

}
