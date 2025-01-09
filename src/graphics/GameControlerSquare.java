package graphics;

import java.awt.Color;
import java.util.Map;
import java.util.Set;
import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.AnimalCard;
import game.Coordinate;
import game.DrawSquare;
import game.TileSquare;

/**
 * GameControlerSquare contains every functions needed to interact with the player in the graphic square version of Cascadia
 */
public final class GameControlerSquare {
	
  /**
   * choiceOverPopulationPlayer takes player input to determine if the player, when available, wish to run an overpopulation
   * @param pe PointerEvent representing the last event given by the player, a mouse action or a keyboard action
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @return int representing the player choice
   */
	private static int choiceOverPopulationPlayer(PointerEvent pe, int widthScreenInfo, int heightScreenInfo) {
		if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
			return -1;
		}
		var location = pe.location();
		if(location.x() > widthScreenInfo / 50 
		    && location.x() < widthScreenInfo / 50  + 65 
		    && location.y() > heightScreenInfo - 100 
		    && location.y() < heightScreenInfo - 100 + 65) {
			return 1;
		}
		if(location.x() > widthScreenInfo / 50 + 120 
		    && location.x() < widthScreenInfo / 50 + 185 
		    && location.y() > heightScreenInfo - 100 
		    && location.y() < heightScreenInfo - 100 + 65) {
			return 0;
		}
		return -1;
	}
	
	/**
	 * askOverPpulation takes player input and makes sure that it is correct, if not, it will wait for the next event until a correct event is given
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @return int representing the player choice
	 */
	public static int askOverPpulation(ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
		var event = context.pollOrWaitEvent(10);
		var hasChosenOverPopulation = -1;
		for(; hasChosenOverPopulation == -1;) {
			for(; event == null; event = context.pollOrWaitEvent(10));
			hasChosenOverPopulation = switch (event) {
			case KeyboardEvent ke -> -1;
			case PointerEvent pe -> choiceOverPopulationPlayer(pe, widthScreenInfo, heightScreenInfo);
			default ->throw new IllegalArgumentException("Unexpected value: " + event);
			};
			event = context.pollOrWaitEvent(10);
		}
		return hasChosenOverPopulation;
	}
	
	/**
	 * choicePositionWildlifeTokenPlayer takes player input to determine on which tile the player wish to put his wildlife token
   * @param pe PointerEvent representing the last event given by the player, a mouse action or a keyboard action
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
	 * @param marge int representing the size of the rectangle for a tile
	 * @param playerBoard Map<Coordinate, TileSquare> representing the board of a player
	 * @return TileSquare representing the player choice
	 */
	private static Coordinate choicePositionWildlifeTokenPlayer(PointerEvent pe, int widthScreenInfo, int heightScreenInfo, int marge, Map<Coordinate, TileSquare> playerBoard) {
		if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
			return null;
		}
		var location = pe.location();
		return playerBoard.keySet().stream()
         .filter(elements -> location.x() > widthScreenInfo / 2 + elements.x() * marge
                 && location.x() < widthScreenInfo / 2 + elements.x() * marge + marge
                 && location.y() > heightScreenInfo / 2 + elements.y() * marge
                 && location.y() < heightScreenInfo / 2 + elements.y() * marge + marge)
         .findFirst()
         .orElse(null);	
	}
	
	/**
	 * askPositionWildlifeToken takes player input and makes sure that it is correct, if not, it will wait for the next event until a correct event is given
	 * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
   * @param playerBoard Map<Coordinate, TileSquare> representing the board of a player
	 * @return TileSquare representing the player choice
	 */
	public static Coordinate askPositionWildlifeToken(ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int marge, Map<Coordinate, TileSquare> playerBoard, Set<Coordinate> movesWildlife) {
	  context.renderFrame(graphics -> {
	  	graphics.setColor(Color.BLACK);
			graphics.drawString("Play your tile by clicking on one of the gray tile", widthScreenInfo / 2 - 100, heightScreenInfo / 50 + 80);
      graphics.setColor(Color.WHITE);
      graphics.drawString("Play your Wildlife token by clicking on one of your tiles", widthScreenInfo / 2 - 100, heightScreenInfo / 50 + 80);
    });
	  var event = context.pollOrWaitEvent(10);
		Coordinate choicetile = null;
		for(; choicetile == null;) {
			for(; event == null; event = context.pollOrWaitEvent(10));
			choicetile = switch (event) {
				case KeyboardEvent ke -> null;
				case PointerEvent pe -> choicePositionWildlifeTokenPlayer(pe, widthScreenInfo, heightScreenInfo, marge, playerBoard);
				default ->throw new IllegalArgumentException("Unexpected value: " + event);
			};
			if(!movesWildlife.contains(choicetile)) {
				choicetile = null;
			}
			event = context.pollOrWaitEvent(10);
		}
		return choicetile;
	}
	
	/**
	 * choicePositionTilePlayer takes player input to determine on which tile the player wish to put his tile
   * @param pe PointerEvent representing the last event given by the player, a mouse action or a keyboard action
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 * @param movesTile Set<Coordinate> representing the available tile moves
	 * @return Coordinate representing the player choice
	 */
	private static Coordinate choicePositionTilePlayer(PointerEvent pe, int widthScreenInfo, int heightScreenInfo, int marge, Set<Coordinate> movesTile) {
    if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
        return null;
    }
    var location = pe.location();
    return movesTile.stream()
            .filter(elements -> location.x() > widthScreenInfo / 2 + elements.x() * marge
                    && location.x() < widthScreenInfo / 2 + elements.x() * marge + marge
                    && location.y() > heightScreenInfo / 2 + elements.y() * marge
                    && location.y() < heightScreenInfo / 2 + elements.y() * marge + marge)
            .findFirst()
            .orElse(null);
	}
	
	/**
	 * askCoordinateTile takes player input and makes sure that it is correct, if not, it will wait for the next event until a correct event is given
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 * @param movesTile Set<Coordinate> representing the available tile moves
	 * @return Coordinate representing the player choice
	 */
	public static Coordinate askCoordinateTile(ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int marge, Set<Coordinate> movesTile) {
		var event = context.pollOrWaitEvent(10);
		Coordinate choicetile = null;
		for(; choicetile == null;) {
			for(; event == null; event = context.pollOrWaitEvent(10));
			choicetile = switch (event) {
				case KeyboardEvent ke -> null;
				case PointerEvent pe -> choicePositionTilePlayer(pe, widthScreenInfo, heightScreenInfo, marge, movesTile);
				default ->throw new IllegalArgumentException("Unexpected value: " + event);
			};
			event = context.pollOrWaitEvent(10);
		}
		return choicetile;
	}
	
	/**
	 * choiceDrawPlayer takes player input to determine which tile and wildlife token the player wish to draw
   * @param pe PointerEvent representing the last event given by the player, a mouse action or a keyboard action
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 * @param draw DrawSquare representing the draw
	 * @return int representing the player choice
	 */
	private static int choiceDrawPlayer(PointerEvent pe, int widthScreenInfo, int heightScreenInfo, int marge, DrawSquare draw) {
		if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
			return -1;
		}
		var location = pe.location();
		var x = 0;
		var y = 0;
		for(int i = 0; i < 4; i++) {
			x= i - 3;
			if(location.x() > widthScreenInfo / 2 + x * marge 
					&& location.x() < widthScreenInfo / 2 + x * marge + marge 
					&& location.y() > heightScreenInfo / 2 + y * marge 
					&& location.y() < heightScreenInfo / 2 + y * marge + 2 *marge) {
				return i + 1;
			}
		}
		
		return -1;
	}
	
	/**
	 * askDraw takes player input and makes sure that it is correct, if not, it will wait for the next event until a correct event is given
   * @param context ApplicationContext representing the application being run
   * @param widthSreenInfo int representing the width of the screen
   * @param heightScreenInfo int representing the height of the screen
   * @param marge int representing the size of the rectangle for a tile
	 * @param draw DrawSquare representing the draw
   * @return int representing the player choice
	 */
	public static int askDraw(ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int marge, DrawSquare draw) {
		var event = context.pollOrWaitEvent(10);
		var choicetile = -1;
		for(; choicetile == -1;) {
			for(; event == null; event = context.pollOrWaitEvent(10));
			choicetile = switch (event) {
				case KeyboardEvent ke -> -1;
				case PointerEvent pe -> choiceDrawPlayer(pe, widthScreenInfo / 4, heightScreenInfo / 4, marge, draw);
				default ->throw new IllegalArgumentException("Unexpected value: " + event);
			};
			event = context.pollOrWaitEvent(10);
		}
		return choicetile;
	}
	
	public static int choiceGame(PointerEvent pe ,int widthScreenInfo, int heightScreenInfo) {
	   if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
	      return -1;
	    }
	    var location = pe.location();
	    if(location.x() > widthScreenInfo / 2 - 20 
	        && location.x() < widthScreenInfo / 2 + 45 
	        && location.y() > heightScreenInfo / 2 + 120 
	        && location.y() < heightScreenInfo /2 + 165) {
	      return 1;
	    }
	    if(location.x() > widthScreenInfo / 2 + 120 
          && location.x() < widthScreenInfo / 2 + 185 
          && location.y() > heightScreenInfo / 2 + 120 
          && location.y() < heightScreenInfo /2 + 165) {
	      return 0;
	    }
	    return -1;
	}
	
	public static int askGame(ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
	   var event = context.pollOrWaitEvent(10);
	    var choiceGameMode = -1;
	    for(; choiceGameMode == -1;) {
	      for(; event == null; event = context.pollOrWaitEvent(10));
	      choiceGameMode = switch (event) {
	        case KeyboardEvent ke -> -1;
	        case PointerEvent pe -> choiceGame(pe, widthScreenInfo, heightScreenInfo);
	        default ->throw new IllegalArgumentException("Unexpected value: " + event);
	      };
	      event = context.pollOrWaitEvent(10);
	    }
	    return choiceGameMode;
	}
	
	 public static AnimalCard choiceAnimalCard(PointerEvent pe ,int widthScreenInfo, int heightScreenInfo) {
     if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
        return null;
      }
      var location = pe.location();
      if(location.x() > widthScreenInfo / 2 - 20 
          && location.x() < widthScreenInfo / 2 + 45 
          && location.y() > heightScreenInfo / 2 + 120 
          && location.y() < heightScreenInfo /2 + 165) {
        return AnimalCard.FAMILY;
      }
      if(location.x() > widthScreenInfo / 2 + 120 
          && location.x() < widthScreenInfo / 2 + 185 
          && location.y() > heightScreenInfo / 2 + 120 
          && location.y() < heightScreenInfo /2 + 165) {
        return AnimalCard.INTERMEDIATE;
      }
      return null;
  }
	
	 public static AnimalCard askAnimalCard(ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
     var event = context.pollOrWaitEvent(10);
      AnimalCard choiceAnimalCard = null;
      for(; choiceAnimalCard == null;) {
        for(; event == null; event = context.pollOrWaitEvent(10));
        choiceAnimalCard = switch (event) {
          case KeyboardEvent ke -> null;
          case PointerEvent pe -> choiceAnimalCard(pe, widthScreenInfo, heightScreenInfo);
          default ->throw new IllegalArgumentException("Unexpected value: " + event);
        };
        event = context.pollOrWaitEvent(10);
      }
      return choiceAnimalCard;
  }
	
	 public static int choiceTileBegin(PointerEvent pe ,int widthScreenInfo, int heightScreenInfo, int[] forbidenNumber) {
     if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
       return forbidenNumber[0];
     }
     var location = pe.location();
     if(location.x() > widthScreenInfo / 4 - 20 && location.x() < widthScreenInfo / 4 + 45 && location.y() > heightScreenInfo / 2 + 120 && location.y() < heightScreenInfo /2 + 165) {
       return 1;
     }
     if(location.x() > widthScreenInfo / 4 + 120 && location.x() < widthScreenInfo / 4 + 185 && location.y() > heightScreenInfo / 2 + 120 && location.y() < heightScreenInfo /2 + 165) {
       return 2;
     }
     if(location.x() > widthScreenInfo / 4 + 260 && location.x() < widthScreenInfo / 4 + 325 && location.y() > heightScreenInfo / 2 + 120 && location.y() < heightScreenInfo /2 + 165) {
       return 3;
     }
     if(location.x() > widthScreenInfo / 4 + 400 && location.x() < widthScreenInfo / 4 + 465 && location.y() > heightScreenInfo / 2 + 120 && location.y() < heightScreenInfo /2 + 165) {
       return 4;
     }
     if(location.x() > widthScreenInfo / 4 + 540 && location.x() < widthScreenInfo / 4 + 605 && location.y() > heightScreenInfo / 2 + 120 && location.y() < heightScreenInfo /2 + 165) {
       return 5;
     }
     return forbidenNumber[0];
	 }
	 
	 public static int askTileBegin(ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int[] forbidenNumber) {
     var event = context.pollOrWaitEvent(10);
     var choiceTileBegin = forbidenNumber[0];
     for(; choiceTileBegin == forbidenNumber[0];) {
       for(; event == null; event = context.pollOrWaitEvent(10));
       choiceTileBegin = switch (event) {
         case KeyboardEvent ke -> forbidenNumber[0];
         case PointerEvent pe -> choiceTileBegin(pe, widthScreenInfo, heightScreenInfo, forbidenNumber);
         default ->throw new IllegalArgumentException("Unexpected value: " + event);
       };
       if(choiceTileBegin != forbidenNumber[0]) {
      	 forbidenNumber[0] = choiceTileBegin;
      	 break;
       }
       event = context.pollOrWaitEvent(10);
     }
     return choiceTileBegin;
	 }
	 
}
