package game;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class AlgoSquare {
	private final HashMap<Coordinate, TileSquare> player1 = new HashMap<>();
	private final HashMap<Coordinate, TileSquare> player2 = new HashMap<>();
	private final DrawSquare draw = DrawSquare.createDraw();
	private AnimalCard card;
	
	private void setMap(int wildlife, Landscape landscape1, Landscape landscape2, Landscape landscape3, int numberPlayer) {
		Landscape[] arrayLandscapes = {landscape1, landscape2, landscape3};
		WildlifeToken[][] wildlifeTokens = {{WildlifeToken.BEAR, WildlifeToken.SALMON}, {WildlifeToken.FOX, WildlifeToken.NOZZLE}, {WildlifeToken.ELK, WildlifeToken.BEAR},
    		{WildlifeToken.ELK, WildlifeToken.SALMON}, {WildlifeToken.NOZZLE, WildlifeToken.BEAR}, {WildlifeToken.NOZZLE, WildlifeToken.ELK},
    		{WildlifeToken.ELK, WildlifeToken.BEAR}, {WildlifeToken.FOX, WildlifeToken.SALMON}, {WildlifeToken.FOX, WildlifeToken.NOZZLE},
    		{WildlifeToken.SALMON, WildlifeToken.ELK}, {WildlifeToken.FOX, WildlifeToken.ELK}, {WildlifeToken.BEAR, WildlifeToken.NOZZLE},
    		{WildlifeToken.NOZZLE, WildlifeToken.SALMON}, {WildlifeToken.NOZZLE, WildlifeToken.BEAR}, {WildlifeToken.FOX, WildlifeToken.ELK}};
    for(int i = 0; i < 3; i++) {
    	if(numberPlayer == 1) {
    		player1.put(new Coordinate(0, i), new TileSquare(Set.of(wildlifeTokens[wildlife + i][0], wildlifeTokens[wildlife + i][1]), null, arrayLandscapes[i]));
    	}
    	else {
    		player2.put(new Coordinate(0, i), new TileSquare(Set.of(wildlifeTokens[wildlife + i][0], wildlifeTokens[wildlife + i][1]), null, arrayLandscapes[i]));
    	}
    }
  }
	
	private void tilesBegin(int n, int numberPlayer) {
    switch (n) {
		case 1 -> setMap(0, Landscape.MEADOW, Landscape.FOREST, Landscape.MOUNTAIN, numberPlayer);
		case 2 -> setMap(3, Landscape.FOREST, Landscape.RIVER, Landscape.SWAMP, numberPlayer);
		case 3 -> setMap(6, Landscape.RIVER, Landscape.SWAMP, Landscape.MOUNTAIN, numberPlayer);
		case 4 -> setMap(9, Landscape.MEADOW, Landscape.RIVER, Landscape.FOREST, numberPlayer);
		case 5 -> setMap(12, Landscape.MEADOW, Landscape.SWAMP, Landscape.RIVER, numberPlayer);
		default -> throw new IllegalArgumentException("Unexpected value: " + n);
		};
	}
	
	public static AlgoSquare initializedGame() {
		var game = new AlgoSquare();
		//choix anmal card
		var n = 0;
		// affichage + choix joueur1
		game.tilesBegin(n, 1);
		//affichage + choix joueur2
		game.tilesBegin(n, 2);
		return game;
	}
	
	private void makeTileMove(int input, Set<Coordinate> movesTiles, int player, TileSquare tile) {
		if(player == 1) {
			player1.put((Coordinate) movesTiles.toArray()[input - 1], tile);
		}
		else {
			player2.put((Coordinate) movesTiles.toArray()[input - 1], tile);
		}
	}
	
	private void makeWildlifeMove(int input, Set<Coordinate> movesWildlife, WildlifeToken wildlife, int player) {
		if(player == 1) {
			player1.get(movesWildlife.toArray()[input - 1]).setWildlifeToken(wildlife);
		}
		else {
			player2.get(movesWildlife.toArray()[input - 1]).setWildlifeToken(wildlife);
		}
	}
	
	private Set<Coordinate> allAvailableTileMove(int player) {
    var moves = (player == 1) ? player1 : player2;
    return moves.keySet().stream()
            .flatMap(coord -> TileSquare.notneighbour(coord, moves).stream())
            .collect(Collectors.toSet());
}

	private Set<Coordinate> allAvailableWildlifeMove(int player,  WildlifeToken wildlife) {
		var moves = (player == 1) ? player1 : player2;
    return moves.keySet().stream().filter(coord -> moves.get(coord).animal() == null && moves.get(coord).animalAccepted().contains(wildlife))
    		.collect(Collectors.toSet());
	}
	
	private void makeMove(int player, Map<TileSquare, WildlifeToken> picked) {
		var movesTiles = allAvailableTileMove(player);
		var movesWildlife = allAvailableWildlifeMove(player, picked.values().iterator().next());
		var input = 0;
		//affiche choix placement tuile choix joueur (renvoie la coordoner le chiffre voulue)
		makeTileMove(input, movesTiles, player, picked.keySet().iterator().next());
		if(movesWildlife.size() == 0) {
			//affiche impossible a jouer
			draw.wildlife().put(picked.values().iterator().next(), draw.wildlife().get(picked.values().iterator().next()) + 1);
			return;
		}
		//affiche choix placement wildlife + choix joueur (renvoie la coordonner voulue)
		makeWildlifeMove(input, movesWildlife, picked.values().iterator().next(), player);
	}
	
	private void winner() {
		var point = new CountPointSquare(player1, player2);
		point.winner(card);
		//afficher winner point
	}
	
	// TO DO
	public void game() {
		var input = 0;
		var currentPlayer = 1;
		Map<TileSquare, WildlifeToken> picked = new HashMap<>();
		for(int i = 0; i < 40; i++) {
			//affichage entete + player + carte + pioche + choix jouer
			picked = draw.pickDraw(input);
			input = draw.isOverpopulation();
			if(input == 4) {
				draw.overpopulation(4);
			}
			else if(input == 3) {
				if(/*afficher pioche + demande roverpopuation (boolean)*/) {
					draw.overpopulation(3);
				}
			}
			makeMove(currentPlayer, picked);
		}
		winner();
	}
}
