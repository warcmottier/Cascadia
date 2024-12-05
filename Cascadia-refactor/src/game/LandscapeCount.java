package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

public record LandscapeCount(Map<Coordinate, TileSquare> player) {
  
  public LandscapeCount {
    Objects.requireNonNull(player);
    player = Map.copyOf(player);
  }
  
  private int isNeighborHaveLandscape(Coordinate current, Set<Coordinate> visited, Landscape landscape) {  //Hello, Darkness my old friend...
    visited.add(current);
    var coordinates = TileSquare.neighbour(current, player);
    var size = 1;
    for(var coordinate : coordinates) { //Hello, Darkness my old friend...
      if(player.get(coordinate).landscape() == landscape && !visited.contains(coordinate)) { //Si c'est pas un endroit vide et qu'il y a un animal et qu'on l'as pas déjà vus
        size += isNeighborHaveLandscape(coordinate, player, visited, landscape); //On regarde du coup les voisins de celui-là
      }
    }
    return size;
  }
  
  public HashMap<Landscape, Integer> everyLandscapeScore() {
    var result = new HashMap<>();
      // TODO MF
  }
  
  private static OptionalInt biggestLandscapeGroup(List<Integer> group) {
    return group.stream().mapToInt(Integer::intValue).max();
  }
  
  private List<Integer> groupSizesLandscape( Landscape landscape) {
    Set<Coordinate> visited = new HashSet<>();
    return player.keySet().stream()
        .filter(key -> !visited.contains(key) && player.get(key).animal() != null)
        .map(key -> isNeighborHaveLandscape(key, visited, landscape))
        .collect(Collectors.toList());
  }
}
