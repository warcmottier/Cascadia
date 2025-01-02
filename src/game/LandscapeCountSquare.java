package game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record LandscapeCountSquare(Map<Coordinate, TileSquare> player) {
  
  public LandscapeCountSquare {
    Objects.requireNonNull(player);
    player = Map.copyOf(player);
  }
  
  private void isNeighborHaveLandscape(Coordinate current, Set<Coordinate> visited, Landscape landscape, int[] size) {  //Hello, Darkness my old friend...
    visited.add(current);
    if(player.get(current).landscape() == landscape) {
    	size[0]++;
    }
    var coordinates = TileSquare.neighbour(current, player);
    for(var coordinate : coordinates) { //Hello, Darkness my old friend...
      if(player.get(coordinate).landscape() == landscape && !visited.contains(coordinate)) { //Si c'est pas un endroit vide et qu'il y a un animal et qu'on l'as pas déjà vus	
      	isNeighborHaveLandscape(coordinate, visited, landscape, size); //On regarde du coup les voisins de celui-là
      }
    }
  }
  
  private int numberLandscape(Coordinate current, Set<Coordinate> visited, Landscape landscape) {
  	int[] number = {0};
  	isNeighborHaveLandscape(current, visited, landscape, number);
  	return number[0];
  }
  
  public Map<Landscape, Integer> everyLandscapeScore() {
    return Arrays.stream(Landscape.values())
                 .collect(Collectors.toMap(
                     landscape -> landscape,
                     landscape -> biggestLandscapeGroup(groupSizesLandscape(landscape))
                 ));
  }
  
  private static Integer biggestLandscapeGroup(List<Integer> group) {
    return group.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
}

  
  private List<Integer> groupSizesLandscape( Landscape landscape) {
    Set<Coordinate> visited = new HashSet<>();
    return player.keySet().stream()
        .filter(key -> !visited.contains(key))
        .map(key -> numberLandscape(key, visited, landscape))
        .collect(Collectors.toList());
  }
}
