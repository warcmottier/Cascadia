public record AnimalCard(String animal, int Cardnumber, int variant) {
  
  /**
   * Select the correct animal or calls the familly or intermediate version depending on the game mode
   * @return int number of points given to the player by this card
   */
  public int cardType(/*A Besoin d'un plateau de joueur*/) {
    if(this.variant == 1) {
      return cardFamilly(/*A Besoin d'un plateau de joueur*/);
    }
    if(this.variant == 2) {
      return cardMedium(/*A Besoin d'un plateau de joueur*/);
    }
    if(this.animal == "Ours") {
      return numberCardOurs(this.Cardnumber/*A Besoin d'un plateau de joueur*/);
    }
    if(this.animal == "Saumon") {
      return numberCardSaumon(this.Cardnumber/*A Besoin d'un plateau de joueur*/);
    }
    return 0;
  }
  
  /**
   * @brief Calcul the number of points attributed by the familly card
   * @return int number of points given to the player
   */
  private int cardFamilly(/*A Besoin d'un plateau de joueur*/) {
    //besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calcul the number of points attributed by the intermediate card
   * @return int number of points given to the player
   */
  private int cardMedium(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Select the correct way to calculate the number of points according to the
   bear card currently in the game
   * @param numeroCarte
   * @return int number of points attributed
   */
  private int numberCardOurs(int Cardnumber/*A Besoin d'un plateau de joueur*/) {
      if(this.Cardnumber == 1) {
        return cardAOurs(/*A Besoin d'un plateau de joueur*/);
      }
      if(this.Cardnumber == 2) {
        return cardBOurs(/*A Besoin d'un plateau de joueur*/);
      }
      if(this.Cardnumber == 3) {
        return cardCOurs(/*A Besoin d'un plateau de joueur*/);
      }
      if(this.Cardnumber == 4) {
        return cardDOurs(/*A Besoin d'un plateau de joueur*/);
      }
      return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card A
   * @return int number of points
   */
  private int cardAOurs(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card B
   * @return int number of points
   */
  private int cardBOurs(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card C
   * @return int number of points
   */
  private int cardCOurs(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the bear
   card D
   * @return int number of points
   */
  private int cardDOurs(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitable
    return 0;
  }
  
  /**
   * @brief Select the correct way to calculate the number of points according to the
   salmon card currently in the game
   * @param numeroCarte
   * @return int number of points attributed
   */
  private int numberCardSaumon(int Cardnumber/*A Besoin d'un plateau de joueur*/) {
    if(this.Cardnumber == 1) {
      return cardASaumon(/*A Besoin d'un plateau de joueur*/);
    }
    if(this.Cardnumber == 2) {
      return cardBSaumon(/*A Besoin d'un plateau de joueur*/);
    }
    if(this.Cardnumber == 3) {
      return cardCSaumon(/*A Besoin d'un plateau de joueur*/);
    }
    if(this.Cardnumber == 4) {
      return cardDSaumon/*A Besoin d'un plateau de joueur*/();
    }
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card A
   * @return int number of points
   */
  private int cardASaumon(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card B
   * @return int number of points
   */
  private int cardBSaumon(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card C
   * @return int number of points
   */
  private int cardCSaumon(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitat
    return 0;
  }
  
  /**
   * @brief Calculate the number of points attributed to the player by the salmon
   card D
   * @return int number of points
   */
  private int cardDSaumon(/*A Besoin d'un plateau de joueur*/) {
    //Besoin TuileHabitat
    return 0;
  }
  
}