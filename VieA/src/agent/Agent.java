/**
 * 
 */
package agent;

import java.awt.Color;
import java.util.List;

import environnement.Environnement;

/**
 * @author detantxavi
 * 
 */
public abstract class Agent<Type extends Proie> implements Proie {

	/**
	 * L'energie de l'agent
	 */
	protected double energy;

	/**
	 * la direction que l'agent suit
	 */
	protected double angleDirection;

	/**
	 * Vitesse de l'agent (distance qu'il peut parcourir à chaque tour)
	 */
	protected double vitesse;

	/**
	 * Abscisse dans le plan de l'agent
	 */
	protected double x;

	/**
	 * Ordonnee dans le plan de l'agent
	 */
	protected double y;

	/**
	 * La couleur de l'agent
	 */
	protected Color couleur;

	/**
	 * Boolean si on a faim ou pas
	 */
	protected Boolean faim;

	/**
	 * L'age de l'agent
	 */
	protected double age;

	/**
	 * L'esperance de vie de l'agent
	 */
	protected double esperanceVie;

	/**
	 * L'angle de mouvement max de l'agent
	 */
	protected double angleMax;

	/**
	 * Le cout du deplacement de l'agent
	 */
	protected double coutDeplacement;

	/**
	 * Le seuil en dessous duquel on a faim
	 */
	protected double seuilFaim;

	/**
	 * Le seuil au dessus duquel n a pas faim
	 */
	protected double seuilPasFaim;

	/**
	 * Le seuil en dessous duquel on a plus envie de baiser
	 */
	protected double seuilBaise;

	/**
	 * La variation de la couleur pour un enfant
	 */
	protected double variationEnfantCouleur;

	/**
	 * La variation de la vitesse pour un enfant
	 */
	protected double variationEnfantVitesse;

	/**
	 * La variation de l'esperance de vie pour un enfant
	 */
	protected double variationEnfantEsperance;

	/**
	 * La variation maximale de la couleur authorisée pour l'accouplement
	 */
	protected double variationCouleurAccouplement;

	/**
	 * La distance maximale a laquelle on recherche juliette
	 */
	protected double distanceRechercheJuliette;

	/**
	 * La distance maximale a laquelle on sniff une proie
	 */
	protected double distanceSniffProie;

	/**
	 * L'age auquel on sort de la puberté et où on peut enfin niquer comme un
	 * gros cochon!
	 */
	protected double agePuberte;

	/**
	 * La liste des agents dans l'environnement
	 */
	protected List<Agent<Type>> agents;

	/**
	 * La liste des proies de l'agent
	 */
	protected List<Type> proies;

	/**
	 * Fait bouger l'agent
	 */
	public abstract void move();

	/**
	 * @return Un nouvel agent
	 */
	protected abstract Agent<Type> creerAgent();

	/**
	 * Permet de tuer l'agent
	 */
	protected void mourir() {
		// si on a une energie positive
		if (energy > 0) {
			// on la rend a l'environnement
			Environnement.getEnvironnement().rendreEnergie(energy, (int) x,
					(int) y);
		}
		// on se supprime de l'environnement
		agents.remove(this);
	}

	/**
	 * Deplace l'agent aleatoirement
	 */
	protected void deplacerAleatoirement() {
		// on calcule un angle aléatoire
		this.angleDirection = this.angleDirection + Math.random() * angleMax
				* 2 - angleMax;
		this.angleDirection = (this.angleDirection + Math.PI * 2)
				% (Math.PI * 2);
		// on se deplace a la vitesse maximale
		deplacer(vitesse, angleDirection);
	}

	/**
	 * Deplace l'agent à une certaine distance en suivant un certain angle
	 * 
	 * @param distance
	 *            La distance a parcourir
	 * @param angle
	 *            L'angle de parcours
	 */
	protected void deplacer(double distance, double angle) {

		Environnement env = Environnement.getEnvironnement();

		// si on consomme moins que d'energie disponible
		if (distance * coutDeplacement < energy) {
			// alors on rend la quantite consommée à l'environnemen
			env.rendreEnergie(distance * coutDeplacement, (int) this.x,
					(int) this.y);

		} else {
			// sinon on rend toute l'energie disponible
			Environnement.getEnvironnement().rendreEnergie(energy,
					(int) this.x, (int) this.y);
		}
		// on consomme l'energie
		this.energy -= distance * coutDeplacement;

		// on calcule notre nouvelle position
		this.x += Math.round(distance * Math.cos(angleDirection));
		this.y += Math.round(distance * Math.sin(angleDirection));

		// On verifie qu'on est pas sorti du terrain
		x = (x + env.getTailleX()) % env.getTailleX();
		y = (y + env.getTailleY()) % env.getTailleY();

	}

	/**
	 * Permet de verifier si on a faim ou pas en fonction de deux seuils
	 */
	public void verifierFaim() {
		// si on est en dessous du seuil alors on a faim
		if (this.energy < seuilFaim) {
			this.faim = true;
		} else if (this.energy > seuilPasFaim) {
			// si on passe au dessus du seuil de non faim alors c'est bon
			this.faim = false;
		}
	}

