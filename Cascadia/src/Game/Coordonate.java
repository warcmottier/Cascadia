package Game;

import java.util.Objects;

public record Coordonate(int x, int y) {
	
	@Override
	public boolean equals(Object object) {
		Objects.requireNonNull(object);
		if(this == object) {
			return false;
		}
		Coordonate other = (Coordonate) object;
		return x == other.x && y == other.y; 
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
