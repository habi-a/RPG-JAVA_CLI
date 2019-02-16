import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Menu implements State {
    public Menu() {}

    public void drawTitle() {
	System.out.println("\033[33m ____   ____   ____               ____   ____  ___ ___    ___");
	System.out.println("|    \\ |    \\ /    |             /    | /    ||   |   |  /  _]");
	System.out.println("|  D  )|  o  )   __|            |   __||  o  || _   _ | /  [_ ]");
	System.out.println("|    / |   _/|  |  |            |  |  ||     ||  \\_/  ||    _]");
	System.out.println("|    \\ |  |  |  |_ |            |  |_ ||  _  ||   |   ||   [_ ]");
	System.out.println("|  .  \\|  |  |     |            |     ||  |  ||   |   ||     |");
	System.out.println("|__|\\_||__|  |___,_|            |___,_||__|__||___|___||_____|\033[0m");
	System.out.println("\n");
    }

    public void drawIndications() {
	System.out.println("Welcome to the RPG Game");
	System.out.println("");
	System.out.println("1 => Arcade");
	System.out.println("2 => Multiplayer");
	System.out.println("3 => Quit");
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
		return StateType.ARCADE;

	    case "2":
		return StateType.MULTIPLAYER;

	    case "3":
		return StateType.QUIT;
	    }
	}
    }
}
