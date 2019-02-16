import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Quiz extends MiniGame {
    private List<Question> _questions;

    public Quiz(String name, Key keyCondition, Key keyReward, List<Question> questions) {
        super(name, keyCondition, keyReward);
        _questions = questions;
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
        System.out.println("Welcome to the quiz");

        boolean failed = false;
	String inputPlayer = null;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < _questions.size(); i++) {
            System.out.println("Question " + Integer.toString(i + 1) + ": " + _questions.get(i).getQuestion());
            System.out.println("1) " + _questions.get(i).getResponse1());
            System.out.println("2) " + _questions.get(i).getResponse2());
            System.out.println("3) " + _questions.get(i).getResponse3());
            System.out.println("4) " + _questions.get(i).getResponse4());

	    try {
		inputPlayer = reader.readLine();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    if (inputPlayer == null) {
		System.out.println("Wrong");
		failed = true;
		break;
	    }

            if (inputPlayer.equals(_questions.get(i).getAnswer()))
                System.out.println("Correct");
            else {
                System.out.println("Wrong");
                failed = true;
                break;
            }
        }

        if (!failed)
            System.out.println("Win");
        return (!failed);
    }
}
