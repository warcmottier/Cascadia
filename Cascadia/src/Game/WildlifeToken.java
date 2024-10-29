package Game;
import java.util.Objects;

public record WildlifeToken(String animal) {
	
	public WildlifeToken {
		Objects.requireNonNull(animal);
	}
	
	@Override
	public String toString() {
		var string = new String();
		switch(animal) {
		case "Renard" -> string = "R";
		case "Ours" -> string = "O";
		case "Saumon" -> string = "S";
		case "Buse" -> string = "B";
		case "Wapiti" -> string = "W";
		}
		return string;
	}
}
