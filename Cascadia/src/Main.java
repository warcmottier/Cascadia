import java.util.HashMap;
import Game.*;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		HashMap<Coordonate, TilesSquare> player = new HashMap<>();
		player.put(new Coordonate(0, 0), new TilesSquare(Set.of("truc"), new WildlifeToken("renard") , "marais"));
		player.put(new Coordonate(1, 0), new TilesSquare(Set.of("truc"), new WildlifeToken("renard") , "marais"));
		var card = new AnimalCard(" ", 0, 1);
		var score = card.cardType(player);
		System.out.println(score);
	}
}
