import Game.*;

public class Main {
	public static void main(String[] args) {
	  var terminal = false;
	  if(args.length == 1 && args[0].equals("-t")) {
	    terminal = true;
	  }
		AlgoSquare.algorythmSquare(terminal);
	}
}
