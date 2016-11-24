/**
 * 
 */
package environnement;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import param.Parametres;
import agent.Agent;
import agent.AgentLoup;
import agent.AgentVache;
import croissance.Capteur;
import croissance.Chaine;
import croissance.EltA;
import croissance.EltR;
import croissance.EltS;
import croissance.Symbole;
import croissance.Terrain;

/**
 * @author Adrien Chant�me
 * 
 */
public class Environnement extends Observable {

	/**
	 * Les plantes de l'environnement
	 */
	private ArrayList<Chaine> plantes;

	/**
	 * La liste des capteurs de plantes
	 */
	private ArrayList<Capteur> capteurs;

	/**
	 * La liste des graines du terrain
	 */
	private ArrayList<EltS> graines;

	/**
	 * La liste des angles de depart des plantes
	 */
	private ArrayList<Integer> anglePlantes;

	/**
	 * La liste des vaches
	 */
	private ArrayList<Agent<EltS>> vaches;

	/**
	 * La liste des loups
	 */
	private ArrayList<Agent<Agent<EltS>>> loups;

	/**
	 * La taille du terrain (longueur, hauteur)
	 */
	private int tailleX, tailleY;

	/**
	 * L'environnement est un singleton. On a ici sa reference
	 */
	private static Environnement env;

	/**
	 * Le pourcentage de l'energie du terrain capt�
	 */
	private final double partieCapte = Parametres.POURCENTAGE_CAPTE;

	/**
	 * Le taux de diffusion du terrain
	 */
	private final double diffusion = Parametres.TAUX_DIFFUSION;

	/**
	 * Le terrain o� evolue la plante
	 */
	private Terrain terrain;

	/**
	 * La valeur max d'une case
	 */
	private final double maxCase = Parametres.ENERGIE_MAX_INITIAL;

	/**
	 * L'age du monde
	 */
	private int age;

	/**
	 * Constructeur priv� de l'environnement car singleton
	 */
	private Environnement() {
		tailleX = Parametres.TAILLE_X;
		tailleY = Parametres.TAILLE_Y;
		terrain = new Terrain(tailleX, tailleY, diffusion);
	}

	/**
	 * @return La reference vers l'environnement actif
	 */
	public static Environnement getEnvironnement() {
		// si la reference n'existe pas
		if (env == null) {
			// alors on la cr�e
			env = new Environnement();
			env.initialiser();
		}
		return env;
	}

	/**
	 * Fonction permettant d'initialiser l'environnement. Une plante est
	 * cr��e a une position al�atoire dans le terrain.
	 */
	public void initialiser() {

		// nouveau monde!
		age = 0;

		// on cr�e toutes les listes dont on a besoin
		plantes = new ArrayList<Chaine>();
		anglePlantes = new ArrayList<Integer>();
		vaches = new ArrayList<Agent<EltS>>();
		loups = new ArrayList<Agent<Agent<EltS>>>();
		capteurs = new ArrayList<Capteur>();
		graines = new ArrayList<EltS>();

		// on ajoute les plantes
		for (int i = 0; i < Parametres.START_PLANTE; i++) {
			nouvellePlante();
		}

		// on ajoute nos vaches
		for (int i = 0; i < Parametres.START_VACHE; i++) {
			vaches.add(new AgentVache());
		}

		// on ajoute nos loups
		for (int i = 0; i < Parametres.START_LOUP; i++) {
			loups.add(new AgentLoup());
		}

		// on initialise le terrain aleatoirement avec comme valeur max : 50
		terrain.init(maxCase);

		// on previens les observateurs qu'on a chang�
		setChanged();
		notifyObservers();
	}

