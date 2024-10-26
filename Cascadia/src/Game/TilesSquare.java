package Game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class TilesSquare {
	private final String landscape;
	private final Set<String> animalAccepted;
	private WildlifeToken animal;
	
	public TilesSquare(Set<String> animalAccepted, WildlifeToken wildlife, String landscape) {
		Objects.requireNonNull(animalAccepted);
		Objects.requireNonNull(landscape);
		this.landscape = landscape;
		this.animalAccepted = animalAccepted;
		this.animal = wildlife;
	}
	
	public WildlifeToken animal() {
		return animal;
	}
	
	public void setWildlifeToken(WildlifeToken wildLife) {
		Objects.requireNonNull(wildLife);
		animal = wildLife;
	}
	
	public static Set<Coordonate> notneighbour(Coordonate currentTiles, HashMap<Coordonate, TilesSquare> tilesMap){
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		Set<Coordonate> neighbour = new HashSet<Coordonate>();
		Coordonate temporary = new Coordonate(currentTiles.x() + 1, currentTiles.y());
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordonate(currentTiles.x() - 1, currentTiles.y());
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordonate(currentTiles.x(), currentTiles.y() + 1);
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordonate(currentTiles.x(), currentTiles.y() - 1);
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		return neighbour;
	}
	
	public static Set<Coordonate> neighbour(Coordonate currentTiles, HashMap<Coordonate, TilesSquare> tilesMap){
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		Set<Coordonate> neighbour = new HashSet<Coordonate>();
		Coordonate temporary = new Coordonate(currentTiles.x() + 1, currentTiles.y());
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordonate(currentTiles.x() - 1, currentTiles.y());
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordonate(currentTiles.x(), currentTiles.y() + 1);
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordonate(currentTiles.x(), currentTiles.y() - 1);
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		return neighbour;
	}
	
	@Override
	public String toString() {
		return landscape + " " + animalAccepted + " " + animal;
	}
}
