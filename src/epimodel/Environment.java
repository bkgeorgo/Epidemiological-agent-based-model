package epiModel;

import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import spaces.Spaces;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
    public int gridHeight = 100;
    public int gridWidth = 100;
    public int numOlderAgents = 50;
    public int numYoungerAgents = 50;
    public double P_infection = 0.3;
    public int infectionDuration = 50;
   
    public Experimenter experimenter = null;
    

    
    
    
    public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getNumOlderAgents() {
		return numOlderAgents;
	}

	public void setNumOlderAgents(int numOlderAgents) {
		this.numOlderAgents = numOlderAgents;
	}

	public int getNumYoungerAgents() {
		return numYoungerAgents;
	}

	public void setNumYoungerAgents(int numYoungerAgents) {
		this.numYoungerAgents = numYoungerAgents;
	}

	public double getP_infection() {
		return P_infection;
	}

	public void setP_infection(double p_infection) {
		P_infection = p_infection;
	}

	public int getInfectionDuration() {
		return infectionDuration;
	}

	public void setInfectionDuration(int infectionDuration) {
		this.infectionDuration = infectionDuration;
	}

	public int getNumInfectedToTriggerVaccination() {
		return numInfectedToTriggerVaccination;
	}

	public void setNumInfectedToTriggerVaccination(int numInfectedToTriggerVaccination) {
		this.numInfectedToTriggerVaccination = numInfectedToTriggerVaccination;
	}

	public int getNumToBecomeVaccinated() {
		return numToBecomeVaccinated;
	}

	public void setNumToBecomeVaccinated(int numToBecomeVaccinated) {
		this.numToBecomeVaccinated = numToBecomeVaccinated;
	}

	public int getImmunityDuration() {
		return immunityDuration;
	}

	public void setImmunityDuration(int immunityDuration) {
		this.immunityDuration = immunityDuration;
	}


	public int numInfectedToTriggerVaccination = 10;
    public int numToBecomeVaccinated = 20;
    public int immunityDuration = 100;

	public Environment(long seed) {
		super(seed);
		// TODO Auto-generated constructor stub
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}

	public Environment(long seed, Class observer, String runTimeFileName) {
		super(seed, observer, runTimeFileName);
		// TODO Auto-generated constructor stub
	}
	@Override
    public void start() {
        super.start();
        makeSpace(gridWidth, gridHeight);
        if(observer != null) {
			observer.initialize(sparseSpace, Spaces.SPARSE);//initialize the experimenter by calling initialize in the parent class
			experimenter = (Experimenter)observer;//cast observer as experimenter
			experimenter.setObservationInterval(this.dataSamplingInterval);
		}
        
        makeAgents(numOlderAgents,numYoungerAgents);

        
    }
	

	 public void makeAgents(int numOlderAgents, int numYoungerAgents) {
		    int x, y, xdir, ydir;

		    // Create older agents
		    for (int i = 0; i < numOlderAgents; i++) {
		        x = random.nextInt(gridWidth);
		        y = random.nextInt(gridHeight);
		        xdir = random.nextInt(3) - 1;
		        ydir = random.nextInt(3) - 1;

		        Agent agent = new Agent(Age.OLD, x, y, xdir, ydir, Health_Status.SUSCEPTIBLE);
		        sparseSpace.setObjectLocation(agent, x, y);
		        schedule.scheduleRepeating(agent);
		    }

		    // Create younger agents
		    for (int i = 0; i < numYoungerAgents; i++) {
		        x = random.nextInt(gridWidth);
		        y = random.nextInt(gridHeight);
		        xdir = random.nextInt(7) - 3; 
		        ydir = random.nextInt(7) - 3;

		        // Create agent and place in the grid
		        Agent agent = new Agent(Age.YOUNG, x, y, xdir, ydir, Health_Status.SUSCEPTIBLE);
		        sparseSpace.setObjectLocation(agent, x, y);
		        schedule.scheduleRepeating(agent);
		        // Make first agent infected
		        if (i == 0) {
		            agent.healthStatus = Health_Status.INFECTED;
		        }
		    }
		}
	 
	 
	
	
}
