package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

public class TileSquare {
	private final Landscape landscape;
	private final Set<WildlifeToken> animalAccepted;
	private WildlifeToken animal;
	
	public TileSquare(Set<WildlifeToken> animalAccepted, WildlifeToken wildlife, Landscape landscape) {
		Objects.requireNonNull(animalAccepted);
		Objects.requireNonNull(landscape);
		this.landscape = landscape;
		this.animalAccepted = animalAccepted;
		this.animal = wildlife;
	}
	
	public Landscape landscape() {
	  return landscape;
	}
	
	public WildlifeToken animal() {
		return animal;
	}
	
	public Set<WildlifeToken> animalAccepted() {
	  return animalAccepted;
	}
	
	public void setWildlifeToken(WildlifeToken wildLife) {
		Objects.requireNonNull(wildLife);
		animal = wildLife;
	}
	
	private HashSet<Object> allNeighbour(Coordinate currentTiles, HashMap<Coordinate, TileSquare> tilesMap) {
		var list = List.of(new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0), new Coordinate(-1, 0));
		var coordinate = new HashSet<>();
		return coordinate;
	}
	
	public static Set<Coordinate> notneighbour(Coordinate currentTiles, HashMap<Coordinate, TileSquare> tilesMap) {
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		Set<Coordinate> neighbour = new HashSet<Coordinate>();
		Coordinate temporary = new Coordinate(currentTiles.x() + 1, currentTiles.y());
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordinate(currentTiles.x() - 1, currentTiles.y());
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordinate(currentTiles.x(), currentTiles.y() + 1);
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordinate(currentTiles.x(), currentTiles.y() - 1);
		if(!tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		return neighbour;
	}
	
	public static Set<Coordinate> neighbour(Coordinate currentTiles, HashMap<Coordinate, TileSquare> tilesMap) {
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		Set<Coordinate> neighbour = new HashSet<Coordinate>();
		Coordinate temporary = new Coordinate(currentTiles.x() + 1, currentTiles.y());
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordinate(currentTiles.x() - 1, currentTiles.y());
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordinate(currentTiles.x(), currentTiles.y() + 1);
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		temporary = new Coordinate(currentTiles.x(), currentTiles.y() - 1);
		if(tilesMap.containsKey(temporary)) {
			neighbour.add(temporary);
		}
		return neighbour;
	}
	
	@Override
	public String toString() {
		return "landscape : " + landscape + ", " + " animal accepted : " + animalAccepted + ", " + " animal poser : " + animal;
	}
}