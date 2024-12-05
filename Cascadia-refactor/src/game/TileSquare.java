package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
	
	private static Set<Coordinate> allNeighbour(Coordinate currentTiles) {
		Set<Coordinate> coordinate = Set.of(new Coordinate(currentTiles.x() + 1, currentTiles.y()),
		    new Coordinate(currentTiles.x() - 1, currentTiles.y()),
		    new Coordinate(currentTiles.x(), currentTiles.y() + 1),
		    new Coordinate(currentTiles.x(), currentTiles.y() - 1));
		return coordinate;
	}
	
	public static Set<Coordinate> notneighbour(Coordinate currentTiles, HashMap<Coordinate, TileSquare> tilesMap) {
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		return allNeighbour(currentTiles).stream()
		    .filter(coordinate -> !tilesMap.containsKey(coordinate))
		    .collect(Collectors.toSet());
	}
	
	public static Set<Coordinate> neighbour(Coordinate currentTiles, HashMap<Coordinate, TileSquare> tilesMap) {
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		return allNeighbour(currentTiles).stream()
        .filter(coordinate -> tilesMap.containsKey(coordinate))
        .collect(Collectors.toSet());
	}
	
	@Override
	public String toString() {
		return "landscape : " + landscape + ", " + " animal accepted : " + animalAccepted + ", " + " animal poser : " + animal;
	}
}