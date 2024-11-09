import Game.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.util.Random;

public class Main {
	
	private static Set<String> generateAnimalAccepeted() {
		String[] animal = {"Ours", "Saumon", "Wapiti", "Buse", "Renard"};
		String in = new String();
		Set<String> animalAccepted = new HashSet<>();
		animalAccepted.add(animal[(int) (Math.random() * 5)]);
		for(in = animalAccepted.iterator().next(); animalAccepted.contains(in) ; in = animal[(int) (Math.random() * 5)]) {}
		animalAccepted.add(in);
		return animalAccepted;
	}
	
	private static List<TilesSquare> initializationTiles() {
		String[] landscape = {"Prairie", "Marais", "Riviere", "Montagne", "Foret"};
		List<TilesSquare> tiles = new ArrayList<>();
		for(var i = 0; i < 43; i++) {
			tiles.add(new TilesSquare(generateAnimalAccepeted(), null, landscape[(int) (Math.random() * 5)]));
		}
		return tiles;
	}
	
	private static HashMap<WildlifeToken, Integer> initializationWildlifeToken() {
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
		String[][] animalAndLandscapeBegin3 = {{"wapiti", "Ours", "Riviere"}, {"Renard", "Saumon", "Marais"}, {"Renard", "Ours", "Montagne"}};
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
	
	private static int parameter(Scanner scan) {
		int enter;
		System.out.println("Cascadia terminal version");
		System.out.println("entrez 1 pour la variante famille ou 2 pour la variante intermediaire");
		for(enter = scan.nextInt(); enter != 1 && enter != 2; enter = scan.nextInt()) {
			System.out.println("mauvaise entrée");
		}
		return enter;
	}
	
	private static HashMap<Coordonate, TilesSquare> assignedPlayer(int player, int[] forbidenNumber, Scanner scan) {
		var enter = -1;
		System.out.println("J" + player + " entrez un nombre entier entre 1 et 5");
		for(enter = scan.nextInt(); enter < 1 && enter > 5 || enter == forbidenNumber[0]; enter = scan.nextInt()) {
			System.out.println("mauvaise entrée");
		}
		forbidenNumber[0] = enter;
		return tilesBegin(enter);
	}
	
	private WildlifeToken removeOverPopulation(HashMap<TilesSquare, WildlifeToken> pickaxe, int n){
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
	private void overPopulation(HashMap<TilesSquare, WildlifeToken> pickaxe, int numberDiscard, List<TilesSquare> tiles, HashMap<WildlifeToken, Integer> wildlifeTokens) {
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
	
	private static void printPickaxe(HashMap<TilesSquare, WildlifeToken> pickaxe) {
	  var integer = 1;
	  for(var elem : pickaxe.entrySet()) {
	    System.out.println(integer + " - " +elem);
	    integer++;
	  }
    System.out.println("----------------------------");
	  System.out.println("Entrez le numéro de la pioche voulue :");
	}
	
	public static HashMap<TilesSquare, WildlifeToken> pickPickaxe(HashMap<TilesSquare, WildlifeToken> pickaxe, Scanner scan) {
	  var picked = new HashMap<TilesSquare, WildlifeToken>();
	  var enter = -1;
	  var arrayPickaxe = pickaxe.keySet().toArray();
	  printPickaxe(pickaxe);
	  for(enter = scan.nextInt(); enter < 1 && enter > 4;) {
      System.out.println("mauvaise entrée");
    }
    System.out.println("----------------------------");
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
	
  public static Set<Coordonate> wildlifeTokenAvailablesSlots(HashMap<Coordonate, TilesSquare> tilesMap, WildlifeToken wildlife) {
    Set<Coordonate> results = new HashSet<>();
    for(var elems : tilesMap.keySet()) {
      if(tilesMap.get(elems).animal() == null && tilesMap.get(elems).animalAccepted().contains(wildlife.animal())) {
        results.add(elems);
      }
    }
    return results;
  }
	
  private static void makeTileMove(HashMap<Coordonate, TilesSquare> player, TilesSquare tile, Scanner scan) {
    Set<Coordonate> moves = allAvailablesTilesMove(player);
    var i = 1;
    int enter;
    for(var elem : moves) {
      System.out.println(i + " - " + elem);
      i++;
    }
    System.out.println("Entrez le numéro de la coordonnée désirée pour la tuile :");
    for(enter = scan.nextInt(); enter < 1 && enter > moves.size();) {
      System.out.println("mauvaise entrée");
    }
    System.out.println("----------------------------");
    player.put((Coordonate) moves.toArray()[enter - 1], tile);
  }
  
  private static void makeWildlifeMove(HashMap<Coordonate, TilesSquare> player, WildlifeToken wildlife, Scanner scan) {
    Set<Coordonate> moves = wildlifeTokenAvailablesSlots(player, wildlife);
    var i = 1;
    int enter;
    for(var elem : moves) {
      System.out.println(i + " - " + elem);
      i++;
    }
    System.out.println("Entrez le numéro de la coordonnée désirée pour le jeton faune :");
    for(enter = scan.nextInt(); enter < 1 && enter > moves.size();) {
      System.out.println("mauvaise entrée");
    }
    System.out.println("----------------------------");
    player.get(moves.toArray()[enter - 1]).setWildlifeToken(wildlife);
  }
  
  private static void printPlayer(HashMap<Coordonate, TilesSquare> player) {
    for(var elem : player.entrySet()) {
      System.out.println(elem);
    }
  }
  
  private static void makeMove(HashMap<Coordonate, TilesSquare> player, WildlifeToken wildlife, TilesSquare tile, Scanner scan) {
    printPlayer(player);
    System.out.println("----------------------------");
    makeTileMove(player, tile, scan);
    printPlayer(player);
    System.out.println("----------------------------");
    makeWildlifeMove(player, wildlife, scan);
  }
  
	public static void algorythmSquare() {
		var scan = new Scanner(System.in);
		int[] forbidenNumber = {-1};
		var picked = new HashMap<TilesSquare, WildlifeToken>();
		var card = parameter(scan);
		var player1 = assignedPlayer(1, forbidenNumber, scan);
		var player2 = assignedPlayer(2, forbidenNumber, scan);
		var wildlifeTokens = initializationWildlifeToken();
		var tiles = initializationTiles();
		var pickaxe = new HashMap<TilesSquare, WildlifeToken>(); //pickaxe, obviously the correct tool to draw tiles
		craftPickaxe(pickaxe, tiles, wildlifeTokens, null, null);
		for(var i = 0; i < 20; i++) {
		  System.out.println("Tour numéro " + (i + 1) + "\n");
		  System.out.println("Tour du J1 :\n");
		  printPlayer(player1);
      System.out.println("----------------------------");
		  picked = pickPickaxe(pickaxe, scan);
		  craftPickaxe(pickaxe, tiles, wildlifeTokens, picked.keySet().iterator().next(), null);
		  makeMove(player1, picked.get(picked.keySet().iterator().next()), picked.keySet().iterator().next(), scan);
      System.out.println("Tour du J2 :\n");
      printPlayer(player2);
      System.out.println("----------------------------");
      picked = pickPickaxe(pickaxe, scan);
      craftPickaxe(pickaxe, tiles, wildlifeTokens, picked.keySet().iterator().next(), null);
      makeMove(player2, picked.get(picked.keySet().iterator().next()), picked.keySet().iterator().next(), scan);
		}
		scan.close();
	}
	
	public static void main(String[] args) {
		algorythmSquare();
	}
}
