package epiModel;

import observer.Observer;
import sim.engine.SimState;
import sim.util.Bag;
import sweep.ParameterSweeper;
import sweep.SimStateSweep;

public class Experimenter extends Observer {
public int newinfected = 0;
    public Experimenter(String fileName, String folderName, SimStateSweep state, ParameterSweeper sweeper,
            String precision, String[] headers) {
        super(fileName, folderName, state, sweeper, precision, headers);
    }
    
    public void countnewlyinfected(Environment environment) {
    	Bag agents = environment.sparseSpace.getAllObjects();
    	
    	for(int i= 0; i < agents.numObjs; i++ ) {
    		Agent agent = (Agent) agents.objs[i];
    		if (agent.newlyinfected = true) {
    			newinfected = newinfected + 1;
    			
    		}
    		
    	}
    	
    }

    @Override
    public void step(SimState state) {
        super.step(state);

        Environment environment = (Environment) state;
        int numInfected = countInfectedAgents(environment);

        
        if (state.schedule.getSteps() % environment.dataSamplingInterval == 0) {
            trackPopulations(environment);
        }

        //vaccination policy check
        if (numInfected >= environment.numInfectedToTriggerVaccination) {
            vaccinate(environment);
        }
        newinfected = 0;
    	Bag agents = environment.sparseSpace.getAllObjects();
        for(int i= 0; i < agents.numObjs; i++ ) {
    		Agent agent = (Agent) agents.objs[i];
    		agent.newlyinfected = false;
        }
    		
       
    }

    public void trackPopulations(Environment environment) {
        int numSusceptible = countSusceptibleAgents(environment);
        int numInfected = countInfectedAgents(environment);
        

        double time = (double) environment.schedule.getTime();

        this.upDateTimeChart(0, time, numSusceptible, true, 1000);  // Chart 0: Susceptible

        this.upDateTimeChart(1, time, numInfected, true, 1000);  // Chart 1: Infected
        this.upDateTimeChart(2, time, newinfected, true, 1000);  

    }

    
 
    
    
    
    public void vaccinate(Environment state) {
        int infectedCount = countInfectedAgents(state);
            Bag allAgents = state.sparseSpace.getAllObjects();
            int vaccinatedCount = 0;
            
            for (int i = 0; i < allAgents.numObjs && vaccinatedCount < state.numToBecomeVaccinated; i++) {
                Agent agent = (Agent) allAgents.objs[i];
                if (agent.healthStatus == Health_Status.SUSCEPTIBLE && !agent.vaccinationStatus) {
                    agent.setVaccinationStatus(true);
                    vaccinatedCount++;
            }
        }
    }
   
    
    
    
    public int countInfectedAgents(Environment state) {
        int infectedCount = 0;
        Bag allAgents = state.sparseSpace.getAllObjects();

        for (int i = 0; i < allAgents.numObjs; i++) {
            Agent agent = (Agent) allAgents.objs[i];

            if (agent.healthStatus == Health_Status.INFECTED) {
                infectedCount++;
            }
        }

        return infectedCount;
    }
    
    public int countSusceptibleAgents(Environment state) {
        int susceptibleCount = 0;
        Bag allAgents = state.sparseSpace.getAllObjects();

        for (int i = 0; i < allAgents.numObjs; i++) {
            Agent agent = (Agent) allAgents.objs[i];
            if (agent.healthStatus == Health_Status.SUSCEPTIBLE) {
                susceptibleCount++;
            }
        }

        return susceptibleCount;
        
    }
    
    
    


}
