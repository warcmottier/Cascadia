package game;

import java.util.ArrayList;
import java.util.HashMap;

public class Tiles {
	private final String landscape;
	private ArrayList<String> animalAccepted;
	private WildlifeToken animal;
	
	Tiles(ArrayList<String> animalAccepted, WildlifeToken wildlife, String landscape) {
		this.landscape = landscape;
		this.animalAccepted = animalAccepted;
		this.animal = wildlife;
	}
	
	public void addWildlifeToken(WildlifeToken wildlife) {
		animal = wildlife;
	}
	
	//to do
	private boolean checkCorrecttCoordonateSquare(Coordonate coordonateTested, Coordonate coordonateCurrent) {
		boolean bool;
		if(coordonateCurrent.x() + 1 == coordonateTested.x() && coordonateCurrent.y())
	}
	
	//to do
	public boolean checkNeighbourTileSquare(Coordonate coordonateCurrent, Coordonate coordonateTested, HashMap<Coordonate, Tiles> playerMap) {
		for(var key: playerMap.keySet()) {
			if(key.equals(coordonateTested)) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
