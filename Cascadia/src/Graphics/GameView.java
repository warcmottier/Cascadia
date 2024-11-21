package Graphics;

import java.util.HashMap;

import com.github.forax.zen.ApplicationContext;

import Game.Coordonate;
import Game.TilesSquare;

public interface GameView {
	void drawPlayer(ApplicationContext context, HashMap<Coordonate, TilesSquare> player, int marge, int widthScreen, int heightScreen);
}
