package game;

public record Coordonate(int x, int y) {
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return false;
		}
		Coordonate other = (Coordonate) object;
		return x == other.x && y == other.y; 
	}
}
