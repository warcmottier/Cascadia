package game;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.forax.zen.ApplicationContext;

import graphics.GameControlerSquare;
import graphics.ViewGameSquare;
import graphics.ViewTerminal;
/*This class represent the data structure of a Cascadia game with squares for tiles and limited to 2 players
 * player1 is a HashMap representing the number 1 player
 * player2 is a HashMap representing the number 2 player
 * draw is a DrawSquare representing the draw for the players
 * card is the animalCard representing the card used for counting point (either family or intermediary)
 */
public final class AlgoSquare {
	private final HashMap<Coordinate, TileSquare> player1 = new HashMap<>();
	private final HashMap<Coordinate, TileSquare> player2 = new HashMap<>();
	private final DrawSquare draw = DrawSquare.createDraw();
	private static AnimalCard card;
	
	/**
	 * setMap creates the tiles with which the players start the game
	 * @param wildlife an int representing the spot of the wildlifeToken on this beginning tile in the widlifeTokens array
	 * @param landscape1 a Landscape representing the first landscape of this beginning tile
	 * @param landscape2 a Landscape representing the second landscape of this beginning tile
	 * @param landscape3 a Landscape representing the third landscape of this beginning tile
	 * @param numberPlayer an int representing if this beginning tile is for the player1 or the player2
	 */
	private void setMap(int wildlife, Landscape landscape1, Landscape landscape2, Landscape landscape3, int numberPlayer) {
		Landscape[] arrayLandscapes = {landscape1, landscape2, landscape3};
		WildlifeToken[][] wildlifeTokens = {{WildlifeToken.BEAR, WildlifeToken.SALMON}, {WildlifeToken.FOX, WildlifeToken.NOZZLE}, {WildlifeToken.ELK, WildlifeToken.BEAR},
    		{WildlifeToken.ELK, WildlifeToken.SALMON}, {WildlifeToken.NOZZLE, WildlifeToken.BEAR}, {WildlifeToken.NOZZLE, WildlifeToken.ELK},
    		{WildlifeToken.ELK, WildlifeToken.BEAR}, {WildlifeToken.FOX, WildlifeToken.SALMON}, {WildlifeToken.FOX, WildlifeToken.NOZZLE},
    		{WildlifeToken.SALMON, WildlifeToken.ELK}, {WildlifeToken.FOX, WildlifeToken.ELK}, {WildlifeToken.BEAR, WildlifeToken.NOZZLE},
    		{WildlifeToken.NOZZLE, WildlifeToken.SALMON}, {WildlifeToken.NOZZLE, WildlifeToken.BEAR}, {WildlifeToken.FOX, WildlifeToken.ELK}};
    for(var i = 0; i < 3; i++) { //Makes the tiles 1 by one and adds them to the Map of the player
    	if(numberPlayer == 1) {
    		player1.put(new Coordinate(0, i), new TileSquare(Set.of(wildlifeTokens[wildlife + i][0], wildlifeTokens[wildlife + i][1]), null, arrayLandscapes[i]));
    	}
    	else {
    		player2.put(new Coordinate(0, i), new TileSquare(Set.of(wildlifeTokens[wildlife + i][0], wildlifeTokens[wildlife + i][1]), null, arrayLandscapes[i]));
    	}
    }
  }
	
	/**
	 * tilesBegin decides which beginning tile is being created
	 * @param n an int representing the random beginning tile chosen by the player
	 * @param numberPlayer an int representing which player is receiving the beginning tile
	 */
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
	
	/**
	 * initializedGame makes an AlgoSquare in the state to begin a Cascadia square game
	 * @param graphic boolean representing if the game is in graphic or terminal version
	 * @param context ApplicationContext representing the application being run
   * @param width int representing the width of the screen
   * @param height int representing the height of the screen
	 * @return an AlgoSquare Object
	 */
	public static AlgoSquare initializedGame(boolean graphic, int width, int height, ApplicationContext context) {
		var game = new AlgoSquare();
		int[] forbidenNumber = {-1};
		if(graphic) {
		  card = ViewGameSquare.drawAskAnimalCard(context, width, height);
		  game.tilesBegin(ViewGameSquare.drawAskTileBegin(context, width, height, forbidenNumber), 1);
		  game.tilesBegin(ViewGameSquare.drawAskTileBegin(context, width, height, forbidenNumber), 2);
		}
		else {
		  card = ViewTerminal.choiceAnimalCard();		  
		  game.tilesBegin(ViewTerminal.choiceTileBegin(forbidenNumber, 1), 1);
		  game.tilesBegin(ViewTerminal.choiceTileBegin(forbidenNumber, 2), 2);
		}
		return game;
	}
	
