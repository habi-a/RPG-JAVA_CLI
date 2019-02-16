import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoseState implements State {
    public LoseState() {}

    public void drawTitle() {
	System.out.println("\033[31m   _       ___     ___     ___   ");
	System.out.println("  | |     / _ \\   / __|   | __|  ");
	System.out.println("  | |__  | (_) |  \\__ \\   | _|   ");
	System.out.println("  |____|  \\___/   |___/   |___|  ");
	System.out.println("_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \033[0m");
	System.out.println("\n\n");
    }

    public void drawIndications() {
	System.out.println("You lost...");
	System.out.println("");
	System.out.println("1 => Return to Menu");
	System.out.println("2 => Quit");
    }

    @Override
    public StateType run() {
	Util.clearScreen();
	String inputPlayer = null;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	while (true) {
	    this.drawTitle();
	    this.drawIndications();

	    try {
		inputPlayer = reader.readLine();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    Util.clearScreen();

	    if (inputPlayer == null)
		return StateType.QUIT;

	    switch (inputPlayer) {
	    case "1":
		return StateType.MENU;

	    case "2":
		return StateType.QUIT;

	    default:
		break;
	    }
	}
    }
}
