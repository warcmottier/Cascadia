import java.awt.Color;
import com.github.forax.zen.Application;
import graphics.ViewGameSquare;


public class Main {
	public static void main(String[] args) {
	  Application.run(Color.BLACK, context -> ViewGameSquare.launchGame(context));
	}
}
