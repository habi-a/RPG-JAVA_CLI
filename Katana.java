import java.io.Serializable;

public class Katana extends Weapon implements Serializable {
    public Katana(String name, int damagePoints) {
	super(name, damagePoints);
	_type = WeaponType.KATANA;
    }

    @Override
    public char showAs() {
	return '/';
    }
}
