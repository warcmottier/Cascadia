package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

public record WildlifeCount(AnimalCard card) {
  
  /**
   * Select the correct animal or calls the familly or intermediate version depending on the game mode
   * @return int number of points given to the player by this card
   */
  public int countCardScore(HashMap<Coordinate, TileSquare> player) {
    Objects.requireNonNull(player);
    return switch (card) {
			case AnimalCard.FAMILY -> cardFamilly(player);
			case AnimalCard.INTERMEDIATE -> cardMedium(player);
			default -> 0;
		};
  }
  
  /**
   * Calcul the number of points attributed by the familly card
   * @return int number of points given to the player
   */
  private int cardFamilly(HashMap<Coordinate, TileSquare> player) {
    var score = 0;
    List<Integer> groupSizes = groupSizesSquare(player);
    for(var groupSize : groupSizes) {
      if(groupSize == 1) {
        score += 2;
      }
      if(groupSize == 2) {
        score += 5;
      }
      if(groupSize >= 3) {
        score += 9;
      }
    }
    return score;
  }
  
  private List<Integer> groupSizesSquare(HashMap<Coordinate, TileSquare> player) {
    Set<Coordinate> visited = new HashSet<>();
    List<Integer> groupSizes = new ArrayList<>();
    for(var key : player.keySet()) {
      if(!visited.contains(key) && player.get(key).animal() != null) { //On a trouvé un truc pas vide pas déjà dans un groupe
        var groupSize = isNeighborHaveTokenSquare(key, player, visited); //On calcule la taille du groupe et on l'ajoute à la liste des tailles de groupes
        groupSizes.add(groupSize);
      }
    }
    return groupSizes;
  }
  
  private int isNeighborHaveTokenSquare(Coordinate current, HashMap<Coordinate, TileSquare> player, Set<Coordinate> visited) {
    visited.add(current);
    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0, -1}}; //Pour éviter les if dans neighbour on pourra faire comme ça je pense (j'ai fais du C mais je sais pas comment ça marche les deux dimensions en Java et Eclipse dit rien)
    var size = 1;
    var neighbour = new Coordinate(0, 0);
    for(var direction : directions) { //Hello, Darkness my old friend...
      neighbour = new Coordinate(current.x() + direction[0], current.y() + direction[1]);
      if(player.containsKey(neighbour) && player.get(neighbour).animal() != null && !visited.contains(neighbour)) { //Si c'est pas un endroit vide et qu'il y a un animal et qu'on l'as pas déjà vus
        size += isNeighborHaveTokenSquare(neighbour, player, visited); //On regarde du coup les voisins de celui-là
      }
    }
    return size;
  }
  
  /**
   * Calcul the number of points attributed by the intermediate card
   * @return int number of points given to the player
   */
  private int cardMedium(HashMap<Coordinate, TileSquare> player) {
    var score = 0;
    List<Integer> groupSizes = groupSizesSquare(player);
    for(var groupSize : groupSizes) {
      if(groupSize == 2) {
        score += 5;
      }
      if(groupSize == 3) {
        score += 8;
      }
      if(groupSize >= 4) {
        score += 12;
      }
    }
    return score;
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
  	if(variant == 1) {
  		return "groupe de 1 animal : 2 points \ngroupe de 2 animaux : 5 points \ngroupe de 3 animaux ou plus : 9 points"; 
  	}
  	if(variant == 2) {
  		return "groupe de 2 animaux : 5 points \ngroupe de 3 animaux : 8 points \ngroupe de 4 animaux ou plus : 12 points";  		
  	}
  	return "les cartes animal ne sont pas implementé en version terminal";
  }
}
