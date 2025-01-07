package game;
/**
 * This enum represent the different cards to play Cascadia with
 */
public enum AnimalCard {
	FAMILY("\"1 animal : 2 points, 2 animals : 5 points, 3 or more : 9 points"),
	INTERMEDIATE("2 animals : 5 points, 3 animals : 8 points, 4 or more : 12 points"),
	ELKA("a horizontal line with 1 elk : 2 points, 2 elk : 5 points, 3 elk : 9 points, 4 : 13 points"),
	ELKB("1 elk : 2 points, 2 elk horizontal line : 5 points, 3 elk tiangle : 9 points, 4 elk diamond : 13 points"),
	ELKC("a groupe of 1 elk : 2 points, 2 elk : 4 points, 3 elk : 7 points, 4 elk : 10 points, 5 elk : 14 points, 6 elk : 18 points, 7 elk : 20 points, 8 or more : 28 points"),
	ELKD("a circle 1 elk : 2 points, 2 elk : 5 points, 3 elk : 8 points, 4 elk : 12 points, 5 elk : 16 points, 6 elk : 21 points"),
	BEARA("number of bear pair 1 pair : 4 points, 2 pair : 11 points, 3 pair : 19 points, 4 pair : 27 points"),
	BEARB("a trio of bear 10 points"),
	BEARC("a group of 1 bear : 2 points, a group of 2 bear : 5 points, a group of 3 bear : 8 points, +3 bonus point if you have all the group sizes"),
	BEARD("a group of 2 bear : 5 points, a group of 3 bear : 8 points, a group of 4 bear : 3 points"),
	NOZZLEA("a solitary nozzle 1 : 2 points, 2 : 5 points, 3 : 8 points, 4 : 11 points, 5 : 14 points, 6 : 18 points, 7 : 22 points, 8 or more : 26 points"),
	NOZZLEB("soltary nozzle who have a least one another nozzlle line of sight 2 : 2 points, 3 : 9 points, 4 : 12 points, 5 : 16 points, 6 : 20 points, 7 : 24 points, 8 or more : 28 points"),
	NOZZLEC("3 points for each line of sight between 2 nozzle not adjacent"),
	NOZZLED("a pair of nozzle with 1 other animal species between them : 4 points, 2 other animal species between them : 7 points, 3 or more other animal species between them : 9 points"),
	SALMONA("only two salmon adjacents to each over 1 chain : 2 points, 2 chain : 5 points, 3 chain : 8 points, 4 chain : 12 points, 5 chain : 16 points, 6 chain : 20 points, 7 or more chain : 25 points"),
	SALMONB("only two salmon adjacents to each over 1 chain : 2 points, 2 chain : 4 points, 3 chain : 9 points, 4 chain : 11 points, 5 or more chain : 17 points"),
	SALMONC("only two salmon adjacents to each over 3 chain : 10 points, 4 chain : 12 points, 5 or more chain : 15 points"),
	SALMOND("1 point for each slamon in the chain and 1 point for each animal adjacent to the chain"),
	FOXA("for 1 fox 1 point for each animal adjacent"),
	FOXB("for 1 fox without another fox 1 adjacents pair animal species : 3 points, 2 adjacents pair animal species : 5 points, 3 adjacents pair animal species : 7 points"),
	FOXC("for 1 fox without another fox 1 point for each animal of the same species adjacent of  the fox"),
	FOXD("for each pair of fox without another fox 1 pair : 5 points, 2 pair : 7 points, 3 pair : 9 points, 4 pair : 11 points");
	private final String pointAnimal;
	
  AnimalCard(String pointAnimal) {
		this.pointAnimal = pointAnimal;
	}
  
  public String pointAnimal() {
  	return pointAnimal;
  }
}
