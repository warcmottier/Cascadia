package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Set;
import com.github.forax.zen.ApplicationContext;
import game.Coordinate;
import game.DrawSquare;
import game.Landscape;
import game.TileSquare;
import game.WildlifeToken;

public final class ViewGame {
  
	private static void colorTile(Graphics2D graphics, Coordinate coordinate, int widthSreen, int heightScreen, int marge, Color color, Landscape landscape) {
		graphics.setColor(color);
		graphics.fill(new Rectangle2D.Float(widthSreen/2 + coordinate.x() * marge, heightScreen/2 + coordinate.y() * marge, marge, marge));
		graphics.setColor(Color.BLACK);
		graphics.drawString(landscape.landscape(), widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 10);
	}
	
	private static void drawAnimal(WildlifeToken wildlife, Graphics2D graphics, Coordinate coordinate, int widthSreen, int heightScreen, int marge) {
			if(wildlife == null) {
				return;
			}
			graphics.drawString(wildlife.wildlifeToken(), widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 30);
	}
	
	private static void drawAnimalAccepted(Set<WildlifeToken> animalAccepted, Graphics2D graphics, Coordinate coordinate, int widthSreen, int heightScreen, int marge) {
		var iterator = animalAccepted.iterator();
		graphics.drawString(iterator.next().wildlifeToken() + ",", widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 45);
		graphics.drawString(iterator.next().wildlifeToken(), widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 60);
	}
	
	private static void drawTile(Graphics2D graphics, Coordinate coordinate, TileSquare tile, int widthSreen, int heightScreen, int marge) {
		switch (tile.landscape()) {
			case Landscape.FOREST -> {
				colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.GREEN, Landscape.FOREST);
			}
			case Landscape.MEADOW -> {
				colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.YELLOW, Landscape.MEADOW );
			}
			case Landscape.SWAMP -> {
				colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.CYAN, Landscape.SWAMP);
			}
			case Landscape.MOUNTAIN -> {
				colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.WHITE, Landscape.MOUNTAIN);
			}
			case Landscape.RIVER -> {
				colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.BLUE, Landscape.RIVER);
			}
		}
		drawAnimal(tile.animal(), graphics, coordinate, widthSreen, heightScreen, marge);
		drawAnimalAccepted(tile.animalAccepted(), graphics, coordinate, widthSreen, heightScreen, marge);
	}
	
	public static void drawPlayer(ApplicationContext context, Map<Coordinate, TileSquare> playerBoard, int marge, int widthScreen, int heightScreen) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(0, 0, widthScreen, heightScreen));
		});
		playerBoard.keySet().forEach(elements -> context.renderFrame(graphics -> drawTile(graphics, elements, playerBoard.get(elements), widthScreen, heightScreen, marge)));
	}
	
	private static void drawDrawWildlife(Graphics2D graphics, WildlifeToken wildlife, int marge, int widthSreen, int heightScreen, int x, int y) {
		graphics.setColor(Color.white);
		graphics.drawString(wildlife.wildlifeToken(), widthSreen/2 + x * marge + 5, heightScreen/2 + y * marge + 30);
	}
	
	public static void drawDraw(DrawSquare draw, ApplicationContext context, int marge, int widthScreen, int heightScreen) {
		final int[]cmpt = {0};
		for(var elements : draw.draw().keySet()) {
			context.renderFrame(graphics -> drawTile(graphics, new Coordinate(cmpt[0] - 3, 0), elements, widthScreen, heightScreen, marge));
			context.renderFrame(graphics -> drawDrawWildlife(graphics, draw.draw().get(elements), marge, widthScreen, heightScreen, cmpt[0] - 3, 1));
			cmpt[0]++;
		}
	}
	
	public static void game(ApplicationContext context, Map<Coordinate, TileSquare> playerBoard, DrawSquare draw) {
		var screenInfo = context.getScreenInfo();
    var width = screenInfo.width();
    var height = screenInfo.height();
    var marge = 65; 
    drawPlayer(context, playerBoard, marge, width, height);
    drawDraw(draw, context, marge, width / 4, height / 4);
	}
}
