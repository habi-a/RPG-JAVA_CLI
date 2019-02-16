import java.io.Serializable;

public abstract class Fightable implements Serializable {
    protected HealthBar _healthBar;
    protected String _name;
    protected int _x;
    protected int _y;

    public Fightable() {}
    
    public Fightable(int x, int y, String name, HealthBar healthBar) {
	_x = x;
	_y = y;
	_name = name;
	_healthBar = healthBar;
    }

    public void setHealthBar(HealthBar healthBar) {
	_healthBar = healthBar;	    
    }
    
    public String getName() {
	return (_name);
    }
    
    public void setName(String name) {
	_name = name;
    }

    public HealthBar getHealthBar() {
	return (_healthBar);
    }

    public void setX(int x) {
	_x = x;
    }

    public void setY(int y) {
	_y = y;
    }

    public int getX() {
	return (_x);
    }

    public int getY() {
	return (_y);	    
    }

    public void setCoord(int x, int y) {
	_x = x;
	_y = y;
    }

    public void attack(Fightable playerToAttack, int damage)
    {
	if (damage >= 0) {
	    playerToAttack.getHealthBar().decreaseCurrentPoints(damage);
	    System.out.println("\033[36m" + this.getName() + "\033[0m inflicted \033[35m" + damage + "\033[0m of damage on \033[36m"
			       + playerToAttack.getName() + "\033[0m");
	}
    }

    public boolean isDead() {
	return (_healthBar.isDead());
    }
}
