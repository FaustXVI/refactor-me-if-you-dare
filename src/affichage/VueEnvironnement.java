/**
 * 
 */
package affichage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JPanel;

import param.Parametres;
import agent.Agent;
import croissance.Chaine;
import croissance.EltS;
import croissance.Module;
import environnement.Environnement;

/**
 * @author detantxavi
 * 
 */
public class VueEnvironnement extends JPanel implements Observer, VueLourde {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414591404149971460L;

	/**
	 * Le terrain qui est observï¿½
	 */
	Environnement terrain;

	/**
	 * La longueur d'une case du terrain
	 */
	private int coteX = Parametres.COTE_CASES;

	/**
	 * La hauteur d'une case du terrain
	 */
	private int coteY = Parametres.COTE_CASES;
	
	/**
	 * L'abscisse oï¿½ est situï¿½ la tortue
	 */
	private int xActuel;

	/**
	 * L'ordonnï¿½e oï¿½ est situï¿½ la tortue
	 */
	private int yActuel;

	/**
	 * L'angle de la tortue par rapport ï¿½ l'axe des abscisses orientï¿½ vers
	 * la droite
	 */
	private double angle;

	/**
	 * La liste des points enregistrï¿½s
	 */
	private Stack<Point> lstPoints = new Stack<Point>();

	/**
	 * La liste des angles enregistrï¿½s
	 */
	private Stack<Double> lstAngles = new Stack<Double>();

	/**
	 * Vrai si on doit raffraichir l'affichage
	 */
	public volatile boolean actif = true;

