import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.github.forax.zen.Application;

import java.util.Set;
import game.*;
import graphics.*;


public class Main {
	private static HashMap<Coordinate, TileSquare> p1 = new HashMap<Coordinate, TileSquare>();
	private static HashMap<Coordinate, TileSquare> p2 = new HashMap<Coordinate, TileSquare>();
	
	private static List<Coordinate> allAvailableTileMove(int player) {
    var moves = (player == 1) ? p1 : p2;
    return moves.keySet().stream()
            .flatMap(coord -> TileSquare.notneighbour(coord, moves).stream())
            .collect(Collectors.toList());
}
	
	public static void main(String[] args) {
		//var p1 = new HashMap<Coordinate, TileSquare>();
		p1.put(new Coordinate(0, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.ELK, Landscape.MOUNTAIN));
		p1.put(new Coordinate(1, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.ELK, Landscape.MOUNTAIN));
		p1.put(new Coordinate(2, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.ELK, Landscape.MOUNTAIN));
		p1.put(new Coordinate(3, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.ELK, Landscape.MOUNTAIN));
		p1.put(new Coordinate(4, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.ELK, Landscape.MEADOW));
		p1.put(new Coordinate(5, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(6, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(7, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(8, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), null, Landscape.MEADOW));
		p1.put(new Coordinate(9, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(10, 0), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(2, 1), new TileSquare(Set.of(WildlifeToken.NOZZLE, WildlifeToken.SALMON), WildlifeToken.BEAR, Landscape.MEADOW));
		//var p2 = new HashMap<Coordinate, TileSquare>();
		
		p2.put(new Coordinate(-1, -1), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(-1, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(0, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(1, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(2, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(3, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.MOUNTAIN));
		p2.put(new Coordinate(4, 1), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), null, Landscape.MEADOW));
		p2.put(new Coordinate(5, 2), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), null, Landscape.MEADOW));
		p2.put(new Coordinate(6, 3), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), null, Landscape.MOUNTAIN));
		p2.put(new Coordinate(7, 4), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p2.put(new Coordinate(8, 5), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), null, Landscape.MEADOW));
		p2.put(new Coordinate(9, 8), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p2.put(new Coordinate(10, 10), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		
		var draw = DrawSquare.createDraw();
		//ViewTerminal.printHead(p2, AnimalCard.FAMILY, draw, 2);
		
		//System.out.println(draw.tiles());
		//System.out.println(draw.wildlife());
		//System.out.println(draw);
		//var input = ViewTerminal.printHead(p2, AnimalCard.FAMILY, draw, -152);
		//System.out.println(input);
		Application.run(Color.BLACK, constext -> ViewGame.game(constext, p1, draw));
		
		/*var game = AlgoSquare.initializedGame();
		game.game();*/
	}
}
