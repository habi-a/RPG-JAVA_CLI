import java.io.Serializable;

public class Reaction implements Tangible, Serializable
{
    private MiniGame _game;

    public Reaction(MiniGame game) {
	_game = game;
    }

    public MiniGame getMiniGame() {
	return (_game);
    }

    @Override
    public char showAs() {
        return '?';
    }
}
