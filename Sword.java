import java.io.Serializable;

public class Sword extends Weapon implements Serializable {
    public Sword(String name, int damagePoints) {
	super(name, damagePoints);
	_type = WeaponType.SWORD;
    }

    @Override
    public char showAs() {
	return '|';
    }
}
