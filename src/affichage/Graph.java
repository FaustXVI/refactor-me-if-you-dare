/**
 * 
 */
package affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import param.Parametres;
import environnement.Environnement;

/**
 * @author detantxavi
 * 
 */
public class Graph extends ChartPanel implements Observer, VueLourde {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5756228374378724894L;

	/**
	 * Le nom de la courbe des plantes
	 */
	private final String strPlantes;

	/**
	 * L'historique du nombre de plantes
	 */
	private ArrayList<Point2D.Double> nbPlantes;

	/**
	 * Le nom de la courbe des gaines
	 */
	private final String strGraines;

	/**
	 * L'historique du nombre de graines
	 */
	private ArrayList<Point2D.Double> nbGraines;

	/**
	 * Le nom de la courbe de vitesse des vaches
	 */
	private final String strVitesseVaches;

	/**
	 * L'historique de la vitesse de vaches
	 */
	private ArrayList<Point2D.Double> vitesseVaches;

	/**
	 * Le nom de la courbe d'esperance des vaches
	 */
	private final String strEsperanceVaches;

	/**
	 * L'historique de l'esperance des vaches
	 */
	private ArrayList<Point2D.Double> esperanceVaches;

	/**
	 * Le nom de la courbe des vaches
	 */
	private final String strVaches;

	/**
	 * L'historique du nombre de vaches
	 */
	private ArrayList<Point2D.Double> nbVaches;

	/**
	 * Le nom de la courbe de vitesse des loups
	 */
	private final String strVitesseLoups;

	/**
	 * L'historique de la vitesse de loups
	 */
	private ArrayList<Point2D.Double> vitesseLoups;

	/**
	 * Le nom de la courbe d'esperance des loups
	 */
	private final String strEsperanceLoups;

	/**
	 * L'historique de l'esperance des loups
	 */
	private ArrayList<Point2D.Double> esperanceLoups;

	/**
	 * Le nom de la courbe des loups
	 */
	private final String strLoups;

	/**
	 * L'historique du nombre de loups
	 */
	private ArrayList<Point2D.Double> nbLoups;

	/**
	 * Vrai si on doit raffraichir l'affichage
	 */
	public volatile boolean vueActive = true;

	/**
	 * Creation du graph
	 */
	public Graph() {
		super(new Hashtable<String, Chart>());

		// on observe l'environnement
		Environnement.getEnvironnement().addObserver(this);

		this.strPlantes = "Nombre Plantes";
		this.strGraines = "Nombre Graines";
		this.strVaches = "Nombre Vaches";
		this.strVitesseVaches = "Vitesse des vaches";
		this.strEsperanceVaches = "Esperance de vie des vaches";
		this.strLoups = "Nombre Loups";
		this.strVitesseLoups = "Vitesse des loups";
		this.strEsperanceLoups = "Esperance de vie des loups";

		// on initialise tout
		this.init();

		// on place le panel correctement
		this.setPreferredSize(new Dimension(Environnement.getEnvironnement()
				.getTailleX()
				* Parametres.COTE_CASES, Environnement.getEnvironnement()
				.getTailleY()
				* Parametres.COTE_CASES));
		this.scale(0.1 * (Parametres.COTE_CASES / 4.0),
				1 * (Parametres.COTE_CASES / 4.0));
		this.center(3000, 350);

	}

	/**
	 * Ajoute les infos du graphe
	 */
	private void addInfos() {
		// on verifie la taille de l'historique
		this.limiterHistorique();

		final Environnement env = Environnement.getEnvironnement();

		// on ajoute un point pour les plantes
		this.nbPlantes.add(new Point2D.Double(env.getAge(), env.getPlantes()
				.size()));

		// un pour les graines
		this.nbGraines
				.add(new Point2D.Double(env.getAge(), env.getNbGraines()));

		// un pour les vaches
		this.nbVaches.add(new Point2D.Double(env.getAge(), env.getVaches()
				.size()));

		// un pour la vitesse moyenne des vaches
		this.vitesseVaches.add(new Point2D.Double(env.getAge(), env
				.getVitesseMoyVaches() * 10));

		// un pour l'esperance moyenne des vahces
		this.esperanceVaches.add(new Point2D.Double(env.getAge(), env
				.getEsperanceMoyVaches() / 10));

		// un pour les loups
		this.nbLoups
				.add(new Point2D.Double(env.getAge(), env.getLoups().size()));

		// un pour la vitesse moyenne des loups
		this.vitesseLoups.add(new Point2D.Double(env.getAge(), env
				.getVitesseMoyLoups() * 10));

		// un pour l'esperance moyenne des loups
		this.esperanceLoups.add(new Point2D.Double(env.getAge(), env
				.getEsperanceMoyLoups() / 10));
	}

