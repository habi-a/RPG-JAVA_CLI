import java.io.Serializable;

public class Wall implements Tangible, Serializable
{
    public Wall() {}

    @Override
    public char showAs() {
        return '#';
    }
}
