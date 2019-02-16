import java.io.Serializable;
import java.util.List;

public abstract class MiniGame implements Serializable {
    private String _name;
    private Key _keyCondition;
    private Key _keyReward;

    public MiniGame(String name, Key keyCondition, Key keyReward) {
        _name = name;
        _keyCondition = keyCondition;
        _keyReward = keyReward;
    }

    public String getName() {
        return (_name);
    }

    public Key getKeyCondition() {
        return (_keyCondition);
    }

    public Key getKeyReward() {
        return (_keyReward);
    }

    public abstract boolean play(List<Key> keys);
    public abstract boolean canPlay(List<Key> keys);
    public abstract Key reward();
}
