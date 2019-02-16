public class HealthBar extends Bar
{
    private boolean _isDead;

    public HealthBar() {
	super();
    }
    
    public HealthBar(String name, int currentPoints, int maxPoint)
    {
	super(name, currentPoints, maxPoint);
	_isDead = false;
    }

    public void setIsDead(boolean isDead) {
	_isDead = isDead;
    }
    
    public boolean isDead() {
	return (_isDead);
    }

    public void increaseCurrentPoints(int point)
    {
	if (!_isDead) {
	    this.setCurrentPoints(this.getCurrentPoints() + point);
	    if (this.getCurrentPoints() > this.getMaxPoint())
		this.setCurrentPoints(this.getMaxPoint());
	}
    }
    
    public void decreaseCurrentPoints(int point)
    {
	if (!_isDead) {
	    this.setCurrentPoints(this.getCurrentPoints() - point);
	    if (this.getCurrentPoints() <= 0) {
		_isDead = true;
		this.setCurrentPoints(0);
	    }
	}
    }
    
    public void increaseMaxPoint(int point)
    {
	if (!_isDead)
	    this.setMaxPoint(this.getMaxPoint() + point);
    }
    
    public void decreaseMaxPoint(int point)
    {
	if (!_isDead)
	    this.setMaxPoint(this.getMaxPoint() - point);
    }

    public HealthBar clone() {
	HealthBar copy = new HealthBar();

	copy.setName(_name);
	copy.setIsDead(_isDead);
	copy.setCurrentPoints(_currentPoints);
	copy.setMaxPoint(_maxPoint);
	return copy;
    }
}