	/**
	 * Cherche la proie la plus proche de l'agent. Si aucune ne se trouve dans
	 * le rayon d'odorat de l'agent, on retourne null
	 * 
	 * @return la proie la plus proche de l'agent si celle-ci se trouve dans le
	 *         rayon d'odorat de l'agent, sinon null.
	 */
	public Type sniffProie() {
		Type proiePlusProche = null;
		double minDist = distanceSniffProie;
		// pour toutes les proies
		for (Type proie : proies) {
			// si elle est plus proches que la precedente
			if (squareDistance(proie.getX(), proie.getY()) < minDist) {
				// alors on a notre nouvelle proie
				proiePlusProche = proie;
				minDist = squareDistance(proie.getX(), proie.getY());
			}
		}

		return proiePlusProche;
	}

	/**
	 * Permet de manger une proie
	 * 
	 * @param proie
	 *            : la proie que l'on veut manger
	 */
	public void mangerProie(Type proie) {
		// on recupère l'energie de la proie
		this.energy += proie.manger();
	}

	/**
	 * Permet l'accouplement entre deux agents et genere un regeton qui a evolué
	 * 
	 * @param juliette
	 *            L'agent super sexy avec qui on s'accouple
	 */
	public void accouplement(Agent<Type> juliette) {

		double nouvelleVitesse;
		double nouvelleEsperance;

		// on genere la vitesse de l'enfant
		if (Math.random() > 0.5) {
			nouvelleVitesse = genererGene(vitesse, variationEnfantVitesse);
		} else {
			nouvelleVitesse = genererGene(juliette.getVitesse(),
					variationEnfantVitesse);
		}

		// on genere son esperance de vie
		if (Math.random() > 0.5) {
			nouvelleEsperance = genererGene(esperanceVie,
					variationEnfantEsperance);
		} else {
			nouvelleEsperance = genererGene(juliette.getEsperanceVie(),
					variationEnfantEsperance);
		}

		// on cree un nouvel agent
		Agent<Type> bebe = creerAgent();

		// qu'on place au meme niveau que nous
		bebe.setX(x);
		bebe.setY(y);

		// on lui file 1/3 de l'energie des deux agents
		bebe.setEnergy((int) Math.floor(this.energy / 3)
				+ (int) Math.floor(juliette.energy / 3));

		// on s'enleve l'energie consomé par cette folle nuit d'amour!
		this.energy -= (int) Math.floor(this.energy / 3);
		juliette.energy -= (int) Math.floor(juliette.energy / 3);

		// on enregistre ses nouveaux genes du bébé
		bebe.setCouleur(genererCouleur(juliette));
		bebe.setVitesse(nouvelleVitesse);
		bebe.setEsperanceVie(nouvelleEsperance);

		// on ajoute le bébé à la liste d'agents
		agents.add(bebe);
	}

	/**
	 * Genere la valeur d'un gene pour un enfant en fonction de la valeur d'un
	 * parent et d'un parametre de variation
	 * 
	 * @param gene
	 *            Le gene du parent
	 * @param parametre
	 *            La variation
	 * @return La valeur du nouveau gene
	 */
	private double genererGene(double gene, double parametre) {
		return Math.max(0, gene + Math.random() * parametre * 2 - parametre);
	}

	/**
	 * Permet de generer une nouvelle couleur pour la vache
	 * 
	 * @param juliette
	 *            : la vache avec laquelle on s'accouple
	 * @return la nouvelle couleur
	 */
	protected Color genererCouleur(Agent<Type> juliette) {
		int r, g, b;

		// on genere le vert
		if (Math.random() > 0.5) {
			r = genererComposanteCouleur(juliette.getCouleur().getRed());
		} else {
			r = genererComposanteCouleur(couleur.getRed());
		}

		// on genere le vert
		if (Math.random() > 0.5) {
			g = genererComposanteCouleur(juliette.getCouleur().getGreen());
		} else {
			g = genererComposanteCouleur(couleur.getGreen());
		}

		// on genere le bleu
		if (Math.random() > 0.5) {
			b = genererComposanteCouleur(juliette.getCouleur().getBlue());
		} else {
			b = genererComposanteCouleur(couleur.getBlue());
		}

		// on retourne la nouvelle couleur
		return (new Color(r, g, b));
	}

	/**
	 * Genere la nouvelle composante de la couleur
	 * 
	 * @param composante
	 *            La composante du parent
	 * @return La valeur de la composante de l'enfant
	 */
	private int genererComposanteCouleur(int composante) {
		// on genere le gene avec une valeur comprise entre 0 et 255
		return Math.max(0,(int)(genererGene(composante, variationEnfantCouleur) % 255));
	}

