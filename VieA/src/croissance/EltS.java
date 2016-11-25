/**
 * 
 */
package croissance;

import java.util.ArrayList;
import java.util.List;

import param.Parametres;
import agent.Proie;
import environnement.Environnement;

/**
 * @author Administrator
 * 
 */
public class EltS extends Element implements Proie{

	/**
	 * L'energie a laquelle la graine est mature
	 */
	private int cout = Parametres.COUT_CREATION_R;

	/**
	 * Constructeur de graine
	 * 
	 * @param x
	 *            L'abscisse de la graine
	 * @param y
	 *            Son ordonnée
	 * @param capteur
	 *            Le capteur centralisant les informations de la plante à
	 *            laquelle on est ratachée
	 * 
	 */
	public EltS(int x, int y, Capteur capteur) {
		this.x = x;
		this.y = y;
coutCreation = 0;
		importance = Parametres.IMPORTANCE_S;

		this.capteur = capteur;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#getModules()
	 */
	@Override
	public List<Module> getModules() {
		// on crée notre module
		ArrayList<Module> listeRetour = new ArrayList<Module>();
		listeRetour.add(new Module('S', energie, x, y));
		// et le retourne dans une liste
		return listeRetour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#step()
	 */
	@Override
	public Symbole step() {
		// si on a plus d'importance c'est qu'on a été bouffé
		if (importance == 0) {
			// on n'existe donc plus
			return null;
		}
		// sinon on existe toujours, on capte donc l'energie qui nous est due
		energie += (capteur.getEnergieCaptee() / capteur.getNbCapteurs())
				* importance;
		capteur.addEnergieCaptee(-(capteur.getEnergieCaptee() / capteur
				.getNbCapteurs())
				* importance);
		// si on a suffisament d'energie pour produire une plante
		if (energie >= cout) {
			// on rend l'energie qu'on a en trop à l'environnement
			Environnement.getEnvironnement()
					.rendreEnergie(energie - cout, x, y);
			// capteur.addEnergieCaptee(energie - cout);
			// on crée une nouvelle plante
			Environnement.getEnvironnement().nouvellePlante();
			// on enleve notre importance au capteur
			capteur.addNbCapteurs(-importance);
			// on est plus presente
			capteur.removeGraine(this);
			return null;
		}
		// si on arrive ici c'est qu'on est toujours là
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#rendreCoutVie()
	 */
	public void rendreCoutVie() {
		// on ne rend l'energie que si on a pas été consomée
		if (importance != 0) {
			Environnement.getEnvironnement().rendreEnergie(energie, x, y);
		}
	}

	/**
	 * Recupère l'energie de la graine et la supprime
	 * 
	 * @return L'energie de la graine consomée
	 */
	public double manger() {
		if (importance > 0) {
			// on enleve notre importance au capteur
			capteur.addNbCapteurs(-importance);
			// on enlève la graine
			capteur.removeGraine(this);
			// on vaut plus rien
			importance = 0;
			// on file notre energie
			return energie;
		} else {
			// si on arrive ici c'est qu'on a été bouffée deux fois donc le
			// second vas se faire voir!
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see croissance.Symbole#activer()
	 */
	public void activer() {
		// on ajoute une graine au capteur
		capteur.addGraine(this);
		capteur.addNbCapteurs(importance);
	}
	
	
	/* (non-Javadoc)
	 * @see agent.Proie#getX()
	 */
	public double getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see agent.Proie#getY()
	 */
	public double getY() {
		return y;
	}
}
