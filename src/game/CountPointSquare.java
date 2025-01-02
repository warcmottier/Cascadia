package game;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public record CountPointSquare(Map<Coordinate, TileSquare> player1, Map<Coordinate, TileSquare> player2, int[] pointPlayer) {
	public CountPointSquare {
		Objects.requireNonNull(player1);
		Objects.requireNonNull(player2);
	}
	
	public CountPointSquare(Map<Coordinate, TileSquare> player1, Map<Coordinate, TileSquare> player2){
		this(player1, player2, new int[] {0, 0});
	}
	
	private int majority(Map<Landscape, Integer> scoreLandscapeplayer1, Map<Landscape, Integer> scoreLandscapeplayer2, Landscape landscape) {
		if(scoreLandscapeplayer1.get(landscape) > scoreLandscapeplayer2.get(landscape)) {
			return 1;
		}
		if(scoreLandscapeplayer1.get(landscape) < scoreLandscapeplayer2.get(landscape)) {
			return 2;
		}
		return 0;
	}
	
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
	
	
	public int winner(AnimalCard card) {
		pointPlayer[0] = countPoint(1, card);
		pointPlayer[1] = countPoint(2, card);
		System.out.println(pointPlayer[0]);
		System.out.println(pointPlayer[1]);
		if(pointPlayer[0] > pointPlayer[1]) {
			return 1;
		}
		if(pointPlayer[0] < pointPlayer[1]) {
			return 2;
		}
		return 0;
	}
}
