package game;

/**
 * this enum represents every wildlife for Cascadia
 */
public enum WildlifeToken {
	ELK("elk"),
	BEAR("bear"),
	SALMON("salmon"),
	NOZZLE("nozzle"),
	FOX("fox");
	private final String wildlifeToken;
	
  WildlifeToken(String wildlifeToken) {
		this.wildlifeToken = wildlifeToken;
	}
	
	public String wildlifeToken() {
		return wildlifeToken;
	}
}
