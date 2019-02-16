import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CellFactory implements Serializable {
    private PlusMinusGame _plusMinus;
    private Quiz _quiz;
    private RockPaperScissors _rockPaperScissors;
    private int _nbReaction;

    public CellFactory() {
	List<Question> listQuestion = new ArrayList<Question>();
	listQuestion.add(new Question("1+1", "1", "2", "3", "4", "2"));
	listQuestion.add(new Question("2+2", "1", "2", "3", "4", "4"));
	listQuestion.add(new Question("5+3", "1", "2", "8", "4", "8"));
	listQuestion.add(new Question("2+4", "1", "2", "3", "6", "6"));

	_nbReaction = 0;
	_plusMinus = new PlusMinusGame("PlusMinus", new Key("Key1"), new Key("Key2"));
	_quiz = new Quiz("Quiz", new Key("Key2"), new Key("Key3"), listQuestion);
	_rockPaperScissors = new RockPaperScissors("RockPaperScissors", new Key("Key3"), new Key("Key4"));
    }

    public Cell getReactionCell() {
	switch (_nbReaction) {
	case 0:
	    _nbReaction += 1;
	    return (new Cell(new Reaction(_plusMinus)));

	case 1:
	    _nbReaction += 1;
	    return (new Cell(new Reaction(_quiz)));

	case 2:
	    _nbReaction = 0;
	    return (new Cell(new Reaction(_rockPaperScissors)));
	}
	return (new Cell(new Reaction(_plusMinus)));
    }

    public Cell getCell(char c) {
	switch (c) {
	case '#':
	    return (new Cell(new Wall()));
	case '!':
	    return (new Cell(new Key("Key1")));
	case ' ':
	    return (new Cell(new EmptyTangible()));
	case '/':
	    return (new Cell(new Katana("Katana", Util.damageKatana)));
	case '|':
	    return (new Cell(new Sword("Sword", Util.damageSword)));
	case '?':
	    return (this.getReactionCell());
	default:
	    break;
	}
	return (new Cell(new EmptyTangible()));
    }
}
