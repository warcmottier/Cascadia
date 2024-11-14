package Graphics;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class TestGraphic {

  private static void test(ApplicationContext context) {
    Rectangle2D.Float rectangle = new Rectangle2D.Float(0, 0, 500, 500);
    var screenInfo = context.getScreenInfo();
    var width = screenInfo.width();
    var height = screenInfo.height();
    System.out.println("Taille de mon bel écran (" + width + " x " + height + ")");
    context.renderFrame(graphics -> {
      graphics.setColor(Color.RED);
      graphics.fill(rectangle);
    });
    context.pollOrWaitEvent(2000);
  }
  
  public static void main(String[] args) {
    Application.run(Color.PINK, TestGraphic::test);
  }
}
