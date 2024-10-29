import Game.*;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	private HashMap<WildlifeToken, Integer> initializationWildlifeToken() {
		var wildlife = new HashMap<WildlifeToken, Integer>();
		wildlife.put(new WildlifeToken("Ours"), 20);
		wildlife.put(new WildlifeToken("Saumon"), 20);
		wildlife.put(new WildlifeToken("Buse"), 20);
		wildlife.put(new WildlifeToken("Wapiti"), 20);
		wildlife.put(new WildlifeToken("Renard"), 20);
		return wildlife;
	}
	
	private static List<HashMap<Coordonate, TilesSquare>> listTilesBegin(){
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
		return tilesBegin;
	}
	
	private void algorythmSquare() {
		
	}
	
	public static void main(String[] args) {
	
	}
}
