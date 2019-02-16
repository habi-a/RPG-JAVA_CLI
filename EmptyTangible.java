import java.io.Serializable;

public class EmptyTangible implements Tangible, Serializable
{
    public EmptyTangible() {}

    @Override
    public char showAs() {
        return ' ';
    }
}
