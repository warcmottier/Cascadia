package game;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Objects;

/**
 * WildlifeCount holds the counting methods for each card
 * @param card is the field card contains the current rules to be applied
 */
public record WildlifeCount(AnimalCard card) {
  
  /**
   * Select the correct animal or calls the family or intermediate version depending on the game mode
   * @param player is a Map representing the player board
   * @return int number of points given to the player by this card
   */
  public int countCardScore(Map<Coordinate, TileSquare> player) {
    Objects.requireNonNull(player);
    return switch (card) {
			case AnimalCard.FAMILY -> cardFamily(player);
			case AnimalCard.INTERMEDIATE -> cardMedium(player);
			default -> 0;
		};
  }
  
  /**
   * Transform the size of an animal group to it's corresponding score 
   * @param size the size of the group
   * @return int score
   */
  private static int sizeToScoreFamily(int size) {
    if(size <= 0) {
      throw new IllegalArgumentException();
    }
    return switch(size) {
      case 1 -> 2;
      case 2 -> 5;
      default -> 9;
    };
  }
  
  /**
   * Calcul the number of points attributed by the family card
   * @param player is a Map representing the player board
   * @return int number of points given to the player
   */
  private int cardFamily(Map<Coordinate, TileSquare> player) {
    List<Integer> groupSizes = groupSizesSquare(player);
    return groupSizes.stream().mapToInt(WildlifeCount::sizeToScoreFamily).sum();
  }
  
  
  /**
   * groupSizesSquare finds the biggest group of wildlife tokens adjacent to each others
   * @param player Map representing the board of a player
   * @return List a list of the sizes of the different groups
   */
  private List<Integer> groupSizesSquare(Map<Coordinate, TileSquare> player) {
    Set<Coordinate> visited = new HashSet<>();
    return player.keySet().stream()
      .filter(key -> !visited.contains(key) && player.get(key).animal() != null)
      .map(key -> isNeighborHaveTokenSquare(key, player, visited))
      .collect(Collectors.toList());
  }
  
  /**
   * isNeighborHaveTokenSquare checks if neighbors of the current cell have a wildlife token on them
   * @param current Coordinate the current cell being checked
   * @param player Map representing the board of a player
   * @param visited Set representing the cells already checked
   * @return int the size of a group
   */
  private int isNeighborHaveTokenSquare(Coordinate current, Map<Coordinate, TileSquare> player, Set<Coordinate> visited) {
    visited.add(current);
    var coordinates = TileSquare.neighbour(current, player);
    var size = 1;
    for(var coordinate : coordinates) {
      if(player.get(coordinate).animal() != null && !visited.contains(coordinate)) { 
        size += isNeighborHaveTokenSquare(coordinate, player, visited); 
      }
    }
    return size;
  }
  
  /**
   * Transform the size of an animal group to it's corresponding score 
   * @param size the size of the group
   * @return int score
   */
  private static int sizeToScoreIntermediate(int size) {
    if(size <= 0) {
      throw new IllegalArgumentException("An animal group without any animal, is, infact, an issue");
    }
    return switch(size) {
      case 1 -> 0;
      case 2 -> 5;
      case 3 -> 8;
      default -> 12;
    };
  }
  
  /**
   * Calculate the number of points attributed by the intermediate card
   * @param player is a Map representing the player board
   * @return int number of points given to the player
   */
  private int cardMedium(Map<Coordinate, TileSquare> player) {
    List<Integer> groupSizes = groupSizesSquare(player);
    return groupSizes.stream().mapToInt(WildlifeCount::sizeToScoreIntermediate).sum();
  }
  
  @Override
  public final String toString() {
    return switch (card) {
      case AnimalCard.FAMILY -> "groupe de 1 animal : 2 points \ngroupe de 2 animaux : 5 points \ngroupe de 3 animaux ou plus : 9 points";
      case AnimalCard.INTERMEDIATE -> "groupe de 2 animaux : 5 points \ngroupe de 3 animaux : 8 points \ngroupe de 4 animaux ou plus : 12 points";
      default -> "les cartes animal ne sont pas implementé en version terminal";
    };
  }
} 
