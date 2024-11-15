package Graphics;

import java.util.HashMap;

import com.github.forax.zen.ApplicationContext;

import Game.Coordonate;
import Game.TilesSquare;
import Game.WildlifeToken;

public record SimpleGameViewSquare(ApplicationContext context, HashMap<Coordonate, TilesSquare> player, HashMap<TilesSquare, WildlifeToken> pickaxe) implements GameView {
	
}
