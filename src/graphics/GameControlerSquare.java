package graphics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;
import game.Coordinate;
import game.DrawSquare;
import game.TileSquare;
import game.WildlifeToken;

public final class GameControlerSquare {
	
	private static int choiceOverPopulationPlayer(PointerEvent pe, int widthScreenInfo, int heightScreenInfo) {
		if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
			return -1;
		}
		var location = pe.location();
		if(location.x() > widthScreenInfo / 50 && location.x() < widthScreenInfo / 50  + 65 && location.y() > heightScreenInfo - 100 && location.y() < heightScreenInfo - 100 + 65) {
			return 1;
		}
		if(location.x() > widthScreenInfo / 50 + 120 && location.x() < widthScreenInfo / 50 + 185 && location.y() > heightScreenInfo - 100 && location.y() < heightScreenInfo - 100 + 65) {
			return 0;
		}
		return -1;
	}
	
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
	
	private static TileSquare choicePositionWildlifeTokenPlayer(PointerEvent pe, int widthScreenInfo, int heightScreenInfo, int marge, Map<Coordinate, TileSquare> playerBoard) {
		if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
			return null;
		}
		var location = pe.location();
		var chosenCoordinate = playerBoard.keySet().stream()
         .filter(elements -> location.x() > widthScreenInfo / 2 + elements.x() * marge
                 && location.x() < widthScreenInfo / 2 + elements.x() * marge + marge
                 && location.y() > heightScreenInfo / 2 + elements.y() * marge
                 && location.y() < heightScreenInfo / 2 + elements.y() * marge + marge)
         .findFirst();	
	  return chosenCoordinate.map(playerBoard::get).orElse(null);
	}
	
	public static TileSquare askPositionWildlifeToken(ApplicationContext context, int widthScreenInfo, int heightScreenInfo, int marge, Map<Coordinate, TileSquare> playerBoard) {
		var event = context.pollOrWaitEvent(10);
		TileSquare choicetile = null;
		for(; choicetile == null;) {
			for(; event == null; event = context.pollOrWaitEvent(10));
			choicetile = switch (event) {
				case KeyboardEvent ke -> null;
				case PointerEvent pe -> choicePositionWildlifeTokenPlayer(pe, widthScreenInfo, heightScreenInfo, marge, playerBoard);
				default ->throw new IllegalArgumentException("Unexpected value: " + event);
			};
			event = context.pollOrWaitEvent(10);
		}
		return choicetile;
	}
	
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
}
