/**
 * Contient l'ensemble des param�tres de l'application.
 */
package param;

/**
 * @author Adrien Chant�me
 * 
 */
public class Parametres {

	/******************************************************
	 * Parametres de l'affichage
	 *****************************************************/

	/**
	 * Taille de l'historique d'affichage du graphe
	 */
	public final static int TAILLE_HISTORIQUE = 50000;

	/**
	 * Taille par defaut d'un cot� du terrain (en pixels)
	 */
	public final static int COTE_CASES = 2;

	/**
	 * Le taux de raffraichissement de l'affichage
	 */
	public final static int TAUX_RAFFRAICHISSEMENT = 10;
	
	/******************************************************
	 * Parametres du terrain (le sol)
	 *****************************************************/

	/**
	 * Largeur de l'environnement
	 */
	public final static int TAILLE_X = 400;

	/**
	 * Hauteur de l'environnement
	 */
	public final static int TAILLE_Y = 400;

	/**
	 * Taux de diffusion de l'energie dans le sol. Compris entre 0 et 1. Plus il
	 * est haut plus l'energie se diffuse vite
	 */
	public final static double TAUX_DIFFUSION = 0.1;

	/**
	 * Energie maximale d'une case lors de l'initialisation. Chaque case aura
	 * une energie comprise entre 0 et cette valeur
	 */
	public final static double ENERGIE_MAX_INITIAL = 20;

	/******************************************************
	 * Parametres des plantes
	 *****************************************************/

	/**
	 * Le pourcentage du cout de cr�ation qui doit �tre pay� � chaque tour pour
	 * survivre
	 */
	public static final int POURCENTAGE_COUT_VIE = 5;
	
	/**
	 * La longueur d'une tige
	 */
	public final static int LONGUEUR_MOYENNE_TIGE = 4;

	/**
	 * L'angle d'une tige par rapport � sa base
	 */
	public final static int ANGLE_MOYEN_PLANTE = 35;

	/**
	 * L'age auquel meurt une plante
	 */
	public final static int MAX_AGE = 2000;

	/**
	 * Le nombre de plantes � l'initialisation
	 */
	public static final int START_PLANTE = 1000;

	/**
	 * Le pourcentage de l'energie capt� � chaque tour par chaque racine
	 */
	public final static double POURCENTAGE_CAPTE = 0.01;
	
	/**
	 * L'energie necessaire pour produire une tige
	 */
	public final static int COUT_CREATION_T = 15;

	/**
	 * L'energie necessaire pour produire une racine (c'est aussi le cout de
	 * maturit� d'une graine)
	 */
	public final static int COUT_CREATION_R = 20;

	/**
	 * L'importance de captage d'un appex. Plus il est haut plus l'appex pompe
	 * un pourcentage de l'energie capt� important
	 */
	public final static int IMPORTANCE_A = 1;

	/**
	 * L'importance de captage d'une graine. Plus il est haut plus la graine
	 * pompe un pourcentage de l'energie capt� important
	 */
	public final static int IMPORTANCE_S = 1;

	
	/******************************************************
	 * Parametres des vaches
	 *****************************************************/
		
	/**
	 * angle avec lequel max avec lequel on tourne (sauf quand on sent une graine)
	 */
	public static final double MAX_ANGLE_VACHE = Math.PI/3;
	
	/**
	 * valeur de la variation pour la vitesse des vaches
	 */
	public static final double ECART_VITESSE_MOYENNE_VACHE = 0;

	/**
	 * Vitesse moyenne d'une vache a la cr�ation
	 */
	public static final double VITESSE_MOYENNE_VACHE = 1;
	
	/**
	 * nombre de vache a l'initialisation
	 */
	public static final double START_VACHE = 200;

	/**
	 * distance (au carr�) a laquelle on sent les graines autour de nous
	 */
	public static final double DISTANCE_SNIFF_GRAINE = 10000;

	/**
	 * distance (au carr�) a laquelle on sent les vaches autour de nous
	 */
	public static final double DISTANCE_SNIFF_VACHE = 4000; 
	
	/**
	 * distance a laquelle on mange une graine
	 */
	public static final double DISTANCE_MANGER_VACHE = 1;
	
	/**
	 * distance a laquelle on se reproduie avec une autre vache
	 */
	public static final double DISTANCE_COPULATE_VACHE = 1;
	
	/**
	 * energie de depart de la vache
	 */
	public static final double START_ENERGY_VACHE = 75;
	
