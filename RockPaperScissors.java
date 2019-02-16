import java.lang.NumberFormatException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class RockPaperScissors extends MiniGame {
    public RockPaperScissors(String name, Key keyCondition, Key keyReward) {
        super(name, keyCondition, keyReward);
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


    private enum MoveType {
	ROCK,
	PAPER,
	SCISSORS;

	public int compareMoves(MoveType otherMove) {
	    if (this == otherMove)
		return 0;
	    switch (this) {
	    case ROCK:
		return (otherMove == SCISSORS ? 1 : -1);
	    case PAPER:
		return (otherMove == ROCK ? 1 : -1);
	    case SCISSORS:
		return (otherMove == PAPER ? 1 : -1);

	    }
	    return 0;
	}
    }

    public MoveType getMove(int input) {

	if (input >= 1 && input <= 3) {
	    switch (input) {
	    case 1:
		return MoveType.ROCK;
	    case 2:
		return MoveType.PAPER;
	    case 3:
		return MoveType.SCISSORS;
	    }
	}
	return MoveType.ROCK;
    }

    public MoveType getMoveComputer() {
	MoveType[] moves = MoveType.values();
	Random random = new Random();
	int index = random.nextInt(moves.length);
	return moves[index];

    }

    public boolean play(List<Key> keys) {
	Util.clearScreen();

        if (!this.canPlay(keys)) {
            System.out.println("Key required");
            return false;
        }

	int moveInt = 0;
	int compareMoves;
	MoveType userMove;
	MoveType user2Move;
	String inputPlayer = null;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to the RockPaperScissors Game");
	System.out.println("The rock breaks the scissors, the paper covers the rock, the scissors cut the paper\n");

	while (true) {
	    System.out.println("1 => Rock");
	    System.out.println("2 => Paper");
	    System.out.println("3 => Scissors");

	    try {
		inputPlayer = reader.readLine();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    try {
		moveInt = Integer.parseInt(inputPlayer);
	    } catch (NumberFormatException e) {
		System.out.println("You lost.");
		return false;
	    }

	    userMove = this.getMove(moveInt);
	    user2Move = this.getMoveComputer();

	    compareMoves = userMove.compareMoves(user2Move);
	    switch (compareMoves) {
	    case 0:
		System.out.println("Tie!\n\n");
		break;
	    case 1:
		System.out.println(userMove + " beats " + user2Move + ". You won!");
		return true;
	    case -1:
		System.out.println(user2Move + " beats " + userMove + ". You lost.");
		return false;
	    }
	}
    }
}
