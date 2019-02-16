import java.io.Serializable;

public abstract class Weapon implements Tangible, Serializable {
    protected int _damagePoints;
    protected String _name;
    protected WeaponType _type;

    public Weapon() {}

    public Weapon(String name, int damagePoints) {
	_name = name;
	_damagePoints = damagePoints;
	_type = WeaponType.KATANA;
    }

    public String toString() {
	return (_name);
    }

    public int getDamagePoints() {
	return (_damagePoints);
    }

    public void setDamagePoints(int damagePoints) {
	_damagePoints = damagePoints;
    }

    public String getName() {
	return (_name);
    }

    public void setName(String name) {
	_name = name;
    }

    public WeaponType getType() {
	return (_type);
    }

    public void setType(WeaponType type) {
	_type = type;
    }

    @Override
    public abstract char showAs();
}
