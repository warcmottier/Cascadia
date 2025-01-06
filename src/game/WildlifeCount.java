package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Objects;

/**
 * WildlifeCount holds the counting methods for each card
 * the field card contains the current rules to be applied
 */
public record WildlifeCount(AnimalCard card) {
  
  /**
   * Select the correct animal or calls the family or intermediate version depending on the game mode
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
      throw new IllegalArgumentException("Un groupe d'animaux sans animaux, c'est problématique");
    }
    return switch(size) {
      case 1 -> 2;
      case 2 -> 5;
      default -> 9;
    };
  }
  
  /**
   * Calcul the number of points attributed by the family card
   * @return int number of points given to the player
   */
  private int cardFamily(Map<Coordinate, TileSquare> player) {
    List<Integer> groupSizes = groupSizesSquare(player);
    return groupSizes.stream().mapToInt(WildlifeCount::sizeToScoreFamily).sum();
  }
  
  
  
  private List<Integer> groupSizesSquare(Map<Coordinate, TileSquare> player) {
    Set<Coordinate> visited = new HashSet<>();
    return player.keySet().stream()
      .filter(key -> !visited.contains(key) && player.get(key).animal() != null)
      .map(key -> isNeighborHaveTokenSquare(key, player, visited))
      .collect(Collectors.toList());
  }
  
  private int isNeighborHaveTokenSquare(Coordinate current, Map<Coordinate, TileSquare> player, Set<Coordinate> visited) {
    visited.add(current);
    var coordinates = TileSquare.neighbour(current, player);
    var size = 1;
    for(var coordinate : coordinates) { //Hello, Darkness my old friend...
      if(player.get(coordinate).animal() != null && !visited.contains(coordinate)) { //Si c'est pas un endroit vide et qu'il y a un animal et qu'on l'as pas déjà vus
        size += isNeighborHaveTokenSquare(coordinate, player, visited); //On regarde du coup les voisins de celui-là
      }
    }
    return size;
  }
  
  private static int sizeToScoreIntermediate(int size) {
    if(size <= 0) {
      throw new IllegalArgumentException("Un groupe d'animaux sans animaux, c'est problématique");
    }
    return switch(size) {
      case 1 -> 0;
      case 2 -> 5;
      case 3 -> 8;
      default -> 12;
    };
  }
  
  /**
   * Calcul the number of points attributed by the intermediate card
   * @return int number of points given to the player
   */
  private int cardMedium(Map<Coordinate, TileSquare> player) {
    List<Integer> groupSizes = groupSizesSquare(player);
    return groupSizes.stream().mapToInt(WildlifeCount::sizeToScoreIntermediate).sum();
  }
  
  /**
   * Calculate the number of points attributed to the player by the bear
   card A
   * @return int number of points
   */
  private int cardAOurs(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * Calculate the number of points attributed to the player by the bear
   card B
   * @return int number of points
   */
  private int cardBOurs(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * Calculate the number of points attributed to the player by the bear
   card C
   * @return int number of points
   */
  private int cardCOurs(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * Calculate the number of points attributed to the player by the bear
   card D
   * @return int number of points
   */
  private int cardDOurs(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card A
   * @return int number of points
   */
  private int cardASaumon(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card B
   * @return int number of points
   */
  private int cardBSaumon(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card C
   * @return int number of points
   */
  private int cardCSaumon(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card D
   * @return int number of points
   */
  private int cardDSaumon(HashMap<Coordinate, TileSquare> player) {
    //Besoin TuileHabitat
    return 0;
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
