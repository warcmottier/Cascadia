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
	
	/**
	 * the getter for the String of landscape field
	 * @return the content of the landscape field
	 */
	public String landscape() {
		return landscape;
	}
}
