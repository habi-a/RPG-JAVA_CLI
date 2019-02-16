import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WinState implements State {
    public WinState() {}

    public void drawTitle() {
	System.out.println("\033[32m __       __  ______  __    __  __ ");
	System.out.println("/  |  _  /  |/      |/  \\  /  |/  |");
	System.out.println("$$ | / \\ $$ |$$$$$$/ $$  \\ $$ |$$ |");
	System.out.println("$$ |/$  \\$$ |  $$ |  $$$  \\$$ |$$ |");
	System.out.println("$$ /$$$  $$ |  $$ |  $$$$  $$ |$$ |");
	System.out.println("$$ $$/$$ $$ |  $$ |  $$ $$ $$ |$$/ ");
	System.out.println("$$$$/  $$$$ | _$$ |_ $$ |$$$$ | __ ");
	System.out.println("$$$/    $$$ |/ $$   |$$ | $$$ |/  |");
	System.out.println("$$/      $$/ $$$$$$/ $$/   $$/ $$/ \033[0m");

	System.out.println("\n\n");
    }

    public void drawIndications() {
	System.out.println("Congratulation!");
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
