import java.util.HashMap;
import java.util.Set;

import game.AnimalCard;
import game.Coordinate;
import game.CountPointSquare;
import game.DrawSquare;
import game.Landscape;
import game.TileSquare;
import game.WildlifeToken;

public class Main {

	public static void main(String[] args) {
		var p1 = new HashMap<Coordinate, TileSquare>();
		p1.put(new Coordinate(0, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p1.put(new Coordinate(1, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p1.put(new Coordinate(2, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p1.put(new Coordinate(3, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p1.put(new Coordinate(4, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.MEADOW));
		p1.put(new Coordinate(5, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(6, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(7, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(8, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), null, Landscape.MEADOW));
		p1.put(new Coordinate(9, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p1.put(new Coordinate(10, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		var p2 = new HashMap<Coordinate, TileSquare>();
		p2.put(new Coordinate(0, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(1, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(2, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.FOREST));
		p2.put(new Coordinate(3, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.MOUNTAIN));
		p2.put(new Coordinate(4, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.ELK, Landscape.MEADOW));
		p2.put(new Coordinate(5, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p2.put(new Coordinate(6, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MOUNTAIN));
		p2.put(new Coordinate(7, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p2.put(new Coordinate(8, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), null, Landscape.MEADOW));
		p2.put(new Coordinate(9, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		p2.put(new Coordinate(10, 0), new TileSquare(Set.of(WildlifeToken.BEAR, WildlifeToken.ELK), WildlifeToken.BEAR, Landscape.MEADOW));
		
		var draw = DrawSquare.createDraw();
		//System.out.println(draw.tiles());
		//System.out.println(draw.wildlife());
		System.out.println(draw.draw());
	}
}