	/**
	 * Constructeur de la vue d'un terrain
	 */
	public VueEnvironnement() {
		// on recupï¿½re l'instance de l'environnemnt
		terrain = Environnement.getEnvironnement();
		// on observe le terrain
		terrain.addObserver(this);
		// on recupï¿½re la taille du terrain
		setPreferredSize(new Dimension(terrain.getTailleX() * coteX, terrain
				.getTailleY()
				* coteY));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		// si on doit rafraichir
		if (actif) {
			// on redessine le terrain
			repaint(0, 0, terrain.getTailleX()*coteX, terrain.getTailleY()*coteY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {

		coteX = this.getWidth() / terrain.getTailleX();
		coteY = this.getHeight() / terrain.getTailleY();
		
		// on affiche le terrain
		afficherTerrain(g);
		// on dessine la plante
		afficherPlantes(g);
		// on dessine des vaches de la mort qui tue !
		drawCows(g); // oui nous on parle anglais (760 au TOEIC) !!!!
		drawLoups(g);
	}

	/**
	 * Affiche le terrain
	 * 
	 * @param g
	 *            le graphique sur lequel on dessine
	 */
	private void afficherTerrain(Graphics g) {
		double max = terrain.getMaxCase();
		double valeurCase;
		float couleur;

		// pour toutes les cases du terrain
		for (int i = 0; i < terrain.getTailleX(); i++) {
			for (int j = 0; j < terrain.getTailleY(); j++) {
				// on recupï¿½re la valeur de la case
				valeurCase = terrain.readEnergie(i, j);

				// on creer la couleur adaptï¿½e
				couleur = Math
						.min(1, new Double(valeurCase / max).floatValue());
				g.setColor(new Color(couleur, couleur, couleur));

				// on affiche le rectangle
				g.fillRect(i * coteX, j * coteY, coteX, coteY);
			}
		}
	}

	/**
	 * Affiche la plante
	 * 
	 * @param g
	 *            le graphique sur lequel on dessine
	 */
	private void afficherPlantes(Graphics g) {
		List<Chaine> plantes = terrain.getPlantes();
		Chaine c;

		// on dessine en vert
		g.setColor(Color.GREEN);

		// pour toutes les plantes du terrain
		for (int i = 0; i < plantes.size(); i++) {
			c = plantes.get(i);
			// on recupï¿½re les coordonnï¿½e de depart de la plante
			angle = terrain.getAnglePlantes().get(i);
			xActuel = c.getModules().get(0).x;
			yActuel = c.getModules().get(0).y;
			// on l'affiche
			afficherModules(g, c.getModules());
		}
	}

	/**
	 * Affiche la vache
	 * 
	 * @param g
	 *            le graphique sur lequel on dessine
	 */
	private void drawCows(Graphics g) {
		List<Agent<EltS>> vaches = terrain.getVaches();

		// on dessine toutes les vaches
		for (int i = 0; i < vaches.size(); i++) {

			Agent<EltS> vache = vaches.get(i);
			double rayonX = 2 * coteX * (vache.getEnergy() / 100);
			double rayonY = 2 * coteY * (vache.getEnergy() / 100);
			
			// on dessine un rond centré sur la case de la vache
			g.setColor(vaches.get(i).getCouleur());
			g.fillOval((int) (coteX * (vache.getX() - rayonX)),
					(int) (coteY * (vache.getY() - rayonY)),
					(int) (coteX * rayonX * 2), (int) (coteY * rayonY * 2));

			// on fait un trait pour montrer l'oriantation de la vache
			if (vache.getFaim()) {
				// plus clair si elle a faim
				g.setColor(Color.WHITE);
			} else {
				// plus foncé sinon
				g.setColor(Color.BLACK);
			}
			
			if (vache.getAngleDirection() > 0) {
				g.drawLine((int)vache.getX() * coteX, (int)vache.getY() * coteY,
						coteX * (int) Math.round(vache.getX() + rayonX * Math.cos(vache.getAngleDirection())),
						coteY * (int) Math.round(vache.getY() + rayonY * Math.sin(vache.getAngleDirection())));
			}
		}

	}

	/**
	 * Affiche un loup
	 * 
	 * @param g
	 *            le graphique sur lequel on dessine
	 */
	private void drawLoups(Graphics g) {
		List<Agent<Agent<EltS>>> loups = terrain.getLoups();

		// on dessine toutes les vaches
		for (int i = 0; i < loups.size(); i++) {

			Agent<?> loup = loups.get(i);
			double rayonX = 2 * coteX * (loup.getEnergy() / 100);
			double rayonY = 2 * coteY * (loup.getEnergy() / 100);
			
			// on dessine un rond centré sur la case de la vache
			g.setColor(loups.get(i).getCouleur());
			g.fillRect((int) (coteX * (loup.getX() - rayonX)),
					(int) (coteY * (loup.getY() - rayonY)),
					(int) (coteX * rayonX * 2), (int) (coteY * rayonY * 2));

			// on fait un trait pour montrer l'oriantation de la vache
			if (loup.getFaim()) {
				// plus clair si elle a faim
				g.setColor(Color.WHITE);
			} else {
				// plus foncé sinon
				g.setColor(Color.BLACK);
			}
			
			if (loup.getAngleDirection() > 0) {
				g.drawLine((int)loup.getX() * coteX, (int)loup.getY() * coteY,
						coteX * (int) Math.round(loup.getX() + rayonX * Math.cos(loup.getAngleDirection())),
						coteY * (int) Math.round(loup.getY() + rayonY * Math.sin(loup.getAngleDirection())));
			}
		}

	}
	
	/**
	 * Affiche une liste de modules
	 * 
	 * @param g
	 *            le graphique sur lequel on dessine
	 * 
	 * @param c
	 *            La chaine a afficher
	 */
	private void afficherModules(Graphics g, List<Module> modules) {

		// pour tous les modules
		for (Module m : modules) {
			// on choisi la fonction par rapport ï¿½ la lettre
			switch (m.lettre) {
			case 'T':
				// T correspond ï¿½ une tige
				afficherTige(g, m.nombre);
				break;

			case 'R':
				// R correspond ï¿½ une racine
				afficherRacine(g, m.nombre);
				break;

			case 'S':
				// S correspond ï¿½ une spore
				afficherSpore(g, m.nombre);
				break;

			case 'A':
				// A correspond ï¿½ une appex
				afficherAppex(g, m.nombre);
				break;

			case '+':
				// + correspond ï¿½ un virage
				angle += m.nombre;
				break;

			case '-':
				// - correspond ï¿½ un virage negatif
				angle -= m.nombre;
				break;

			case '[':
				// on enregistre notre position
				lstAngles.push(new Double(angle));
				lstPoints.push(new Point(xActuel, yActuel));
				break;

			case ']':
				// on recupï¿½re la derniï¿½re position enregistrï¿½e
				angle = lstAngles.pop().doubleValue();
				Point tmp = lstPoints.pop();
				xActuel = tmp.x;
				yActuel = tmp.y;
				break;

			default:
				// si on arrive ici on a un truc inconnu
				System.err.println("Symbole inconnu : " + m.lettre);
				break;
			}
		}
	}

	/**
	 * @param g
	 *            le graphique sur lequel on dessine
	 * @param taille
	 *            La longueur de la tige
	 */
	private void afficherTige(Graphics g, double taille) {
		g.setColor(Color.GREEN);
		// on dessine la ligne
		g.drawLine(xActuel * coteX, yActuel * coteY,
				(int) (xActuel + Math.cos(Math.toRadians(angle)) * taille) * coteX,
				(int) (yActuel + Math.sin(Math.toRadians(angle)) * taille) * coteY);
		// on enregistre les nouvelles coordonnï¿½e
		xActuel = getX(xActuel, (int) angle, (int) taille);
		yActuel = getY(yActuel, (int) angle, (int) taille);
	}

	/**
	 * @param g
	 *            le graphique sur lequel on dessine
	 * @param valeur
	 *            euh.... ï¿½a sert ï¿½ rien pour l'instant non?
	 */
	private void afficherRacine(Graphics g, double valeur) {
		g.setColor(Color.BLUE);
		// on affiche un rectangle vide
		g.drawRect(xActuel * coteX, yActuel * coteY, coteX, coteY);
	}

	/**
	 * @param g
	 *            le graphique sur lequel on dessine
	 * @param valeur
	 *            son energie
	 */
	private void afficherSpore(Graphics g, double valeur) {
		g.setColor(Color.ORANGE);

		// on dessine un rond plein centré sur la case de la graine
		g.fillOval((xActuel * coteX) - (int) (coteX * (valeur / 10)),
				(yActuel * coteY) - (int) (coteY * (valeur / 10)),
				(int) (coteX * (valeur / 10)), (int) (coteY * (valeur / 10)));
	}

	/**
	 * @param g
	 *            le graphique sur lequel on dessine
	 * @param valeur
	 *            son energie
	 */
	private void afficherAppex(Graphics g, double valeur) {
		g.setColor(Color.GREEN);
		// on fait un croix lï¿½ ou on est
		g.drawLine(xActuel * coteX, yActuel * coteY, (xActuel * coteX) + coteX,
				(yActuel * coteY) + coteY);
		g.drawLine((xActuel * coteX) + coteX, yActuel * coteY, xActuel * coteX,
				(yActuel * coteY) + coteY);
	}

	/**
	 * Cette fonction permet de placer la plante dans un environnement torique.
	 * 
	 * @param x
	 *            : le x de dï¿½part.
	 * @param angle
	 *            : l'angle sur lequel on se dï¿½place.
	 * @param longueur
	 *            : la longueur du segment.
	 * @return le x rï¿½sultant.
	 */
	private int getX(int x, int angle, int longueur) {
		int retour = (int) (x + Math.cos(Math.toRadians(angle)) * longueur);
		if (retour > Environnement.getEnvironnement().getTailleX()) {
			retour = retour - Environnement.getEnvironnement().getTailleX();
		} else if (retour < 0) {
			retour = Environnement.getEnvironnement().getTailleX() + retour;
		}
		return retour;
	}

	/**
	 * Cette fonction permet de placer la plante dans un environnement torique.
	 * 
	 * @param y
	 *            : le y de dï¿½part.
	 * @param angle
	 *            : l'angle sur lequel on se dï¿½place.
	 * @param longueur
	 *            : la longueur du segment.
	 * @return le y rï¿½sultant.
	 */
	private int getY(int y, int angle, int longueur) {
		int retour = (int) (y + Math.sin(Math.toRadians(angle)) * longueur);
		if (retour > Environnement.getEnvironnement().getTailleY()) {
			retour = retour - Environnement.getEnvironnement().getTailleY();
		} else if (retour < 0) {
			retour = Environnement.getEnvironnement().getTailleX() + retour;
		}
		return retour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see affichage.VueLourde#setActif(boolean)
	 */
	public void setActif(boolean actif) {
		this.actif = actif;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see affichage.VueLourde#isActif()
	 */
	public boolean isActif() {
		return actif;
	}

}
