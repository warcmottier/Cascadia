package Graphics;

import java.util.HashMap;
import java.util.List;

import com.github.forax.zen.ApplicationContext;

import Game.AnimalCard;
import Game.Coordonate;
import Game.TilesSquare;
import Game.WildlifeToken;
import Game.AlgoSquare;

public class SimpleGameController {
	private final static HashMap<Coordonate, TilesSquare> player1 = new HashMap<>();
	private final static HashMap<Coordonate, TilesSquare> player2 = new HashMap<>();
	private final HashMap<TilesSquare, WildlifeToken> pickaxe = new HashMap<>();
	private final HashMap<WildlifeToken, Integer> wildlifeTokens = AlgoSquare.initializationWildlifeToken();
	private final static List<TilesSquare> tiles = AlgoSquare.initializationTiles();
	private AnimalCard card = null;
	
	public static void game(ApplicationContext context) {
		var screenInfo = context.getScreenInfo();
    var width = screenInfo.width();
    var height = screenInfo.height();
    var marge = 50; 
    GameView gameView = new SimpleGameViewSquare();
    player1.put(new Coordonate(0, 0), tiles.get(0));
    player1.put(new Coordonate(1, 0), tiles.get(1));
    player1.put(new Coordonate(2, 0), tiles.get(2));
    player1.put(new Coordonate(-1, 0), tiles.get(3));
    player1.put(new Coordonate(2, 1), tiles.get(4));
    player1.put(new Coordonate(2, 2), tiles.get(5));
    player1.put(new Coordonate(2, 3), tiles.get(6));
    player1.put(new Coordonate(0, -1), tiles.get(7));
    gameView.drawPlayer(context, player1, marge, width, height);   
	}
	
}
