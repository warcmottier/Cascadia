package game;
/**
 * this record contains an Object representing a simple two dimensions coordinates
 * x is an int representing the x coordinate
 * y is an int representing the y coordinate
 */
public record Coordinate(int x, int y) {
	
	@Override
	public final String toString() {
		return "(" + x + ", " + y + ")";
	}
}
