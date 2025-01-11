package game;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * this record the way points are counted for a player
 * player1 is a Map representing the player 1
 * player2 is a Map representing the player 2
 * pointPlayer is an array of int representing the points of the 2 different players
 */
public record CountPointSquare(Map<Coordinate, TileSquare> player1, Map<Coordinate, TileSquare> player2, int[] pointPlayer) {
	
  /**
	 * Constructs a CountPointSquare Object checking that both players exist
	 * @param player1 a Map representing the player 1
	 * @param player2 a Map representing the player 2
	 * @param pointPlayer is an array of int representing the points of the 2 different players
	 */
  public CountPointSquare {
		Objects.requireNonNull(player1);
		Objects.requireNonNull(player2);
	}
	
  /**
   * CountPointSquare is an overload constructor made because all players must start at 0 points
   * @param player1 a Map representing the player 1
   * @param player2 a Map representing the player 2
   */
	public CountPointSquare(Map<Coordinate, TileSquare> player1, Map<Coordinate, TileSquare> player2){
		this(player1, player2, new int[] {0, 0});
	}
	
	/**
	 * majority finds out which player get the majority bonus points for the specified landscape
	 * @param scoreLandscapeplayer1 a Map of Landscape and Integer representing the score of the player 1 for each landscape
	 * @param scoreLandscapeplayer2 a Map of Landscape and Integer representing the score of the player 2 for each landscape
	 * @param landscape a Landscape representing the current landscape being evaluated
	 * @return
	 */
	private int majority(Map<Landscape, Integer> scoreLandscapeplayer1, Map<Landscape, Integer> scoreLandscapeplayer2, Landscape landscape) {
		if(scoreLandscapeplayer1.get(landscape) > scoreLandscapeplayer2.get(landscape)) {
			return 1;
		}
		if(scoreLandscapeplayer1.get(landscape) < scoreLandscapeplayer2.get(landscape)) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * countPoint count the total score of each player
	 * @param player an int representing the player being evaluated
	 * @param card the AnimalCard being use for this game
	 * @return an int representing the points of the player
	 */
	private int countPoint(int player, AnimalCard card) {
		var pointWildlife = new WildlifeCount(card);
		var pointLandscapePlayer1 = new LandscapeCountSquare(player1);
		var pointLandscapePlayer2 = new LandscapeCountSquare(player2);
		var scoreLandscapeplayer1 = pointLandscapePlayer1.everyLandscapeScore();
		var scoreLandscapeplayer2 = pointLandscapePlayer2.everyLandscapeScore();

		if(player == 1) {
			return pointWildlife.countCardScore(player1) + Arrays.stream(Landscape.values())
			.mapToInt(land -> {return majority(scoreLandscapeplayer1, scoreLandscapeplayer2, land) == 1 ? scoreLandscapeplayer1.get(land) * 2 : 0;})
			.sum();
		}
		return pointWildlife.countCardScore(player2) + Arrays.stream(Landscape.values())
		.mapToInt(land -> {return majority(scoreLandscapeplayer1, scoreLandscapeplayer2, land) == 1 ? scoreLandscapeplayer2.get(land) * 2 : 0;})
		.sum();
	}
	
	/**
	 * winner calculates the player with the highest number of points
	 * @param card the AnimalCard being use for this game
	 * @return an int representing the winning player
	 */
	public int winner(AnimalCard card) {
	  Objects.requireNonNull(card);
		pointPlayer[0] = countPoint(1, card);
		pointPlayer[1] = countPoint(2, card);
		if(pointPlayer[0] > pointPlayer[1]) {
			return 1;
		}
		if(pointPlayer[0] < pointPlayer[1]) {
			return 2;
		}
		return 0;
	}
}