	/**
	 * Permet de sentir les agents proches de nous
	 * 
	 * @return l'agent la plus proche
	 */
	protected Agent<Type> chercherJuliette() {
		Agent<Type> juliette = null;
		double minDist = distanceRechercheJuliette;

		// pour tous les agents semblables
		for (Agent<Type> agent : agents) {
			// si c'est pas moi
			if ((agent != this)
			// qu'il a pas faim
					&& (!agent.getFaim())
					// qu'il a assez d'energie pour baiser
					&& getEnergy() > seuilBaise
					// qu'il nous traitera pas de pedophile
					&& (agent.getAge() > agePuberte)
					// qu'il est plus proche que le dernier vu
					&& (squareDistance(agent.getX(), agent.getY()) < minDist)
					// que sa couleur est correcte
					&& (verifierCouleur(agent))) {
				// alors c'est notre nouvelle juliette
				juliette = agent;
				// et on enregistre sa distance
				minDist = squareDistance(agent.getX(), agent.getY());
			}
		}

		// on retourne notre juliette
		return juliette;
	}

	/**
	 * La distance au carrée d'un point par rapport a nous
	 * 
	 * @param x L'abcisse de la cible
	 * @param y L'ordonnee de la cible
	 * @return La distance à la cible au carré
	 */
	protected double squareDistance(double x, double y) {
		// on se place au centre du monde
		int tailleX = Environnement.getEnvironnement().getTailleX();
		int tailleY = Environnement.getEnvironnement().getTailleY();
		double centreX = tailleX/2.0;
		double centreY = tailleY/2.0;
		
		// on replace le point par rapport au centre
		x = (x + (centreX - this.x) + tailleX) % tailleX;
		y = (y + (centreY - this.y) + tailleY) % tailleY;
		
		// on calcule la distance sans la racine
		return ((x - centreX) * (x - centreX) + (y - centreY) * (y - centreY));
	}

	/**
	 * Calcul l'angle qu'il faut adopter pour aller au point voulu
	 * 
	 * @param x L'abcisse de la cible
	 * @param y L'ordonnee de la cible
	 * @param distance La distance qui nous sépare de la cible
	 */
	protected void calculerAngle(double x, double y, double distance) {
		double arCos;

		// on se place au centre du monde
		int tailleX = Environnement.getEnvironnement().getTailleX();
		int tailleY = Environnement.getEnvironnement().getTailleY();
		double centreX = tailleX/2.0;
		double centreY = tailleY/2.0;
		
		// on replace le point par rapport au centre
		x = (x + (centreX - this.x) + tailleX) % tailleX;
		y = (y + (centreY - this.y) + tailleY) % tailleY;
		
		// on calcul l'arc cosinus avec la cible
		if (x == centreX) {
			arCos = 0;
		} else {
			arCos = Math.acos((x - centreX) / distance);
		}

		// on choisi l'angle en fonction de notre position par rapport à la cible
		if (y <= centreY) {
			this.angleDirection = -arCos;
		} else {
			this.angleDirection = arCos;
		}

		this.angleDirection = (this.angleDirection + 2 * Math.PI)
				% (2 * Math.PI);
	}

	/**
	 * Verifie que la couleur de l'agent avec lequel on veut s'accoupler n'est
	 * pas trop éloigné de la notre
	 * 
	 * @param juliette
	 *            L'agent avec lequel on veux s'acoupler
	 * @return Vrai si la couleur est assez proche et faux sinon
	 */
	protected Boolean verifierCouleur(Agent<Type> juliette) {
		// on verifie que les composantes couleurs ne sont pas éloignées
		return (Math.abs(this.couleur.getBlue()
				- juliette.getCouleur().getBlue()) < variationCouleurAccouplement)
				&& (Math.abs(this.couleur.getGreen()
						- juliette.getCouleur().getGreen()) < variationCouleurAccouplement)
				&& (Math.abs(this.couleur.getRed()
						- juliette.getCouleur().getRed()) < variationCouleurAccouplement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agent.Proie#manger()
	 */
	public double manger() {
		// on se supprime de la liste de l'environnement
		agents.remove(this);
		// on retourne notre energie
		return energy;
	}

	/**
	 * @return the energy
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * @return the vitesse
	 */
	public double getVitesse() {
		return vitesse;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * @return the angleDirection
	 */
	public double getAngleDirection() {
		return angleDirection;
	}

	/**
	 * @return the faim
	 */
	public Boolean getFaim() {
		return faim;
	}

	/**
	 * @return the esperanceVie
	 */
	public double getEsperanceVie() {
		return esperanceVie;
	}

	/**
	 * @return the age
	 */
	public double getAge() {
		return age;
	}

	/**
	 * @param energy
	 *            the energy to set
	 */
	protected void setEnergy(double energy) {
		this.energy = energy;
	}

	/**
	 * @param vitesse
	 *            the vitesse to set
	 */
	protected void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	protected void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	protected void setY(double y) {
		this.y = y;
	}

	/**
	 * @param esperanceVie
	 *            the esperanceVie to set
	 */
	protected void setEsperanceVie(double esperanceVie) {
		this.esperanceVie = esperanceVie;
	}

	/**
	 * @param couleur
	 *            the couleur to set
	 */
	protected void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

}