	/**
	 * fonction permettant la progression d'une unit� de temps.
	 */
	public void progresser() {
		Chaine plante;

		// un an de plus
		age++;

		// on fait bouger les loups
		for (int i = 0; i < loups.size(); i++) {
			// on fait avancer le loup
			loups.get(i).move();
		}

		// on fait bouger les vaches
		for (int i = 0; i < vaches.size(); i++) {
			// on fait avancer la vache
			vaches.get(i).move();
		}

		// on initialise la liste des graines
		graines = new ArrayList<EltS>();

		// pour toutes les plantes
		for (int i = 0; i < plantes.size(); i++) {
			// on recup�re la plante
			plante = plantes.get(i);

			// on capte de l'energie
			plante.getCapteur().capte();
			// on paye la vie
			plante.payerVie();
			// on les fait evoluer
			if (plante.step() == null) {
				// si on existe plus
				plante.rendreCoutVie();
				terrain.ajouterResource(plante.getCapteur().getxOrigine(),
						plante.getCapteur().getyOrigine(), plante.getCapteur()
								.getEnergieCaptee());
				// on enleve la plante
				plantes.remove(i);
				// et son angle d'origine
				anglePlantes.remove(i);
				capteurs.remove(i);
				// on modifie le compteur pour continuer dans le bon ordre
				i--;
			} else {
				// si on arrive ici c'est que la plante vie toujours, on
				// enregistre donc ses graines
				graines.addAll(plantes.get(i).getCapteur().getGraines());
			}
		}

		// on fait evoluer le terrain
		terrain.step();

		if (age % Parametres.TAUX_RAFFRAICHISSEMENT == 0) {
			// on previens les observateurs qu'on a chang�
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * fonction permettant d'ajouter une nouvelle plante a l'environnement
	 * (apr�s l'�closion d'une graine par exemple)
	 */
	public void nouvellePlante() {
		ArrayList<Symbole> lst = new ArrayList<Symbole>();

		// on la place al�atoirement sur le terrain
		int x = (int) (Math.random() * tailleX);
		int y = (int) (Math.random() * tailleY);
		int angle = (int) (Math.random() * 360);

		// on enregistre son angle de d�part
		anglePlantes.add(new Integer(angle));

		// on cr�e son capteur
		Capteur c = new Capteur(x, y);

		// au debut une plante est juste une racine avec une appex pour evoluer
		lst.add(new EltR(x, y, c));
		lst.add(new EltA(x, y, angle, c));
		Chaine chaine = new Chaine(lst, c);

		// on active la plante
		chaine.activer();

		// on enregistre la plante sur le capteur
		c.setPlante(chaine);

		// on ajoute le capteur � la liste des capteurs
		capteurs.add(c);

		// on enregistre la plante � la liste des plantes
		plantes.add(chaine);
	}

	/**
	 * Cette fonction permet de capter de l'energie depuis le terrain en
	 * fonction des coordonn�es de captage.
	 * 
	 * @param x
	 *            : l'absice du point de captage
	 * @param y
	 *            : l'ordonn�e du point de captage
	 * @return l'�nergie disponnible dans le terrain.
	 */
	public double getEnergie(int x, int y) {
		double capte = terrain.getResource(x, y) * partieCapte;
		// on recup�re l'energie du terrain, on la consomme donc
		terrain.consommerResource(x, y, capte);
		return capte;
	}

	/**
	 * Lecture simple de l'energie d'une case
	 * 
	 * @param x
	 *            L'abscisse du point
	 * @param y
	 *            Son ordonn�e
	 * @return La valeur de la case
	 */
	public double readEnergie(int x, int y) {
		// on recup�re l'energie du terrain
		return terrain.getResource(x, y);
	}

	/**
	 * R�cup�ration de la taille x de l'environnement.
	 * 
	 * @return la taille de la dimention (x) de l'environnement.
	 */
	public int getTailleX() {
		return tailleX;
	}

	/**
	 * R�cup�ration de la taille y de l'environnement.
	 * 
	 * @return la taille de la dimention (y) de l'environnement.
	 */
	public int getTailleY() {
		return tailleY;
	}

	/**
	 * @return La valeur maximale d'une case � l'initialisation
	 */
	public double getMaxCase() {
		return maxCase;
	}

	/**
	 * @return La liste des plantes
	 */
	public List<Chaine> getPlantes() {
		return plantes;
	}

	/**
	 * @return La liste des vaches
	 */
	public List<Agent<EltS>> getVaches() {
		return vaches;
	}

	/**
	 * @return La liste des loups
	 */
	public List<Agent<Agent<EltS>>> getLoups() {
		return loups;
	}

	/**
	 * @return La list des angles des plantes
	 */
	public List<Integer> getAnglePlantes() {
		return anglePlantes;
	}

	/**
	 * Permet de recuperer la position des graines de l'environnement
	 * 
	 * @return La liste des graines de l'environnement
	 */
	public List<EltS> getGraines() {
		return graines;
	}

	/**
	 * Cette fonction permet aux plantes mourante de rendre leur �nergie au
	 * terrain.
	 * 
	 * @param energie
	 *            : la quantit� d'�nergie a rendre.
	 * @param x
	 *            : l'absice du point sur lequel l'�nergie est rendue.
	 * @param y
	 *            : l'ordonn�e du point sur lequel l'�nergie est rendue.
	 */
	public void rendreEnergie(double energie, int x, int y) {
		terrain.ajouterResource(x, y, energie);
	}

	/**
	 * @return Le nombre de graines presente dans l'environnement
	 */
	public int getNbGraines() {
		int graines = 0;

		// pour toutes les capteurs de l'environnement
		for (Capteur capteur : capteurs) {
			// on ajoute les graines � la liste
			graines += capteur.getGraines().size();
		}

		return graines;
	}

	/**
	 * @return L'energie total de l'environnement
	 */
	public double getEnergieTotal() {
		double energie = 0;

		// on recup�re l'energie totale du terrain
		for (int i = 0; i < tailleX; i++) {
			for (int j = 0; j < tailleY; j++) {
				energie += readEnergie(i, j);
			}
		}

		// on recup�re l'energie des plantes
		for (int i = 0; i < plantes.size(); i++) {
			energie += capteurs.get(i).getCout()
					+ capteurs.get(i).energieContenue()
					+ capteurs.get(i).getEnergieCaptee();

		}

		// on recup�re l'energie totale des vaches
		for (int i = 0; i < vaches.size(); i++) {
			energie += vaches.get(i).getEnergy();
		}

		// on recup�re l'energie totale des vaches
		for (int i = 0; i < loups.size(); i++) {
			energie += loups.get(i).getEnergy();
		}

		// on retourne le total
		return energie;
	}

	/**
	 * @return La vitesse moyenne des vaches
	 */
	public double getVitesseMoyVaches() {
		double retour = 0;

		// si on a des vaches
		if (vaches.size() != 0) {

			// on calcule la vitesse totale de toutes les vaches
			for (Agent<?> vache : vaches) {
				retour += vache.getVitesse();
			}
			// et on divise par le nombre de vaches
			return retour / vaches.size();

		} else {
			// si on a pas de vaches, la vitesse moyenne est nulle
			return 0;
		}
	}

	/**
	 * @return La vitesse moyenne des loups
	 */
	public double getVitesseMoyLoups() {
		double retour = 0;

		// si on a des loups
		if (loups.size() != 0) {

			// on calcule la vitesse totale de tous les loups
			for (Agent<?> loup : loups) {
				retour += loup.getVitesse();
			}
			// et on divise par le nombre de loups
			return retour / loups.size();

		} else {
			// si on a pas de loups, la vitesse moyenne est nulle
			return 0;
		}
	}

	/**
	 * @return L'esperance de vie moyenne des vaches
	 */
	public double getEsperanceMoyVaches() {
		double retour = 0;

		// si on a des vaches
		if (vaches.size() != 0) {

			// on calcule la vitesse totale de toutes les vaches
			for (Agent<?> vache : vaches) {
				retour += vache.getEsperanceVie();
			}
			// et on divise par le nombre de vaches
			return retour / vaches.size();

		} else {
			// si on a pas de vaches, la vitesse moyenne est nulle
			return 0;
		}
	}

	/**
	 * @return L'esperance de vie moyenne des loups
	 */
	public double getEsperanceMoyLoups() {
		double retour = 0;

		// si on a des loups
		if (loups.size() != 0) {

			// on calcule la vitesse totale de tous les loups
			for (Agent<?> loup : loups) {
				retour += loup.getEsperanceVie();
			}
			// et on divise par le nombre de loups
			return retour / loups.size();

		} else {
			// si on a pas de loups, la vitesse moyenne est nulle
			return 0;
		}
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

}
