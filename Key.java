import java.io.Serializable;

public class Key implements Tangible, Serializable {
    private String _name;

    public Key(String name) {
        _name = name;
    }

    public String toString() {
	return (_name);
    }

    public String getName() {
        return (_name);
    }

    public void setName(String name) {
        _name = name;
    }

    @Override
    public char showAs()
    {
	return '!';
    }
}
