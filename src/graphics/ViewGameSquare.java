package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.github.forax.zen.ApplicationContext;

import game.AlgoSquare;
import game.AnimalCard;
import game.Coordinate;
import game.CountPointSquare;
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
			case Landscape.FOREST -> colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.GREEN, Landscape.FOREST);
			case Landscape.MEADOW -> colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.YELLOW, Landscape.MEADOW );
			case Landscape.SWAMP -> colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.CYAN, Landscape.SWAMP);
			case Landscape.MOUNTAIN -> colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.WHITE, Landscape.MOUNTAIN);
			case Landscape.RIVER -> colorTile(graphics, coordinate, widthSreen, heightScreen, marge, Color.BLUE, Landscape.RIVER);
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
		playerBoard.keySet()
		  .forEach(elements -> context
		      .renderFrame(graphics -> drawTile(graphics, elements, playerBoard.get(elements), widthScreen, heightScreen, marge)));
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
	private static void drawDraw(DrawSquare draw, ApplicationContext context, int marge, int widthScreen, int heightScreen) {
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
	private static void drawAnimalCard(AnimalCard card, ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
		context.renderFrame(graphics -> drawText(graphics, widthScreenInfo/ 2, heightScreenInfo / 50, card));
	}
	
	/**
	 * drawBox draws the boxes to answer when asked if an over population should be run
	 * @param graphics Graphics2D representing the current window
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @param sentence String representing the sentence to be printed
	 */
	private static void drawBox(Graphics2D graphics, int widthScreenInfo, int heightScreenInfo, String sentence) {
		graphics.setColor(Color.GRAY);
		graphics.fill(new Rectangle2D.Float(widthScreenInfo, heightScreenInfo, 65, 65));
		graphics.setColor(Color.WHITE);
		graphics.drawString(sentence, widthScreenInfo + 10, heightScreenInfo + 30);
	}
	
	/**
	 * drawOverPopulation draws the necessary tools for the player to interact in case of an overpopulation
	 * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 */
	public static int drawOverPopulation(DrawSquare draw ,ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
	  Objects.requireNonNull(context);
	  Objects.requireNonNull(draw);
	  context.renderFrame(graphics -> {
	    graphics.setColor(Color.BLACK);
	    graphics.fill(new Rectangle2D.Float(0, 0, widthScreenInfo, heightScreenInfo));
	  });
	  drawDraw(draw, context, 65, widthScreenInfo, heightScreenInfo);
		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			graphics.drawString("do you want to do the overPopulation", widthScreenInfo / 50 - 20, heightScreenInfo - 150);
		});
		context.renderFrame(graphics -> drawBox(graphics, widthScreenInfo / 50, heightScreenInfo - 100, "yes"));
		context.renderFrame(graphics -> drawBox(graphics, widthScreenInfo / 50 + 120, heightScreenInfo - 100, "no"));
		return GameControlerSquare.askOverPpulation(context, widthScreenInfo, heightScreenInfo);
	}
	
	/**
	 * drawMoves draws the available tile moves
	 * @param tileMoves Set<Coordinate> representing the available moves
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 */
	public static Coordinate drawMoves(Set<Coordinate> tileMoves, ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int marge) {
		tileMoves.forEach(elements -> context.renderFrame(graphics -> {
			graphics.setColor(Color.GRAY);
			graphics.fill(new Rectangle2D.Float(widthScreenInfo/2 + elements.x() * marge, heightScreenInfo/2 + elements.y() * marge, marge, marge));
			graphics.setColor(Color.BLACK);
      graphics.drawString("Pick a tile and wildlife token from the draw up on the left", widthScreenInfo / 2 - 100, heightScreenInfo / 50 + 80);
			graphics.setColor(Color.WHITE);
			graphics.drawString("Play your tile by clicking on one of the gray tile", widthScreenInfo / 2 - 100, heightScreenInfo / 50 + 80);
		}));
		return GameControlerSquare.askCoordinateTile(context, widthScreenInfo, heightScreenInfo, marge, tileMoves);
	}
	
	/**
	 * menu prints the menu to know if the player wants to play the terminal or graphic version of Cascadia
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @return int the player's choice
	 */
	public static int menu(ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
	  context.renderFrame(graphics -> {
	    graphics.setColor(Color.WHITE);
	    graphics.drawString("Does thou wish to play on the terminal or graphic version of Square Cascadia ?", widthScreenInfo / 2 - 100, heightScreenInfo / 2);
	  });
	  context.renderFrame(graphics -> drawBox(graphics, widthScreenInfo / 2 - 20, heightScreenInfo / 2 + 100, "terminal"));
	  context.renderFrame(graphics -> drawBox(graphics, widthScreenInfo / 2 + 120, heightScreenInfo / 2 + 100, "graphic"));
	  return GameControlerSquare.askGame(context, widthScreenInfo, heightScreenInfo);
	}
	
	/**
	 * drawHead draws the necessary for a player to play his turn
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @param player HashMap<Coordinate, TileSquare> representing the player board
	 * @param card AnimalCard representing the current card being used
	 * @param draw DrawSquare representing the draw
	 * @param currentPlayer int representing the current player
	 * @param marge int representing the size of a cell
	 * @return int representing the player's choice
	 */
	public static int drawHead(ApplicationContext context, int widthScreenInfo, int heightScreenInfo, HashMap<Coordinate, TileSquare> player, AnimalCard card, DrawSquare draw, int currentPlayer, int marge) {
	  Objects.requireNonNull(context);
	  Objects.requireNonNull(player);
	  Objects.requireNonNull(card);
	  Objects.requireNonNull(draw);
	  context.renderFrame(graphics -> {
      graphics.setColor(Color.BLACK);
      graphics.fill(new Rectangle2D.Float(0, 0, widthScreenInfo, heightScreenInfo));
      graphics.setColor(Color.WHITE);
      graphics.drawString("Pick a tile and wildlife token from the draw up on the left", widthScreenInfo / 2 - 100, heightScreenInfo / 50 + 80);
    });
	  drawPlayer(context, player, marge, widthScreenInfo, heightScreenInfo);
	  context.renderFrame(graphics -> graphics.drawString("Player : " + currentPlayer, widthScreenInfo / 2, heightScreenInfo / 50 + 50));
	  drawAnimalCard(card, context, widthScreenInfo, heightScreenInfo);
	  drawDraw(draw, context, marge, widthScreenInfo, heightScreenInfo);
	  return GameControlerSquare.askDraw(context, widthScreenInfo, heightScreenInfo, marge, draw);
	}
	
	/**
	 * drawWinner prints the winner of the game and the number of points
   * @param context ApplicationContext representing the application being run
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
	 * @param player1 Map<Coordinate, TileSquare> representing the first player
	 * @param player2 Map<Coordinate, TileSquare> representing the second player
	 * @param card AnimalCard representing the current card being used
	 */
	public static void drawWinner(ApplicationContext context, int widthScreen, int heightScreen ,Map<Coordinate, TileSquare> player1, Map<Coordinate, TileSquare> player2, AnimalCard card) {
	  Objects.requireNonNull(context);
	  Objects.requireNonNull(player1);
	  Objects.requireNonNull(player2);
	  Objects.requireNonNull(card);
	  var point = new CountPointSquare(player1, player2);
	  context.renderFrame(graphics -> {
	    graphics.setColor(Color.BLACK);
	    graphics.fill(new Rectangle2D.Float(0, 0, widthScreen, heightScreen));
	  });
	  if(point.winner(card) != 0) {
	    context.renderFrame(graphics -> {
	      graphics.setColor(Color.WHITE);
	      graphics.drawString("The winner is " + point.winner(card), widthScreen / 2 - 100, heightScreen / 2);
	    });
	  }
	  else {
      context.renderFrame(graphics -> {
        graphics.setColor(Color.WHITE);
        graphics.drawString("It is a draw with " + point.pointPlayer()[0] + "points", widthScreen / 2 - 100, heightScreen / 2);
      });
	  }
	}
	
	/**
	 * drawAskAnimalCard prints the choice between the family and intermediate version
   * @param context ApplicationContext representing the application being run
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
	 * @return AnimalCard the chosen card
	 */
	public static AnimalCard drawAskAnimalCard(ApplicationContext context, int widthScreen, int heightScreen) {
	  Objects.requireNonNull(context);
    context.renderFrame(graphics -> {
      graphics.setColor(Color.WHITE);
      graphics.drawString("Does thou wish to play with the intermediate card or the family card ?", widthScreen / 2 - 100, heightScreen / 2);
    });
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 2 - 20, heightScreen / 2 + 100, "family"));
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 2 + 120, heightScreen / 2 + 100, "intermediate"));
    return GameControlerSquare.askAnimalCard(context, widthScreen, heightScreen);
	}
	
	/**
	 * drawAskTileBegin prints the menu to randomly pick a beginning tile, randomly from the eyes of the player
   * @param context ApplicationContext representing the application being run
   * @param widthSreen int representing the width of the screen
   * @param heightScreen int representing the height of the screen
	 * @param forbidenNumber int[] representing the last player choice
	 * @return int the player's choice
	 */
	public static int drawAskTileBegin(ApplicationContext context, int widthScreen, int heightScreen, int[] forbidenNumber) {
	  Objects.requireNonNull(context);
    context.renderFrame(graphics -> {
      graphics.setColor(Color.BLACK);
      graphics.fill(new Rectangle2D.Float(0, 0, widthScreen, heightScreen));
    });
    context.renderFrame(graphics -> {
      graphics.setColor(Color.WHITE);
      graphics.drawString("Choose one of the boxes to randomly pick a beginning tile, don't pick the same box twice", widthScreen / 2 - 100, heightScreen / 2);
    });
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 4 - 20, heightScreen / 2 + 100, "1"));
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 4 + 120, heightScreen / 2 + 100, "2"));
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 4 + 260, heightScreen / 2 + 100, "3"));
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 4 + 400, heightScreen / 2 + 100, "4"));
    context.renderFrame(graphics -> drawBox(graphics, widthScreen / 4 + 540, heightScreen / 2 + 100, "5"));
    return GameControlerSquare.askTileBegin(context, widthScreen, heightScreen, forbidenNumber);
	}
	
	/**
	 * launchGame launches the game in terminal or graphic version
	 * @param context ApplicationContext representing the application being run
	 */
	public static void launchGame(ApplicationContext context) {
	  Objects.requireNonNull(context);
	  AlgoSquare game;
	  var width = context.getScreenInfo().width();
	  var height = context.getScreenInfo().height();
	  var gameMode = menu(context, width, height);
	  if(gameMode == 1) {
	    context.dispose();
	    game = AlgoSquare.initializedGame(false, width, height, null);
	    game.gameTerminal();
	  }
	  else {
	    context.renderFrame(graphics -> {
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle2D.Float(0, 0, width, height));
      });
	    game = AlgoSquare.initializedGame(true, width, height, context);
	    game.gameSquareGraphic(context, width, height);
	  }
	}
	
}
