package graphics;

import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

public final class GameControler {
	
	private static int coordinate(PointerEvent pe, int widthScreenInfo, int heightScreenInfo ) {
		if (pe.action() != PointerEvent.Action.POINTER_DOWN) {
			return -1;
		}
		var location = pe.location();
		if(location.x() > widthScreenInfo / 50 && location.x() < widthScreenInfo / 50  + 65 && location.y() < heightScreenInfo - 100 && location.y() > heightScreenInfo - 100 + 65) {
			return 1;
		}
		if(location.x() > widthScreenInfo / 50 + 120 && location.x() < widthScreenInfo / 50 + 185 && location.y() < heightScreenInfo - 100 && location.y() > heightScreenInfo - 100 + 65) {
			return 0;
		}
		return -1;
	}
	
	public static int askOverPpulation(ApplicationContext context, int widthScreenInfo, int heightScreenInfo) {
		var event = context.pollEvent();
		return switch (event) {
		  case KeyboardEvent ke -> -1;
		  case PointerEvent pe -> coordinate(pe, widthScreenInfo, heightScreenInfo);
		  default ->throw new IllegalArgumentException("Unexpected value: " + event);
		};
	}
}
