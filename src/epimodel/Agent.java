package epiModel;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Agent implements Steppable {
	public int x, y;
    public int xdir, ydir;
    public Age age = Age.YOUNG;
    public Health_Status healthStatus =  Health_Status.SUSCEPTIBLE;
    public boolean vaccinationStatus = false;
    public int currentInfectionTime = 0;
    public int currentImmunityTime = 0;
    public boolean newlyinfected = false;

    public double P_activeMovement;
    public double P_changingDirection;
    public  double young_PactiveMovement = 0.0;
    public  double young_PchangingDirection = 0.0;
    public  double old_PactiveMovement = 0.0;
    public  double old_PchangingDirection = 0.0;
    Experimenter experimenter;

    public static final int infectionRadius = 1;

    public Agent(Age age, int x, int y, int xdir, int ydir, Health_Status healthStatus) {
        this.age = age;
        this.x = x;
        this.y = y;
        this.xdir = xdir;
        this.ydir = ydir;

        if (age == Age.YOUNG) {
            P_activeMovement = 0.8;
            P_changingDirection = 0.7;
        } else {
            P_activeMovement = 0.3;
            P_changingDirection = 0.2;
        }
    }
    public void placeAgent(Environment state) {
     
		 x = state.sparseSpace.stx(x + xdir);
         y = state.sparseSpace.sty(y + ydir);
         state.sparseSpace.setObjectLocation(this, this.x, this.y); 
   }
    
    
    public void move(Environment state) {
        if (state.random.nextDouble() < P_activeMovement) {
            int maxMovement = (age.equals(Age.YOUNG)) ? 3 : 1; // Young move 3 blocks, old move 1 block
            x = x + xdir * maxMovement;
            y = y + ydir * maxMovement;
        }

        if (state.random.nextDouble() < P_changingDirection) {
            xdir = state.random.nextInt(3) - 1;
            ydir = state.random.nextInt(3) - 1;
        }

        placeAgent(state); 
    }
    
    
   
	@Override
	public void step(SimState state) {
	      Environment eState = (Environment) state;
	      move(eState);
	      if(vaccinationStatus == true) {
	    	  return;
	      }
		 
		 if (healthStatus == Health_Status.SUSCEPTIBLE) {
	         checkinfection(eState);
	        }
		 if (healthStatus == Health_Status.INFECTED) {
	           handleInfection(eState);
	          
	        }
		 if (healthStatus == Health_Status.RECOVERED) {
	           handleRecovery(eState);
	        }
		
	}
	
	
	 public void checkinfection(Environment eState) {
	        // Check nearby agents for infection
		 Bag nearbyAgents = eState.sparseSpace.getMooreNeighbors(x, y, infectionRadius, eState.sparseSpace.TOROIDAL, false);
	        for (int i = 0; i < nearbyAgents.numObjs; i++) {
	            Agent otherAgent = (Agent) nearbyAgents.objs[i];
	            if (otherAgent.healthStatus == Health_Status.INFECTED) {
	            	
	                if (eState.random.nextDouble() < eState.P_infection) {
	                    healthStatus = Health_Status.INFECTED;
	                    newlyinfected = true;
	                }
	            }
	        }
	    }
	 
	 public void handleInfection(Environment eState) {
	        currentInfectionTime++;
	        if (currentInfectionTime >= eState.infectionDuration) {
	            // After infection duration, agent recovers
	            healthStatus = Health_Status.RECOVERED;
	            currentInfectionTime = 0;
	            currentImmunityTime = 0;
	        }
	    }

	    public void handleRecovery(Environment eState) {
	        currentImmunityTime++;
	        if (currentImmunityTime >= eState.immunityDuration) {
	            // after immunity duration, make agent susceptible again
	            healthStatus = Health_Status.SUSCEPTIBLE;
	            currentImmunityTime = 0;
	        }
	    }
	    
	 public void setVaccinationStatus(boolean status) {
	        vaccinationStatus = status;
	    }

}
