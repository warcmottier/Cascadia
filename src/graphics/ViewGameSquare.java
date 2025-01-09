package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Set;
import com.github.forax.zen.ApplicationContext;

import game.AnimalCard;
import game.Coordinate;
import game.DrawSquare;
import game.Landscape;
import game.TileSquare;
import game.WildlifeToken;

/**
 * ViewGameSquare contains the functions necessary to print and draw for the graphic version of Cascadia square
 */
public final class ViewGameSquare {
  
  /**
   * colorTile draw a tile with the correct color according to it's landscape and prints it on the tile
   * @param graphics Graphics2D representing the current window
   * @param coordinate Coordinate representing the coordinates of the tile being drawn
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
   * @param color Color representing the color given to this tile
   * @param landscape Landscape representing the landscape of the tile
   */
	private static void colorTile(Graphics2D graphics, Coordinate coordinate, int widthSreen, int heightScreen, int marge, Color color, Landscape landscape) {
		graphics.setColor(color);
		graphics.fill(new Rectangle2D.Float(widthSreen/2 + coordinate.x() * marge, heightScreen/2 + coordinate.y() * marge, marge, marge));
		graphics.setColor(Color.BLACK);
		graphics.drawString(landscape.landscape(), widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 10);
	}
	
	/**
	 * drawAnimal draw the current wildlife token on the tile if there is one
	 * @param wildlife WildlifeToken representing the wildlife token on the cell (null if there is none)
	 * @param graphics Graphics2D representing the current window
	 * @param coordinate Coordinate representing the coordinates of the tile being drawn
	 * @param widthSreen int representing the width of the screen
	 * @param heightScreen int representing the height of the screen
	 * @param marge int representing the size of the rectangle for a tile
	 */
	private static void drawAnimal(WildlifeToken wildlife, Graphics2D graphics, Coordinate coordinate, int widthSreen, int heightScreen, int marge) {
			if(wildlife == null) {
				return;
			}
			graphics.drawString(wildlife.wildlifeToken(), widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 30);
	}
	
	/**
	 * drawAnimalAccepted draws the animals accepted by a tile
	 * @param animalAccepted Set<WildlifeToken> representing the animals accepted by this tile
   * @param graphics Graphics2D representing the current window
   * @param coordinate Coordinate representing the coordinates of the tile being drawn
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 */
	private static void drawAnimalAccepted(Set<WildlifeToken> animalAccepted, Graphics2D graphics, Coordinate coordinate, int widthSreen, int heightScreen, int marge) {
		var iterator = animalAccepted.iterator();
		graphics.drawString(iterator.next().wildlifeToken() + ",", widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 45);
		graphics.drawString(iterator.next().wildlifeToken(), widthSreen/2 + coordinate.x() * marge + 5, heightScreen/2 + coordinate.y() * marge + 60);
	}
	
	/**
	 * drawTile draws a tile and its content 
   * @param graphics Graphics2D representing the current window
   * @param coordinate Coordinate representing the coordinates of the tile being drawn
   * @param tile TileSquare representing the tile being drawn
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 */
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
	
