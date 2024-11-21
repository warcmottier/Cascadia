package Graphics;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.awt.Color;
import com.github.forax.zen.ApplicationContext;
import Game.Coordonate;
import Game.TilesSquare;
import Game.WildlifeToken;

public final record SimpleGameViewSquare() implements GameView {
	
	private static void colorTile(Graphics2D graphics, Coordonate coordonate, int widthSreen, int heightScreen, int marge, Color color, String landscape) {
		graphics.setColor(color);
		graphics.fill(new Rectangle2D.Float(widthSreen/2 + coordonate.x() * marge, heightScreen/2 + coordonate.y() * marge, marge, marge));
		graphics.setColor(Color.BLACK);
		graphics.drawString(landscape, widthSreen/2 + coordonate.x() * marge + 5, heightScreen/2 + coordonate.y() * marge + 10);
	}
	
	private static void drawTile(Graphics2D graphics, Coordonate coordonate, TilesSquare tile, int widthSreen, int heightScreen, int marge) {
		switch (tile.landscape()) {
			case "Forêt" -> {
				colorTile(graphics, coordonate, widthSreen, heightScreen, marge, Color.GREEN, "Forêt");
			}
			case "Prairie" -> {
				colorTile(graphics, coordonate, widthSreen, heightScreen, marge, Color.YELLOW, "Prairie");
			}
			case "Marais" -> {
				colorTile(graphics, coordonate, widthSreen, heightScreen, marge, Color.CYAN, "Marais");
			}
			case "Montagne" -> {
				colorTile(graphics, coordonate, widthSreen, heightScreen, marge, Color.WHITE, "Montagne");
			}
			case "Riviere" -> {
				colorTile(graphics, coordonate, widthSreen, heightScreen, marge, Color.BLUE, "Riviere");
			}
		}
		
		
	}
	
	@Override
	public void drawPlayer(ApplicationContext context, HashMap<Coordonate, TilesSquare> player, int marge, int widthScreen, int heightScreen) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(0, 0, widthScreen, heightScreen));
		});
		for(var elements : player.keySet()) {
			context.renderFrame(graphics -> drawTile(graphics, elements, player.get(elements), widthScreen, heightScreen, marge));
		}
	}
}
