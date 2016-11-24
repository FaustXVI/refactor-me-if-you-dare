/**
 * 
 */
package croissance;

import java.util.ArrayList;
import java.util.List;

import param.Parametres;
import environnement.Environnement;

/**
 * @author Administrator
 * 
 */
public class EltA extends Element {

	/**
	 * La chaine qui sera produite par l'appex
	 */
	private Chaine production;

	/**
	 * L'angle de l'appex
	 */
	private int angle;

	/**
	 * Constructeur d'appex
	 * 
	 * @param x
	 *            L'abssice de l'appex
	 * @param y
	 *            Son ordonnée
	 * @param angle
	 *            L'angle de la tige où elle est située
	 * @param capteur
	 *            Le capteur associée à la plante qui contient l'appex
	 */
	public EltA(int x, int y, int angle, Capteur capteur) {
		this.x = x;
		this.y = y;
		this.angle = angle;

		importance = Parametres.IMPORTANCE_A;
		energie = 0;
		coutCreation = 0;

		ArrayList<Symbole> lst = new ArrayList<Symbole>();

		this.capteur = capteur;
		production = new Chaine(lst, capteur);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#getModules()
	 */
	public List<Module> getModules() {
		// on construit notre module
		ArrayList<Module> listeRetour = new ArrayList<Module>();
		listeRetour.add(new Module('A', (energie / production.getCoutCreation()) * 100,
				x, y));
		// et on le retourne dans une liste
		return listeRetour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#step()
	 */
	public Symbole step() {
		
		// on capte l'energie qui nous est due
		energie += (capteur.getEnergieCaptee() / capteur.getNbCapteurs())
				* importance;
		capteur.addEnergieCaptee(-(capteur.getEnergieCaptee() / capteur
				.getNbCapteurs())
				* importance);
		// si on a suffisament d'energie pour donner evoluer
		if (energie >= production.getCoutCreation()) {
			// on rend a l'environnement l'energie qui reste après production
			Environnement.getEnvironnement().rendreEnergie(
					energie - production.getCoutCreation(), x, y);
			// on s'enlève des capteurs de la plante
			capteur.addNbCapteurs(-importance);
			capteur.removeAppex(this);
			// on active notre production
			production.activer();
			// et on la retourne
			return production;
		}
		// sinon on est toujours là
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#activer()
	 */
	public void activer() {
		capteur.addAppex(this);
		capteur.addNbCapteurs(importance);
		
		// on crée la chaine qu'on produira
		ArrayList<Symbole> lst = new ArrayList<Symbole>();
		
		// on tir un nombre aléatoire
		double test = Math.random() * 100;

		// en fonction du nombre aléatoire, on crée une plante differente
		if (test < 25) {
			lst.add(new EltS(x, y, capteur));
			lst.add(new MvtMoins());
			lst.add(new EltT(x, y, capteur));
			lst.add(new EltA(getX(x, angle - angleMoyen, longueurMoyenne),
					getY(y, angle - angleMoyen, longueurMoyenne), angle
							- angleMoyen, capteur));
		} else if (test < 50) {
			lst.add(new EltS(x, y, capteur));
			lst.add(new MvtPlus());
			lst.add(new EltT(x, y, capteur));
			lst.add(new EltA(getX(x, angle + angleMoyen, longueurMoyenne),
					getY(y, angle + angleMoyen, longueurMoyenne), angle
							+ angleMoyen, capteur));
		} else if (test < 70) {
			lst.add(new EltR(x, y, capteur));
			lst.add(new MvtPlus());
			lst.add(new EltT(x, y, capteur));
			lst.add(new EltA(getX(x, angle + angleMoyen, longueurMoyenne),
					getY(y, angle + angleMoyen, longueurMoyenne), angle
							+ angleMoyen, capteur));
		} else if (test < 90) {
			lst.add(new EltR(x, y, capteur));
			lst.add(new MvtMoins());
			lst.add(new EltT(x, y, capteur));
			lst.add(new EltA(getX(x, angle - angleMoyen, longueurMoyenne),
					getY(y, angle - angleMoyen, longueurMoyenne), angle
							- angleMoyen, capteur));
		} else {
			lst.add(new EltR(x, y, capteur));
			lst.add(new MvtCG());
			lst.add(new MvtPlus());
			lst.add(new EltT(x, y, capteur));
			lst.add(new EltA(getX(x, angle + angleMoyen, longueurMoyenne),
					getY(y, angle + angleMoyen, longueurMoyenne), angle
							+ angleMoyen, capteur));
			lst.add(new MvtCD());
			lst.add(new MvtMoins());
			lst.add(new EltT(x, y, capteur));
			lst.add(new EltA(getX(x, angle - angleMoyen, longueurMoyenne),
					getY(y, angle - angleMoyen, longueurMoyenne), angle
							- angleMoyen, capteur));
		}

		// on enregistre notre future production
		production = new Chaine(lst, capteur);
	}

	/**
	 * Cette fonction permet de placer la plante dans un environnement torique.
	 * 
	 * @param x
	 *            : le x de départ.
	 * @param angle
	 *            : l'angle sur lequel on se déplace.
	 * @param longueur
	 *            : la longueur du segment.
	 * @return le x résultant.
	 */
	private int getX(int x, int angle, int longueur) {
		int retour = (int) (x + Math.cos(Math.toRadians(angle)) * longueur);
		if (retour >= Environnement.getEnvironnement().getTailleX()) {
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
	 *            : le y de départ.
	 * @param angle
	 *            : l'angle sur lequel on se déplace.
	 * @param longueur
	 *            : la longueur du segment.
	 * @return le y résultant.
	 */
	private int getY(int y, int angle, int longueur) {
		int retour = (int) (y + Math.sin(Math.toRadians(angle)) * longueur);
		if (retour >= Environnement.getEnvironnement().getTailleY()) {
			retour = retour - Environnement.getEnvironnement().getTailleY();
		} else if (retour < 0) {
			retour = Environnement.getEnvironnement().getTailleX() + retour;
		}
		return retour;
	}

	/* (non-Javadoc)
	 * @see croissance.Symbole#rendreCoutVie()
	 */
	public void rendreCoutVie() {
		Environnement.getEnvironnement().rendreEnergie(energie, x, y);
	}
}