	/**
	 * makeTileMove modifies the Map associated with a player to add his chosen tile to his chosen spot
	 * @param input an int representing the player's choice
	 * @param movesTiles a List of Coordinate repesenting the possibe moves
	 * @param player an int representing which player is playing his turn
	 * @param tile a TileSquare representing the tile drawn by the player
	 */
	private void makeTileMove(int input, Set<Coordinate> movesTiles, int player, TileSquare tile) {
		if(player == 1) {
			player1.put((Coordinate) movesTiles.toArray()[input - 1], tile);
		}
		else {
			player2.put((Coordinate) movesTiles.toArray()[input - 1], tile);
		}
	}
	
	/**
	 * makeWildlifeMove modifies the Map associated with a player to add his chosen WildlifeToken to his chosen spot
	 * @param input an int representing the player's choice
	 * @param movesWildlife a List of Coordinate repesenting the possibe moves
	 * @param wildlife a WildlifeToken representing the token chosen by the player
	 * @param player an int representing which player is playing his turn
	 */
	private void makeWildlifeMove(int input, Set<Coordinate> movesWildlife, WildlifeToken wildlife, int player) {
		if(player == 1) {
			player1.get(movesWildlife.toArray()[input - 1]).setWildlifeToken(wildlife);
		}
		else {
			player2.get(movesWildlife.toArray()[input - 1]).setWildlifeToken(wildlife);
		}
	}
	
	/**
	 * allAvailableTileMove finds all the possible player moves in this position
	 * @param player an int representing which player is playing his turn
	 * @return a List of Coordinate containing the Coordinate of every possible move
	 */
	private Set<Coordinate> allAvailableTileMove(int player) {
    var moves = (player == 1) ? player1 : player2;
    return moves.keySet().stream()
            .flatMap(coord -> TileSquare.notneighbour(coord, moves).stream())
            .collect(Collectors.toSet());
}

	/**
	 * allAvailableWildlifeMove finds all the possible player moves in this position
	 * @param player an int representing which player is playing his turn
	 * @param wildlife
	 * @return a List of Coordinate containing the Coordinate of every possible move
	 */
	private Set<Coordinate> allAvailableWildlifeMove(int player,  WildlifeToken wildlife) {
		var moves = (player == 1) ? player1 : player2;
    return moves.keySet().stream().filter(coord -> moves.get(coord).animal() == null && moves.get(coord).animalAccepted().contains(wildlife))
    		.collect(Collectors.toSet());
	}
	
	/**
	 * MakeMove is used to allow the player to play the wildlife token and tile he just drawn
	 * @param player an int representing which player is playing his turn
	 * @param picked a Map<TileSquare, WildlifeToken> representing the WildlifeToken and the TileSquare drawn
	 */
	private void makeMove(int player, Map<TileSquare, WildlifeToken> picked) {
		var movesTiles = allAvailableTileMove(player);
		var movesWildlife = allAvailableWildlifeMove(player, picked.values().iterator().next());
		var input = -1;
		input = ViewTerminal.choiceMoveTileOrWildelife(movesTiles, true);
		makeTileMove(input, movesTiles, player, picked.keySet().iterator().next());
		if(movesWildlife.size() == 0) {
			System.out.println("there is no available spot for this wildlife token return to the draw");
			draw.wildlife().put(picked.values().iterator().next(), draw.wildlife().get(picked.values().iterator().next()) + 1);
			return;
		}
		input = ViewTerminal.choiceMoveTileOrWildelife(movesWildlife, false);
		makeWildlifeMove(input, movesWildlife, picked.values().iterator().next(), player);
	}
	
	/**
	 * winner determines  the winner of the game
	 */
	private void winner() {
		var point = new CountPointSquare(player1, player2);
		ViewTerminal.printWinner(point, point.winner(card));
	}

	/**
	 * overpopulationMenu decides if the overpopulation option should be given to the player
	 * @param input int representing the necessity to run an overpopulation or give the ability to ask for one to the player
	 */
	private void overpopulationMenuTerminal(int input) {
    for(; input == 3 || input == 4; input = draw.isOverpopulation()) {
      if(input == 4) {
        draw.overpopulation(4);
      }
      else if(input == 3) {
        if(ViewTerminal.askOverpopulation(draw)) {
          draw.overpopulation(3);
        }
        else {
          break;
        }
      }
    }
	}
	
