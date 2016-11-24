package agent;

import java.awt.Color;

import param.Parametres;
import croissance.EltS;
import environnement.Environnement;

/**
Le loup bouffe marguerite 
 */
public class AgentLoup extends Agent<Agent<EltS>>{


	/**
	 * Constructeur de classe
	 */
	public AgentLoup() {
		this.faim = false;
		this.couleur = new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random());
		this.energy = Parametres.START_ENERGY_LOUP;
		this.vitesse = Parametres.VITESSE_MOYENNE_LOUP + Math.random() * 2
		* Parametres.ECART_VITESSE_MOYENNE_LOUP
		- Parametres.ECART_VITESSE_MOYENNE_LOUP;
		this.angleDirection = 2 * Math.random() * Math.PI;
		double d = Environnement.getEnvironnement().getTailleX()
		* Math.random();
		this.x = (int) d;
		d = Environnement.getEnvironnement().getTailleY() * Math.random();
		this.y = (int) d;
		this.age = 0;
		this.esperanceVie = Parametres.ESPERANCE_VIE_LOUP;
		angleMax = Parametres.MAX_ANGLE_VACHE;
		coutDeplacement = Parametres.COUT_DEPLACEMENT_VACHE;
		seuilFaim = Parametres.ENERGY_FAIM_LOUP;
		seuilBaise = Parametres.ENERGY_BAISE_LOUP;
		seuilPasFaim = Parametres.ENERGY_PAS_FAIM_LOUP;
		agents = Environnement.getEnvironnement().getLoups();
		variationEnfantCouleur = Parametres.VARIATION_COULEUR_LOUP;
		variationEnfantVitesse = Parametres.VARIATION_ENFANT_VITESSE_LOUP;
		variationEnfantEsperance = Parametres.VARIATION_ESPERANCE_VIE_LOUP;
		variationCouleurAccouplement = Parametres.VARIATION_COULEUR_ACCOUPLEMENT_LOUP;
		distanceRechercheJuliette = Parametres.DISTANCE_SNIFF_LOUP;
		agePuberte = Parametres.AGE_REPRODUCTION_LOUP;
		distanceSniffProie = Parametres.DISTANCE_SNIFF_MARGUERITE;
		proies = Environnement.getEnvironnement().getVaches();
	}

	/* (non-Javadoc)
	 * @see agent.Agent#move()
	 */
	public void move() {
		this.age += 1;
		// si l'energie de la Loup est inférieure ou égal à 0 elle meurt
		if ((this.energy <= 0) || (this.age > this.esperanceVie)) {
			mourir();
		} else {

			verifierFaim();
			// si il n'y a pas de graine dans notre zone de sniffage, on se
			// deplace au hasard
			if (!faim) {
				Agent<Agent<EltS>> juliette = chercherJuliette();
				
				if ((juliette != null) && (this.age > Parametres.AGE_REPRODUCTION_LOUP)) {
					// si on est proche on s'acouple
					if (squareDistance(juliette.x, juliette.y) < Parametres.DISTANCE_COPULATE_LOUP) {
						accouplement(juliette);
					} else {
						// sinon on se dirige vers la Loup
						double distance = Math.sqrt(squareDistance(
								juliette.getX(), juliette.getY()));
						
						calculerAngle(juliette.getX(), juliette.getY(),
								distance);
						
						if (distance < vitesse) {
							deplacer(distance / 2, angleDirection);
						} else {
							deplacer(vitesse, angleDirection);
						}
					}

				} else {
					// sinon on se balade à la cherche juliette
					deplacerAleatoirement();
					}
			} else {
				
				Agent<EltS> vache = sniffProie();
				// on se deplace par la graine la plus proche
				if (vache == null) {

					deplacerAleatoirement();
					} else {
					double distance = Math.sqrt(squareDistance(vache.getX(),vache.getY()));

					// on calcul l'angle qu'on doit prendre
					calculerAngle(vache.getX(), vache.getY(),
							distance);

					// si le but est trop loin
					if (distance > vitesse) {
						// on avance du maximum
						deplacer(vitesse, angleDirection);
					} else if (distance < Parametres.DISTANCE_MANGER_LOUP) {
						mangerProie(vache);
					} else {
						// sinon on va sur le but
					 deplacer(distance, angleDirection);
					}
				}
			}

		}
	}


	/* (non-Javadoc)
	 * @see agent.Agent#creerAgent()
	 */
	protected Agent<Agent<EltS>> creerAgent() {
		return new AgentLoup();
	}
	
}
