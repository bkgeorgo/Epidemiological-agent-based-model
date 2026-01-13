package epiModel;

import java.awt.Color;


import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class AgentsGUI extends GUIStateSweep {

	public AgentsGUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		// TODO Auto-generated constructor stub
	}

	public AgentsGUI(SimStateSweep state) {
		super(state);
		// TODO Auto-generated constructor stub
	}

	 public static void main(String[] args) {
	        String[] title = {"Susceptible Agents", "Infected Agents", "New infected"}; // Titles for the charts
	        String[] x = {"Time Steps", "Time Steps", "Time steps"}; // X-axis titles
	        String[] y = {"Number of Susceptible Agents", "Number of Infected Agents","New infected"}; // Y-axis titles

	        AgentsGUI.initializeArrayTimeSeriesChart(3, title, x, y);


	        AgentsGUI.initialize(Environment.class, Experimenter.class, AgentsGUI.class, 600, 600, Color.WHITE, Color.BLACK, true, Spaces.SPARSE);
	    }

}
