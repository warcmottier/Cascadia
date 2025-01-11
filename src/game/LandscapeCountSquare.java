package game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * LandscapeCountSquare counts the point for landscapes
 * @param player Map represent the Map of the player having it's point counted
 */
public record LandscapeCountSquare(Map<Coordinate, TileSquare> player) {
  
  /**
   * the constructor checks that player is in fact real and makes a copy of it just to be sure it doesn't try changing mid counting
   * @param player Map represent the Map of the player having it's point counted
   */
  public LandscapeCountSquare {
    Objects.requireNonNull(player);
    player = Map.copyOf(player);
  }
  
  /**
   * isNeighborHaveLandscape checks the neighbors to see if they have the same landscape as the current tile and adds them to the group
   * @param current a Coordinate representing the coordinates of the tile currently explored
   * @param visited a Set representing the already explored coordinates
   * @param landscape a Landscape representing the landscape we are searching for
   * @param size an int representing the size of the current group
   */
  private void isNeighborHaveLandscape(Coordinate current, Set<Coordinate> visited, Landscape landscape, int[] size) {
    visited.add(current);
    if(player.get(current).landscape() == landscape) {
    	size[0]++;
    }
    var coordinates = TileSquare.neighbour(current, player);
    for(var coordinate : coordinates) {
      if(player.get(coordinate).landscape() == landscape && !visited.contains(coordinate)) { 
      	isNeighborHaveLandscape(coordinate, visited, landscape, size); 
      }
    }
  }
  
  /**
   * numberLandscape finds the size of a group of tiles having the same Landscape and neighboring each others
   * @param current a Coordinate representing the coordinates of the tile currently explored
   * @param visited a Set representing the already explored coordinates
   * @param landscape a Landscape representing the landscape we are searching for
   * @return an int representing the size of the group
   */
  private int numberLandscape(Coordinate current, Set<Coordinate> visited, Landscape landscape) {
  	int[] number = {0};
  	isNeighborHaveLandscape(current, visited, landscape, number);
  	return number[0];
  }
  
  /**
   * everyLandscapeScore finds the score of every single Landscape
   * @return Map the map associating each landscape with its corresponding score
   */
  public Map<Landscape, Integer> everyLandscapeScore() {
    return Arrays.stream(Landscape.values())
                 .collect(Collectors.toMap(
                     landscape -> landscape,
                     landscape -> biggestLandscapeGroup(groupSizesLandscape(landscape))
                 ));
  }
  
  /**
   * biggestLandscapeGroup finds the biggest group
   * @param group List representing a list of every group
   * @return an Integer representing the index of the biggest group
   */
  private static Integer biggestLandscapeGroup(List<Integer> group) {
    return group.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
}

  /**
   * groupSizesLandscape finds the different groups for the current landscape
   * @param landscape a Landscape representing the current landscape
   * @return List representing the list of the size of every group
   */
  private List<Integer> groupSizesLandscape( Landscape landscape) {
    Set<Coordinate> visited = new HashSet<>();
    return player.keySet().stream()
        .filter(key -> !visited.contains(key))
        .map(key -> numberLandscape(key, visited, landscape))
        .collect(Collectors.toList());
  }
}
