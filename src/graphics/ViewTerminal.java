package graphics;

import game.*;
import java.util.function.Function;
import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record ViewTerminal() {
  
  private static void printCard(AnimalCard card) {
    switch (card) {
    case AnimalCard.FAMILY -> System.out.println("1 animal : 2 points, 2 animals : 5 points, 3 or more : 9 points\n");
    case AnimalCard.INTERMEDIATE -> System.out.println("2 animals : 5 points, 3 animals : 8 points, 4 or more : 12 points\n");
    default ->
    throw new IllegalArgumentException("Unexpected value: " + card);
    }
  }
  
  private static int readPlayerInput() {
    int input = -1;
    try (Scanner scan = new Scanner(System.in)) {
        for(;;) {
            try {
                input = scan.nextInt();
                if (input >= 1 && input <= 4) {
                    break; 
                } else {
                    System.out.println("Wrong input, enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid number between 1 and 4.");
                scan.nextLine();  
              }
        }
    } catch (Exception e) {
        System.out.println("An error occurred, if we got there this is bad.");
    }
    return input;
}

  
  public static int printHead(HashMap<Coordinate, TileSquare> player, AnimalCard card, DrawSquare draw, int currentPlayer) {
    var turn = currentPlayer == 1 ? "Player 1 turn : \n" : "Player 2 turn : \n";
    System.out.println(turn);
    int[] coordinate = {0, 0, 0, 0};
    getMinAndMaxCoordinate(player, coordinate);
    printPlayer(player, coordinate[1], coordinate[0], coordinate[3], coordinate[2]);
    System.out.println("Card :");
    printCard(card);
    System.out.println("Draw :");
    System.out.println(draw + "\n");
    System.out.println("Choose a number between 1 and 4 to draw :");
    return readPlayerInput();
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
    for(int w = minY; w < maxY + 1; w++) {
      playerBoard.append("_______________");
    }
    playerBoard.append("\n");
    for(int i = minX + 1; i <= maxX; i++) {
      for(int u = 0; u < 3; u++) {
        playerBoard.append("|");
        for(int j = minY; j < maxY + 1; j++) {
          var currentCoordinate = new Coordinate(i, j);
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
      for(int j = minY; j <= maxY; j++) {
        playerBoard.append("_______________");
      }
      playerBoard.append("\n");
    }
    System.out.println(playerBoard.toString());
  }
  
}