	/**
	 * cout de deplacement d'une vache
	 */
	public static final double COUT_DEPLACEMENT_VACHE = 0.05;
	
	/**
	 * seuil a partir duquel on a faim
	 */
	public static final double ENERGY_FAIM_VACHE = 70;
	
	/**
	 * seuil a partir duquel on a plus faim
	 */
	public static final double ENERGY_PAS_FAIM_VACHE = 80;

	/**
	 * valeur de la variation de la couleur d'une vache
	 */
	public static final double VARIATION_COULEUR_VACHE = 15;
	
	/**
	 * variation de la vitesse lors de la reproduction
	 */
	public static final double VARIATION_ENFANT_VITESSE_VACHE = 1;
	
	/**
	 * variation de l'esperance de vie a la variation
	 */
	public static final double VARIATION_ESPERANCE_VIE_VACHE = 50;
	
	/**
	 * esperance de vie moyenne d'une vache
	 */
	public static final double ESPERANCE_VIE_VACHE = 4000;
	
	/**
	 * age a partir duquel une vache peut se reproduite
	 */
	public static final double AGE_REPRODUCTION_VACHE = 300;
	
	/**
	 * Limite d'ecart entre les couleurs de deux vaches pour qu'elles puissent s'accoupler
	 */
	public static final double VARIATION_COULEUR_ACCOUPLEMENT_VACHE = 25;
	
	/******************************************************
	 * Parametres des loups
	 *****************************************************/
	
	/**
	 * vitesse de base d'une LOUP
	 */
	public static final double VITESSE_MOYENNE_LOUP = 1; 
	
	/**
	 * angle avec lequel max avec lequel on tourne (sauf quand on sent une graine)
	 */
	public static final double MAX_ANGLE_LOUP = Math.PI/3;
	
	/**
	 * valeur de la variation pour la vitesse des LOUPs
	 */
	public static final double ECART_VITESSE_MOYENNE_LOUP = 0;
	
	/**
	 * nombre de LOUP a l'initialisation
	 */
	public static final double START_LOUP = 20;

	/**
	 * distance (au carr�) a laquelle on sent les vaches autour de nous
	 */
	public static final double DISTANCE_SNIFF_MARGUERITE = 5*5;

	/**
	 * distance (au carr�) a laquelle on sent les LOUPs autour de nous
	 */
	public static final double DISTANCE_SNIFF_LOUP = 150 * 150; 
	
	/**
	 * distance a laquelle on mange une graine
	 */
	public static final double DISTANCE_MANGER_LOUP = 1;
	
	/**
	 * distance a laquelle on se reproduie avec une autre LOUP
	 */
	public static final double DISTANCE_COPULATE_LOUP = 1;
	
	/**
	 * energie de depart de la LOUP
	 */
	public static final double START_ENERGY_LOUP = 150;
	
	/**
	 * cout de deplacement d'une LOUP
	 */
	public static final double COUT_DEPLACEMENT_LOUP = 1;
	
	/**
	 * seuil a partir duquel on a faim
	 */
	public static final double ENERGY_FAIM_LOUP = 7;
	
	/**
	 * seuil a partir duquel on a plus faim
	 */
	public static final double ENERGY_PAS_FAIM_LOUP = 140;

	/**
	 * seuil a partir duquel on a envie de baise
	 */
	public static final double ENERGY_BAISE_LOUP = 90;
	
	/**
	 * valeur de la variation de la couleur d'une LOUP
	 */
	public static final double VARIATION_COULEUR_LOUP = 15;
	
	/**
	 * variation de la vitesse lors de la reproduction
	 */
	public static final double VARIATION_ENFANT_VITESSE_LOUP = 1;
	
	/**
	 * variation de l'esperance de vie a la variation
	 */
	public static final double VARIATION_ESPERANCE_VIE_LOUP = 50;
	
	/**
	 * esperance de vie moyenne d'une LOUP
	 */
	public static final double ESPERANCE_VIE_LOUP = 2000;
	
	/**
	 * age a partir duquel un LOUP peut se reproduire
	 */
	public static final double AGE_REPRODUCTION_LOUP = 150;
	
	/**
	 * Limite d'ecart entre les couleurs de deux loups pour qu'ils puissent s'accoupler
	 */
	public static final double VARIATION_COULEUR_ACCOUPLEMENT_LOUP = 255;
}
