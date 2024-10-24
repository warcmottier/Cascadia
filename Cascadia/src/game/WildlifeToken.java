package game;
import java.util.Objects;

public record WildlifeToken(String animal) {
	
	public WildlifeToken {
		Objects.requireNonNull(animal);
	}
	
	@Override
	public String toString() {
		var string = new String();
		switch(animal) {
		case "renard" -> string = "R";
		case "ours" -> string = "O";
		case "saumon" -> string = "S";
		case "buse" -> string = "B";
		case "wapiti" -> string = "W";
		}
		return string;
	}
}
