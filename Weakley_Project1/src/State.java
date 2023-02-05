/**
 * <p>
 * A class designed for storing data related to US states. A State entity is created by
 * processing an array of strings such that each element of the array is stored to a specific
 * attribute of the State entity. These attributes are stored as private instance variables and
 * are thus accessed with getters and setters.
 * </p>
 * <p>
 * Each State object has a variable meant for storing the following: state name, state capitol,
 * region, number of seats in the US House, population, median household income, violent crime rate,
 * total number of COVID-19 cases and deaths, as well as cases and deaths per 100,000 residents,
 * and COVID-19 case fatality rate.
 *
 * </p>
 * @author Konrad Weakley n01432685
 * @version 9/17/2021
 */
public class State {
	private String name;
	private String capitol;
	private String region;
	private int houseSeats;
	private int population;
	private int covidCases;
	private int covidDeaths;
	private int medianIncome;
	private float violentRate;
	private float caseRate;
	private float deathRate;
	private float caseFatalityRate;

	/**
	 * Constructor for State objects. Stores elements of an array into instance variables for a State
	 * object. Calculations for COVID case rates, death rates, and case fatality rates are done during
	 * the initialization of those variables as well.
	 * @param elements an array of Strings containing data for the construction of a State object
	 */
	public State(String[] elements) {
		setName(elements[0]);
		setCapitol(elements[1]);
		setRegion(elements[2]);
		setHouseSeats(Integer.parseInt(elements[3]));
		setPopulation(Integer.parseInt(elements[4]));
		setCovidCases(Integer.parseInt(elements[5]));
		setCovidDeaths(Integer.parseInt(elements[6]));
		setMedianIncome(Integer.parseInt(elements[7]));
		setViolentRate(Float.parseFloat(elements[8]));
		setCaseRate((float)this.getCovidCases()/this.getPopulation() * 100000);
		setDeathRate((float)this.getCovidDeaths()/this.getPopulation() * 100000);
		setCaseFatalityRate((float)this.getCovidDeaths()/this.getCovidCases());
	}

	/**
	 *
	 * @return name of a State object as a String
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name of a State object as a String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return capitol of a State as a String
	 */
	public String getCapitol() {
		return capitol;
	}

	/**
	 *
	 * @param capitol of a State as a String
	 */
	public void setCapitol(String capitol) {
		this.capitol = capitol;
	}

	/**
	 *
	 * @return region of a State as a String
	 */
	public String getRegion() {
		return region;
	}

	/**
	 *
	 * @param region of a State as a String
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 *
	 * @return houseSeats (int)
	 */
	public int getHouseSeats() {
		return houseSeats;
	}

	/**
	 *
	 * @param houseSeats (int) number of house seats
	 */
	public void setHouseSeats(int houseSeats) {
		this.houseSeats = houseSeats;
	}

	/**
	 *
	 * @return population of state int
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 *
	 * @param population int
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * Return the total number of COVID cases within a state as of Sept 2, 2021
	 * @return covidCases total number of Covid cases int
	 */
	public int getCovidCases() {
		return covidCases;
	}

	/**
	 * Set the number of COVID cases as of Sept 2, 2021
	 * @param covidCases int
	 */
	public void setCovidCases(int covidCases) {
		this.covidCases = covidCases;
	}

	/**
	 * Get the total number of COVID deaths as of Sept 2, 2021
	 * @return covidDeaths int
	 */
	public int getCovidDeaths() {
		return covidDeaths;
	}

	/**
	 * set the total number of COVID deaths as of Sept 2, 2021
	 * @param covidDeaths int
	 */
	public void setCovidDeaths(int covidDeaths) {
		this.covidDeaths = covidDeaths;
	}

	/**
	 * Returns the median household income as of the 2020 World Population Review
	 * @return medianIncome int
	 */
	public int getMedianIncome() {
		return medianIncome;
	}

	/**
	 * sets the median household income as of the 2020 World Population Review
	 * @param medianIncome int
	 */
	public void setMedianIncome(int medianIncome) {
		this.medianIncome = medianIncome;
	}

	/**
	 * gets the violent crime rate of a state as reported by the FBI in 2019
	 * @return violentRate float
	 */
	public float getViolentRate() {
		return violentRate;
	}

	/**
	 * sets the violent crime rate of a state as reported by the FBI in 2019
	 * @param violentRate float
	 */
	public void setViolentRate(float violentRate) {
		this.violentRate = violentRate;
	}

	/**
	 * gets the COVID case rate, defined as cases per 100K people
	 * @return caseRate float
	 */
	public float getCaseRate() {
		return caseRate;
	}

	/**
	 * sets the COVID case rate, defined as cases per 100K people
	 * @param caseRate float
	 */
	public void setCaseRate(float caseRate) {
		this.caseRate = caseRate;
	}

	/**
	 * gets the COVID death rate, defined as COVID deaths per 100k people
	 * @return deathRate float
	 */
	public float getDeathRate() {
		return deathRate;
	}

	/**
	 * sets the COVID death rate, defined as COVID deaths per 100k people
	 * @param deathRate float
	 */
	public void setDeathRate(float deathRate) {
		this.deathRate = deathRate;
	}

	/**
	 * gets the COVID-19 Case Fatality Rate (CFR), defined the ratio of cases to deaths
	 * @return caseFatalityRate float
	 */
	public float getCaseFatalityRate() {
		return caseFatalityRate;
	}

	/**
	 * sets the COVID-19 Case Fatality Rate (CFR), defined the ratio of cases to deaths
	 * @param caseFatalityRate float
	 */
	public void setCaseFatalityRate(float caseFatalityRate) {
		this.caseFatalityRate = caseFatalityRate;
	}
} // end class