	/**
	 * gameTerminal is the main function controlling the progress of the game
	 */
	public void gameTerminal() {
		var input = 0;
		var currentPlayer = 1;
		Map<TileSquare, WildlifeToken> picked = new HashMap<>();
		for(var i = 0; i < 40; i++) { // the game starts here
			input = draw.isOverpopulation();
			overpopulationMenuTerminal(input);
			input = (currentPlayer == 1) ? ViewTerminal.printHead(player1, card, draw, currentPlayer) : ViewTerminal.printHead(player2, card, draw, currentPlayer);
			picked = draw.pickDraw(input);
			makeMove(currentPlayer, picked);
			currentPlayer = (currentPlayer == 1) ? 2 : 1;
		}
		winner();
	}
	
	/**
	 * makeMoveGraphic allows the player to make his move in the graphic version of Cascadia
   * @param context ApplicationContext representing the application being run
   * @param width int representing the width of the screen
   * @param height int representing the height of the screen
	 * @param marge int representing the size of a box
	 * @param player int representing the current player
	 * @param picked Map<TileSquare, WildlifeToken> representing the player choice from the draw
	 */
	public void makeMoveGraphic(ApplicationContext context, int width, int height, int marge, int player, Map<TileSquare, WildlifeToken> picked) {
	  Objects.requireNonNull(context);
	  Objects.requireNonNull(picked);
	  var movesTiles = allAvailableTileMove(player);
	  var movesWildlife = allAvailableWildlifeMove(player, picked.values().iterator().next());
	  var coordinates = Set.of(ViewGameSquare.drawMoves(movesTiles, context, width, height, marge));
	  makeTileMove(1, coordinates, player, picked.keySet().iterator().next());
	  if(movesWildlife.size() == 0) {
	    draw.wildlife().put(picked.values().iterator().next(), draw.wildlife().get(picked.values().iterator().next()) + 1);
	    return;
	  }
	  if(player == 1) {
	  	ViewGameSquare.drawPlayer(context, player1, marge, width, height);
	  }
	  else {
	  	ViewGameSquare.drawPlayer(context, player2, marge, width, height);
	  }
	  coordinates = Set.of((player == 1) ? GameControlerSquare.askPositionWildlifeToken(context, width, height, marge, player1, movesWildlife) : GameControlerSquare.askPositionWildlifeToken(context, width, height, marge, player2, movesWildlife));
	  makeWildlifeMove(1, coordinates,picked.values().iterator().next(), player);
	}
	
	/**
	 * overpopulationMenuGraphic decides if the overpopulation option should be given to the player
   * @param input int representing the necessity to run an overpopulation or give the ability to ask for one to the player
   * @param context ApplicationContext representing the application being run
   * @param width int representing the width of the screen
   * @param height int representing the height of the screen
	 */
	private void overpopulationMenuGraphic(int input, ApplicationContext context,int width, int height) {
    for(; input == 3 || input == 4; input = draw.isOverpopulation()) {
      if(input == 4) {
        draw.overpopulation(4);
      }
      else if(input == 3) {
        if(ViewGameSquare.drawOverPopulation(draw, context, width, height) == 1) {
          draw.overpopulation(3);
        }
        else {
          break;
        }
      }
    }
	}
	
	/**
	 * gameSquareGraphic is the main function controlling the progress of the game
   * @param context ApplicationContext representing the application being run
   * @param width int representing the width of the screen
   * @param height int representing the height of the screen
	 */
	public void gameSquareGraphic(ApplicationContext context,int width, int height) {
	  Objects.requireNonNull(context);
	  var marge = 65;
	  var input = 0;
	  var currentPlayer = 1;
	  Map<TileSquare, WildlifeToken> picked = new HashMap<>();
	  for(var i = 0; i < 40; i++) {
	    input = draw.isOverpopulation();
	    overpopulationMenuGraphic(input, context, width, height);
      input = (currentPlayer == 1) ? ViewGameSquare.drawHead(context, width, height, player1, card, draw, currentPlayer, marge) : ViewGameSquare.drawHead(context, width, height, player2, card, draw, currentPlayer, marge);
      picked = draw.pickDraw(input);
      makeMoveGraphic(context, width, height, marge, currentPlayer, picked);
      currentPlayer = (currentPlayer == 1) ? 2 : 1;
	  }
	  ViewGameSquare.drawWinner(context, width, height, player1, player2, card);
	  context.pollOrWaitEvent(50000);
	  context.dispose();
	}
	
}
