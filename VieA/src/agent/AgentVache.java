package agent;

import java.awt.Color;

import param.Parametres;
import croissance.EltS;
import environnement.Environnement;

/**
 * Notre Vache, o� agent pour les intellectuels, parcourt inlassablement le
 * terrain en qu�te de graines pour survivre dans ce monde impitoyable qui pompe
 * � cette pauvre vache de l'�nergie chaque fois qu'elle se d�place. Eh oui,
 * rien est gratuit ! A chaque action le terrain reprend se qu'il lui est d�.
 * Nos vaches arriveront-ils a survivre sur ce terrain d'intelligence
 * artificielle ? Seront-elles seules avec les plantes dans ce monde ? Pas si
 * s�r ! Marguerite, la vache leader du troupeau aurait aper�u la nuit tomb�,
 * une �trange ombre de pr�dateurs... Quel sera le d�nouement de ce thriller ?
 * Vous le serez en cliquant sur le bouton Run d'Eclipse !
 * 
 * @author Cyrille, le litt�raire
 * 
 */
public class AgentVache extends Agent<EltS> {

	/**
	 * Constructeur de vache
	 */
	public AgentVache() {
		// on a faim des le debut
		this.faim = true;
		// on a une couleur aleatoire
		this.couleur = new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random());
		// on prends les parametres par defaut
		this.energy = Parametres.START_ENERGY_VACHE;
		this.vitesse = Parametres.VITESSE_MOYENNE_VACHE + Math.random() * 2
				* Parametres.ECART_VITESSE_MOYENNE_VACHE
				- Parametres.ECART_VITESSE_MOYENNE_VACHE;
		this.angleDirection = 2 * Math.random() * Math.PI;
		double d = Environnement.getEnvironnement().getTailleX()
				* Math.random();
		this.x = d;
		d = Environnement.getEnvironnement().getTailleY() * Math.random();
		this.y = d;
		this.age = 0;
		this.esperanceVie = Parametres.ESPERANCE_VIE_VACHE;
		angleMax = Parametres.MAX_ANGLE_VACHE;
		coutDeplacement = Parametres.COUT_DEPLACEMENT_VACHE;
		seuilFaim = Parametres.ENERGY_FAIM_VACHE;
		seuilBaise = seuilFaim;
		seuilPasFaim = Parametres.ENERGY_PAS_FAIM_VACHE;
		agents = Environnement.getEnvironnement().getVaches();
		variationEnfantCouleur = Parametres.VARIATION_COULEUR_VACHE;
		variationEnfantVitesse = Parametres.VARIATION_ENFANT_VITESSE_VACHE;
		variationEnfantEsperance = Parametres.VARIATION_ESPERANCE_VIE_VACHE;
		variationCouleurAccouplement = Parametres.VARIATION_COULEUR_ACCOUPLEMENT_VACHE;
		distanceRechercheJuliette = Parametres.DISTANCE_SNIFF_VACHE;
		agePuberte = Parametres.AGE_REPRODUCTION_VACHE;
		distanceSniffProie = Parametres.DISTANCE_SNIFF_GRAINE;
	}

	/**
	 * Permet de faire bouger une vache
	 */
	public void move() {
		proies = Environnement.getEnvironnement().getGraines();
		// si l'energie de la vache est inf�rieure ou �gal � 0 elle meurt
		if ((this.energy <= 0) || (this.age > this.esperanceVie)) {
			mourir();
		} else {
			// on regarde si la vache a faim
			verifierFaim();
			// si on a pas faim
			if (!faim) {
				// alors on veut baiser, on cherche donc les autres vaches
				Agent<EltS> juliette = chercherJuliette();
				// si on trouve notre juliette et qu'on a plus de 18 ans
				// parceque sinon en dessous c'est d�gueulasse franchement!
				if ((juliette != null) && (this.age > agePuberte)) {
					// on s'accouple si on est assez proche
					if (squareDistance(juliette.getX(), juliette.getY()) < Parametres.DISTANCE_COPULATE_VACHE) {
						accouplement(juliette);
					} else {
						// sinon on se dirige vers la vache
						double distance = Math.sqrt(squareDistance(juliette
								.getX(), juliette.getY()));
						calculerAngle(juliette.getX(), juliette.getY(),
								distance);
						if (distance < vitesse) {
							deplacer(distance / 2, angleDirection);
						} else {
							deplacer(vitesse, angleDirection);
						}
					}

				} else {
					// sinon on se balade � la cherche juliette
					deplacerAleatoirement();
				}
			} else {
				EltS positionBean = sniffProie();
				if (positionBean == null) {

					deplacerAleatoirement();
				} else {
					double distance = Math.sqrt(squareDistance(positionBean
							.getX(), positionBean.getY()));
					// on calcul l'angle qu'on doit prendre
					calculerAngle(positionBean.getX(), positionBean.getY(),
							distance);
					// si le but est trop loin
					if (distance > vitesse) {
						// on avance du maximum
						deplacer(vitesse, angleDirection);
					} else if (distance < Parametres.DISTANCE_MANGER_VACHE) {
						mangerProie(positionBean);
					} else {
						// sinon on va sur le but
						deplacer(distance, angleDirection);
					}
				}
			}
			// on a pris un an
			age++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agent.Agent#creerAgent()
	 */
	protected Agent<EltS> creerAgent() {
		return new AgentVache();
	}

}
