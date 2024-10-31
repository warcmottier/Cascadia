import Game.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.Scanner;

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
	
	private static HashMap<Coordonate, TilesSquare> tilesBegin(int n){
		var tilesBegin = new ArrayList<HashMap<Coordonate, TilesSquare>>();
		String[][] animalAndLandscapeBegin = {{"Ours", "Saumon", "Prairie"}, {"Renard", "Buse", "Foret"}, {"Wapiti", "Ours", "Montagne"}, 
				{"Wapiti", "Saumon", "Foret"}, {"Buse", "Ours", "Riviere"}, {"Buse", "Wapiti", "Marais"}, 
				{"wapiti", "Ours", "Riviere"}, {"Renard", "Saumon", "Marais"}, {"Renard", "Ours", "Montagne"},
				{"Saumon", "Wapiti", "Prairie"}, {"Renard", "Wapiti", "Riviere"}, {"Ours", "Buse", "Foret"},
				{"Buse", "Saumon", "Prairie"}, {"Buse", "Ours", "Marais"}, {"Renard", "Wapiti", "Riviere"}};
		var i = 0;
		var map = new HashMap<Coordonate, TilesSquare>();
		for(var element : animalAndLandscapeBegin) {
			map.put(new Coordonate(i, 0), new TilesSquare(Set.of(element[0], element[1]), null, element[2]));
			i++;
			if(i == 3) {
				tilesBegin.add(map);
				map = new HashMap<Coordonate, TilesSquare>();
				i = 0;
			}
		}
		return tilesBegin.get(n);
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
		int enter = -1;
		System.out.println("J" + player + " entrez un nombre entier entre 0 et 4");
		for(enter = scan.nextInt(); enter < 0 && enter > 4 || enter == forbidenNumber[0]; enter = scan.nextInt()) {
			System.out.println("mauvaise entrée");
		}
		forbidenNumber[0] = enter;
		return tilesBegin(enter);
	}
	
	//discard = 0 if not overpopulation and numberDiscard = 4 if all wildlife or = 3 if 3 same wildlife or = 0 for the rest
	private void craftPickaxe(HashMap<TilesSquare, WildlifeToken> pickaxe, int discard, int numberDiscard) {
		
	}
	
	private static void algorythmSquare() {
		var scan = new Scanner(System.in);
		int[] forbidenNumber = {-1};
		var card = parameter(scan);
		var player1 = assignedPlayer(1, forbidenNumber, scan);
		var player2 = assignedPlayer(2, forbidenNumber, scan);
		var wildlifeToken = initializationWildlifeToken();
		var tiles = initializationTiles();
		var pickaxe = new HashMap<TilesSquare, WildlifeToken>(); //pickaxe, obviously the correct tool to draw tiles
		
		scan.close();
	}
	
	public static void main(String[] args) {
		algorythmSquare();
	}
}
