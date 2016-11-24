package croissance;

/**
 * 
 */


/**
 * @author detantxavi
 * 
 */
public class Terrain {

	/**
	 * Le terrain possÃ¨de des ressouces qui sont reprÃ©sentÃ©es par des
	 * doubles.
	 */
	private double resources[][];

	/**
	 * La copie temporaire lors de la diffusion
	 */
	private double copie[][];

	/**
	 * Il y a un certain nombre de lignes.
	 */
	private int rows;

	/**
	 * Il y a un certain nombre de colonnes.
	 */
	private int columns;

	/**
	 * 
	 */
	private double diffusionRate;


	/**
	 * Constructeur du terrain.
	 * 
	 * @param rows
	 *            le nombre de lignes du terrain
	 * @param columns
	 *            le nombre de colonnes du terrain
	 * @param diffusionRate
	 *            le taux de diffusion
	 */
	public Terrain(int rows, int columns, double diffusionRate) {
		super();
		// on enregistre le tout
		this.rows = rows;
		this.columns = columns;
		// on creer un tableau vide de bonne dimension
		resources = new double[rows][columns];
		this.diffusionRate = diffusionRate;
	}

	/**
	 * Affecte une valeur alÃ©atoire Ã  toutes les cases. La valeur est entre 0
	 * et max.
	 * 
	 * @param max
	 *            La valeur max Ã  affecter
	 */
	public void init(double max) {
		// pour toutes les cases du terrain
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				// on affecte une valeur alÃ©atoire comprise entre 0 et ma
				resources[i][j] = Math.random() * max;
			}
		}
	}

	/**
	 * Fournit la valeur de la case au coordonÃ©es fournis
	 * 
	 * @param row
	 *            La ligne du terrain
	 * @param column
	 *            La colonne du terrain
	 * @return La resource de la case
	 */
	public double getResource(int row, int column) {
		// si on est dans le cadre
		if (row < 0 || column < 0 || row >= rows || column >= columns) {
			// sinon on a pas de ressources
			return 0;
		} else {
			// on recupère les resources de la case
			return (resources[row][column]);
		}
	}

	/**
	 * Consomme la valeur donnée à la case donnée
	 * 
	 * @param row
	 *            La ligne du terrain
	 * @param column
	 *            La colonne du terrain
	 * @param conso
	 *            La valeur consomée
	 */
	public void consommerResource(int row, int column, double conso) {
		// on enlève les ressources
		// si on est dans le cadre
		if (!(row < 0 || column < 0 || row >= rows || column >= columns)) {
			resources[row][column] -= conso;
		}
	}

	/**
	 * Redonne des ressources à une case
	 * 
	 * @param row
	 *            La ligne du terrain
	 * @param column
	 *            La colonne du terrain
	 * @param conso
	 *            La valeur à ajouter à la case
	 */
	public void ajouterResource(int row, int column, double ressource) {
		// on enlève les ressources
		// si on est dans le cadre
		if (!(row < 0 || column < 0 || row >= rows || column >= columns)) {
			resources[row][column] += ressource;
		}
	}

	/**
	 * Fait une Ã©volution du terrain.
	 */
	public void step() {
		// on fait une copie temporaire de la resource
		copie = new double[rows][columns];
		// pour toutes les cases
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				// on diffuse l'energie
				diffuse(i, j);
			}
		}
		// on enregistre la nouvelle grille
		resources = copie.clone();

	}

	/**
	 * Diffuse l'energie de la case vers ses voisins
	 * 
	 * @param row
	 * @param column
	 */
	private void diffuse(int row, int column) {
		double valeurOrigine;

		// on recupere la valeur de dÃ©part
		valeurOrigine = resources[row][column];

		// la case de droite
		valeurOrigine = diffuserCaseVoisine((row + 1) % rows, column, valeurOrigine);

		// la case de gauche
		valeurOrigine = diffuserCaseVoisine((row + rows - 1) % rows, column, valeurOrigine);

		// la case du haut
		valeurOrigine = diffuserCaseVoisine(row,(column + columns - 1) % columns, valeurOrigine);

		// la case du bas
		valeurOrigine = diffuserCaseVoisine(row,(column + 1) % columns,valeurOrigine);

		// on ajoute ce qu'il nous reste
		copie[row][column] += valeurOrigine;
	}

	/**
	 * Diffuse l'energie vers la case voisine fournie en parametre
	 * @param row La ligne de la case voisine
	 * @param column La colonne de la case voisine
	 * @param valeurOrigine La valeur de la case d'origine
	 * @return La nouvelle valeur de la case
	 */
	private double diffuserCaseVoisine(int row, int column, double valeurOrigine){
		double difference;
		// on calcule la difference avec la valeur d'origine
		difference = valeurOrigine - resources[row][column];
		// si elle est positive 
		if (difference > 0) {
			// on diffuse
			copie[row][column] += (diffusionRate * difference / 2);
			valeurOrigine -= (diffusionRate * difference / 2);
		}
		return valeurOrigine;
	}
	
}
