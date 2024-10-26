package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

public record AnimalCard(String animal, int cardNumber, int variant) {
  
  /**
   * Select the correct animal or calls the familly or intermediate version depending on the game mode
   * @return int number of points given to the player by this card
   */
  public int cardType(HashMap<Coordonate, TilesSquare> player) {
    if(this.variant == 1) {
      return cardFamilly(player);
    }
    if(this.variant == 2) {
      return cardMedium(player);
    }
    if(this.animal == "Ours") {
      return numberCardOurs(this.cardNumber, player);
    }
    if(this.animal == "Saumon") {
      return numberCardSaumon(this.cardNumber, player);
    }
    return 0;
  }
  
  /**
   * @brief Calcul the number of points attributed by the familly card
   * @return int number of points given to the player
   */
  private int cardFamilly(HashMap<Coordonate, TilesSquare> player) {
    Objects.requireNonNull(player);
    var score = 0;
    List<Integer> groupSizes = groupSizes(player);
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
  
  private List<Integer> groupSizes(HashMap<Coordonate, TilesSquare> player) {
    Objects.requireNonNull(player);
    Set<Coordonate> visited = new HashSet<>();
    List<Integer> groupSizes = new ArrayList<>();
    for(var key : player.keySet()) {
      if(player.get(key) != null && !visited.contains(key)) { //On a trouvé un truc pas vide pas déjà dans un groupe
        //On calcule la taille du groupe et on l'ajoute à la liste des tailles de groupes
        int groupSize = recursiveStuff(key, player, visited); //Faut changer le nom
        groupSizes.add(groupSize);
      }
    }
    return groupSizes;
  }
  
  private int recursiveStuff(Coordonate current, HashMap<Coordonate, TilesSquare> player, Set<Coordonate> visited) {  //Faut toujours changer le nom
    Objects.requireNonNull(current);
    Objects.requireNonNull(player);
    Objects.requireNonNull(visited);
    visited.add(current);
    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0, -1}}; //Pour éviter les if dans neighbour on pourra faire comme ça je pense (j'ai fais du C mais je sais pas comment ça marche les deux dimensions en Java et Eclipse dit rien)
    int size = 1;
    for(int[] direction : directions) { //Hello, Darkness my old friend...
      Coordonate neighbour = new Coordonate(current.x() + direction[0], current.y() + direction[1]);
      if(player.containsKey(neighbour) && player.get(neighbour) != null && !visited.contains(neighbour)) { //Si c'est pas un endroit vide et qu'il y a un animal et qu'on l'as pas déjà vus
        size += recursiveStuff(neighbour, player, visited); //On regarde du coup les voisins de celui-là
      }
    }
    return size;
  }
  
  /**
   * @brief Calcul the number of points attributed by the intermediate card
   * @return int number of points given to the player
   */
  private int cardMedium(HashMap<Coordonate, TilesSquare> player) {
    Objects.requireNonNull(player);
    var score = 0;
    List<Integer> groupSizes = groupSizes(player);
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
   * @brief Select the correct way to calculate the number of points according to the
   bear card currently in the game
   * @param numeroCarte
   * @return int number of points attributed
   */
  private int numberCardOurs(int cardNumber, HashMap<Coordonate, TilesSquare> player) {
      if(this.cardNumber == 1) {
        return cardAOurs(player);
      }
      if(this.cardNumber == 2) {
        return cardBOurs(player);
      }
      if(this.cardNumber == 3) {
        return cardCOurs(player);
      }
      if(this.cardNumber == 4) {
        return cardDOurs(player);
      }
      return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card A
   * @return int number of points
   */
  private int cardAOurs(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card B
   * @return int number of points
   */
  private int cardBOurs(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card C
   * @return int number of points
   */
  private int cardCOurs(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card D
   * @return int number of points
   */
  private int cardDOurs(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Select the correct way to calculate the number of points according to the
   salmon card currently in the game
   * @param numeroCarte
   * @return int number of points attributed
   */
  private int numberCardSaumon(int Cardnumber, HashMap<Coordonate, TilesSquare> player) {
    if(this.cardNumber == 1) {
      return cardASaumon(player);
    }
    if(this.cardNumber == 2) {
      return cardBSaumon(player);
    }
    if(this.cardNumber == 3) {
      return cardCSaumon(player);
    }
    if(this.cardNumber == 4) {
      return cardDSaumon(player);
    }
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card A
   * @return int number of points
   */
  private int cardASaumon(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card B
   * @return int number of points
   */
  private int cardBSaumon(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card C
   * @return int number of points
   */
  private int cardCSaumon(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card D
   * @return int number of points
   */
  private int cardDSaumon(HashMap<Coordonate, TilesSquare> player) {
    //Besoin TuileHabitat
    return 0;
  }
  
  @Override
  public final String toString() {
  	if(variant == 1) {
  		return "groupe de 1 : 2 points \ngroupe de 2 : 5 points \ngroupe de 3 ou plus : 9 points"; 
  	}
  	if(variant == 2) {
  		return "groupe de 2 : 5 points \ngroupe de 3 : 8 points \ngroupe de 4 ou plus : 12 points";  		
  	}
  	return "les cartes animal ne sont pas implementé en version terminal";
  }
}