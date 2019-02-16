import java.util.ArrayList;
import java.util.List;

public class MiniGameRunner {
    private List<Key> _keysToPlay;

    public MiniGameRunner(List<Key> keys) {
        _keysToPlay = keys;
    }

    public Key playGame(MiniGame game) {
        if (game.play(_keysToPlay))
	    return (game.reward());
	return null;
    }
}
