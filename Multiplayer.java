import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Multiplayer implements State {
    public Multiplayer() {}

    public void drawTitle() {
	System.out.println("Multiplayer mode:\n");
    }

    public void drawIndications() {
	System.out.println("1 => Create Server");
	System.out.println("2 => Join Server");
	System.out.println("3 => Return to Menu");
	System.out.println("4 => Quit");
    }

    @Override
    public StateType run() {
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
		return StateType.GAME_SERVER;

	    case "2":
		return StateType.GAME_CLIENT;

	    case "3":
		return StateType.MENU;

	    case "4":
		return StateType.QUIT;

	    default:
		break;
	    }
	}
    }
}