	/**
	 * drawPlayer draws the board of a player
	 * @param context ApplicationContext representing the application being run
	 * @param playerBoard Map<Coordinate, TileSquare> representing the board of a player
	 * @param marge int representing the size of the rectangle for a tile
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
	 */
	public static void drawPlayer(ApplicationContext context, Map<Coordinate, TileSquare> playerBoard, int marge, int widthScreen, int heightScreen) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(0, 0, widthScreen, heightScreen));
		});
		playerBoard.keySet().forEach(elements -> context.renderFrame(graphics -> drawTile(graphics, elements, playerBoard.get(elements), widthScreen, heightScreen, marge)));
	}
	
	/**
	 * drawDrawWildlife draws the the wildlife tokens contained in the draw
	 * @param graphics Graphics2D representing the current window
	 * @param wildlife WildlifeToken representing the wildlife token currently being printed
   * @param marge int representing the size of the rectangle for a tile
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
	 * @param x int representing the coordinate of the tile in the draw
	 * @param y int representing the coordinate of the tile in the draw
	 */
	private static void drawDrawWildlife(Graphics2D graphics, WildlifeToken wildlife, int marge, int widthSreen, int heightScreen, int x, int y) {
		graphics.setColor(Color.white);
		graphics.drawString(wildlife.wildlifeToken(), widthSreen/2 + x * marge + 5, heightScreen/2 + y * marge + 30);
	}
	
	/**
	 * drawDraw draws the draw
	 * @param draw DrawSquare representing the draw
	 * @param context ApplicationContext representing the application being run
   * @param marge int representing the size of the rectangle for a tile
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
	 */
	public static void drawDraw(DrawSquare draw, ApplicationContext context, int marge, int widthScreen, int heightScreen) {
    final int[] cmpt = {0};
    draw.draw().keySet().stream().forEach(elements -> {
        context.renderFrame(graphics -> drawTile(graphics, new Coordinate(cmpt[0] - 3, 0), elements, widthScreen / 4, heightScreen / 4, marge));
        context.renderFrame(graphics -> drawDrawWildlife(graphics, draw.draw().get(elements), marge, widthScreen / 4, heightScreen / 4, cmpt[0] - 3, 1));
        cmpt[0]++;
    });
	}
	
	/**
	 * drawText draws the text representing the rules of an animalCard
	 * @param graphics Graphics2D representing the current window
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @param card AnimalCard representing the current card
	 */
	private static void drawText(Graphics2D graphics, int widthScreenInfo, int heightScreenInfo, AnimalCard card) {
		graphics.setColor(Color.WHITE);
		switch (card) {
			case AnimalCard.FAMILY -> graphics.drawString(card.pointAnimal(), widthScreenInfo, heightScreenInfo);
			case AnimalCard.INTERMEDIATE -> graphics.drawString(card.pointAnimal(), widthScreenInfo, heightScreenInfo);
			default -> throw new IllegalArgumentException();
		}
	}
	
	/**
	 * drawAnimalCard draws an animal card
	 * @param card AnimalCard representing the current card
	 * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 */
	public static void drawAnimalCard(AnimalCard card, ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
		context.renderFrame(graphics -> drawText(graphics, widthScreenInfo/ 2, heightScreenInfo / 50, card));
	}
	
	/**
	 * drawBox draws the boxes to answer when asked if an over population should be run
	 * @param graphics Graphics2D representing the current window
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @param sentence String representing the sentence to be printed
	 */
	public static void drawBox(Graphics2D graphics, int widthScreenInfo, int heightScreenInfo, String sentence) {
		graphics.setColor(Color.GRAY);
		graphics.fill(new Rectangle2D.Float(widthScreenInfo, heightScreenInfo, 65, 65));
		graphics.setColor(Color.WHITE);
		graphics.drawString(sentence, widthScreenInfo + 30, heightScreenInfo + 30);
	}
	
	/**
	 * drawOverPopulation draws the necessary tools for the player to interact in case of an overpopulation
	 * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 */
	public static void drawOverPopulation(ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			graphics.drawString("do you want to do the overPopulation", widthScreenInfo / 50 - 20, heightScreenInfo - 150);
		});
		context.renderFrame(graphics -> drawBox(graphics, widthScreenInfo / 50, heightScreenInfo - 100, "yes"));
		context.renderFrame(graphics -> drawBox(graphics, widthScreenInfo / 50 + 120, heightScreenInfo - 100, "no"));
	}
	
	/**
	 * drawMoves draws the available tile moves
	 * @param tileMoves Set<Coordinate> representing the available moves
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 */
	public static void drawMoves(Set<Coordinate> tileMoves, ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int marge) {
		tileMoves.forEach(elements -> context.renderFrame(graphics -> {
			graphics.setColor(Color.GRAY);
			graphics.fill(new Rectangle2D.Float(widthScreenInfo/2 + elements.x() * marge, heightScreenInfo/2 + elements.y() * marge, marge, marge));
		}));
	}
	
	/**
	 * game runs the function and the main loop for a graphic game of Cascadia with square tiles
	 * @param context context ApplicationContext representing the application being run
	 * @param playerBoard Map<Coordinate, TileSquare> representing the board of a player
	 * @param draw DrawSquare representing the draw
	 * @param card AnimalCard representing an animal card
	 */
	public static void game(ApplicationContext context, Map<Coordinate, TileSquare> playerBoard, DrawSquare draw, AnimalCard card) {
		var screenInfo = context.getScreenInfo();
    var width = screenInfo.width();
    var height = screenInfo.height();
    var marge = 65;
    drawPlayer(context, playerBoard, marge, width, height);
    drawDraw(draw, context, marge, width, height);
    drawAnimalCard(card, context, width, height);
    drawOverPopulation(context, width, height);
    System.out.println(GameControlerSquare.askDraw(context, width, height, marge, draw));
	}
}
