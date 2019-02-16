import java.util.Random;
import java.lang.NumberFormatException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlusMinusGame extends MiniGame {
    private int _findValue;

    public PlusMinusGame(String name, Key keyCondition, Key keyReward) {
        super(name, keyCondition, keyReward);
	Random randomNum = new Random();
        _findValue = 1 + randomNum.nextInt(100);
    }

    public Key reward() {
        return (this.getKeyReward());
    }

    public boolean isGoodKey(Key k) {
        if (getKeyCondition() == null)
            return true;

        if (k != null)
            if (k.getName().equals(getKeyCondition().getName()))
                return true;
        return false;
    }

    public boolean canPlay(List<Key> keys) {
	for (Key x : keys)
	    if (this.isGoodKey(x))
		return true;
	return false;
    }

    public boolean play(List<Key> keys) {
	Util.clearScreen();

        if (!this.canPlay(keys)) {
            System.out.println("Key required");
            return false;
        }

	int inputPlayerInt = 0;
	String inputPlayer = null;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean won = false;

        System.out.println("Welcome to the " + this.getName()+ " game!");
	System.out.println("Find the good number between 1 and 100\n");

        for (int i = 0; i < 10; i++) {
	    try {
		System.out.println("find the number: ");
		inputPlayer = reader.readLine();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    try {
		inputPlayerInt = Integer.parseInt(inputPlayer);
	    } catch (NumberFormatException e) {
		System.out.println("Bad Format Input (Tentative remaining attempt: " + Integer.toString(9 - i) + ")");
		continue;
	    }

            if (_findValue < Integer.parseInt(inputPlayer))
                System.out.println("Lower (Tentative remaining attempt: " + Integer.toString(9 - i) + ")");
            else if (_findValue > Integer.parseInt(inputPlayer))
                System.out.println("Greater (Tentative remaining attempt: " + Integer.toString(9 - i) + ")");
            else if (_findValue == Integer.parseInt(inputPlayer)) {
                System.out.println("Win  (Tentative remaining attempt: " + Integer.toString(9 - i) + ")");
                won = true;
		break;
            }
	    else
		break;
        }

	if (!won)
	    System.out.println("Lose");
	return won;
    }
}
