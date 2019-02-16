import java.io.Serializable;

public abstract class Bar implements Serializable
{
    protected String _name;
    protected int _currentPoints;
    protected int _maxPoint;

    public Bar() {}

    public Bar(String name, int currentPoints, int maxPoint)
    {
	_name = name;
	_currentPoints = currentPoints;
	_maxPoint = maxPoint;
    }

    public String getName()
    {
	return (_name);
    }

    public void setName(String name)
    {
	_name = name;
    }

    public int getCurrentPoints()
    {
	return (_currentPoints);
    }

    public void setCurrentPoints(int currentPoints)
    {
	_currentPoints = currentPoints;
    }

    public int getMaxPoint()
    {
	return (_maxPoint);
    }

    public void setMaxPoint(int maxPoint)
    {
	_maxPoint = maxPoint;
    }

    public abstract void increaseCurrentPoints(int point);
    public abstract void decreaseCurrentPoints(int point);
    public abstract void increaseMaxPoint(int point);
    public abstract void decreaseMaxPoint(int point);
}
