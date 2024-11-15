package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public final class AlgoSquare {
  
  private static Set<String> generateAnimalAccepeted() {
    String[] animal = {"Ours", "Saumon", "Wapiti", "Buse", "Renard"};
    String in = new String();
    Set<String> animalAccepted = new HashSet<>();
    animalAccepted.add(animal[(int) (Math.random() * 5)]);
    for(in = animalAccepted.iterator().next(); animalAccepted.contains(in) ; in = animal[(int) (Math.random() * 5)]) {}
    animalAccepted.add(in);
    return animalAccepted;
  }
  
  public static List<TilesSquare> initializationTiles() {
    String[] landscape = {"Prairie", "Marais", "Riviere", "Montagne", "Foret"};
    List<TilesSquare> tiles = new ArrayList<>();
    for(var i = 0; i < 43; i++) {
      tiles.add(new TilesSquare(generateAnimalAccepeted(), null, landscape[(int) (Math.random() * 5)]));
    }
    return tiles;
  }
  
  public static HashMap<WildlifeToken, Integer> initializationWildlifeToken() {
    var wildlife = new HashMap<WildlifeToken, Integer>();
    wildlife.put(new WildlifeToken("Ours"), 20);
    wildlife.put(new WildlifeToken("Saumon"), 20);
    wildlife.put(new WildlifeToken("Buse"), 20);
    wildlife.put(new WildlifeToken("Wapiti"), 20);
    wildlife.put(new WildlifeToken("Renard"), 20);
    return wildlife;
  }
  
  private static HashMap<Coordonate, TilesSquare> setMap(String[][] animalAndLandscapeBegin) {
    var map = new HashMap<Coordonate, TilesSquare>();
    var i = 0;
    for(var element : animalAndLandscapeBegin) {
      map.put(new Coordonate(i, 0), new TilesSquare(Set.of(element[0], element[1]), null, element[2]));
      i++;
    }
    return map;
  }
  
  private static HashMap<Coordonate, TilesSquare> tilesBegin(int n) {
    String[][] animalAndLandscapeBegin1 = {{"Ours", "Saumon", "Prairie"}, {"Renard", "Buse", "Foret"}, {"Wapiti", "Ours", "Montagne"}};
    String[][] animalAndLandscapeBegin2 = {{"Wapiti", "Saumon", "Foret"}, {"Buse", "Ours", "Riviere"}, {"Buse", "Wapiti", "Marais"}};
    String[][] animalAndLandscapeBegin3 = {{"Wapiti", "Ours", "Riviere"}, {"Renard", "Saumon", "Marais"}, {"Renard", "Ours", "Montagne"}};
    String[][] animalAndLandscapeBegin4 = {{"Saumon", "Wapiti", "Prairie"}, {"Renard", "Wapiti", "Riviere"}, {"Ours", "Buse", "Foret"}};
    String[][] animalAndLandscapeBegin5 = {{"Buse", "Saumon", "Prairie"}, {"Buse", "Ours", "Marais"}, {"Renard", "Wapiti", "Riviere"}};
    if(n == 1) {
      return setMap(animalAndLandscapeBegin1);
    }
    if(n == 2) {
      return setMap(animalAndLandscapeBegin2);
    }
    if(n == 3) {
      return setMap(animalAndLandscapeBegin3);
    }
    if(n == 4) {
      return setMap(animalAndLandscapeBegin4);
    }
    return setMap(animalAndLandscapeBegin5);
  }
  
  private static AnimalCard parameter(Scanner scan, boolean terminal) {
    var enter = -1;
    if(terminal) {
      System.out.println("Cascadia terminal version");
      System.out.println("entrez 1 pour la variante famille ou 2 pour la variante intermediaire");
      for(enter = scan.nextInt(); enter != 1 && enter != 2; enter = scan.nextInt()) {
        System.out.println("mauvaise entrée");
      }
    }
    return new AnimalCard(null, 0, enter);
  }
  
  private static HashMap<Coordonate, TilesSquare> assignedPlayer(int player, int[] forbidenNumber, Scanner scan, boolean terminal) {
    var enter = -1;
    if(terminal) {
      System.out.println("J" + player + " entrez un nombre entier entre 1 et 5");
      for(enter = scan.nextInt(); enter < 1 || enter > 5 || enter == forbidenNumber[0]; enter = scan.nextInt()) {
        System.out.println("mauvaise entrée");
      }
    }
    forbidenNumber[0] = enter;
    return tilesBegin(enter);
  }
  
  private static WildlifeToken removeOverPopulation(HashMap<TilesSquare, WildlifeToken> pickaxe, int n){
    var tiles = pickaxe.keySet().toArray();
    var wildlife = pickaxe.get(tiles[0]);
    if(n == 4) {
      return wildlife;
    }
    var count = 0;
    for(var elements : pickaxe.values()) {
      if(wildlife == elements) {
        count++;
      }
    }
    if(count == 3) {
      return wildlife;
    }
    return pickaxe.get(tiles[1]);
  }
  
  private static WildlifeToken drawWildlifeToken(HashMap<WildlifeToken, Integer> wildlifeTokens) {
    String[] draw = {"Ours", "Saumon", "Wapiti", "Renard", "Buse"};
    var random = new Random();
    var wildlife = new WildlifeToken(draw[random.nextInt(5)]);
    for(; wildlifeTokens.get(wildlife) == 0; ) {
      wildlife = new WildlifeToken(draw[random.nextInt(5)]);
    }
    wildlifeTokens.put(wildlife, wildlifeTokens.get(wildlife) - 1);
    return wildlife;
  }
  
  private static TilesSquare drawWTilesSquare(List<TilesSquare> tiles, HashMap<TilesSquare, WildlifeToken> pickaxe) {
    var random = new Random();
    var tile = tiles.get(random.nextInt(tiles.size()));
    for(; pickaxe.containsKey(tile); ) {
      tile = tiles.get(random.nextInt(tiles.size()));
    }
    return tile;
  }
  
  //numberDiscard = 4 if all wildlife or = 3 if 3 same
  private static void overPopulation(HashMap<TilesSquare, WildlifeToken> pickaxe, int numberDiscard, HashMap<WildlifeToken, Integer> wildlifeTokens, boolean terminal) {
    var  wildlife = removeOverPopulation(pickaxe, numberDiscard);
    if(numberDiscard == 4) {
      for(var elements : pickaxe.keySet()) {
        pickaxe.put(elements, drawWildlifeToken(wildlifeTokens));
      }
      wildlifeTokens.put(wildlife, wildlifeTokens.get(wildlife) + 4);
    }
    else {
      for(var elements : pickaxe.keySet()) {
        if(pickaxe.get(elements).equals(wildlife)) {
          pickaxe.put(elements, drawWildlifeToken(wildlifeTokens));
        }
      }
      wildlifeTokens.put(wildlife, wildlifeTokens.get(wildlife) + 3);
    }
    printPickaxe(pickaxe, terminal);
  }
  
  private static void craftPickaxe(HashMap<TilesSquare, WildlifeToken> pickaxe, List<TilesSquare> tiles, HashMap<WildlifeToken, Integer> wildlifeTokens, TilesSquare tile, TilesSquare tileWildlife) {
    if(tile == null) {
      for(int i = 0; i < 4; i++) {
        pickaxe.put(drawWTilesSquare(tiles, pickaxe), drawWildlifeToken(wildlifeTokens));
      }
    }
    else {
      if(tileWildlife == null) {
        pickaxe.remove(tile);
        pickaxe.put(drawWTilesSquare(tiles, pickaxe), drawWildlifeToken(wildlifeTokens));
      }
      else {
        var temporaryTiles = pickaxe.get(tile);
        var temporarWildlife = pickaxe.get(tileWildlife);
        pickaxe.remove(tile);
        pickaxe.put(drawWTilesSquare(tiles, pickaxe), temporaryTiles);
        pickaxe.put(tileWildlife, drawWildlifeToken(wildlifeTokens));
        wildlifeTokens.put(temporarWildlife, wildlifeTokens.get(temporarWildlife) + 1);
      }
    }
  }
  
  private static void printPickaxe(HashMap<TilesSquare, WildlifeToken> pickaxe, boolean terminal) {
    var integer = 1;
    for(var elem : pickaxe.entrySet()) {
      if(terminal) {
        System.out.println(integer + " - " +elem);
      }
      integer++;
    }
    if(terminal) {
      System.out.println("----------------------------");
    }
  }
  
  private static HashMap<WildlifeToken, Integer> intializeValueCount(HashMap<TilesSquare, WildlifeToken> pickaxe) {
    var valueCount = new HashMap<WildlifeToken, Integer>();
    for(var elem : pickaxe.values()) {
      valueCount.put(elem, valueCount.getOrDefault(elem, 0) + 1);
    }
    return valueCount;
  }
  
  private static boolean isOverpopulation(HashMap<TilesSquare, WildlifeToken> pickaxe, Scanner scan, HashMap<WildlifeToken, Integer> wildlifeTokens, boolean terminal) {
    var valueCount = intializeValueCount(pickaxe);
    var enter = -1;
    for(var elem: valueCount.values()){
      if(elem == 3) {
        if(terminal) {
          System.out.println("Voulez vous déclenchez une sûrpopulation ? 1 - oui,  2 - non :");
          for(enter = scan.nextInt(); enter < 1 || enter > 2; enter = scan.nextInt()) {
            System.out.println("mauvaise entrée");
          }
        }
        if(enter == 1) {
          overPopulation(pickaxe, 3, wildlifeTokens, terminal);
          return true;
        }
      }
      if(elem == 4) {
        if(terminal) {
          System.out.println("4 jetons faunes identiques, déclenchement de la sûrpopulation");
        }
        overPopulation(pickaxe, 4, wildlifeTokens, terminal);
        return true;
      }
    }
    return false;
  }
  
  private static HashMap<TilesSquare, WildlifeToken> pickPickaxe(HashMap<TilesSquare, WildlifeToken> pickaxe, Scanner scan, HashMap<WildlifeToken, Integer> wildlifeTokens, boolean terminal) {
    var picked = new HashMap<TilesSquare, WildlifeToken>();
    var enter = -1;
    var arrayPickaxe = pickaxe.keySet().toArray();
    printPickaxe(pickaxe, terminal);
    for(;isOverpopulation(pickaxe, scan, wildlifeTokens, terminal););
    if(terminal) {
      System.out.println("Entrez le numéro de la pioche voulue :");
      for(enter = scan.nextInt(); enter < 1 || enter > 4; enter = scan.nextInt()) {
        System.out.println("mauvaise entrée");
      }
    }
    if(terminal) {
      System.out.println("----------------------------");      
    }
    picked.put((TilesSquare) arrayPickaxe[enter - 1], pickaxe.get(arrayPickaxe[enter - 1]));
    return picked;
  }
  
  private static Set<Coordonate> allAvailablesTilesMove(HashMap<Coordonate, TilesSquare> player) {
    Set<Coordonate> moves = new HashSet<>();
    for(var coordonates : player.keySet()) {
      moves.addAll(TilesSquare.notneighbour(coordonates, player));
    }
    return moves;
  }
  
  private static Set<Coordonate> wildlifeTokenAvailablesSlots(HashMap<Coordonate, TilesSquare> tilesMap, WildlifeToken wildlife) {
    Set<Coordonate> results = new HashSet<>();
    for(var elems : tilesMap.keySet()) {
      if(tilesMap.get(elems).animal() == null && tilesMap.get(elems).animalAccepted().contains(wildlife.animal())) {
        results.add(elems);
      }
    }
    return results;
  }
  
  private static void makeTileMove(HashMap<Coordonate, TilesSquare> player, TilesSquare tile, Scanner scan, boolean terminal) {
    Set<Coordonate> moves = allAvailablesTilesMove(player);
    var i = 1;
    var enter = -1;
    for(var elem : moves) {
      if(terminal) {
        System.out.println(i + " - " + elem);
      }
      i++;
    }
    if(terminal) {
      System.out.println("Entrez le numéro de la coordonnée désirée pour la tuile :");
      for(enter = scan.nextInt(); enter < 1 || enter > moves.size(); enter = scan.nextInt()) {
        System.out.println("mauvaise entrée");
    }
      System.out.println("----------------------------");
    }
    player.put((Coordonate) moves.toArray()[enter - 1], tile);
  }
  
  private static void makeWildlifeMove(HashMap<Coordonate, TilesSquare> player, WildlifeToken wildlife, Scanner scan, HashMap<WildlifeToken, Integer> wildlifeTokens, boolean terminal) {
    Set<Coordonate> moves = wildlifeTokenAvailablesSlots(player, wildlife);
    if(moves.size() == 0) {
      if(terminal) {
        System.out.println("Impossible à jouer, le jeton retourne à la pioche");
      }
      wildlifeTokens.put(wildlife, wildlifeTokens.get(wildlife) + 1);
      return;
    }
    var i = 1;
    var enter = -1;
    for(var elem : moves) {
      if(terminal) {
        System.out.println(i + " - " + elem);
      }
      i++;
    }
    if(terminal) {
      System.out.println("Entrez le numéro de la coordonnée désirée pour le jeton faune :");
    }
    if(terminal) {
      for(enter = scan.nextInt(); enter < 1 || enter > moves.size(); enter = scan.nextInt()) { 
        System.out.println("mauvaise entrée");
      }
      System.out.println("----------------------------");
    }
    player.get(moves.toArray()[enter - 1]).setWildlifeToken(wildlife);
  }
  
  private static void printPlayer(HashMap<Coordonate, TilesSquare> player, boolean terminal) {
    for(var elem : player.entrySet()) {
      if(terminal) {
        System.out.println(elem);
      }
    }
  }
  
  private static void makeMove(HashMap<Coordonate, TilesSquare> player, WildlifeToken wildlife, TilesSquare tile, Scanner scan, HashMap<WildlifeToken, Integer> wildlifeTokens, boolean terminal) {
    printPlayer(player, terminal);
    System.out.println("----------------------------");
    makeTileMove(player, tile, scan, terminal);
    printPlayer(player, terminal);
    System.out.println("----------------------------");
    makeWildlifeMove(player, wildlife, scan, wildlifeTokens, terminal);
  }
  
  private static void loopAlgorythmSquare(HashMap<TilesSquare, WildlifeToken> pickaxe, HashMap<Coordonate, TilesSquare> player1, HashMap<Coordonate, TilesSquare> player2, List<TilesSquare> tiles, HashMap<WildlifeToken, Integer> wildlifeTokens, Scanner scan, AnimalCard card, boolean terminal) {
    var picked = new HashMap<TilesSquare, WildlifeToken>();
    for(var i = 0; i < 20; i++) {
      if(terminal) {
        System.out.println("Tour numéro " + (i + 1) + "\n");
        System.out.println("Tour du J1 :\n");
      }
      printPlayer(player1, terminal);
      if(terminal) {
        System.out.println("----------------------------");
        System.out.println(card);
      }
      picked = pickPickaxe(pickaxe, scan, wildlifeTokens, terminal);
      craftPickaxe(pickaxe, tiles, wildlifeTokens, picked.keySet().iterator().next(), null);
      makeMove(player1, picked.get(picked.keySet().iterator().next()), picked.keySet().iterator().next(), scan, wildlifeTokens, terminal);
      if(terminal) {
        System.out.println("Tour du J2 :\n");
      }
      printPlayer(player2, terminal);
      if(terminal) {
        System.out.println("----------------------------");
        System.out.println(card);
      }
      picked = pickPickaxe(pickaxe, scan, wildlifeTokens, terminal);
      craftPickaxe(pickaxe, tiles, wildlifeTokens, picked.keySet().iterator().next(), null);
      makeMove(player2, picked.get(picked.keySet().iterator().next()), picked.keySet().iterator().next(), scan, wildlifeTokens, terminal);
    }
  }
  
  private static int isNeighbor(Coordonate current, HashMap<Coordonate, TilesSquare> player, Set<Coordonate> visited, String landscape) {
    visited.add(current);
    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0, -1}}; //Pour éviter les if dans neighbour on pourra faire comme ça je pense (j'ai fais du C mais je sais pas comment ça marche les deux dimensions en Java et Eclipse dit rien)
    var size = 1;
    for(var direction : directions) { //Hello, Darkness my old friend...
      var neighbour = new Coordonate(current.x() + direction[0], current.y() + direction[1]);
      if(player.containsKey(neighbour) && player.get(neighbour).landscape().equals(landscape) && !visited.contains(neighbour)) { //Si c'est pas un endroit vide et qu'il y a un animal et qu'on l'as pas déjà vus
        size += isNeighbor(neighbour, player, visited, landscape); //On regarde du coup les voisins de celui-là
      }
    }
    return size;
  }
  
  private static int groupSizesLandscape(HashMap<Coordonate, TilesSquare> player, String landscape) {
    Set<Coordonate> visited = new HashSet<>();
    var groupSize = 0;
    var tempGroupSize = 0;
    for(var key : player.keySet()) {
      if(player.get(key).landscape().equals(landscape) && !visited.contains(key)) { //On a trouvé un truc pas vide pas déjà dans un groupe
        tempGroupSize = isNeighbor(key, player, visited, landscape);
        if(tempGroupSize > groupSize) {
          groupSize = tempGroupSize; //On calcule la taille du groupe et on l'ajoute à la liste des tailles de groupes
        }
      }
    }
    return groupSize;
  }
  
  private static void countLandscapeScore(HashMap<Coordonate, TilesSquare> player1, HashMap<Coordonate, TilesSquare> player2, int[] resultPlayer) {
    int landscapeSizePlayer1 = 0;
    int landscapeSizePlayer2 = 0;
    String[] landscape = {"Prairie", "Marais", "Riviere", "Montagne", "Foret"};
    for(var elem : landscape) {
      landscapeSizePlayer1 = groupSizesLandscape(player1, elem);
      landscapeSizePlayer2 = groupSizesLandscape(player2, elem);
      resultPlayer[0] += landscapeSizePlayer1;
      resultPlayer[1] += landscapeSizePlayer2;
      if(landscapeSizePlayer1 > landscapeSizePlayer2) {
        resultPlayer[0] += landscapeSizePlayer1;
      }
      else {
        resultPlayer[1] += landscapeSizePlayer2;
      }
    }
  }

  private static void countPoints(AnimalCard card, HashMap<Coordonate, TilesSquare> player1, HashMap<Coordonate, TilesSquare> player2, boolean terminal) {
    int[] resultPlayer = {0, 0};
    resultPlayer[0] += card.countCardScore(player1);
    resultPlayer[1] += card.countCardScore(player2);
    countLandscapeScore(player1, player2, resultPlayer);
    if(terminal) {
      System.out.println("Score J1 : " + resultPlayer[0]);
      System.out.println("Score J2 : " + resultPlayer[1]);
      if(resultPlayer[0] == resultPlayer[1]) {
        System.out.println("Vous êtes à égalité, GG à vous deux !");
      }
      else if(resultPlayer[0] > resultPlayer[1]) {
        System.out.println("Bravo J1 ! Vous êtes victorieux ! Gloire à vous !");
        System.out.println("Honte à vous J2 ! Vous avez failli !");
      }
      else {
        System.out.println("Bravo J2 ! Vous êtes victorieux ! Gloire à vous !");
        System.out.println("Honte à vous J1 ! Vous avez failli !");
      }
    }
  }
  
  public static void algorythmSquare(boolean terminal) {
    var scan = new Scanner(System.in);
    int[] forbidenNumber = {-1};
    var card = parameter(scan, terminal);
    var player1 = assignedPlayer(1, forbidenNumber, scan, terminal);
    var player2 = assignedPlayer(2, forbidenNumber, scan, terminal);
    var wildlifeTokens = initializationWildlifeToken();
    var tiles = initializationTiles();
    var pickaxe = new HashMap<TilesSquare, WildlifeToken>(); //pickaxe, obviously the correct tool to draw tiles
    craftPickaxe(pickaxe, tiles, wildlifeTokens, null, null);
    loopAlgorythmSquare(pickaxe, player1, player2, tiles, wildlifeTokens, scan, card, terminal);
    scan.close();
    countPoints(card, player1, player2, terminal);
  }
  
}
