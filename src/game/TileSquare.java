package game;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

/**
 * TileSquare represents a Square version of a tile for Cascadia
 *  landscape is a field representing the landscape of a tile
 *  animalAccepted is a field representing the animals accepted by thiss tile
 *  animal is a field representing the current animal on this tile
 */
public final class TileSquare {
	private final Landscape landscape;
	private final Set<WildlifeToken> animalAccepted;
	private WildlifeToken animal;
	
	/**
	 * TileSquare, the constructor, checks if animalAccepted and landscape do exist, animal can be null
	 * @param animalAccepted Set represents the animals accepted by this tile
	 * @param animal a WildlifeToken representing the current animal on this tile, can be null
	 * @param landscape a Landscape representing the landscape of a tile
	 */
	public TileSquare(Set<WildlifeToken> animalAccepted, WildlifeToken animal, Landscape landscape) {
		Objects.requireNonNull(animalAccepted);
		Objects.requireNonNull(landscape);
		this.landscape = landscape;
		this.animalAccepted = Set.copyOf(animalAccepted);
		this.animal = animal;
	}
	
	/**
	 * the getter for the landscape field
	 * @return the content of the landscape field
	 */
	public Landscape landscape() {
	  return landscape;
	}
	
	 /**
   * the getter for the animal field
   * @return the content of the animal field
   */
	public WildlifeToken animal() {
		return animal;
	}
	
	 /**
   * the getter for the animalAccepted field
   * @return the content of the animalAccepted field
   */
	public Set<WildlifeToken> animalAccepted() {
	  return animalAccepted;
	}
	
	 /**
   * setWildlifeToken set the wildlife Token on the tile
   * @param wildlife is a wildlifeToken representing the future wildlife on the tile
   */
	public void setWildlifeToken(WildlifeToken wildlife) {
		Objects.requireNonNull(wildlife);
		animal = wildlife;
	}
	
	/**
	 * allNeighbour finds every neighbor of a tile
	 * @param currentTiles the Coordinate of the tile being checked
	 * @return Set the neighbors of the tile
	 */
	private static Set<Coordinate> allNeighbour(Coordinate currentTiles) {
		Set<Coordinate> coordinate = Set.of(new Coordinate(currentTiles.x() + 1, currentTiles.y()),
		    new Coordinate(currentTiles.x() - 1, currentTiles.y()),
		    new Coordinate(currentTiles.x(), currentTiles.y() + 1),
		    new Coordinate(currentTiles.x(), currentTiles.y() - 1));
		return coordinate;
	}
	
	 /**
   * notneighbour finds every empty neighbor of a tile
   * @param currentTiles the Coordinate of the tile being checked
   * @param tilesMap is a Map representing the player map
   * @return Set the empty spots of the tile
   */
	public static Set<Coordinate> notneighbour(Coordinate currentTiles, Map<Coordinate, TileSquare> tilesMap) {
		Objects.requireNonNull(currentTiles);
		Objects.requireNonNull(tilesMap);
		return allNeighbour(currentTiles).stream()
		    .filter(coordinate -> !tilesMap.containsKey(coordinate))
		    .collect(Collectors.toSet());
	}
	
	/**
	 * neighbour search for every neighbors of a map
	 * @param currentTiles the tile currently being checked
	 * @param tilesMap the Map containing every tiles and their coordinates
	 * @return a set of every neighbors
	 */
	public static Set<Coordinate> neighbour(Coordinate currentTiles, Map<Coordinate, TileSquare> tilesMap) {
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