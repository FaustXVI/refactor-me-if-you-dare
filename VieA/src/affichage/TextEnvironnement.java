/**
 * 
 */
package affichage;

import java.awt.GridLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import environnement.Environnement;

/**
 * @author detantxavi
 * 
 */
public class TextEnvironnement extends JPanel implements Observer {

	/**
	 * Affiche l'energie totale de l'environnement
	 */
	JLabel energie = new JLabel();

	/**
	 * L'age du monde
	 */
	JLabel age = new JLabel();

	/**
	 * Affiche le nombre de plantes presentes
	 */
	JLabel plantes = new JLabel();

	/**
	 * Affiche le nombre de graines presentes
	 */
	JLabel graines = new JLabel();

	/**
	 * Affiche le nombre de vaches presentes
	 */
	JLabel vaches = new JLabel();

	/**
	 * Affiche le nombre de loups presents
	 */
	JLabel loups = new JLabel();

	/**
	 * Affiche le nombre d'iration par minute
	 */
	JLabel frequence = new JLabel();

	/**
	 * Affiche la vitesse moyenne
	 */
	JLabel VitesseVache = new JLabel();

	/**
	 * Vitesse moyenne des loups
	 */
	JLabel VitesseLoup = new JLabel();

	/**
	 * Affiche l'esperance de vie moyenne des vaches
	 */
	JLabel EsperanceVache = new JLabel();

	/**
	 * L'esperance de vie moyenne des loups
	 */
	JLabel EsperanceLoup = new JLabel();

	/**
	 * 
	 */
	private static final long serialVersionUID = 5831643410725218913L;

	/**
	 * 
	 */
	public TextEnvironnement() {
		// on observe l'environnement
		Environnement.getEnvironnement().addObserver(this);

		// on recupère les donnée
		this.afficher();

		// on met un layout pour tout afficher en colonnes
		this.setLayout(new GridLayout(11, 1));

		// on affiche l'energie
		this.add(this.energie);

		// on affiche l'age du monde
		this.add(this.age);

		// on affiche le nombre de plantes
		this.add(this.plantes);

		// on affiche les graines
		this.add(this.graines);

		// on affiche les vaches
		this.add(this.vaches);

		// on affiche les loups
		this.add(this.loups);

		// on affiche la frequence
		this.add(this.frequence);

		// on affiche la vitesse moyenne
		this.add(this.VitesseVache);

		// on affiche la vitesse moyenne
		this.add(this.VitesseLoup);

		// on affiche l'esperance de vie des vaches
		this.add(this.EsperanceVache);

		// on affiche l'esperance de vie des loups
		this.add(this.EsperanceLoup);

	}

	/**
	 * Affiche les informations
	 */
	private void afficher() {

		// on met à jour le texte de l'energie
		this.energie.setText("Energie : "
				+ this.format(Environnement.getEnvironnement()
						.getEnergieTotal()));

		// on met a jour l'age du monde
		this.age.setText("Age : " + Environnement.getEnvironnement().getAge());

		// on met à jour le nombre de plantes
		this.plantes.setText("Nombre plantes : "
				+ Environnement.getEnvironnement().getPlantes().size());

		// on met à jour le nombre de graines
		this.graines.setText("Nombre graines : "
				+ Environnement.getEnvironnement().getNbGraines());

		// on met à jour le nombre de vaches
		this.vaches.setText("Nombre vaches : "
				+ Environnement.getEnvironnement().getVaches().size());

		// on met à jour le nombre de loups
		this.loups.setText("Nombre loups : "
				+ Environnement.getEnvironnement().getLoups().size());

		// on met à jour la frequence
		this.frequence.setText("Iterations/min : "
				+ ThreadRun.getInstance().getFrequence());

		// on met a jour la vitesse moyenne
		this.VitesseVache.setText("Vitesse moy vaches : "
				+ this.format(Environnement.getEnvironnement()
						.getVitesseMoyVaches()));

		// on met a jour la vitesse moyenne
		this.VitesseLoup.setText("Vitesse moy loups : "
				+ this.format(Environnement.getEnvironnement()
						.getVitesseMoyLoups()));

		// on met a jour l'esperance des vaches
		this.EsperanceVache.setText("Esperance moy vaches : "
				+ this.format(Environnement.getEnvironnement()
						.getEsperanceMoyVaches()));

		// on met a jour l'esperance des loups
		this.EsperanceLoup.setText("Esperance moy loups : "
				+ this.format(Environnement.getEnvironnement()
						.getEsperanceMoyLoups()));
	}

	/**
	 * @param valeur
	 *            Le double à formater
	 * @return Retourne un double correctement formaté
	 */
	private String format(final double valeur) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);

		// on format le double
		pw.printf("%.4f", new Double(valeur));
		return sw.toString();
	}

	public void update(final Observable arg0, final Object arg1) {
		this.afficher();
	}

}
