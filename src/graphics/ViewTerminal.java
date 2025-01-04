package graphics;

import game.*;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Console;

public record ViewTerminal() {
  
  private static void printCard(AnimalCard card) {
    switch (card) {
    case AnimalCard.FAMILY -> System.out.println("1 animal : 2 points, 2 animals : 5 points, 3 or more : 9 points\n");
    case AnimalCard.INTERMEDIATE -> System.out.println("2 animals : 5 points, 3 animals : 8 points, 4 or more : 12 points\n");
    default ->
    throw new IllegalArgumentException("Unexpected value: " + card);
    }
  }
  
  private static int readPlayerInputDraw() {
    int input = -1;
    Console console = System.console();
    if (console == null) {
        System.out.println("Console is unavailable. Please run this program in a terminal.");
        System.exit(1);
    }
    try {
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

  
  public static boolean askOverpopulation(DrawSquare draw) {
  	System.out.println(draw);
  	System.out.println("do you wish to run the OverPopulation yes or no");
  	return readPlayerInputOverpopulation();
  	
  }
  
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
  
  public static void printPlayer(Map<Coordinate, TileSquare> player, int minX, int maxX, int minY, int maxY) {
    var playerBoard = new StringBuilder();
    var cellWidth = 15;
    for(int i = minY; i < maxY + 1; i++) {
    	playerBoard.append("       ").append(i).append("       ");
    }
    playerBoard.append("\n");
    
    playerBoard.append("     ");
    
    for(int w = minY; w < maxY + 1; w++) {
      playerBoard.append("_______________");
    }
    playerBoard.append("\n");
    for(int i = minX; i <= maxX; i++) {
      for(int u = 0; u < 3; u++) {
      	if(u == 1) {
      		if(i <= -10) {
      			playerBoard.append(i).append("  |");
      		}
      		else if(i >= 10 || i < 0) {
      			playerBoard.append(" ").append(i).append("  |");
      		}
      		else {
      			playerBoard.append("  ").append(i).append("  |");      			
      		}
      	}
      	else {
      		playerBoard.append("     |");      		
      	}
        for(int j = minY; j < maxY + 1; j++) {
          var currentCoordinate = new Coordinate(j, i);
          if(player.containsKey(currentCoordinate)) {   
            if(u == 0) {
              var landscape = player.get(currentCoordinate).landscape();
              var size = landscape.toString().length();
              playerBoard.append(landscape.toString());
              for(int z = size; z < cellWidth - 1; z++) {
                playerBoard.append(" ");
              }
              playerBoard.append("|");
            }
            if(u == 1) {
              if(player.get(currentCoordinate).animal() != null) {
                var animal = player.get(currentCoordinate).animal();
                var size = animal.toString().length();
                playerBoard.append("\u001B[32m" + animal.toString() + "\u001B[0m");
                for(int z = size; z < cellWidth - 1; z++) {
                  playerBoard.append(" ");
                }
                playerBoard.append("|");
               }
              else {
                var animalAccepted = player.get(currentCoordinate).animalAccepted();
                var stringAnimalAccepted = new StringBuilder();
                var iterator = animalAccepted.iterator();
                var firstAnimal = iterator.next().toString();
                var secondAnimal = iterator.next().toString();
                var size = firstAnimal.length() + secondAnimal.length() + 2;
                stringAnimalAccepted.append("\u001B[31m" + firstAnimal);
                stringAnimalAccepted.append(", ");
                stringAnimalAccepted.append(secondAnimal + "\u001B[0m");
                playerBoard.append(stringAnimalAccepted);
                for(int z = size; z < cellWidth - 1; z++) {
                  playerBoard.append(" ");
                }
                playerBoard.append("|");
              }
            }
          }
          playerBoard.append("              |");
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

  
  public static int choiceMoveTileOrWildelife(List<Coordinate> moves, boolean tileOrNot) {
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
  
  public static void printWinner(CountPointSquare point, int winner) {
  	if(winner != 0) {
  		System.out.println("the winner is player " + winner + " with " + point.pointPlayer()[0] + " Points against " + point.pointPlayer()[1] + " Points for the looser");
  	}
  	else {
  		System.out.println("it's a tie with " + point.pointPlayer()[0] + " Points");
  	}
  }
  
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
  
  public static AnimalCard choiceAnimalCard() {
		System.out.println("choose family or intermediary card : ");
  	if(readPlayerInputAnimal()) {
  		return AnimalCard.FAMILY;
  	}
  	return AnimalCard.INTERMEDIATE;
	}
  
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

  public static int choiceTileBegin(int[] forbidenNumber, int player) {
  	System.out.println("player " + player + " choose a Begining tile at random between 1 and 5 please don't take the same tile");
  	return readPlayerInputTileBegin(forbidenNumber);
  }
}

	
	