import java.io.Serializable;

public class Cell implements Serializable
{
    private Tangible _tangible;

    public Cell(Tangible tangible)
    {
        _tangible = tangible;
    }

    public void setTangible(Tangible tangible)
    {
        _tangible = tangible;
    }

    public boolean isEmpty()
    {
        return (_tangible instanceof EmptyTangible || _tangible == null);
    }

    public boolean isWalkable() {
	return (this.isEmpty() || !(_tangible instanceof Wall));
    }

    public boolean isTakable() {
	return (_tangible instanceof Key || _tangible instanceof Reaction || _tangible instanceof Weapon);
    }

    public Tangible getTangible() {
	return (_tangible);
    }

    public char showAs()
    {
        return (_tangible.showAs());
    }
}
