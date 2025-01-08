package graphics;

import game.*;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.Console;

/**
 * ViewTerminal contains every function used to interact with the player and print the state of the geme in the terminal version of Cascadia
 */
public final class ViewTerminal {
  
  /**
   * printCard prints the Animal card used in the current game
   * @param card an AnimalCard representing the card used in the current game
   */
  private static void printCard(AnimalCard card) {
    switch (card) {
    case AnimalCard.FAMILY -> System.out.println(card.pointAnimal());
    case AnimalCard.INTERMEDIATE -> System.out.println(card.pointAnimal());
    default ->
    throw new IllegalArgumentException("Unexpected value: " + card);
    }
  }
  
  /**
   * readPlayerInputDraw takes the player input to determine which tile and wildlifeToken the current player wish to draw
   * @return an int representing the player input
   */
  private static int readPlayerInputDraw() {
    int input = -1;
    Console console = System.console();
    if (console == null) {
        System.out.println("Console is unavailable. Please run this program in a terminal.");
        System.exit(1);
    }
    try {               //Makes sure no wrong input causes the application to terminate with an error
        for(;;) {
            String inputString = console.readLine();
            try {
                input = Integer.parseInt(inputString);
                if (input >= 1 && input <= 4) {
                    break;
                } else {
                    System.out.println("Wrong input, enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number between 1 and 4.");
            }
        }
    } catch (Exception e) {
        System.out.println("An error occurred while reading input.");
        e.printStackTrace();
        System.exit(1);
    }
    return input;
  }

  /**
   * printHead prints everything the player needs to choose from the draw, including the player board
   * @param player HashMap<Coordinate, TileSquare> representing the player's board
   * @param card AnimalCard representing the current card used
   * @param draw DrawSquare representing the draw
   * @param currentPlayer int representing the player currently playing its turn
   * @return an int representing the player input
   */
  public static int printHead(HashMap<Coordinate, TileSquare> player, AnimalCard card, DrawSquare draw, int currentPlayer) {
    var turn = currentPlayer == 1 ? "Player 1 turn : \n" : "Player 2 turn : \n";
    System.out.println(turn);
    int[] coordinate = {0, 0, 0, 0};
    getMinAndMaxCoordinate(player, coordinate);
    printPlayer(player, coordinate[3], coordinate[2], coordinate[1], coordinate[0]);
    System.out.println("Card :");
    printCard(card);
    System.out.println("Draw :");
    System.out.println(draw + "\n");
    System.out.println("Choose a number between 1 and 4 to draw :");
    return readPlayerInputDraw();
  }

  /**
   * readPlayerInputOverpopulation takes the player input to determines if they wish to run an overpopulation or not according to the rules of Cascadia
   * @return a boolean representing the player input, true means yes, false means no
   */
  private static boolean readPlayerInputOverpopulation() {
    String input;
    boolean value = false;
    Console console = System.console();
    if (console == null) {
        System.out.println("Console is unavailable. Please run this program in a terminal.");
        System.exit(1);
    }
    try {
    	for(;;) {
            input = console.readLine().trim().toLowerCase();
            if (input.equals("yes")) {
                value = true;
                break;
            } else if (input.equals("no")) {
                value = false;
                break;
            } else {
                System.out.println("Wrong input, enter yes or no");
            }
        }
    } catch (Exception e) {
        System.out.println("An error occurred while reading input.");
        e.printStackTrace();
        System.exit(1);
    }
    return value;
  }

  /**
   * askOverpopulation prints the current state of the draw (over populated) and ask for player input
   * @param draw DrawSquare representing the draw
   * @return a boolean representing the player input, true means yes, false means no
   */
  public static boolean askOverpopulation(DrawSquare draw) {
  	System.out.println(draw);
  	System.out.println("do you wish to run the OverPopulation yes or no");
  	return readPlayerInputOverpopulation();
  	
  }
  
  /**
   * getMinandMaxCoordinate gets the most left tile, the most right tile, the most up tile, and the most down tile coordinates
   * @param player HashMap<Coordinate, TileSquare> representing the player board
   * @param coordinate int[] representing the four extreme values
   */
  private static void getMinAndMaxCoordinate(HashMap<Coordinate, TileSquare> player, int[] coordinate) {
    var maxX = player.keySet().stream().mapToInt(Coordinate::x).max();
    var minX = player.keySet().stream().mapToInt(Coordinate::x).min();
    var maxY = player.keySet().stream().mapToInt(Coordinate::y).max();
    var minY = player.keySet().stream().mapToInt(Coordinate::y).min();
    coordinate[0] = Integer.valueOf(maxX.getAsInt());
    coordinate[1] = Integer.valueOf(minX.getAsInt());
    coordinate[2] = Integer.valueOf(maxY.getAsInt());
    coordinate[3] = Integer.valueOf(minY.getAsInt());
  }
  
  /**
   * printXcoordinate prints the X coordinates in a player board
   * @param minY int representing the min X
   * @param maxY int representing the max X
   * @return String to append to a StringBuilder or syso
   */
  private static String printXcoordinate(int minY, int maxY) {
    var Xcoordinate = new StringBuilder();
    Xcoordinate.append("     ");
    for(int i = minY; i < maxY + 1; i++) {
      Xcoordinate.append("       ").append(i).append("       ");
    }
    Xcoordinate.append("\n");
    Xcoordinate.append("     ");
    return Xcoordinate.toString();
  }
  
  /**
   * printYcoordinate prints the Y coordinates in a player board
   * @param u int representing the line inside the line, a line for cells is on 3 lines and the y coordinate is on the second line
   * @param i int representing the coordinate to be printed
   * @return String to append to a StringBuilder or syso
   */
  private static String printYcooordinate(int u, int i) {
    var Ycoordinate = new StringBuilder();
    if(u == 1) {
      if(i <= -10) {
        Ycoordinate.append(i).append("  |");
      }
      else if(i >= 10 || i < 0) {
        Ycoordinate.append(" ").append(i).append("  |");
      }
      else {
        Ycoordinate.append("  ").append(i).append("  |");           
      }
    }
    else {
      Ycoordinate.append("     |");         
    }
    return Ycoordinate.toString();
  }
  
  /**
   * printLandscape prints the landscape of a tile
   * @param player Map<Coordinate, TileSquare> represent the player board
   * @param u int represent the line inside a cell
   * @param currentCoordinate Coordinate the coordinates of the tiles currently being printed
   * @param cellWidth int representing the width of a cell
   * @return String to append to a StringBuilder or syso
   */
  private static String printLandscape(Map<Coordinate, TileSquare> player, int u, Coordinate currentCoordinate, int cellWidth) {
    var landscapeToString = new StringBuilder();
    if(u == 0) {
      var landscape = player.get(currentCoordinate).landscape();
      var size = landscape.toString().length();
      landscapeToString.append(landscape.toString());
      for(int z = size; z < cellWidth - 1; z++) {
        landscapeToString.append(" ");
      }
      landscapeToString.append("|");
    }
    return landscapeToString.toString();
  }
  
  /**
   * printWildlifeToken prints the wildlifeToken currently on the cell (if this function is called, there is one)
   * @param player Map<Coordinate, TileSquare> represent the player board
   * @param currentCoordinate Coordinate the coordinates of the tiles currently being printed
   * @param cellWidth int representing the width of a cell
   * @return String to append to a StringBuilder or syso
   */
  private static String printWildlifeToken(Map<Coordinate, TileSquare> player, Coordinate currentCoordinate, int cellWidth) {
    var wildlifeToString = new StringBuilder();
      var animal = player.get(currentCoordinate).animal();
      var size = animal.toString().length();
      wildlifeToString.append("\u001B[32m" + animal.toString() + "\u001B[0m");
      for(int z = size; z < cellWidth - 1; z++) {
        wildlifeToString.append(" ");
      }
      wildlifeToString.append("|");
    return wildlifeToString.toString();
  }
  
  /**
   * printAnimalAccepted prints the animals accepted on the cell (if this function is called, there is no wildlife token currently on the cell)
   * @param player Map<Coordinate, TileSquare> represent the player board
   * @param currentCoordinate Coordinate the coordinates of the tiles currently being printed
   * @param cellWidth int representing the width of a cell
   * @return String to append to a StringBuilder or syso
   */
  private static String printAnimalAccepted(Map<Coordinate, TileSquare> player, Coordinate currentCoordinate, int cellWidth) {
    var animalAcceptedToString = new StringBuilder();
    var animalAccepted = player.get(currentCoordinate).animalAccepted();
    var stringAnimalAccepted = new StringBuilder();
    var iterator = animalAccepted.iterator();
    var firstAnimal = iterator.next().toString();
    var secondAnimal = iterator.next().toString();
    var size = firstAnimal.length() + secondAnimal.length() + 2;
    stringAnimalAccepted.append("\u001B[31m" + firstAnimal);
    stringAnimalAccepted.append(", ");
    stringAnimalAccepted.append(secondAnimal + "\u001B[0m");
    animalAcceptedToString.append(stringAnimalAccepted);
    for(int z = size; z < cellWidth - 1; z++) {
      animalAcceptedToString.append(" ");
    }
    animalAcceptedToString.append("|");
    return animalAcceptedToString.toString();
  }
  
  /**
   * printAnimal prints the animals accepted or the wildlife token depending on the content of the cell
   * @param player Map<Coordinate, TileSquare> represent the player board
   * @param currentCoordinate Coordinate the coordinates of the tiles currently being printed
   * @param cellWidth int representing the width of a cell
   * @param u int representing the line number
   * @return String to append to a StringBuilder or syso
   */
  private static String printAnimal(Map<Coordinate, TileSquare> player, Coordinate currentCoordinate, int cellWidth, int u) {
    var animalToString = new StringBuilder();
    if(u == 1) {
      if(player.get(currentCoordinate).animal() != null) {
        animalToString.append(printWildlifeToken(player, currentCoordinate, cellWidth));
       }
      else {
        animalToString.append(printAnimalAccepted(player, currentCoordinate, cellWidth));
      }
    }
    return animalToString.toString();
  }
  
  /**
   * printCellContent prints the content of a cell
   * @param player Map<Coordinate, TileSquare> represent the player board
   * @param currentCoordinate Coordinate the coordinates of the tiles currently being printed
   * @param cellWidth int representing the width of a cell
   * @param u int representing the line number
   * @return String to append to a StringBuilder or syso
   */
  private static String printCellContent(Map<Coordinate, TileSquare> player, Coordinate currentCoordinate, int cellWidth, int u) {
    var cell = new StringBuilder();
    if(player.containsKey(currentCoordinate)) {   
      cell.append(printLandscape(player, u, currentCoordinate, cellWidth));
      cell.append(printAnimal(player, currentCoordinate, cellWidth, u));
    }
    return cell.toString();
  }
  
  /**
   * printPlayer prints the player board
   * @param player Map<Coordinate, TileSquare> represent the player board
   * @param minX the left most coordinate
   * @param maxX the right most coordinate
   * @param minY the up most coordinate
   * @param maxY the down most coordinate
   */
  private static void printPlayer(Map<Coordinate, TileSquare> player, int minX, int maxX, int minY, int maxY) {
    var playerBoard = new StringBuilder();
    var cellWidth = 15;
    playerBoard.append(printXcoordinate(minY, maxY));
    for(int w = minY; w < maxY + 1; w++) {
      playerBoard.append("_______________");
    }
    playerBoard.append("\n");
    for(int i = minX; i <= maxX; i++) {
      for(int u = 0; u < 3; u++) {
        playerBoard.append(printYcooordinate(u, i));
        for(int j = minY; j < maxY + 1; j++) {
          var currentCoordinate = new Coordinate(j, i);
          playerBoard.append(printCellContent(player, currentCoordinate, cellWidth, u));
          if(u == 2 || player.get(currentCoordinate) == null) {
          playerBoard.append("              |");
          }
        }
        playerBoard.append("\n");
      }
      playerBoard.append("     ");
      for(int j = minY; j <= maxY; j++) {
        playerBoard.append("_______________");
      }
      playerBoard.append("\n");
    }
    System.out.println(playerBoard.toString());
  }
  
  /**
   * readPlayerInputMoveTilesOrWildlife takes the player input to make a tile or wildlife token move
   * @param size int representing the size of the Set of all possible moves
   * @return int representing the player input
   */
  private static int readPlayerInputMoveTilesOrWildlife(int size) {
    int input = -1;
    Console console = System.console();
    if (console == null) {
        System.out.println("Console is unavailable. Please run this program in a terminal.");
        System.exit(1);
    }
    try {
        for(;;) {
            String inputLine = console.readLine();
            try {
                input = Integer.parseInt(inputLine.trim());
                if (input >= 1 && input <= size) {
                    break; 
                } else {
                    System.out.println("Wrong input, enter a number between 1 and " + size + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number between 1 and " + size + ".");
            }
        }
    } catch (Exception e) {
        System.out.println("An error occurred while reading input.");
        e.printStackTrace();
        System.exit(1);
    }
    return input;
  }

  /**
   * choiceMoveTileOrWildelife prints the available tile or wildlife moves
   * @param moves Set<Coordinate> representing all the moves available
   * @param tileOrNot boolean representing if it's a tile or a wildlife token move
   * @return
   */
  public static int choiceMoveTileOrWildelife(Set<Coordinate> moves, boolean tileOrNot) {
  	int[] index = {1};
  	moves.stream().map(coordinate -> index[0]++ + " - " + coordinate)
  		.forEach(System.out::println);
  	if(tileOrNot) {
  		System.out.println("choose between all available tile moves : ");  	
  	}
  	else {
  		System.out.println("choose between all available wildlife token moves : ");  
  	}
  	return readPlayerInputMoveTilesOrWildlife(moves.size());
  }
  
  /**
   * printWinner prints the winner of the game
   * @param point CountPointSquare containing the points of each players
   * @param winner int representing the winning player
   */
  public static void printWinner(CountPointSquare point, int winner) {
  	if(winner != 0) {
  		System.out.println("the winner is player " + winner + " with " + point.pointPlayer()[0] + " Points against " + point.pointPlayer()[1] + " Points for the looser");
  	}
  	else {
  		System.out.println("it's a tie with " + point.pointPlayer()[0] + " Points");
  	}
  }
  
  /**
   * readPlayerInputAnimal takes the player input to choose to play with the family or the intermediary version of the game
   * @return boolean representing if the game will be played in family or intermediary mode
   */
  private static boolean readPlayerInputAnimal() {
    boolean value = false;
    Console console = System.console();
    if (console == null) {
        System.out.println("Console is unavailable. Please run this program in a terminal.");
        System.exit(1);
    }
    try {
        for(;;) {
            String input = console.readLine().trim().toLowerCase();
            if (input.equals("family")) {
                value = true;
                break;
            } else if (input.equals("intermediary")) {
                value = false;
                break;
            } else {
                System.out.println("Wrong input, enter 'family' or 'intermediary'.");
            }
        }
    } catch (Exception e) {
        System.out.println("An error occurred while reading input.");
        e.printStackTrace();
        System.exit(1);
    }
    return value;
  }
  
  /**
   * choiceAnimalCard prints the choice for the 2 animal cards available in the terminal version
   * @return AnimalCard the chosen card
   */
  public static AnimalCard choiceAnimalCard() {
		System.out.println("choose family or intermediary card : ");
  	if(readPlayerInputAnimal()) {
  		return AnimalCard.FAMILY;
  	}
  	return AnimalCard.INTERMEDIATE;
	}
  
  /**
   * readPlayerInputTileBegin takes the player input to determine their beginning tile
   * @param forbidenNumber the tiles already taken by an other player
   * @return int representing the player input
   */
  private static int readPlayerInputTileBegin(int[] forbidenNumber) {
    var input = -1;
    Console console = System.console();
    if (console == null) {
        System.out.println("Console is not available.");
        System.exit(1);
    }
    for(;;) {
        String inputLine = console.readLine();
        try {
            input = Integer.parseInt(inputLine);
            if (input >= 1 && input <= 5 && input != forbidenNumber[0]) {
            		forbidenNumber[0] = input;
                break;
            } else {
                System.out.println("Wrong input, enter a number between 1 and 5 or don't take the same tile as the previous player.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a valid number.");
        }
    }
    return input;
 }

  /**
   * choiceTileBegin prints the necessary knowledge to pick a beginning tile, which is not much since the choice is made blindly
   * @param forbidenNumber the tiles already taken by an other player
   * @param player int representing the player currently making his choice
   * @return int representing the chosen beginning tile
   */
  public static int choiceTileBegin(int[] forbidenNumber, int player) {
  	System.out.println("player " + player + " choose a Begining tile at random between 1 and 5 please don't take the same tile");
  	return readPlayerInputTileBegin(forbidenNumber);
  }
}

	
	