	/**
	 * 
	 * 
	 * @author Detant Xavier <xavier.detant@gmail.com>
	 * @date 9 avr. 2010
	 */
	public void init() {
		// on initialise les plantes
		this.nbPlantes = new ArrayList<Point2D.Double>();
		this.add(this.strPlantes, this.nbPlantes);
		this.color(this.strPlantes, Color.GREEN);

		// on initialise les graines
		this.nbGraines = new ArrayList<Point2D.Double>();
		this.add(this.strGraines, this.nbGraines);
		this.color(this.strGraines, Color.ORANGE);

		// on initialise les vaches
		this.nbVaches = new ArrayList<Point2D.Double>();
		this.add(this.strVaches, this.nbVaches);
		this.color(this.strVaches, Color.RED);

		// on initialise leur vitesse
		this.vitesseVaches = new ArrayList<Point2D.Double>();
		this.add(this.strVitesseVaches, this.vitesseVaches);
		this.color(this.strVitesseVaches, new Color(0xAA0000));
		this.width(this.strVitesseVaches, 2);

		// on initialise leur esperance
		this.esperanceVaches = new ArrayList<Point2D.Double>();
		this.add(this.strEsperanceVaches, this.esperanceVaches);
		this.color(this.strEsperanceVaches, new Color(0xCC0000));
		this.width(this.strEsperanceVaches, 2);

		// on initialise les loups
		this.nbLoups = new ArrayList<Point2D.Double>();
		this.add(this.strLoups, this.nbLoups);
		this.color(this.strLoups, Color.BLUE);

		// on initialise leur vitesse
		this.vitesseLoups = new ArrayList<Point2D.Double>();
		this.add(this.strVitesseLoups, this.vitesseLoups);
		this.color(this.strVitesseLoups, new Color(0x0000AA));
		this.width(this.strVitesseLoups, 2);

		// on initialise leur esperance
		this.esperanceLoups = new ArrayList<Point2D.Double>();
		this.add(this.strEsperanceLoups, this.esperanceLoups);
		this.color(this.strEsperanceLoups, new Color(0x0000CC));
		this.width(this.strEsperanceLoups, 2);

		// on ajoute les infos de depart
		this.addInfos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see affichage.VueLourde#isActif()
	 */
	public boolean isActif() {
		return this.vueActive;
	}

	/**
	 * Permet de limiter la taille de l'historique
	 */
	private void limiterHistorique() {

		// si on a trop d'infos
		if (this.nbPlantes.size() > Parametres.TAILLE_HISTORIQUE) {
			// on supprimer les infos les plus anciennes
			this.nbPlantes.remove(0);
			this.nbGraines.remove(0);
			this.vitesseVaches.remove(0);
			this.esperanceVaches.remove(0);
			this.nbVaches.remove(0);
			this.nbLoups.remove(0);
			this.vitesseLoups.remove(0);
			this.esperanceLoups.remove(0);
			// on decale tous les points de 1
			/*
			 * for (int i = 0; i < nbPlantes.size(); i++) {
			 * nbPlantes.get(i).x--; nbGraines.get(i).x--;
			 * vitesseVaches.get(i).x--; esperanceVaches.get(i).x--;
			 * nbVaches.get(i).x--; nbLoups.get(i).x--; vitesseLoups.get(i).x--;
			 * esperanceLoups.get(i).x--; }
			 */
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see affichage.VueLourde#setActif(boolean)
	 */
	public void setActif(final boolean actif) {
		this.vueActive = actif;
	}

	public void update(final Observable arg0, final Object arg1) {
		// on recupère les infos
		this.addInfos();
		// si on est actif
		if (this.vueActive) {
			// on repaint le tout
			this.repaint();
		}
	}

}
