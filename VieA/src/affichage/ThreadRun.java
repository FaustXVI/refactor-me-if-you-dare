/**
 * 
 */
package affichage;

import environnement.Environnement;

/**
 * @author detantxavi
 * 
 */
public class ThreadRun extends Thread {

	/**
	 * Le thread est un singleton
	 */
	private static ThreadRun instance = null;

	/**
	 * Recupere l'instance du thread
	 * 
	 * @return l'instance active du thread
	 */
	public static ThreadRun getInstance() {
		// si on a pas d'instance
		if (instance == null) {
			// alors on la crée
			instance = new ThreadRun();
			instance.start();
		}
		// on retourne l'instance
		return instance;
	}

	/**
	 * Il est actif ou pas
	 */
	private volatile boolean actif;

	/**
	 * La frequence des itérations
	 */
	private double frequence;

	/**
	 * Le constructeur du thread
	 */
	private ThreadRun() {
		// on est pas actif par defaut
		this.actif = false;
		this.frequence = 0;
	}

	/**
	 * @return La frequence de iterations
	 */
	public double getFrequence() {
		return this.frequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		long debut = 0;
		int cpt;
		// on tourne en boucle
		while (true) {
			cpt = 0;
			// temps qu'on est actifs
			while (this.actif) {
				if (cpt == 0) {
					debut = System.currentTimeMillis();
				}
				// on fait evoluer l'environement
				Environnement.getEnvironnement().progresser();
				if (cpt == 19) {
					this.frequence = (1200000 / (System.currentTimeMillis() - debut));
					cpt = 0;
				} else {
					cpt++;
				}
			}

		}
	}

	/**
	 * @param actif1
	 *            the actif to set
	 */
	public void setActif(final boolean actif1) {
		this.actif = actif1;
	}

}
