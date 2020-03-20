/*
@author
Adam Brandt 
*/

package cs228hw4.game;


import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cs228hw4.graph.CS228DiGraph;
import cs228hw4.graph.CS228Dijkstra;
import edu.iastate.cs228.game.Agent;
import edu.iastate.cs228.game.GalaxyState;
import edu.iastate.cs228.game.SystemState;

public class MyAgent implements Agent {
	private Color myColor;
	private Color oppColor;
	private boolean setScan = false;
	int count = 0;
	public MyAgent() {
		
	}

	@Override
	public String getFirstName() {
		return "Adam";
	}

	@Override
	public String getLastName() {
		return "Brandt";
	}

	@Override
	public String getStuID() {
		return "734236833";
	}

	@Override
	public String getUsername() { 
		return "abrandt1";
	}

	@Override
	public String getAgentName() {
		return "abrandt1897";
	}

	@Override
	public File getAgentImage() {
		return null;
	}

	@Override
	public boolean inTournament() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentAction[] getCommandTurnTournament(GalaxyState g, int energyLevel) {
		AgentAction actions[] = new AgentAction[3];
		int actionsTaken = 0;
		int elevel = energyLevel;
		SystemState[] currSystems = g.getSystems();
		SystemState curr = g.getCurrentSystemFor(myColor);
		SystemState opp = g.getCurrentSystemFor(oppColor);
		CS228DiGraph<SystemState> myGraph = new CS228DiGraph<SystemState>();
		count++;
		if(!setScan) {
			SetScan scan = new SetScan(20);
			actions[actionsTaken] = scan;
			actionsTaken++;
			setScan = true;
		}
		/*
		if(elevel < 3 && setScan){
			SetScan scan = new SetScan(0);
			actions[actionsTaken] = scan;
			actionsTaken++;
		}
		*/
		if(curr.getCostToCapture() < elevel && curr.getOwner() != myColor) { 
			Capture capt = new Capture(curr.getCostToCapture());
			actions[actionsTaken] = capt;
			actionsTaken++;
		}
		else if(curr.getCostToCapture() > elevel && curr.getOwner() != myColor) {
			NoAction NoAction = new NoAction();
			for(int i = 0; i < actions.length; i++)
				actions[i] = NoAction;
			Arrays.stream(actions).forEach(System.out :: println);
			return actions;
		}
		Refuel refuel = new Refuel();
		actions[actionsTaken] = refuel;
		actionsTaken++;
		while(actionsTaken < actions.length) {
			for(int i = 0; i < currSystems.length; i++) {
				myGraph.addVertex(currSystems[i]);
			}
			for(int j = 0; j < currSystems.length; j++) {
				for(int i = 0; i < currSystems[j].getNeighbors().length; i++)
					if(currSystems[j].getTravelCost()[i] != 0)  
						myGraph.addEdge(currSystems[j], currSystems[j].getNeighbors()[i], currSystems[j].getTravelCost()[i]);
			}
			CS228Dijkstra<SystemState> myDi = new CS228Dijkstra<SystemState>(myGraph);
			myDi.run(curr);
			int shortCost = Integer.MAX_VALUE;
			ArrayList<SystemState> shortPath = new ArrayList<SystemState>();
			/*
			if(count == 3) {
				ArrayList<SystemState> shootPath = new ArrayList<SystemState>();
				shootPath = (ArrayList<SystemState>) myDi.getShortestPath(opp);
				String[] temp = new String[shootPath.size()]; 
				for(int d = 0; d < shootPath.size(); d++) {
					temp[d] = shootPath.get(d).toString();
				}
				Shoot shot = new Shoot(temp, 20);
				actions[actionsTaken] = shot;
				actionsTaken++;
				count = 0; 
			}
			*/
			for(int i = 0; i < myGraph.numVertices(); i++) {
				if(shortCost > myDi.getShortestDistance(currSystems[i]) && myDi.getShortestDistance(currSystems[i]) != 0 && currSystems[i].getOwner() != myColor) {
					shortCost = myDi.getShortestDistance(currSystems[i]);
					shortPath = (ArrayList<SystemState>) myDi.getShortestPath(currSystems[i]);
				}
			}
			if(shortPath.size() == 0) {
				Fortify fort = new Fortify(1);
				for(int h = actionsTaken; h < actions.length; h++) 
					actions[h] = fort;
				Arrays.stream(actions).forEach(System.out :: println);
				return actions;
			}
			int i = 1;
			if(actionsTaken < actions.length) {
				Move myMove = new Move(shortPath.get(i).getName());
				actions[actionsTaken] = myMove;
				actionsTaken++;
			}
			if(shortPath.get(i).getCostToCapture() < elevel && actionsTaken < actions.length) {
				Capture capt = new Capture(curr.getCostToCapture());
				actions[actionsTaken] = capt;
				actionsTaken++;
			}
			else if(shortPath.get(i).getCostToCapture() > elevel && actionsTaken < actions.length) {
				NoAction none =  new NoAction();
				for(int t = actionsTaken; t < actions.length; t++)
					actions[t] = none;
				Arrays.stream(actions).forEach(System.out :: println);
				return actions;
			}
			else {
				break;
			}
			i++;
			Arrays.stream(actions).forEach(System.out :: println);
		}
		return actions;
	}

	@Override
	public AgentAction[] getCommandTurnGrading(GalaxyState g, int energyLevel) {
		AgentAction actions[] = new AgentAction[3];
		int actionsTaken = 0;
		int elevel = energyLevel;
		SystemState[] currSystem = g.getSystems();
		SystemState curr = g.getCurrentSystemFor(myColor);
		if (curr.getOwner() != myColor)  {
			if(curr.getCostToCapture() < elevel) {
				Capture capt = new Capture(curr.getCostToCapture());
				actions[actionsTaken] = capt;
				actionsTaken++;
			}
			else {
				NoAction NoAction = new NoAction();
				for(int k = 0; k < actionsTaken; k++)
					actions[k] = NoAction;
				return actions;
			}
		}
		Refuel refuel = new Refuel();
		actions[actionsTaken] = refuel;
		actionsTaken++;
		int temp = Integer.MAX_VALUE;
		String path = " ";
		int j = 0;
		for(; j < curr.getNeighbors().length; j++) {
			if(temp > curr.getTravelCost()[j] && curr.getOwner() != myColor) {
				temp = curr.getTravelCost()[j];
				path = curr.getNeighbors()[j].getName();
			}
		}
		Random rand = new Random();
		int myNum = rand.nextInt(curr.getNeighbors().length);
		if(temp == Integer.MAX_VALUE) {
			path = curr.getNeighbors()[myNum].getName();
		}
		Move myMove = new Move(path);
		actions[actionsTaken] = myMove;
		actionsTaken++;
		if(actionsTaken != 3) {
			if(curr.getNeighbors()[j-1].getOwner() != myColor && curr.getNeighbors()[j-1].getCostToCapture() < elevel) {
				Capture capt = new Capture(curr.getCostToCapture());
				actions[actionsTaken] = capt;
				actionsTaken++;
			}
			else {
				NoAction NoAction = new NoAction();
				actions[actionsTaken] = NoAction;
				actionsTaken++;
			}
		}
		return actions;
	}
		

	@Override
	public void setColor(Color c) {
		myColor = c;
	}

	@Override
	public void setOpponentColor(Color c) {
		oppColor = c;
	}
}
