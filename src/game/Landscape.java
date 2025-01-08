package game;

/**
 * This enum represents the different landscape for Cascadia
 */
public enum Landscape {
	MEADOW("meadow"),
	RIVER("river"),
	SWAMP("swamp"),
	MOUNTAIN("mountain"),
	FOREST("forest");
	private final String landscape;

	Landscape(String landscape) {
		this.landscape = landscape;
	}
	
	public String landscape() {
		return landscape;
	}
}
